package com.cmtt.base.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumUtil {
    private static int sn = 0;

    public static String NextOrderNo() {
        if (sn == 999999999)
            sn = 0;
        else
            sn++;
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date()) + padRight(String.valueOf(sn), 9, '0');
    }

    public static String padLeft(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, 0, src.length());
        for (int i = src.length(); i < len; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }

    public static String padRight(String src, int len, char ch) {
        int diff = len - src.length();
        if (diff <= 0) {
            return src;
        }

        char[] charr = new char[len];
        System.arraycopy(src.toCharArray(), 0, charr, diff, src.length());
        for (int i = 0; i < diff; i++) {
            charr[i] = ch;
        }
        return new String(charr);
    }
}