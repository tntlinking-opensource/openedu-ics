package com.ruoyi.teach.school.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.teach.school.vo.LessonDateTime;
import lombok.Data;

import java.util.List;

/**
 * 课表管理对象 ts_lesson
 *
 * @author ruoyi
 * @date 2021-07-14
 */
@Data
@TableName("ts_lesson")
public class Lesson extends SchoolEntity {

    public static final String WEEK_TYPE_ALL = "全部";
    public static final String WEEK_TYPE_EVEN = "单周";
    public static final String WEEK_TYPE_ODD = "双周";

    /**
     * 学年
     */
    @Excel(name = "学年", isKey = true)
    private String year;

    /**
     * 学期
     */
    @Excel(name = "学期", isKey = true)
    private String term;

    /**
     * 学年学期
     */
    private Long teachCalendarId;

    /**
     * 班级
     */
    @Excel(name = "年级")
    private String grade;
    /**
     * 班级
     */
    @Excel(name = "班级", isKey = true)
    private String adminclass;

    /**
     * 星期
     */
    @Deprecated
//    @Excel(name = "星期", isKey = true)
    private String weekday;
    @Excel(name = "周类型", isKey = true)
    private String weekType;

    /**
     * 班级ID
     */
    private Long adminclassId;

    /**
     * 开始小节
     */
    @Deprecated
//    @Excel(name = "开始小节", isKey = true)
    private String timeStart;

    /**
     * 结束小节
     */
    @Deprecated
//    @Excel(name = "结束小节", isKey = true)
    private String timeEnd;

    /**
     * 课程
     */
    @Excel(name = "课程", isKey = true)
    private String course;

    /**
     * 课程名称
     */
    @TableField(exist = false)
    @Excel(name = "课程名称")
    private String courseName;

    /**
     * 课程名称
     */
    @TableField(exist = false)
    @Excel(name = "课程代码", isKey = true)
    private String courseCode;

    /**
     * 课程ID
     */
    private Long courseId;

    @Excel(name = "课时")
    private Integer hour;

    @TableField(exist = false)
    @Excel(name = "教师工号", type = Excel.Type.IMPORT)
    private String teacherCode;
    /**
     * 教师
     */
    @Excel(name = "教师姓名")
    private String teacher;
    private String teachers;

    /**
     * 教师ID
     */
    private Long teacherId;
    /**
     * 辅讲教师
     */
    private Long teacher2Id;

    /**
     * 教室
     */
    @Excel(name = "教室")
    private String classroom;
    @Excel(name = "课时安排")
    private String times;
    @Excel(name = "星期")
    private String weeks;
    @Excel(name = "新增课时安排")
    private String timesInclude;
    @Excel(name = "排除课时安排")
    private String timesExclude;

    @TableField(exist = false)
    private List<LessonDateTime> dateTimes;
//    @TableField(exist = false)
    private String weekTimes;

    public String getGrade() {
        if (grade == null && adminclass != null) {
            grade = adminclass.substring(0, 3);
        }
        return grade;
    }

    public String getInfo() {
        return String.format("%s,%s,%s,%s,%s", teacher, adminclass, weekday, timeStart, course);
    }

//    public String getTimes() {
//        String times = timeStart;
//        if (!StringUtils.equals(timeStart, timeEnd)) {
//            times += "、" + timeEnd;
//        }
//        return times;
//    }
}
