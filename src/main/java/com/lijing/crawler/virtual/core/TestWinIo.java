package com.lijing.crawler.virtual.core;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * Created by lijing on 2017/10/14 0014.
 */
public class TestWinIo {
    public static void main(String[] args) {
        System.out.println(WinIo.INSTANCE);
        System.out.println(WinIo.INSTANCE.InitializeWinIo());
        WinIo.INSTANCE.ShutdownWinIo();
//        WebClient webClient = new WebClient(BrowserVersion.CHROME);

    }
}
