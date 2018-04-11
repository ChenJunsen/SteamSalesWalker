package com.cjs.mycomputer.walker;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cjs.mycomputer.api.GameInfo;
import com.cjs.mycomputer.tools.Log;
import com.cjs.mycomputer.tools.TimeKit;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class JsoupHelper {
	
	static final String TAG="JsoupHelper";
	/**
	 * 解析:https://steamdb.info/sales/
	 * @param page
	 * @return
	 */
	public static List<GameInfo> getParsedSteamGameinfoList(HtmlPage page){
		if(page==null){
			Log.e(TAG, "getParsedSteamGameinfoList->page是空的");
			return null;
		}
		String pageXML=page.asXml();
		Document doc=Jsoup.parse(pageXML);
		List<GameInfo> gameList=parseDocument(doc);	
		page.getWebClient().close();
		return gameList;
	}
	
	/**
	 * 考虑到折扣期间数据太大，所以才写了这个分页的方法<br>
	 * 默认数据为50一页，折扣期间往往数据大于一万条，这里选择最大的250条为分页容量<br>
	 * <pre>
	 * 实现的原理概述:
	 * 1.使用JSoup找到页面的分页区域
	 * 		分页脚标的基本元素是个li,该元素有三种状态：可点击（默认），不可点击(class属性disable),已经激活(class属性active)
	 * 2.目标就是不断地去循环点击最后一个next元素，直到其class属性为disable
	 * </pre>
	 * @return
	 */
	public static List<GameInfo> getParsedSteamGameinfoListWithPagination(HtmlPage page){
		if(page==null){
			Log.e(TAG, "getParsedSteamGameinfoList->page是空的");
			return null;
		}
		String pageXML=page.asXml();
		Document doc=Jsoup.parse(pageXML);
		List<GameInfo> gameList=parseDocument(doc);	
//		page.getWebClient().close();
		return gameList;
	}
	
	/*private static boolean hasNext(HtmlPage page){
		
	}*/
	
	private static List<GameInfo> parseDocument(Document doc){
		List<GameInfo> gameList=new ArrayList<GameInfo>();
		if(doc==null){
			Log.e(TAG, "getParsedSteamGameinfoList->doc解析是空的");
			return null;
		}
		Element table=doc.getElementById("DataTables_Table_0");
		if(table==null){
			Log.e(TAG, "getParsedSteamGameinfoList->table解析是空的");
			return null;
		}
		Element tbody=table.select("tbody").first();
		if(tbody==null){
			Log.e(TAG, "getParsedSteamGameinfoList->tbody解析是空的");
			return null;
		}
		Elements trs=tbody.select("tr");
		if(trs==null){
			Log.e(TAG, "getParsedSteamGameinfoList->trs解析是空的");
			return null;
		}
		Log.d(TAG, "getParsedSteamGameinfoList->总数据量:"+trs.size());
		for(Element tr:trs){
			GameInfo game=new GameInfo();
			try {
				//通过页面分析，appId有data-appid和data-subid两种
				String appId = tr.attr("data-appid");
				if (appId == null || appId.trim().equals("")) {
					appId = tr.attr("data-subid");
				}
				game.setAppId(appId);
				
				Elements tds = tr.select("td");
				
				//商店链接
				Element tdStore = tds.get(0);
				String storeLink = tdStore.select("a").first().attr("href");
				game.setStoreLink(storeLink);
				
				//logo
				Element tdLogo=tds.get(1);
				String detailLink=tdLogo.select("a").first().attr("href");
				String imgLink=tdLogo.select("a > img").first().attr("src");
				game.setDetailLink(detailLink);
				game.setImgUrl(imgLink);
				
				//标题
				Element tdTitle = tds.get(2);//第三个td包含了标题
				String title = tdTitle.select("a").first().text();
				game.setName(title);
				Elements subinfoSpans=tdTitle.select("span").first().select("span");
				String dealsDesc="";
				int spanSize=subinfoSpans.size();
				if(spanSize>0){
					for(int i=0;i<spanSize;i++){
						Element span=subinfoSpans.get(i);
						dealsDesc+=span.text(); 
						if(i!=spanSize-1){
							dealsDesc+="|";
						}
					}
				}
				game.setDealsDesc(dealsDesc);
				
				//折扣
				Element tdDisCount=tds.get(3);
				String discountStr=tdDisCount.attr("data-sort");
				float discount=-1;
				try {
					discount = Float.parseFloat(discountStr);
				} catch (Exception e) {
					Log.e(TAG, "getParsedSteamGameinfoList->折扣转换出错,discountStr="+discountStr);
					e.printStackTrace();
				}
				game.setDiscount(discount);
				
				//折扣后的价格
				Element tdPrice=tds.get(4);
				String priceStr=tdPrice.attr("data-sort");
				float price=-1;
				try {
					price = Float.parseFloat(priceStr);
				} catch (Exception e) {
					Log.e(TAG, "getParsedSteamGameinfoList->价格转换出错,priceStr="+priceStr);
					e.printStackTrace();
				}
				game.setCurrentPrice(price/100);
				
				//好评率
				Element tdRating=tds.get(5);
				String ratingStr=tdRating.attr("data-sort");
				float rating=-1;
				try {
					rating = Float.parseFloat(ratingStr);
				} catch (Exception e) {
					Log.e(TAG, "getParsedSteamGameinfoList->好评率转换出错,ratingStr="+ratingStr);
					e.printStackTrace();
				}
				game.setRating(rating);
				
				game.setDealsEndsInUTC(getUTCTimeStr(tds.get(6)));
				game.setDealsEndsIn(getUTCTimestamp(tds.get(6)));
				
				game.setDealsStartAtUTC(getUTCTimeStr(tds.get(7)));
				game.setDealsStartAt(getUTCTimestamp(tds.get(7)));
				
				game.setReleaseTimeUTC(getUTCTimeStr(tds.get(8)));
				game.setReleaseTime(getUTCTimestamp(tds.get(8)));
			} catch (Exception e) {
				Log.e(TAG, "getParsedSteamGameinfoList->解析tr出错:"+e.getMessage());
				e.printStackTrace();
			}
			Log.d(TAG, "getParsedSteamGameinfoList->game:"+game.toString());
			gameList.add(game);
		}
		return gameList;
	}
	
	private static String getUTCTimeStr(Element td){
		String str=td.attr("title");
		if(str==null || "".equals(str.trim())){
			Log.e(TAG, "getUTCTimeStr->解析UTC时间出错,td:"+td.outerHtml());
		}
		return str;
	}
	
	private static long getUTCTimestamp(Element td){
		return TimeKit.getLocalCountryTimeStamp(getUTCTimeStr(td), TimeKit.FORMAT_UTC_XXX);
	}
}
