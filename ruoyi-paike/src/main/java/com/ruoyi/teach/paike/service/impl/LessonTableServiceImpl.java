package com.ruoyi.teach.paike.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.paike.domain.Classroom;
import com.ruoyi.teach.paike.entity.*;
import com.ruoyi.teach.paike.service.ClassroomService;
import com.ruoyi.teach.paike.service.ClassroomSpecialService;
import com.ruoyi.teach.paike.service.LessonPlanScheduleService;
import com.ruoyi.teach.paike.service.LessonTableService;
import com.ruoyi.teach.paike.vo.ViewAdminclassForm;
import com.ruoyi.teach.school.domain.LessonTime;
import com.ruoyi.teach.school.service.LessonTimeService;
import com.ruoyi.teach.util.ChineseNumberComparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LessonTableServiceImpl implements LessonTableService {

    @Autowired
    private LessonPlanScheduleService scheduleService;
    @Autowired
    private ClassroomService classroomService;
    @Autowired
    private ClassroomSpecialService classroomSpecialService;
    @Autowired
    private LessonTimeService lessonTimeService;

    @Override
    public void setRoom(ScheduleData data) {
        List<String> classrooms = new ArrayList<>();
        List<Classroom> rooms = classroomService.getByScheduleId(data.getScheduleId());
        rooms.forEach(r -> classrooms.add(r.getName()));
//        List<ClassroomSpecial> roomSpecials = classroomSpecialService.getByScheduleId(data.getScheduleId());
//        roomSpecials.forEach(r -> classrooms.add(r.getName()));
        data.getRooms().forEach(r -> classrooms.add(r.getName()));
        data.setClassrooms(classrooms);
        data.getPlans().forEach(plan -> {
            String room = getClassroom(rooms, data.getRooms(), plan.getGrade(), plan.getCourseName(), plan.getAdminclass());
            plan.setClassroom(room);
        });
    }

    @Override
    public List<LessonRow> getRowsByAdminclass(ScheduleData data, ViewAdminclassForm form) {
        List<String> adminclasses = getAdminClasses(data, form);
        List<Classroom> rooms = classroomService.getByScheduleId(data.getScheduleId());
//        List<ClassroomSpecial> roomSpecials = classroomSpecialService.getByScheduleId(data.getScheduleId());
        List<LessonTime> lessonTimes = lessonTimeService.getBySchoolId(data.getSchoolId());
        List<LessonRow> rows = new ArrayList<>();
        for (String adminclass : adminclasses) {
//            System.out.println(adminclass);
            for (int i = 0; i < data.getTimeOfDay(); i++) {
                LessonRow row = new LessonRow();
                row.setTimeIndex(i);
                row.setTime(String.format("第%d节", i + 1));
                row.setAdminclass(adminclass);
                row.setTimes(getTimes(lessonTimes, i + 1));
                for (int j = 0; j < data.getWeekdays(); j++) {
                    LessonCell cell = getLessonCell(data, row, i, j);
                    cell.setRoom(getClassroom(rooms, data.getRooms(), cell.getGrade(), cell.getCourse(), adminclass));
                    BeanUtil.setFieldValue(row, "cell" + (j + 1), cell);
                }
                rows.add(row);
            }
        }
        return rows;
    }

    public List<LessonRow> getRowsByAdminclassAndTime(ScheduleData data, ViewAdminclassForm form) {
        List<String> adminclasses = getAdminClasses(data, form);
        List<Classroom> rooms = classroomService.getByScheduleId(data.getScheduleId());
//        List<ClassroomSpecial> roomSpecials = classroomSpecialService.getByScheduleId(data.getScheduleId());
        List<LessonTime> lessonTimes = lessonTimeService.getBySchoolId(data.getSchoolId());
        List<LessonRow> rows = new ArrayList<>();
        String[] weekdays = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        for (String adminclass : adminclasses) {
            for (int i = 0; i < data.getWeekdays(); i++) {
                LessonRow row = new LessonRow();
                row.setTimeIndex(i);
                row.setTime(weekdays[i]);
                row.setAdminclass(adminclass);
                row.setTimes(getTimes(lessonTimes, i + 1));
                for (int j = 0; j < data.getTimeOfDay(); j++) {
                    LessonCell cell = getLessonCell(data, row, j, i);
//                    cell.setRoom(getClassroom(rooms, roomSpecials, cell.getGrade(), cell.getCourse(), adminclass));
                    cell.setRoom(getClassroom(rooms, data.getRooms(), cell.getGrade(), cell.getCourse(), adminclass));
                    BeanUtil.setFieldValue(row, "cell" + (j + 1), cell);
                }
                rows.add(row);
            }
        }
        return rows;
    }

    @Override
    public List<LessonRow> getRowsByTeacher(ScheduleData data) {
        List<String> teachers = getTeacher(data);
        List<Classroom> rooms = classroomService.getByScheduleId(data.getScheduleId());
//        List<ClassroomSpecial> roomSpecials = classroomSpecialService.getByScheduleId(data.getScheduleId());
        List<LessonTime> lessonTimes = lessonTimeService.getBySchoolId(data.getSchoolId());
        List<LessonRow> rows = new ArrayList<>();
        for (String teacher : teachers) {
//            System.out.println(adminclass);
            for (int i = 0; i < data.getTimeOfDay(); i++) {
                LessonRow row = new LessonRow();
                row.setTimeIndex(i);
                row.setTime(String.format("第%d节", i + 1));
                row.setTeacher(teacher);
                row.setTimes(getTimes(lessonTimes, i + 1));
                for (int j = 0; j < data.getWeekdays(); j++) {
                    LessonCell cell = getLessonCell(data, row, i, j);
//                    cell.setRoom(getClassroom(rooms, roomSpecials, cell.getGrade(), cell.getCourse(), cell.getAdminclass()));
                    cell.setRoom(getClassroom(rooms, data.getRooms(), cell.getGrade(), cell.getCourse(), cell.getAdminclass()));
                    BeanUtil.setFieldValue(row, "cell" + (j + 1), cell);
                }
                rows.add(row);
            }
        }
        return rows;
    }

    private List<String> getTeacher(ScheduleData data) {
        List<String> list = new ArrayList<>();
        for (SchedulePlan plan : data.getPlans()) {
            plan.getPlans().forEach(p -> {
                String teacher = p.getTeacher();
                if (list.contains(teacher)) {
                    return;
                }
                list.add(teacher);
            });
        }
        data.setTeacherNames(list);
        return list;
    }

    private String getTimes(List<LessonTime> lessonTimes, int sort) {
        Optional<LessonTime> first = lessonTimes.stream()
                .filter(t -> t.getSort() != null && t.getSort() == sort)
                .findFirst();
        if (first.isPresent()) {
            LessonTime time = first.get();
            return String.format("%s~%s", time.getStartTime(), time.getEndTime());
        }
        return "";
    }

    private String getClassroom(List<Classroom> rooms, List<ScheduleClassroom> roomSpecials, String grade, String course, String adminclass) {
        if (adminclass == null) {
            return "";
        }
        ScheduleClassroom classroomSpecial = getClassroomSpecial(roomSpecials, grade, course, adminclass);
        if (classroomSpecial != null) {
            return classroomSpecial.getName();
        }
        Classroom room = getClassroom(rooms, adminclass);
        if (room != null) {
            return room.getName();
        }
        return "";
    }

    private Classroom getClassroom(List<Classroom> rooms, String adminclass) {
//        if(adminclass == null){
//            System.out.println(adminclass);
//        }
        return rooms.stream().filter(r -> adminclass.equals(r.getGrade() + r.getAdminclass())).findFirst().orElse(null);
    }

//    private ClassroomSpecial getClassroomSpecial(LessonRow row, LessonCell cell, List<ClassroomSpecial> roomSpecials) {
//        return roomSpecials.stream().filter(r -> {
//            if (!r.getCourse().equals(cell.getCourse())) {
//                return false;
//            }
//            if (StringUtils.isNotBlank(r.getAdminclass())) {
//                String adminclass = null;
//                if (StringUtils.isNotBlank(row.getAdminclass())) {
//                    adminclass = row.getAdminclass();
//                } else if (StringUtils.isNotBlank(cell.getAdminclass())) {
//                    adminclass = cell.getAdminclass().split(",")[0];
//                }
//                return r.getAdminclass().indexOf(adminclass) >= 0;
//            }
//            return r.getGrade().indexOf(row.getGrade()) >= 0;
//        }).findFirst().orElse(null);
//    }

    private ScheduleClassroom getClassroomSpecial(List<ScheduleClassroom> roomSpecials, String grade, String course, String adminclass) {
        return roomSpecials.stream().filter(r -> {
//            if (!r.getCourse().equals(course)) {
//                return false;
//            }
//            if (StringUtils.isBlank(r.getGrade()) && StringUtils.isBlank(r.getAdminclass())) {
//                return true;
//            }
//            if (StringUtils.isNotBlank(r.getAdminclass())) {
//                return r.getAdminclass().indexOf(adminclass) >= 0;
//            }
//            return r.getGrade().indexOf(grade) >= 0;
            return r.match(course, grade, adminclass);
        }).findFirst().orElse(null);
    }

    private LessonCell getLessonCell(ScheduleData data, LessonRow row, int time, int weekday) {
        String key = String.format("%d-%d", weekday + 1, time + 1);
        Optional<SchedulePlan> first = data.getPlans().stream().filter(p -> {
            if (p.getPlanTimes() == null) {
                return false;
            }
            if (row.getAdminclass() != null) {
                return p.getAdminclasses().contains(row.getAdminclass())
                        && p.getPlanTimes().contains(key);
            } else {
                return StringUtils.equals(p.getTeacher(), row.getTeacher())
                        && p.getPlanTimes().contains(key);
            }
        }).findFirst();
        LessonCell cell = new LessonCell();
        if (first.isPresent()) {
            SchedulePlan plan = first.get();
            cell.setGrade(plan.getGrade());
            cell.setCourse(plan.getCourseName());
            cell.setTeacher(plan.getTeacher());
            cell.setAdminclass(plan.getAdminclasses().stream().collect(Collectors.joining(",")));
        }
        return cell;
    }

    private List<String> getAdminClasses(ScheduleData data, ViewAdminclassForm form) {
        List<String> list = new ArrayList<>();
        for (SchedulePlan plan : data.getPlans()) {
            plan.getPlans().forEach(p -> {
                if (form != null) {
                    if (StringUtils.isNotBlank(form.getGrade())) {
                        if (!form.getGrade().equals(p.getGrade())) {
                            return;
                        }
                    }
                    if (StringUtils.isNotBlank(form.getAdminclass())) {
                        if (!form.getAdminclass().equals(p.getAdminclass())) {
                            return;
                        }
                    }
                }
                String adminclassName = p.getAdminclass();
                if (list.contains(adminclassName)) {
                    return;
                }
                list.add(adminclassName);
            });
        }
        ChineseNumberComparator comparator = new ChineseNumberComparator();
        Collections.sort(list, (a, b) -> comparator.compare(a, b));
        data.setAdminclassNames(list);
        return list;
    }

    @Override
    public String exportAdminclass(ScheduleData data, List<LessonRow> rows, ViewAdminclassForm form) {
        XSSFWorkbook wb = new XSSFWorkbook();
        exportAdminclassByWeekday(wb, data, rows, form);
        rows = getRowsByAdminclassAndTime(data, form);
        exportAdminclassByTimes(wb, data, rows, form);
        return getFileName(wb, "班级课表");
    }

    private void exportAdminclassByWeekday(XSSFWorkbook wb, ScheduleData data, List<LessonRow> rows, ViewAdminclassForm form) {
        XSSFSheet sheet = wb.createSheet("Sheet1");
        CellStyle titleStyle = getTitleStyle(sheet);
        CellStyle cellStyle = getCellStyle(sheet);
        int rowNum = 0, colWidth = 18 * 256, rowHeight = 60;
        if (!form.isShowTeacher() && !form.isShowRoom() && !form.isShowTime()) {
            rowHeight = 30;
        } else if (!form.isShowTeacher() || !form.isShowRoom()) {
            rowHeight = 40;
        }
        String[] titles = new String[]{"节次", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        // 列宽
        sheet.setColumnWidth(0, colWidth);
        for (int i = 0; i < data.getWeekdays(); i++) {
            sheet.setColumnWidth(i + 1, colWidth);
        }

        XSSFRow row;
        XSSFCell cell;
        for (String adminclass : data.getAdminclassNames()) {
            if (rowNum > 0) {
                sheet.createRow(rowNum++);
            }
            // 班级行
            row = sheet.createRow(rowNum++);
            row.setHeightInPoints(30);
            for (int i = 0; i < data.getWeekdays() + 1; i++) {
                cell = row.createCell(i);
                if (i == 0) {
                    cell.setCellValue(adminclass);
                }
                cell.setCellStyle(titleStyle);
            }
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, data.getWeekdays()));
            // 标题行
            row = sheet.createRow(rowNum++);
            row.setHeightInPoints(30);
            for (int i = 0; i < data.getWeekdays() + 1; i++) {
                cell = row.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(titleStyle);
            }
            for (LessonRow lessonRow : rows) {
                if (adminclass.equals(lessonRow.getAdminclass())) {
                    if (lessonRow.getTimeIndex() == data.getTime().getMorning()) {
                        row = sheet.createRow(rowNum++);
                        row.setHeightInPoints(20);
                    }
                    row = sheet.createRow(rowNum++);
                    row.setHeightInPoints(rowHeight);
                    cell = row.createCell(0);
                    cell.setCellStyle(cellStyle);
                    if (form.isShowTime() && StringUtils.isNotBlank(lessonRow.getTimes())) {
                        cell.setCellValue(String.format("%s\n(%s)", lessonRow.getTime(), lessonRow.getTimes()));
                    } else {
                        cell.setCellValue(lessonRow.getTime());
                    }
                    for (int i = 0; i < data.getWeekdays(); i++) {
                        cell = row.createCell(i + 1);
                        cell.setCellStyle(cellStyle);
                        LessonCell lessonCell = BeanUtil.getProperty(lessonRow, String.format("cell%d", i + 1));
                        if (lessonCell != null && StringUtils.isNotBlank(lessonCell.getCourse())) {
                            StringBuilder sb = new StringBuilder(lessonCell.getCourse());
                            if (form.isShowTeacher()) {
                                sb.append("\n").append(lessonCell.getTeacher());
                            }
                            if (form.isShowRoom() && StringUtils.isNotBlank(lessonCell.getRoom())) {
                                sb.append("\n").append(lessonCell.getRoom());
                            }
                            cell.setCellValue(sb.toString());
                        }
                    }
                }
            }
        }
    }

    private void exportAdminclassByTimes(XSSFWorkbook wb, ScheduleData data, List<LessonRow> rows, ViewAdminclassForm form) {
        XSSFSheet sheet = wb.createSheet("Sheet2");
        CellStyle titleStyle = getTitleStyle(sheet);
        CellStyle cellStyle = getCellStyle(sheet);
        List<LessonTime> lessonTimes = lessonTimeService.getBySchoolId(data.getSchoolId());
        int rowNum = 0, colWidth = 18 * 256, rowHeight = 60;
        if (!form.isShowTeacher() && !form.isShowRoom() && !form.isShowTime()) {
            rowHeight = 30;
        } else if (!form.isShowTeacher() || !form.isShowRoom()) {
            rowHeight = 40;
        }
//        String[] titles = new String[data.getTimeOfDay() + 1];
//        titles[0] = "星期";
//        for (int i = 0; i < data.getTimeOfDay(); i++) {
//            titles[i + 1] = String.format("第%d节", i + 1);
//        }
        // 列宽
        sheet.setColumnWidth(0, colWidth);
        for (int i = 0; i < data.getTimeOfDay(); i++) {
            sheet.setColumnWidth(i + 1, colWidth);
        }

        XSSFRow row;
        XSSFCell cell;
        for (String adminclass : data.getAdminclassNames()) {
            if (rowNum > 0) {
                sheet.createRow(rowNum++);
            }
            // 班级行
            row = sheet.createRow(rowNum++);
            row.setHeightInPoints(30);
            for (int i = 0; i < data.getTimeOfDay() + 1; i++) {
                cell = row.createCell(i);
                if (i == 0) {
                    cell.setCellValue(adminclass);
                }
                cell.setCellStyle(titleStyle);
            }
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, data.getTimeOfDay()));
            // 标题行
            row = sheet.createRow(rowNum++);
            row.setHeightInPoints(30);
            for (int i = 0; i < data.getTimeOfDay() + 1; i++) {
                cell = row.createCell(i);
                String title = "星期";
                if (i > 0) {
//					Assert.isTrue(i - 1 >= lessonTimes.size(), "节次错误");
                    Assert.isTrue(i - 1 < lessonTimes.size(), "节次错误");
                    title = lessonTimes.get(i - 1).getName();
                    if (form.isShowTime()) {
                        title += "\n" + getTimes(lessonTimes, i);
                    }
                }
                cell.setCellValue(title);
                cell.setCellStyle(titleStyle);
            }
            for (LessonRow lessonRow : rows) {
                if (adminclass.equals(lessonRow.getAdminclass())) {
                    row = sheet.createRow(rowNum++);
                    row.setHeightInPoints(rowHeight);
                    cell = row.createCell(0);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(lessonRow.getTime());
                    for (int i = 0; i < data.getTimeOfDay(); i++) {
                        cell = row.createCell(i + 1);
                        cell.setCellStyle(cellStyle);
                        LessonCell lessonCell = BeanUtil.getProperty(lessonRow, String.format("cell%d", i + 1));
                        if (lessonCell != null && StringUtils.isNotBlank(lessonCell.getCourse())) {
                            StringBuilder sb = new StringBuilder(lessonCell.getCourse());
                            if (form.isShowTeacher()) {
                                sb.append("\n").append(lessonCell.getTeacher());
                            }
                            if (form.isShowRoom() && StringUtils.isNotBlank(lessonCell.getRoom())) {
                                sb.append("\n").append(lessonCell.getRoom());
                            }
                            cell.setCellValue(sb.toString());
                        }
                    }
                }
            }
        }
    }

    private CellStyle getTitleStyle(XSSFSheet sheet) {
        XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setWrapText(true);
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 10);
        font.setFontName("微软雅黑");
        style.setFont(font);
        return style;
    }

    private CellStyle getCellStyle(XSSFSheet sheet) {
        XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setWrapText(true);
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setFontHeightInPoints((short) 10);
        font.setFontName("微软雅黑");
        style.setFont(font);
        return style;
    }

    @Override
    public String exportTeacher(ScheduleData data, List<LessonRow> rows, ViewAdminclassForm form) {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Sheet1");
        CellStyle titleStyle = getTitleStyle(sheet);
        CellStyle cellStyle = getCellStyle(sheet);
        int rowNum = 0, colWidth = 18 * 256, rowHeight = 60;
        if (!form.isShowRoom()) {
            rowHeight = 40;
        }
        String[] titles = new String[]{"节次", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        // 列宽
        sheet.setColumnWidth(0, colWidth);
        for (int i = 0; i < data.getWeekdays(); i++) {
            sheet.setColumnWidth(i + 1, colWidth);
        }

        XSSFRow row;
        XSSFCell cell;
        List<String> teachers = getTeachers(data, form);
        for (String teacher : teachers) {
            if (teacher == null) {
                continue;
            }
            if (rowNum > 0) {
                sheet.createRow(rowNum++);
            }
            // 教师行
            row = sheet.createRow(rowNum++);
            row.setHeightInPoints(30);
            for (int i = 0; i < data.getWeekdays() + 1; i++) {
                cell = row.createCell(i);
                if (i == 0) {
                    cell.setCellValue(teacher);
                }
                cell.setCellStyle(titleStyle);
            }
            sheet.addMergedRegion(new CellRangeAddress(row.getRowNum(), row.getRowNum(), 0, data.getWeekdays()));
            // 标题行
            row = sheet.createRow(rowNum++);
            row.setHeightInPoints(30);
            for (int i = 0; i < data.getWeekdays() + 1; i++) {
                cell = row.createCell(i);
                cell.setCellValue(titles[i]);
                cell.setCellStyle(titleStyle);
            }
            for (LessonRow lessonRow : rows) {
                if (teacher.equals(lessonRow.getTeacher())) {
                    if (lessonRow.getTimeIndex() == data.getTime().getMorning()) {
                        row = sheet.createRow(rowNum++);
                        row.setHeightInPoints(20);
                    }
                    row = sheet.createRow(rowNum++);
                    row.setHeightInPoints(rowHeight);
                    cell = row.createCell(0);
                    cell.setCellStyle(cellStyle);
                    if (form.isShowTime() && StringUtils.isNotBlank(lessonRow.getTimes())) {
                        cell.setCellValue(String.format("%s\n(%s)", lessonRow.getTime(), lessonRow.getTimes()));
                    } else {
                        cell.setCellValue(lessonRow.getTime());
                    }
                    for (int i = 0; i < data.getWeekdays(); i++) {
                        cell = row.createCell(i + 1);
                        cell.setCellStyle(cellStyle);
                        LessonCell lessonCell = BeanUtil.getProperty(lessonRow, String.format("cell%d", i + 1));
                        if (lessonCell != null && StringUtils.isNotBlank(lessonCell.getCourse())) {
                            StringBuilder sb = new StringBuilder(lessonCell.getAdminclass());
                            sb.append("\n").append(lessonCell.getCourse());
                            if (form.isShowRoom()) {
                                sb.append("\n").append(lessonCell.getRoom());
                            }
                            cell.setCellValue(sb.toString());
                        }
                    }
                }
            }
        }
        return getFileName(wb, "教师课表");
    }

    private List<String> getTeachers(ScheduleData data, ViewAdminclassForm form) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isNotBlank(form.getTeacher())) {
            list.add(form.getTeacher());
            return list;
        }
        data.getPlans().forEach(plan -> {
            if (StringUtils.isNotBlank(form.getGrade()) && !plan.getGrade().equals(form.getGrade())) {
                return;
            }
            if (StringUtils.isNotBlank(form.getCourse()) && !plan.getCourseName().equals(form.getCourse())) {
                return;
            }
            if (!list.contains(plan.getTeacher())) {
                list.add(plan.getTeacher());
            }
        });
        return list;
    }

    @Override
    public String exportClassroom(ViewAdminclassForm form) {
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(form.getSchoolId());
        setRoom(data);
        List<LessonTime> times = lessonTimeService.getBySchoolId(data.getSchoolId());
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Sheet1");
        sheet.createFreezePane(1, 2);

        CellStyle titleStyle = getTitleStyle(sheet);
        CellStyle cellStyle = getCellStyle(sheet);
        int rowNum = 0, cellNum = 0, colWidth = 16 * 256, rowHeight = 60;
        String[] titles = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

        XSSFRow row;
        XSSFCell cell;
        row = sheet.createRow(rowNum++);
        row.setHeightInPoints(30);
        cell = row.createCell(0);
        cell.setCellValue("教学场所");
        cell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet.setColumnWidth(0, colWidth);
        for (int i = 0; i < data.getTimeLength(); i++) {
            sheet.setColumnWidth(i + 1, colWidth);
        }
        XSSFRow row2 = sheet.createRow(rowNum++);
        row2.setHeightInPoints(30);
        for (int i = 0; i < data.getWeekdays(); i++) {
            int start = i * data.getTimeOfDay() + 1;
            cell = row.createCell(start);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, start, start + data.getTimeOfDay() - 1));

            for (int j = 0; j < data.getTimeOfDay(); j++) {
                sheet.setColumnWidth(i + 1, colWidth);
                cell = row2.createCell(start + j);
                StringBuilder sb = new StringBuilder(String.format("第%d节", j + 1));
                if (form.isShowTime()) {
                    sb.append("\n").append(times.get(j).getTimes());
                }
                cell.setCellValue(sb.toString());
                cell.setCellStyle(titleStyle);
            }
        }

