package com.lijing.crawler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Excl cell value type
 * Created by lijing on 2017/8/10 0010.
 */
@Getter
@AllArgsConstructor
public enum ExclValueTypeEnum {
    NUM("NUM","数字"),
    STRING("STRING","字符串");
    private String type;
    private String desc;
}
