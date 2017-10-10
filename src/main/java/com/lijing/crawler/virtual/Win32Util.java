package com.lijing.crawler.virtual;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.W32APIOptions;

import static com.lijing.crawler.virtual.Win32MessageConstants.*;

/**
 * Created by lijing on 2017/10/10 0010.
 */
public class Win32Util{
    private static final int N_MAX_COUNT = 512;
    private static User32Ext USER32EXT = (User32Ext) Native.loadLibrary("user32", User32Ext.class, W32APIOptions.DEFAULT_OPTIONS);
    private Win32Util() {
    }
    public static HWND findHandleByClassName(String className, long timeout, TimeUnit unit) {
        return findHandleByClassName(USER32EXT.GetDesktopWindow(), className, timeout, unit);
    }
    public static HWND findHandleByClassName(String className) {
        return findHandleByClassName(USER32EXT.GetDesktopWindow(), className);
    }
    public static HWND findHandleByClassName(HWND root, String className, long timeout, TimeUnit unit) {
        if(null == className || className.length() <= 0) {
            return null;
        }

        long start = System.currentTimeMillis();
        HWND hwnd = findHandleByClassName(root, className);
        while(null == hwnd && (System.currentTimeMillis() - start < unit.toMillis(timeout))) {
            hwnd = findHandleByClassName(root, className);
        }
        return hwnd;
    }
    public static HWND findHandleByClassName(HWND root, String className) {
        if(null == className || className.length() <= 0) {
            return null;
        }
        HWND[] result = new HWND[1];
        findHandle(result, root, className);
        return result[0];
    }
    private static boolean findHandle(final HWND[] target, HWND root, final String className) {
        if(null == root) {
            root = USER32EXT.GetDesktopWindow();
        }
        return USER32EXT.EnumChildWindows(root, new WNDENUMPROC() {
            @Override
            public boolean callback(HWND hwnd, Pointer pointer) {
                char[] winClass = new char[N_MAX_COUNT];
                USER32EXT.GetClassName(hwnd, winClass, N_MAX_COUNT);
                if(USER32EXT.IsWindowVisible(hwnd) && className.equals(Native.toString(winClass))) {
                    target[0] = hwnd;
                    return false;
                } else {
                    return target[0] == null || findHandle(target, hwnd, className);
                }
            }
        }, Pointer.NULL);
    }
    public static boolean simulateKeyboardEvent(HWND hwnd, int[][] keyCombination) {
        if(null == hwnd) {
            return false;
        }
        USER32EXT.SwitchToThisWindow(hwnd, true);
        USER32EXT.SetFocus(hwnd);
        for(int[] keys : keyCombination) {
            for(int i = 0; i < keys.length; i++) {
                USER32EXT.keybd_event((byte) keys[i], (byte) 0, KEYEVENTF_KEYDOWN, 0); // key down
            }
            for(int i = keys.length - 1; i >= 0; i--) {
                USER32EXT.keybd_event((byte) keys[i], (byte) 0, KEYEVENTF_KEYUP, 0); // key up
            }
        }
        return true;
    }
    public static boolean simulateCharInput(final HWND hwnd, final String content) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SetFocus(hwnd);
                    for(char c : content.toCharArray()) {
                        Thread.sleep(5);
                        USER32EXT.SendMessage(hwnd, WM_CHAR, (byte) c, 0);
                    }
                    return true;
                }
            });
        } catch(Exception e) {
            return false;
        }
    }
    public static boolean simulateCharInput(final HWND hwnd, final String content, final long sleepMillisPreCharInput) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SetFocus(hwnd);
                    for(char c : content.toCharArray()) {
                        Thread.sleep(sleepMillisPreCharInput);
                        USER32EXT.SendMessage(hwnd, WM_CHAR, (byte) c, 0);
                    }
                    return true;
                }
            });
        } catch(Exception e) {
            return false;
        }
    }
    public static boolean simulateTextInput(final HWND hwnd, final String content) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SetFocus(hwnd);
                    USER32EXT.SendMessage(hwnd, WM_SETTEXT, 0, content);
                    return true;
                }
            });
        } catch(Exception e) {
            return false;
        }
    }
    public static boolean simulateClick(final HWND hwnd) {
        if(null == hwnd) {
            return false;
        }
        try {
            return execute(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    USER32EXT.SwitchToThisWindow(hwnd, true);
                    USER32EXT.SendMessage(hwnd, BM_CLICK, 0, null);
                    return true;
                }
            });
        } catch(Exception e) {
            return false;
        }
    }
    private static <T> T execute(Callable<T> callable) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<T> task = executor.submit(callable);
            return task.get(10, TimeUnit.SECONDS);
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) {
        String password = "your password";
        HWND alipayEdit = findHandle("Chrome_RenderWidgetHostHWND", "Edit"); //Chrome浏览器，使用Spy++可以抓取句柄的参数
        System.out.println("获取支付宝密码控件失败。"+alipayEdit);
        boolean isSuccess = Win32Util.simulateCharInput(alipayEdit, password);
        System.out.println("输入结果:{}"+isSuccess);
//        assertThat("输入支付宝密码["+ password +"]失败。", isSuccess,  is(true));
        }

    private static HWND findHandle(String browserClassName, String alieditClassName) {
        HWND browser = Win32Util.findHandleByClassName(browserClassName, 10, TimeUnit.SECONDS);
        return Win32Util.findHandleByClassName(browser, alieditClassName, 10, TimeUnit.SECONDS);
    }
}
