package com.lijing.crawler.virtual.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符按键对应map
 */
class VKMapping {
    private static final Map<String, Integer> map = new HashMap<String, Integer>();
    
    protected static Logger logger= LoggerFactory.getLogger(VKMapping.class);

    static {
        map.put("0", KeyEvent.VK_0);
        map.put("1", KeyEvent.VK_1);
        map.put("2", KeyEvent.VK_2);
        map.put("3", KeyEvent.VK_3);
        map.put("4", KeyEvent.VK_4);
        map.put("5", KeyEvent.VK_5);
        map.put("6", KeyEvent.VK_6);
        map.put("7", KeyEvent.VK_7);
        map.put("8", KeyEvent.VK_8);
        map.put("9", KeyEvent.VK_9);
        map.put("a", KeyEvent.VK_A);
        map.put("b", KeyEvent.VK_B);
        map.put("c", KeyEvent.VK_C);
        map.put("d", KeyEvent.VK_D);
        map.put("e", KeyEvent.VK_E);
        map.put("f", KeyEvent.VK_F);
        map.put("g", KeyEvent.VK_G);
        map.put("h", KeyEvent.VK_H);
        map.put("i", KeyEvent.VK_I);
        map.put("j", KeyEvent.VK_J);
        map.put("k", KeyEvent.VK_K);
        map.put("l", KeyEvent.VK_L);
        map.put("m", KeyEvent.VK_M);
        map.put("n", KeyEvent.VK_N);
        map.put("o", KeyEvent.VK_O);
        map.put("p", KeyEvent.VK_P);
        map.put("q", KeyEvent.VK_Q);
        map.put("r", KeyEvent.VK_R);
        map.put("s", KeyEvent.VK_S);
        map.put("t", KeyEvent.VK_T);
        map.put("u", KeyEvent.VK_U);
        map.put("v", KeyEvent.VK_V);
        map.put("w", KeyEvent.VK_W);
        map.put("x", KeyEvent.VK_X);
        map.put("y", KeyEvent.VK_Y);
        map.put("z", KeyEvent.VK_Z);
        map.put("a", KeyEvent.VK_A);
        map.put("b", KeyEvent.VK_B);
        map.put("c", KeyEvent.VK_C);
        map.put("d", KeyEvent.VK_D);
        map.put("e", KeyEvent.VK_E);
        map.put("f", KeyEvent.VK_F);
        map.put("g", KeyEvent.VK_G);
        map.put("h", KeyEvent.VK_H);
        map.put("i", KeyEvent.VK_I);
        map.put("j", KeyEvent.VK_J);
        map.put("k", KeyEvent.VK_K);
        map.put("l", KeyEvent.VK_L);
        map.put("m", KeyEvent.VK_M);
        map.put("n", KeyEvent.VK_N);
        map.put("o", KeyEvent.VK_O);
        map.put("p", KeyEvent.VK_P);
        map.put("q", KeyEvent.VK_Q);
        map.put("r", KeyEvent.VK_R);
        map.put("s", KeyEvent.VK_S);
        map.put("t", KeyEvent.VK_T);
        map.put("u", KeyEvent.VK_U);
        map.put("v", KeyEvent.VK_V);
        map.put("w", KeyEvent.VK_W);
        map.put("x", KeyEvent.VK_X);
        map.put("y", KeyEvent.VK_Y);
        map.put("z", KeyEvent.VK_Z);
        
        map.put("!", KeyEvent.VK_EXCLAMATION_MARK);
        map.put("@", KeyEvent.VK_AT);
        map.put("#", KeyEvent.VK_NUMBER_SIGN);
        map.put("$", KeyEvent.VK_DOLLAR);
//        map.put("%", KeyEvent.VK_4);
        map.put("^", KeyEvent.VK_CIRCUMFLEX);
//        map.put("&", KeyEvent.VK_6);
//        map.put("*", KeyEvent.VK_7);
        map.put("(", KeyEvent.VK_LEFT_PARENTHESIS);
        map.put(")", KeyEvent.VK_RIGHT_PARENTHESIS);
        map.put("-", KeyEvent.VK_UNDERSCORE);
        map.put("_", KeyEvent.VK_UNDERSCORE);
        map.put("+", KeyEvent.VK_PLUS);
        map.put("=", KeyEvent.VK_EQUALS);
        map.put("[", KeyEvent.VK_OPEN_BRACKET);
        map.put("]", KeyEvent.VK_CLOSE_BRACKET);
//        map.put("{", KeyEvent.VK_UNDERSCORE);
//        map.put("}", KeyEvent.VK_UNDERSCORE);
        
        map.put("A", KeyEvent.VK_A);
        map.put("B", KeyEvent.VK_B);
        map.put("C", KeyEvent.VK_C);
        map.put("D", KeyEvent.VK_D);
        map.put("E", KeyEvent.VK_E);
        map.put("F", KeyEvent.VK_F);
        map.put("G", KeyEvent.VK_G);
        map.put("H", KeyEvent.VK_H);
        map.put("I", KeyEvent.VK_I);
        map.put("J", KeyEvent.VK_J);
        map.put("K", KeyEvent.VK_K);
        map.put("L", KeyEvent.VK_L);
        map.put("M", KeyEvent.VK_M);
        map.put("N", KeyEvent.VK_N);
        map.put("O", KeyEvent.VK_O);
        map.put("P", KeyEvent.VK_P);
        map.put("Q", KeyEvent.VK_Q);
        map.put("R", KeyEvent.VK_R);
        map.put("S", KeyEvent.VK_S);
        map.put("T", KeyEvent.VK_T);
        map.put("U", KeyEvent.VK_U);
        map.put("V", KeyEvent.VK_V);
        map.put("W", KeyEvent.VK_W);
        map.put("X", KeyEvent.VK_X);
        map.put("Y", KeyEvent.VK_Y);
        map.put("Z", KeyEvent.VK_Z);       
        
        map.put("CAPS", KeyEvent.VK_CAPS_LOCK);        
    }

    static int toVK(String key) {
    	if(null == map.get(key)){
    		logger.error("==>[{}]暂无对应的ASCII码", key);
    		return -1;
    	}
        return map.get(key);
    }
}
