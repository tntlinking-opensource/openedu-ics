package com.ruoyi.teach.school.service.impl;

import com.ruoyi.teach.school.domain.*;
import com.ruoyi.teach.school.service.*;
import com.ruoyi.teach.system.domain.School;
import com.ruoyi.teach.system.service.TeachService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonYunXiaoServiceImpl implements LessonYunXiaoService {

    @Autowired
    private LessonTimeService lessonTimeService;
    @Autowired
    private TeachCalendarService teachCalendarService;
    @Autowired
    private TeachService teachService;
    @Autowired
    private AdminclassService adminclassService;
    @Autowired
    private CourseService courseService;

    @Override
    public List<Lesson> importExcel(MultipartFile file) throws Exception {
        School school = teachService.getSchool();
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        List<LessonTime> times = lessonTimeService.getLessonTime(school.getId());
        List<Lesson> lessons = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            getLessons(sheet, lessons, times);
//            lessons.addAll(getLessons(sheet, times));
        }
        TeachCalendar teachCalendar = teachCalendarService.getTeachCalendarCurrent(school.getId());
//        lessons = filterLesson(lessons);
        List<Adminclass> adminclasses = adminclassService.getAll(school.getId(), teachCalendar.getYear());
        List<Course> courses = courseService.getAll(school.getId());
        lessons.forEach(lesson -> {
            lesson.setSchoolId(school.getId());
            lesson.setYear(teachCalendar.getYear());
            lesson.setTerm(teachCalendar.getTerm());
            lesson.setTeachCalendarId(teachCalendar.getId());
            saveAdminclass(lesson, adminclasses);
            saveCourse(lesson, courses);
        });
        return lessons;
    }

    private List<Lesson> filterLesson(List<Lesson> lessons) {
        List<Lesson> result = new ArrayList<>();
        lessons.forEach(lesson -> {
            if (!result.stream().filter(l -> l.getAdminclass().equals(lesson.getAdminclass())
                            && l.getCourse().equals(lesson.getCourse())
//                    && l.getWeekday().equals(lesson.getWeekday())
//                    && l.getTimeStart().equals(lesson.getTimeStart())
            ).findFirst().isPresent()) {
                result.add(lesson);
            }
        });
        return result;
    }

    private void saveAdminclass(Lesson lesson, List<Adminclass> adminclasses) {
        List<Adminclass> temp = adminclasses.stream().filter(a ->
                        a.getYear().equals(lesson.getYear()) && a.getName().equals(lesson.getAdminclass()))
                .collect(Collectors.toList());
        if (temp.isEmpty()) {
            Adminclass adminclass = new Adminclass();
            adminclass.setSchoolId(lesson.getSchoolId());
            adminclass.setYear(lesson.getYear());
//            adminclass.setGrade(lesson.getAdminclass().substring(0, 3));
            adminclass.setGrade(lesson.getGrade());
            adminclass.setName(lesson.getAdminclass());
            adminclassService.insertAdminclass(lesson.getSchoolId(), adminclass);
            adminclasses.add(adminclass);
        }
    }

    private void saveCourse(Lesson lesson, List<Course> courses) {
        List<Course> temp = courses.stream()
                .filter(a -> a.getName().equals(lesson.getCourse()))
                .collect(Collectors.toList());
        if (temp.isEmpty()) {
            Course course = new Course();
            course.setSort(99);
            course.setSchoolId(lesson.getSchoolId());
            course.setCode("AUTO");
            course.setName(lesson.getCourse());
            course.setStatus("0");
            courseService.insertCourse(lesson.getSchoolId(), course);
        }
    }

    private List<Lesson> getLessons(Sheet sheet, List<Lesson> lessons, List<LessonTime> times) {
//        List<Lesson> lessons = new ArrayList<>();
//        String[] weeks = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        String grade = sheet.getSheetName();
        String adminclass = null;
//        String teacherName = null;
//        String teacherCode = null;
        int timeIndex = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || row.getCell(0) == null
                    || StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
                continue;
            }
            String firstCellValue = row.getCell(0).getStringCellValue();
            firstCellValue = firstCellValue.split("\n")[0];
            if ("节次".equals(firstCellValue)) {
                timeIndex = 0;
                continue;
            }
            String time = getTime(times, firstCellValue);
            if (time == null) {
                adminclass = firstCellValue;
//                teacherName = firstCellValue;
//                teacherCode = null;
//                if (teacherName.indexOf("+") > 0) {
//                    String[] ss = teacherName.split("\\+");
//                    teacherName = ss[0];
//                    teacherCode = ss[1];
//                }
                adminclass = adminclass.trim();
                continue;
            }
            timeIndex++;
            for (int j = 0; j < 5; j++) {
                if(row.getCell(j + 1) == null){
                    continue;
                }
                String str = row.getCell(j + 1).getStringCellValue();
                if (StringUtils.isBlank(str)) {
                    continue;
                }
                String[] ss = str.split("\n");
//                if (ss.length < 2) {
//                    continue;
//                }
//                String adminclass = ss[0].replaceAll("-", "");
//                String course = ss[1];
                String course = ss[0].trim();
                String teacher = ss.length > 1 ? ss[1] : null;
                String classroom = ss.length == 3 ? ss[2] : "";
                Lesson lesson = getLesson(lessons, adminclass, course);
                lesson.setGrade(grade);
//                lesson.setAdminclass(adminclass);
                lesson.setClassroom(classroom);
//                lesson.setWeekday(weeks[j]);
//                lesson.setTimeStart(time);
//                lesson.setTimeEnd(time);
//                lesson.setCourse(course);
                if (lesson.getTimes().length() > 0) {
                    lesson.setTimes(lesson.getTimes() + "、");
                }
                lesson.setTimes(lesson.getTimes() + String.format("%d-%d", j + 1, timeIndex));
//                lesson.setTeacher(teacherName);
//                lesson.setTeacherCode(teacherCode);
                if (teacher != null) {
                    lesson.setTeacher(teacher.split(",")[0]);
                    lesson.setTeachers(teacher);
                }

//                if (sheet.getRow(row.getRowNum() + 1) != null
//                        && sheet.getRow(row.getRowNum() + 1).getCell(j + 1) != null) {
//                    Cell nextCell = sheet.getRow(row.getRowNum() + 1).getCell(j + 1);
//                    String nextLesson = nextCell.getStringCellValue();
//                    if (str.equalsIgnoreCase(nextLesson)) {
//                        String endTime = getTime(times, sheet.getRow(row.getRowNum() + 1).getCell(0).getStringCellValue());
//                        if (endTime != null) {
//                            lesson.setTimeEnd(endTime);
//                        }
//                        nextCell.setBlank();
//                    }
//                }
//                lessons.add(lesson);
            }
        }
        return lessons;
    }

    private Lesson getLesson(List<Lesson> lessons, String adminclass, String course) {
        Optional<Lesson> first = lessons.stream().filter(l -> l.getAdminclass().equals(adminclass)
                && l.getCourse().equals(course)
        ).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        Lesson lesson = new Lesson();
        lesson.setAdminclass(adminclass);
        lesson.setCourse(course);
        lesson.setTimes("");
        lessons.add(lesson);
        return lesson;
    }

    private String getTime(List<LessonTime> times, String time) {
        Optional<LessonTime> first = times.stream().filter(t -> time.equals(String.format("第%d节", t.getSort()))).findFirst();
        if (first.isPresent()) {
            return first.get().getName();
        }
        return null;
    }

}
