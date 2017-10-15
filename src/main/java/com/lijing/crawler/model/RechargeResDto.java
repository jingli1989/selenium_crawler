package com.lijing.crawler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 充值结果
 * Created by lijing on 2017/10/15 0015.
 */
@Getter
@Setter
@ToString
public class RechargeResDto implements Serializable{
    /** 充值结果 0 成功 -1 失败*/
    private String code;
    /** 描述 */
    private String msg;
    /** 标识 true 成功 false 失败 */
    private boolean flag;
    /** 充值金额 */
    private String money;
    /** 充值手机号码 */
    private String phoneNo;
}
