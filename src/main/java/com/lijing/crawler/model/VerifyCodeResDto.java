package com.lijing.crawler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 验证码解码响应dto
 * Created by lijing on 2017/10/14 0014.
 */
@Getter
@Setter
@ToString
public class VerifyCodeResDto implements Serializable{
    /** 响应代码 */
    private String code;
    /** 响应描述 */
    private String message;
    /** 响应验证码结果 */
    private VerifyCodeDto data;

}
