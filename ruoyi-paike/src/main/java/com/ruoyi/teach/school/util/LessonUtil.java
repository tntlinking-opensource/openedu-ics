package com.ruoyi.teach.school.util;

public class LessonUtil {

    public static final String[] WEEKDAYS = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五"};

    public static String getWeekday(String index) {
        return WEEKDAYS[Integer.parseInt(index) - 1];
    }
}
