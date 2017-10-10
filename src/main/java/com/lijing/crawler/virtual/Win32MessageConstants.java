package com.lijing.crawler.virtual;

/**
 * win32事件
 * Created by lijing on 2017/10/10 0010.
 */
public class Win32MessageConstants {
    public static final int WM_SETTEXT = 0x000C; //输入文本
    public static final int WM_CHAR = 0x0102; //输入字符
    public static final int BM_CLICK = 0xF5; //点击事件，即按下和抬起两个动作
    public static final int KEYEVENTF_KEYUP = 0x0002; //键盘按键抬起
    public static final int KEYEVENTF_KEYDOWN = 0x0; //键盘按键按下
}
