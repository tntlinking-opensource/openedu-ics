package com.ruoyi.teach.paike.util;

import cn.hutool.core.util.ArrayUtil;
import com.ruoyi.teach.paike.domain.LessonScheduleTime;
import com.ruoyi.teach.paike.entity.ScheduleData;

public class LessonPlanScheduleUtil {

    public static Long transformTimes(ScheduleData data, String times) {
        times = formatTimes(data.getTime(), times);
        long time = 0;
        int timeOfDay = data.getTimeOfDay();
        for (String t : times.split(";")) {
            time = time << timeOfDay;
            time = time | Long.parseLong(t, 2);
        }
        return time;
    }

    public static String formatTimes(LessonScheduleTime time, String times) {
        try {
            String[] ss = times.split(";");
            String[] tt = new String[time.getWeekdays()];
            for (int i = 0; i < time.getWeekdays(); i++) {
                String s = ss.length > i ? ss[i] : "";
                if (s.length() < time.getCount()) {
                    for (int j = s.length(); j < time.getCount(); j++) {
                        s += '1';
                    }
                } else {
                    s = s.substring(0, time.getCount());
                }
                tt[i] = s;
            }
            return ArrayUtil.join(tt, ";");
        } catch (Exception e) {
        }
        return times;
    }

}
