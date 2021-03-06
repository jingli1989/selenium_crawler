package com.lijing.crawler.service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.lijing.crawler.browser.WebDriverSchedule;
import com.lijing.crawler.utils.CookieUtils;
import com.lijing.crawler.virtual.utils.VirtualKeyBoard;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 登录服务
 * Created by lijing on 2017/10/10 0010.
 */
@Slf4j
@Service
public class LoginService {

    private WebDriverSchedule schedule = new WebDriverSchedule();

    /** 验证码打码服务 */
    @Autowired
    private CodeCheckService codeCheckService;

    public void login(String username,String password){
//        WebDriver webDriver = schedule.allotWebDriver();
        WebDriver webDriver = WebDriverSchedule.getFirefox();
        codeCheckService = new CodeCheckService();
        try {
            // 1.开始登陆
            log.info("==>0.[{}]用户正常认证开始.....");

            log.info("==>0.1调用浏览器登陆开始.....");
            webDriver.get("https://www.bestpay.com.cn/index");
//            Thread.sleep(10000L);
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebElement phoneNo = webDriver.findElement(By.id("phoneNo"));
            phoneNo.click();
            phoneNo.sendKeys(username);


//            Thread.sleep(1000L);


            WebElement codeImg = webDriver.findElement(By.id( "codeImg"));
            String imageStr = codeImg.getAttribute("src");
            log.info("图片地址:{}",imageStr);

            // Get entire page screenshot
            File screenshot = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshot);

            Point point = codeImg.getLocation();

            int eleWidth = codeImg.getSize().getWidth();
            int eleHeight = codeImg.getSize().getHeight();

            BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
                    eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", screenshot);

            String verifyCode = codeCheckService.getVerifyCode(CodeCheckService.getBase64(screenshot));
//            String verifyCode = "4567";

            Thread.sleep(1000L);
            WebElement codeEle = webDriver.findElement(By.id("codeVcode"));
//            codeEle.click();

            codeEle.sendKeys(verifyCode);
//                        ((JavascriptExecutor)webDriver).executeScript("document.getElementById(\"codeCheckFlag\").value ='true';");
            Thread.sleep(2000L);
            WebElement passwordEle = webDriver.findElement(By.id("_ocx_password6"));
            passwordEle.click();
////            passwordEle.sendKeys(password);
////            SendKeyUtils.sendPassword(password);
            VirtualKeyBoard.keyPress(password);
//            passwordEle.click();
            Thread.sleep(1000L);
            WebElement loginBtn = webDriver.findElement(By.id("telLoginButtn"));

            loginBtn.click();

            log.info("==>0.1调用浏览器登陆结束.");
//            WebClient webClient = initWebClient(null);
            log.info("==>0.2获取Cookie开始.....");
//            Set<Cookie> cookies = webDriver.manage().getCookies();
//            CookieUtils.copyCookies(webClient, cookies, ".abchina.com");
            log.info("==>0.2获取Cookie结束.....");

        } catch (Exception e) {
            log.error("==>0.[{}]用户正常认证出现异常:[{}]",  e);
        } finally {
            schedule.recoverWebDriver("1234", webDriver);
        }
    }

    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        System.setProperty("webdriver.chrome.driver", "D:/webdriver/chromedriver.exe");
        loginService.login("15000425356","lijing892140");

    }

    /**
     * 创建默认的webclient
     * <b>如果需要特殊处理,需要重写些方法</b>
     * @return
     */
    private WebClient initWebClient(WebClient webClient) {
        if (null == webClient) {
            webClient = new WebClient(BrowserVersion.CHROME);
        }
        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setAppletEnabled(false);
        webClient.getOptions().setCssEnabled(false);// 禁用css支持
        webClient.getOptions().setThrowExceptionOnScriptError(false);// js运行错误时，是否抛出异常
        webClient.getOptions().setJavaScriptEnabled(false); //禁用JS
        webClient.getOptions().setTimeout(120000);
//        if (enableProxy) {
//            if (StringUtils.isNotBlank(proxyConfig.getProxyHost())) {
//                webClient.getOptions().setProxyConfig(proxyConfig);
//            }
//        }
        return webClient;
    }

}
