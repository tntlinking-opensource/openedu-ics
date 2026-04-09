package com.ruoyi.teach.paike;

public class GeneticAlgorithmTest {

    public static void main(String[] args) {
        GeneticAlgorithm gen = new GeneticAlgorithm();
        // 作息时间
        gen.timeConfig = PaikeUtil.getTimeConfig();
        // 课程不排课时间
        gen.courseConfigs = PaikeUtil.getCourseConfigs();
        // 班级不排课时间
        gen.adminclassConfigs = PaikeUtil.getAdminclassConfigs();
        // 教师不排课时间
        gen.teacherConfigs = PaikeUtil.getTeacherConfigs();
        // 任课信息
        gen.lessonInfos = PaikeUtil.getLessonInfos();
//        gen.teachers = getTeachers();
//        gen.lessonTasks = getLessonTasks();
        gen.evolution();
    }
}
