package com.ruoyi.teach.util;

import java.util.Comparator;

public class ChineseNumberComparator implements Comparator<String> {

    private String numbers = "一二三四五六七八九十";

    @Override
    public int compare(String o1, String o2) {
        String s1 = formatNumber(o1);
        String s2 = formatNumber(o2);
        return s1.compareTo(s2);
    }

    private String formatNumber(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < sb.length(); i++) {
            int index = numbers.indexOf(sb.charAt(i));
            if (index >= 0) {
                sb.setCharAt(i, (char) (index + 1 + '0'));
            }
        }
        return sb.toString();
    }
}
