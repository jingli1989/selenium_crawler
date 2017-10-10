package com.lijing.crawler.utils;

import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Cookie;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * cookie操作工具
 */
public class CookieUtils {

	/**
	 * 将selenium cookies替换到gargoyle的cookies上
	 * @param webClient 目标Client
	 * @param cookies 源Cookies
	 * @param defaultDomain 默认Domain
	 * @description
	 * @author yyj
	 * @create 2016年10月12日 下午3:27:00
	 */
	public static void copyCookies(WebClient webClient, Set<Cookie> cookies, String defaultDomain){
		webClient.getCookieManager().clearCookies();
		com.gargoylesoftware.htmlunit.util.Cookie c;
		for (Cookie ckie : cookies) {
			if(null != ckie.getDomain() && StringUtils.isNotEmpty(ckie.getDomain())){
				c = new com.gargoylesoftware.htmlunit.util.Cookie(ckie.getDomain(),
						ckie.getName(),ckie.getValue(),
						ckie.getPath(),ckie.getExpiry(),
						ckie.isSecure(),ckie.isHttpOnly());
			}else{
				c = new com.gargoylesoftware.htmlunit.util.Cookie(defaultDomain,
						ckie.getName(),ckie.getValue(),
						ckie.getPath(),ckie.getExpiry(),
						ckie.isSecure(),ckie.isHttpOnly());
			}
			webClient.getCookieManager().addCookie(c);
		}
	}

	/**
	 * 将selenium cookies替换到gargoyle的cookies上
	 * @param cookies 源Cookies
	 * @description
	 * @author heliang
	 * @create 2016年10月12日 下午3:27:00
	 */
	public static Map<String, String> copyCookiesToHeader(Set<com.gargoylesoftware.htmlunit.util.Cookie> cookies){
		Map<String, String> header = new HashMap<>();
		String cookiesVal = "";
		for (com.gargoylesoftware.htmlunit.util.Cookie ckie : cookies) {
			cookiesVal = cookiesVal +";"+ckie.getName()+"="+ckie.getValue();
		}
		cookiesVal = cookiesVal.substring(1,cookiesVal.length());
		header.put("Cookie", cookiesVal);
		return header;
	}
}