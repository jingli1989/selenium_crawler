package com.lijing.crawler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 浏览器类型枚举
 * Created by lijing on 2017/10/10 0010.
 */
@Getter
@AllArgsConstructor
public enum BrowserTypeEnum {
    IE("IE","IE"),
    FIREFOX("FIREFOX","FIREFOX"),
    CHROME("CHROME","CHROME"),
    ;

    private String code;
    private String desc;
}