//        List<String> classrooms = data.getClassrooms();
        List<String> classrooms = form.getClassrooms();
        for (String classroom : classrooms) {
            cellNum = 0;
            row = sheet.createRow(rowNum++);
            row.setHeightInPoints(60);
            cell = row.createCell(cellNum++);
            cell.setCellValue(classroom);
            cell.setCellStyle(titleStyle);
            for (int i = 0; i < data.getWeekdays(); i++) {
                for (int j = 0; j < data.getTimeOfDay(); j++) {
                    cell = row.createCell(cellNum++);
//                    SchedulePlan plan = getSchedulePlanByClassroom(data, classroom, i, j);
//                    if (plan != null) {
//                        StringBuilder sb = new StringBuilder();
//                        sb.append(plan.getAdminclassName());
//                        sb.append("\n").append(plan.getCourseName());
//                        sb.append("\n").append(plan.getTeacher());
//                        cell.setCellValue(sb.toString());
//                    }
                    List<SchedulePlan> plans = getSchedulePlansByClassroom(data, classroom, i, j);
                    StringBuilder sb = new StringBuilder();
                    plans.forEach(plan -> {
                        if (sb.length() > 0) {
                            sb.append("\n");
                        }
                        sb.append(plan.getAdminclassName());
                        sb.append("\n").append(plan.getCourseName());
                        sb.append("\n").append(plan.getTeacher());
                    });
                    cell.setCellValue(sb.toString());
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        return getFileName(wb, "教室课表");
    }

    private SchedulePlan getSchedulePlanByClassroom(ScheduleData data, String classroom, int weekday, int time) {
        String key = String.format("%d-%d", weekday + 1, time + 1);
        return data.getPlans().stream().filter(p -> p.getClassroom().equals(classroom)
                && p.getPlanTimes().indexOf(key) >= 0).findAny().orElse(null);
    }

    private List<SchedulePlan> getSchedulePlansByClassroom(ScheduleData data, String classroom, int weekday, int time) {
        String key = String.format("%d-%d", weekday + 1, time + 1);
        return data.getPlans().stream().filter(p -> p.getClassroom().equals(classroom)
                && p.getPlanTimes().indexOf(key) >= 0).collect(Collectors.toList());
    }

    @Override
    public String exportAdminclasses(ViewAdminclassForm form) {
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(form.getSchoolId());
        setRoom(data);
        List<LessonTime> times = lessonTimeService.getBySchoolId(data.getSchoolId());
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Sheet1");
        sheet.createFreezePane(1, 2);

        CellStyle titleStyle = getTitleStyle(sheet);
        CellStyle cellStyle = getCellStyle(sheet);
        int rowNum = 0, cellNum = 0, colWidth = 16 * 256, rowHeight = 60;
        String[] titles = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

        XSSFRow row, row1, row2, row3;
        XSSFCell cell, cell1, cell2, cell3;
        row = sheet.createRow(rowNum++);
        row.setHeightInPoints(30);
        cell = row.createCell(0);
        cell.setCellValue("总课程表");
        cell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet.setColumnWidth(0, colWidth);
        for (int i = 0; i < data.getTimeLength(); i++) {
            sheet.setColumnWidth(i + 1, colWidth);
        }
        row2 = sheet.createRow(rowNum++);
        row2.setHeightInPoints(30);
        for (int i = 0; i < data.getWeekdays(); i++) {
            int start = i * data.getTimeOfDay() + 1;
            cell = row.createCell(start);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, start, start + data.getTimeOfDay() - 1));

            for (int j = 0; j < data.getTimeOfDay(); j++) {
                sheet.setColumnWidth(i + 1, colWidth);
                cell = row2.createCell(start + j);
                StringBuilder sb = new StringBuilder(String.format("第%d节", j + 1));
                if (form.isShowTime()) {
                    sb.append("\n").append(times.get(j).getTimes());
                }
                cell.setCellValue(sb.toString());
                cell.setCellStyle(titleStyle);
            }
        }

        List<String> adminclasses = new ArrayList<>();
        data.getPlans().forEach(p -> {
            if (form.getGrades().contains(p.getGrade())) {
                p.getAdminclasses().forEach(a -> {
                    if (!adminclasses.contains(a)) {
                        adminclasses.add(a);
                    }
                });
            }
        });
        for (String adminclass : adminclasses) {
            cellNum = 0;
            row1 = sheet.createRow(rowNum++);
            row2 = sheet.createRow(rowNum++);
            row3 = sheet.createRow(rowNum++);
            row1.setHeightInPoints(25);
            row2.setHeightInPoints(25);
            row3.setHeightInPoints(25);
            cell = row1.createCell(cellNum);
            cell.setCellValue(adminclass);
            cell.setCellStyle(titleStyle);
            cell = row3.createCell(cellNum++);
            cell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row3.getRowNum(), 0, 0));
            for (int i = 0; i < data.getWeekdays(); i++) {
                for (int j = 0; j < data.getTimeOfDay(); j++) {
                    cell1 = row1.createCell(cellNum);
                    cell2 = row2.createCell(cellNum);
                    cell3 = row3.createCell(cellNum++);
                    SchedulePlan plan = getSchedulePlanByAdminclass(data, adminclass, i, j);
                    if (plan != null) {
                        cell1.setCellValue(plan.getCourseName());
                        cell2.setCellValue(plan.getTeacher());
                        cell3.setCellValue(plan.getClassroom());
                    } else {
                        sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row3.getRowNum(), cell1.getColumnIndex(), cell1.getColumnIndex()));
                    }
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellStyle(cellStyle);
                    cell3.setCellStyle(cellStyle);
                }
            }
        }

        return getFileName(wb, "总课表");
    }

    @Override
    public String exportTeachers(ViewAdminclassForm form) {
        ScheduleData data = scheduleService.getScheduleDataBySchoolId(form.getSchoolId());
        setRoom(data);
        List<LessonTime> times = lessonTimeService.getBySchoolId(data.getSchoolId());
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("Sheet1");
        sheet.createFreezePane(1, 2);

        CellStyle titleStyle = getTitleStyle(sheet);
        CellStyle cellStyle = getCellStyle(sheet);
        int rowNum = 0, cellNum = 0, colWidth = 16 * 256, rowHeight = 60;
        String[] titles = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

        XSSFRow row, row1, row2, row3;
        XSSFCell cell, cell1, cell2, cell3;
        row = sheet.createRow(rowNum++);
        row.setHeightInPoints(30);
        cell = row.createCell(0);
        cell.setCellValue("总课程表");
        cell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet.setColumnWidth(0, colWidth);
        for (int i = 0; i < data.getTimeLength(); i++) {
            sheet.setColumnWidth(i + 1, colWidth);
        }
        row2 = sheet.createRow(rowNum++);
        row2.setHeightInPoints(30);
        for (int i = 0; i < data.getWeekdays(); i++) {
            int start = i * data.getTimeOfDay() + 1;
            cell = row.createCell(start);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, start, start + data.getTimeOfDay() - 1));

            for (int j = 0; j < data.getTimeOfDay(); j++) {
                sheet.setColumnWidth(i + 1, colWidth);
                cell = row2.createCell(start + j);
                StringBuilder sb = new StringBuilder(String.format("第%d节", j + 1));
                if (form.isShowTime()) {
                    sb.append("\n").append(times.get(j).getTimes());
                }
                cell.setCellValue(sb.toString());
                cell.setCellStyle(titleStyle);
            }
        }

        List<String> teachers = new ArrayList<>();
        data.getPlans().forEach(p -> {
            if (form.getGrades().contains(p.getGrade())) {
                if (!teachers.contains(p.getTeacher())) {
                    teachers.add(p.getTeacher());
                }
            }
        });
        for (String teacher : teachers) {
            cellNum = 0;
            row1 = sheet.createRow(rowNum++);
            row2 = sheet.createRow(rowNum++);
            row3 = sheet.createRow(rowNum++);
            row1.setHeightInPoints(25);
            row2.setHeightInPoints(25);
            row3.setHeightInPoints(25);
            cell = row1.createCell(cellNum);
            cell.setCellValue(teacher);
            cell.setCellStyle(titleStyle);
            cell = row3.createCell(cellNum++);
            cell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row3.getRowNum(), 0, 0));
            for (int i = 0; i < data.getWeekdays(); i++) {
                for (int j = 0; j < data.getTimeOfDay(); j++) {
                    cell1 = row1.createCell(cellNum);
                    cell2 = row2.createCell(cellNum);
                    cell3 = row3.createCell(cellNum++);
                    SchedulePlan plan = getSchedulePlanByTeacher(data, teacher, i, j);
                    if (plan != null) {
                        cell1.setCellValue(plan.getCourseName());
                        cell2.setCellValue(plan.getTeacher());
                        cell3.setCellValue(plan.getClassroom());
                    } else {
                        sheet.addMergedRegion(new CellRangeAddress(row1.getRowNum(), row3.getRowNum(), cell1.getColumnIndex(), cell1.getColumnIndex()));
                    }
                    cell1.setCellStyle(cellStyle);
                    cell2.setCellStyle(cellStyle);
                    cell3.setCellStyle(cellStyle);
                }
            }
        }

        return getFileName(wb, "总任课表");
    }

    private SchedulePlan getSchedulePlanByAdminclass(ScheduleData data, String adminclass, int weekday, int time) {
        String key = String.format("%d-%d", weekday + 1, time + 1);
        return data.getPlans().stream().filter(p -> p.getAdminclasses().contains(adminclass)
                && p.getPlanTimes().indexOf(key) >= 0).findFirst().orElse(null);
    }

    private SchedulePlan getSchedulePlanByTeacher(ScheduleData data, String teacher, int weekday, int time) {
        String key = String.format("%d-%d", weekday + 1, time + 1);
        return data.getPlans().stream().filter(p -> p.getTeacher() != null && p.getTeacher().equals(teacher)
                && p.getPlanTimes().indexOf(key) >= 0).findFirst().orElse(null);
    }

    private String getFileName(XSSFWorkbook wb, String sheetName) {
        ExcelUtil excelUtil = new ExcelUtil(LessonRow.class);
        String filename = excelUtil.encodingFilename(sheetName);
        try (
                OutputStream out = new FileOutputStream(excelUtil.getAbsoluteFile(filename));
        ) {
            wb.write(out);
        } catch (Exception e) {
            throw new UtilException("导出Excel失败！", e);
        }
        return filename;
    }

}
