package com.ruoyi.teach.paike.entity;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import lombok.Data;

@Data
public class ScheduleRule extends LessonScheduleRule {

    private JSONObject data;
    private Long times;
    private String planTimes;

    public String get(String key) {
        return data.getString(key);
    }

    public JSONArray getArray(String key) {
        return data.getJSONArray(key);
    }
}
