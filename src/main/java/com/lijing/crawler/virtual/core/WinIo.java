package com.lijing.crawler.virtual.core;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.W32APIOptions;

/**
 * winio操作
 */
public interface WinIo extends Library {
    WinIo INSTANCE = (WinIo) Native.loadLibrary("WinIo64", WinIo.class, W32APIOptions.DEFAULT_OPTIONS);

    int CONTROL_PORT = 0x64;
    int DATA_PORT = 0x60;

    boolean InitializeWinIo();

    void ShutdownWinIo();

    boolean GetPortVal(int portAddr, Pointer pPortVal, int size);

    boolean SetPortVal(int portAddr, int portVal, int size);
}
