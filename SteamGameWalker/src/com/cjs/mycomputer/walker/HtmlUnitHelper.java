package com.cjs.mycomputer.walker;

import java.io.IOException;
import java.net.MalformedURLException;

import com.cjs.mycomputer.api.Api;
import com.cjs.mycomputer.tools.Log;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;

public class HtmlUnitHelper {
	static final long WAIT_TIME_FOR_JS=20000;
	static final String TAG="HtmlUnitHelper";
	/**
	 * 获取一个指定内核的浏览器模型
	 * @param browserVersion 浏览器的内核，{@link BrowserVersion}
	 * @return
	 */
	public static WebClient generateHtmlUnitWebClient(BrowserVersion browserVersion){
		//1.创建对象
		WebClient webClient=new WebClient(browserVersion);
		
		//2.设置参数
		//启动js
		webClient.getOptions().setJavaScriptEnabled(true);
		//关闭css渲染
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setDownloadImages(false);
		//启动重定向
		webClient.getOptions().setRedirectEnabled(true);
		//启动cookie管理
		webClient.setCookieManager(new CookieManager());
		//启动ajax代理
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		//js运行时错误，是否抛出异常
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setPrintContentOnFailingStatusCode(false);  
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);   

		//3.等待js渲染执行 waitime等待时间(ms)
		webClient.waitForBackgroundJavaScript(WAIT_TIME_FOR_JS);
		return webClient;
	}
	
	/**
	 * 获取一个Chrome内核的浏览器模型
	 * @return
	 */
	public static WebClient generateHtmlUnitWebClient(){
		return generateHtmlUnitWebClient(BrowserVersion.CHROME);
	}
	
	public static HtmlPage getHtmlPage(WebClient webClient,String url){
		Log.d(TAG, "getHtmlPage->webclient="+webClient+" url="+url);
		HtmlPage page=null;
		try {
			page=webClient.getPage(url);
		} catch (FailingHttpStatusCodeException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (MalformedURLException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		Log.d(TAG, "getHtmlPage->page="+page);
		return page;
	}
	
	/**
	 * 获取折扣的页面数据(该页面截至2017-11-28时，默认是以50为分页参数的)
	 * @return 模拟点击页面的<select>,选择all,来获取所有数据
	 */
	public static HtmlPage getHtmlPageForSteamDeals(){
		HtmlPage basePage=getHtmlPage(generateHtmlUnitWebClient(), Api.getSteamSalesUrl(70, 0, Api.CURRENCY.CHINESE_YUAN_RENMINGBI));
		HtmlSelect select=(HtmlSelect) basePage.getElementsByName("DataTables_Table_0_length").get(0);
		Log.d(TAG, "getHtmlPageForSteamDeals->select:"+select.asXml());
//		HtmlOption option=select.getOptionByValue("-1");//-1表示ALL
		HtmlOption option=select.getOptionByText("All");//二者的实现是一样的
		Log.d(TAG, "getHtmlPageForSteamDeals->option:"+option.asXml());
		HtmlPage allPage=null;
		try {
			allPage=option.click();
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		Log.d(TAG, "getHtmlPageForSteamDeals->allPage:"+allPage);
		return allPage;
	}
	
	
	/**
	 * 设置页面分页容量为250(因为最大可用容量就是250)
	 * @return
	 */
	public static HtmlPage getHtmlPageForSteamDealsWithPagination(){
		HtmlPage basePage=getHtmlPage(generateHtmlUnitWebClient(), Api.getSteamSalesUrl(50, 50, Api.CURRENCY.CHINESE_YUAN_RENMINGBI));
		HtmlSelect select=(HtmlSelect) basePage.getElementsByName("DataTables_Table_0_length").get(0);
		Log.d(TAG, "getHtmlPageForSteamDeals->select:"+select.asXml());
		HtmlOption option=select.getOptionByText("All");
		Log.d(TAG, "getHtmlPageForSteamDeals->option:"+option.asXml());
		HtmlPage paginationPage=null;
//		HtmlPage paginationPage=basePage;
		try {
			paginationPage=option.click();
			HtmlElement ele=(HtmlElement) paginationPage.getElementById("DataTables_Table_0_next");
			String classAttr=ele.getAttribute("class");
			Log.d(TAG, "getHtmlPageForSteamDeals->table_next_class:"+classAttr);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		Log.d(TAG, "getHtmlPageForSteamDeals->paginationPage:"+paginationPage);
		return paginationPage;
	}
}
