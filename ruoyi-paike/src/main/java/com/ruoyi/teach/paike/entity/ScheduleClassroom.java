package com.ruoyi.teach.paike.entity;

import com.ruoyi.teach.paike.domain.ClassroomSpecial;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class ScheduleClassroom extends ClassroomSpecial {

    private List<String> courses;
    private List<String> grades;
    private List<String> adminclasses;

    public boolean match(String course, String grade, String adminclass) {
        return match(course, grade, Arrays.asList(adminclass));
    }

    public boolean match(String course, String grade, List<String> adminclass) {
        if (!courses.contains(course)) {
            return false;
        }
        if (adminclasses != null) {
            if (Collections.disjoint(adminclasses, adminclass)) {
                return false;
            }
        }
        if (grades != null) {
            if (!grades.contains(grade)) {
                return false;
            }
        }
        return true;
    }
}
