package com.lijing.crawler.utils;

import java.awt.*;

/**
 * Created by lijing on 2017/10/10 0010.
 */
public class SendKeyUtils {
    private static void pressKey(int kc){
        try {
            Robot rbt=new Robot();
            rbt.keyPress(kc);
            rbt.keyRelease(kc);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static void sendPassword(String value){
            char[] c = value.toCharArray();
            for (char c1:c){
                pressKey(c1);
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    public static void main(String[] args) {
        System.out.println(Integer.valueOf("qwewe".toCharArray()[0]));
    }
}
