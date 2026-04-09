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


    private ScheduleClassroom getClassroomSpecial(List<ScheduleClassroom> roomSpecials, String grade, String course, String adminclass) {
        return roomSpecials.stream().filter(r -> {
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
