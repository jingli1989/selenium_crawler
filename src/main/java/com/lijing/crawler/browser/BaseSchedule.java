package com.lijing.crawler.browser;

import com.lijing.crawler.enums.BrowserTypeEnum;

/**
 * @description 
 * @author yyj
 * @date 2016年10月17日 上午10:42:36
 * @version V1.0
 */
public interface BaseSchedule {
	
	/**
	 * 获取并返回队列头部浏览器   如果队列为空，则阻塞
	 * @description 
	 * @author yyj
	 * @create 2016年10月17日 上午10:57:42
	 * @return
	 */
	BrowserTypeEnum consumeWebBrowser();
	
	/**
	 * 添加一个元素浏览器   如果队列满，则阻塞
	 * @description 
	 * @author yyj
	 * @create 2016年10月17日 上午10:57:50
	 * @return
	 */
	void produceWebBrowser(BrowserTypeEnum browserTypeEnum);

}
