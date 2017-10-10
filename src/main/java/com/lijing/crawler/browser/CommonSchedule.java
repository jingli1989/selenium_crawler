package com.lijing.crawler.browser;

import com.lijing.crawler.enums.BrowserTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;


/**
 * 浏览器分配
 */
@Component
public class CommonSchedule implements BaseSchedule{
	
    private static Logger logger = LoggerFactory.getLogger(CommonSchedule.class);
    private LinkedBlockingQueue<BrowserTypeEnum> wdQueeu = new LinkedBlockingQueue<>(1);
//
//	{
//		wdQueeu.offer(Constants.WEB_BROWSER_IE);
//	}

	@Override
	public BrowserTypeEnum consumeWebBrowser() {
		//获取并返回队列头部浏览器   如果队列为空，则阻塞
		try {
			return wdQueeu.take();
		} catch (InterruptedException e) {
			logger.info("==>分配浏览器失败.", e);
		}
		return null;
	}

	@Override
	public void produceWebBrowser(BrowserTypeEnum browserTypeEnum) {
		//添加一个元素浏览器   如果队列满，则阻塞
		try {
			wdQueeu.put(browserTypeEnum);
		} catch (InterruptedException e) {
			logger.info("==>恢复浏览器失败.", e);
		}
	}
}
