package com.cjs.mycomputer.api;

/**
 * 请求信息的api
 * 
 * @author chenjunsen
 * @email chenjunsen@outlook.com
 * 创建于:2017年11月28日下午3:11:28
 */
public class Api {
	/**
	 * 请求链接<br>
	 * 如果遇到像夏季促销，秋季促销之类的大型促销活动，由于页面数据太多，会导致java heap oom
	 * 
	 * @author chenjunsen
	 * @email chenjunsen@outlook.com
	 * 创建于:2017年11月28日下午5:06:08
	 */
	public static class URL{
		/**
		 * Steam折扣信息请求的根地址,然后获取到的图片链接,详情链接都要拼上这个根地址
		 */
		public static final String STEAM_SALES_BASE="https://steamdb.info";
		
		/**Steam价格走向请求根地址*/
		public static final String STEAM_PRICE_TRACING_BASE="https://steamdb.info/pricechanges/";
	}
	
	/**
	 * 币种
	 * 
	 * @author chenjunsen
	 * @email chenjunsen@outlook.com
	 * 创建于:2017年11月28日下午5:06:23
	 */
	public static class CURRENCY{
		/**人民币*/
		public static final String CHINESE_YUAN_RENMINGBI="cn";
		/**美元*/
		public static final String USA_DOLLARS="us";
		/**日元*/
		public static final String JAPANESE_YEN="jp";
		/**欧元*/
		public static final String EURO="eu";
	}
	
	/**
	 * 获取指定参数的Steam打折信息请求链接
	 * @param minDiscount 最低折扣，例如传入60表示最低是6折起(包含6折)
	 * @param minRating 好评率,例如传入50，表示有50%的购买者推荐这款产品
	 * @param currency 币种，参考{@link CURRENCY}
	 * @return
	 */
	public static String getSteamSalesUrl(int minDiscount,int minRating,String currency){
		String url=URL.STEAM_SALES_BASE;
		url+="/sales?min_discount="+minDiscount+"&min_rating="+minRating+"&cc="+currency;
		return url;
	}
}
