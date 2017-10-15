package com.lijing.crawler.browser;

import com.lijing.crawler.enums.BrowserTypeEnum;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;


/**
 * Created by yyj on 2016/10/18.
 */
@Component
public class WebDriverSchedule {
    private static Logger logger = LoggerFactory.getLogger(WebDriverSchedule.class);
    private static DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
    
    @Autowired
    private CommonSchedule s;
    
    private static String webdriverIEDriver = "/webdriver/IEDriverServer.exe";

    static{
        //加载IE浏览器
        try {
            System.getProperties().setProperty("webdriver.ie.driver", webdriverIEDriver);
            InternetExplorerDriverService ieService = new InternetExplorerDriverService.Builder()
                    .usingDriverExecutable(new File(webdriverIEDriver))
                    .usingAnyFreePort().build();
            ieService.start();
        } catch (Exception e) {
            logger.info("==>初始IE浏览器发生错误",e);
        }
    }

    /**
     * 分配浏览器
     * @return 浏览器driver
     */
    public WebDriver allotWebDriver(){
        BrowserTypeEnum wbName = s.consumeWebBrowser();
        logger.info("==>系统正在分配[{}]浏览器", wbName);
        switch(wbName){
            case CHROME:{
                return getChrome();
            }
            case IE:{
                return getIe();
            }
            default:{
                return null;
            }
        }
    }

    /**
     * 用完后归还WebDriver
     * @description
     * @author yyj
     * @create 2016年10月17日 上午11:08:35
     * @param bankCode
     * @param wb
     */
    public void recoverWebDriver(String bankCode, WebDriver wb){
    	if(null != wb){
    		logger.info("==>系统正在归还[{}]银行浏览器", bankCode);
    		wb.close();
    		wb.quit();
    		s.produceWebBrowser(BrowserTypeEnum.IE);
    	}
    }

    public static WebDriver getChrome(){
//        ChromeOptions options = new ChromeOptions();
    	WebDriver webDriver = new ChromeDriver();
//    	webDriver.manage().window().maximize();
    	return webDriver;
    }

    public static WebDriver getIe(){
        DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
        caps.setCapability("ignoreProtectedModeSettings", true); 
        caps.setCapability("ignoreZoomSetting", true); 
        caps.setCapability("javascript.enabled", true); 
        WebDriver webDriver = new InternetExplorerDriver(caps);
//        webDriver.manage().window().maximize();
        return webDriver;
    }

    public static WebDriver getFirefox(){
        System.setProperty("webdriver.firefox.bin", "D:\\soft\\Mozilla Firefox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver", "/webdriver/geckodriver.exe");
        WebDriver webDriver = new FirefoxDriver();
        return webDriver;
    }
}
