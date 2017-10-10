package com.lijing.crawler.virtual;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.win32.W32APIOptions;

/**
 * Created by lijing on 2017/10/10 0010.
 */
public class TestHWND {
    private static User32Ext USER32EXT = (User32Ext) Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);

    public static void main(String[] args) {
        WinDef.HWND hwnd = USER32EXT.GetDesktopWindow();
        USER32EXT.FindWindowEx(hwnd,null,"chrome","翼支付");
    }
}
