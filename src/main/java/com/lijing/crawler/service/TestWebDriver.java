package com.lijing.crawler.service;

import com.lijing.crawler.browser.WebDriverSchedule;
import com.lijing.crawler.virtual.utils.VirtualKeyBoard;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by lijing on 2017/10/15 0015.
 */
@Slf4j
public class TestWebDriver {
    public static void main(String[] args) throws Exception {
        WebDriver webDriver = WebDriverSchedule.getFirefox();
        webDriver.get("https://www.bestpay.com.cn/index");
//            Thread.sleep(10000L);
        webDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

        WebElement loadTest = ( new WebDriverWait( webDriver, 15 )) .until(
                (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "codeImg" ))
        );
        WebElement phoneNo = ( new WebDriverWait( webDriver, 10 )) .until(
                (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "phoneNo" ))
        );
        for (int i=0;i<10;i++) {
            Thread.sleep(1000L);
            try {
                phoneNo.click();
                break;
            }catch (Exception e){
                log.error("元素加载失败，点击失败");
            }
            if(i==9){
                log.error("加载超时");
                return;
            }
        }
        phoneNo.sendKeys("15000425356");
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
        CodeCheckService codeCheckService = new CodeCheckService();
        String verifyCode = codeCheckService.getVerifyCode(CodeCheckService.getBase64(screenshot));
//            String verifyCode = "4567";

        Thread.sleep(1000L);
        WebElement codeEle = webDriver.findElement(By.id("codeVcode"));
//            codeEle.click();
        log.info("输入验证码:{}",verifyCode);
        codeEle.sendKeys(verifyCode);
//                        ((JavascriptExecutor)webDriver).executeScript("document.getElementById(\"codeCheckFlag\").value ='true';");
        Thread.sleep(2000L);
        WebElement passwordEle = webDriver.findElement(By.id("_ocx_password6"));
        passwordEle.click();
////            passwordEle.sendKeys(password);
////            SendKeyUtils.sendPassword(password);
        VirtualKeyBoard.keyPress("lijing892140");
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
    }
}
