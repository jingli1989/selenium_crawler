package com.lijing.crawler.service;

import com.lijing.crawler.browser.WebDriverSchedule;
import com.lijing.crawler.virtual.utils.VirtualKeyBoard;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 翼支付卡火狐登录
 * Created by lijing on 2017/10/15 0015.
 */
@Slf4j
@Service
public class BestPayFirefoxLoginService {


    public void login(){
        try {
           WebDriver webDriver = cardLogin("160040146354","808788");
            String balance = checkLoginSuccess(webDriver);
            log.info("卡内余额:{}",balance);
            recharge("0.01",webDriver);
            //充值确认
            for (int i =0;i<3;i++) {
                if(sureRecharge(webDriver)){
                    return;
                }
            }

        }catch (Exception e){
            log.error("登录失败，异常原因:",e);
        }
    }

    /**
     * 翼支付卡登录
     * @param cardId 登录id
     * @param password 登录密码
     * @return driver
     */
    public WebDriver cardLogin(String cardId,String password){
        try {
            WebDriver webDriver = WebDriverSchedule.getFirefox();
            webDriver.get("https://www.bestpay.com.cn/index");
            webDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
            WebElement card = ( new WebDriverWait( webDriver, 15 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.name( "card" ))
            );
            //点击翼支付卡登录选项卡
            waitClick(card,10);
            //卡号
            WebElement cardNoEle = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "cardNo" ))
            );
            cardNoEle.sendKeys(cardId);
            //密码
            WebElement passwordEle = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "_ocx_password4" ))
            );
            passwordEle.click();
            VirtualKeyBoard.keyPress(password);
            //图片验证码
            WebElement verifyCodeImgEle = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "cardcodeImg" ))
            );
            //图片验证码输入框
            WebElement verifyCodeEle = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "cardcodeVcode" ))
            );
            String verifyCode = analysisImgCode(webDriver,verifyCodeImgEle);
            log.error("解析获取的图片验证码内容:{}",verifyCode);
            verifyCodeEle.sendKeys(verifyCode);
            WebElement loginBtn = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "cardLoginButtn" ))
            );
            Thread.sleep(1000L);
            loginBtn.click();
            log.info("登录成功");
            return webDriver;
        }catch (Exception e){
            log.error("登录失败，异常原因:",e);
            return null;
        }
    }

    /**
     * 获取登录后的卡余额
     * @param webDriver driver
     * @return 余额
     * @throws InterruptedException 异常
     */
    public String checkLoginSuccess(WebDriver webDriver) throws InterruptedException {
        WebElement balanceEle = ( new WebDriverWait( webDriver, 10 )) .until(
                (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "bal-text" ))
        );
        String balance = balanceEle.getText();
        log.info("获取到的余额：{}",balance);
        for (int i = 0;i<10;i++){
            balance = balanceEle.getText();
            if(StringUtils.isBlank(balance)){
                Thread.sleep(1000L);
                continue;
            }
            return balance;
        }
        return null;
    }

    /**
     * 按钮点击(带超时)
     * @param btnEle 需要点击的按钮
     * @throws Exception 超时异常
     */
    private void waitClick(WebElement btnEle,int seconds) throws Exception {
        for (int i=0;i<seconds;i++) {
            try {
                btnEle.click();
                return;
            }catch (Exception e){
                log.error("元素加载失败，点击失败");
                Thread.sleep(1000L);
            }
        }
        log.error("加载超时");
        throw new Exception("按钮点击超时");
    }
    /**
     * 充值处理
     * @param money 充值金额
     * @param webDriver driver
     */
    public void recharge(String money,WebDriver webDriver){
        try {
            WebElement rechargeBtn = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.linkText( "充值" ))
            );
            rechargeBtn.click();

            WebElement amountEle = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.name( "amount" ))
            );
            waitClick(amountEle,10);
            amountEle.sendKeys(money);

            WebElement cardPassword = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "_ocx_password" ))
            );

            cardPassword.click();
            VirtualKeyBoard.keyPress("808788");

            WebElement phoneNo1 = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "toCardNo" ))
            );
            phoneNo1.sendKeys("15000425356");
            WebElement phoneNo2 = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "toCardNo2" ))
            );
            phoneNo2.sendKeys("15000425356");
            WebElement nextStepButtn = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "nextStepButtn" ))
            );
            Thread.sleep(1000L);
            nextStepButtn.click();

        }catch (Exception e){
            log.error("充值操作失败，原因:",e);
        }
    }

    /**
     * 确认充值操作
     * @param webDriver driver
     */
    public boolean sureRecharge(WebDriver webDriver){
        try {
            WebElement vCode = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "codeVcode" ))
            );
            waitClick(vCode,10);

            WebElement codeImg = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.name( "codeImg" ))
            );
            String code = analysisImgCode(webDriver,codeImg);
            vCode.sendKeys(code);

            WebElement suerBtn = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "submitButtn" ))
            );
            Thread.sleep(1000L);
            suerBtn.click();

            WebElement message = ( new WebDriverWait( webDriver, 10 )) .until(
                    (ExpectedCondition<WebElement>) d -> d.findElement( By.className( "popup-dialog-message" ))
            );
            String resultMessage = message.getText();
            log.info("充值确认结果消息:{}",resultMessage);
            if(!"充值成功".equals(resultMessage)){
                return true;
            }

        }catch (Exception e){
             log.error("确认充值失败，原因:",e);
        }
        return false;
    }

    /**
     * 登出
     * @param webDriver driver
     */
    public void loginOut(WebDriver webDriver){
        WebElement loginOut = ( new WebDriverWait( webDriver, 10 )) .until(
                (ExpectedCondition<WebElement>) d -> d.findElement( By.id( "loginOut" ))
        );
        try {
            waitClick(loginOut,3);
        } catch (Exception e) {
            log.error("登出失败");
        }

    }

    /**
     * 图片验证码处理
     * @param webDriver web
     * @param codeImg 图片验证码元素
     * @return 解析后的验证码结果
     * @throws IOException 异常
     */
    private String analysisImgCode(WebDriver webDriver,WebElement codeImg) throws IOException {
        File screenshot = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);
        Point point = codeImg.getLocation();
        int eleWidth = codeImg.getSize().getWidth();
        int eleHeight = codeImg.getSize().getHeight();
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);
        return CodeCheckService.getVerifyCode(CodeCheckService.getBase64(screenshot));
    }

    public static void main(String[] args) {
        BestPayFirefoxLoginService bestPayFirefoxLoginService = new BestPayFirefoxLoginService();
        bestPayFirefoxLoginService.login();
    }
}
