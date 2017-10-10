package com.lijing.crawler.virtual.utils;


import com.lijing.crawler.virtual.core.User32;
import com.lijing.crawler.virtual.core.WinIo;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * 按键操作
 */
public class VirtualKeyBoard {
    private static final WinIo WIN_IO = WinIo.INSTANCE;

    static {
        if (!WinIo.INSTANCE.InitializeWinIo()) {
            System.err.println("Cannot Initialize the WinIO");
            System.exit(1);
        }
    }

    private static void cleanCache() throws Exception {
        int val;
        do {
            Pointer p = new Memory(8);
            if (!WinIo.INSTANCE.GetPortVal(WinIo.CONTROL_PORT, p, 1)) {
                System.err.println("Cannot get the Port");
            }
            val = p.getInt(0);

        } while ((0x2 & val) > 0);
    }

    private static void keyDown(int key) throws Exception {
        cleanCache();
        WIN_IO.SetPortVal(WinIo.CONTROL_PORT, 0xd2, 1);
        cleanCache();
        WIN_IO.SetPortVal(WinIo.DATA_PORT, key, 1);
    }

    private static void keyUp(int key) throws Exception {
        cleanCache();
        WIN_IO.SetPortVal(WinIo.CONTROL_PORT, 0xd2, 1);
        cleanCache();
        WIN_IO.SetPortVal(WinIo.DATA_PORT, (key | 0x80), 1);
    }

    /**
     * 输入字符串
     *
     * @param key
     * @param waitMs 等毫秒
     * @throws Exception
     */
    public static void keyPress(String key, int waitMs) throws Exception {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        for (int i = 0; i < key.length(); i++) {
            Thread.sleep(waitMs);
            VirtualKeyBoard.keyPress(key.charAt(i));
        }
    }

    /**
     * 输入字符串
     *
     * @param key
     * @throws Exception
     */
    public static void keyPress(String key) throws Exception {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        for (int i = 0; i < key.length(); i++) {
            Thread.sleep(200L);
            VirtualKeyBoard.keyPress(key.charAt(i));
        }
    }

    /**
     * 输入字符串并定位
     *
     * @param key
     */
    public static void keyPressWithLocation(String key, int x, int y) throws Exception {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        Robot robot = new Robot();
        robot.mouseMove(x, y);
        for (int i = 0; i < key.length(); i++) {
            Thread.sleep(100);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            Thread.sleep(200);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Thread.sleep(100);
            VirtualKeyBoard.keyPress(key.charAt(i));
        }
    }

    /**
     * 输入Tab键
     *
     * @throws Exception
     */
    public static void keyTable() throws Exception {
        keyPress(KeyEvent.VK_TAB);
    }

    /**
     * 输入回车键
     *
     * @throws Exception
     */
    public static void keyEnter() throws Exception {
        keyPress(0x0d);
    }

    /**
     * 输入空格键
     *
     * @throws Exception
     */
    public static void keySpace() throws Exception {
        keyPress(KeyEvent.VK_SPACE);
    }

    /**
     * 输入单个字符
     *
     * @param vk
     * @throws Exception
     */
    private static void keyPress(int vk) throws Exception {
        int scan = User32.INSTANCE.MapVirtualKey(vk, 0);
        Thread.sleep(100L);
        keyDown(scan);
        Thread.sleep(200L);
        keyUp(scan);
        Thread.sleep(100L);
    }

    /**
     * 输入单个字符
     *
     * @param key
     * @throws Exception
     */
    private static void keyPress(char key) throws Exception {
        if (65 <= key && key <= 90) {
            Thread.sleep(100L);
            keyPress(VKMapping.toVK("CAPS"));
            Thread.sleep(200L);
            keyPress(VKMapping.toVK("" + key));
            Thread.sleep(200L);
            keyPress(VKMapping.toVK("CAPS"));
            Thread.sleep(100L);
        } else {
            if (VKMapping.toVK("" + key) >= 0) {
                keyPress(VKMapping.toVK("" + key));
            }
        }
    }
}