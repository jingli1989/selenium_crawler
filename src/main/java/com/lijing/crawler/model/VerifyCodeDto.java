package com.lijing.crawler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *  验证码解码响应dto
 * Created by lijing on 2017/10/14 0014.
 */
@Getter
@Setter
@ToString
public class VerifyCodeDto implements Serializable{
    /** 验证码解码结果 */
    private String recognition;
    /** 解码订单号 */
    private String captchaId;
}
