package com.ruoyi.teach.paike.service.impl;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.UtilException;
import com.ruoyi.common.utils.RuoYiUtil;
import com.ruoyi.teach.paike.PaikeUtil;
import com.ruoyi.teach.paike.domain.LessonPlan;
import com.ruoyi.teach.paike.service.LessonPlanImportService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LessonPlanImportServiceImpl implements LessonPlanImportService {

    @Override
    public List<LessonPlan> importExcel(MultipartFile file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        List<LessonPlan> plans = new ArrayList<>();
//        List<String> courses = getCourses(workbook);
        for (int k = 0; k < workbook.getNumberOfSheets(); k++) {
            Sheet sheet = workbook.getSheetAt(k);
            List<String> courses = getCourses(sheet);
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null || row.getLastCellNum() < 3) {
                    continue;
                }
                String grade = getCellValue(row, 0);
                String adminclass = getCellValue(row, 1);
                if (StringUtils.isBlank(grade) || StringUtils.isBlank(adminclass)) {
                    continue;
                }
                for (int j = 0; j < courses.size(); j++) {
                    String course = courses.get(j);
                    if (StringUtils.isBlank(course)) {
                        continue;
                    }
                    String hours = getCellValue(row, j * 2 + 2);
                    String teacher = getCellValue(row, j * 2 + 3);
                    if (StringUtils.isBlank(hours)) {
                        continue;
                    }
                    hours = StringUtils.replace(hours, ".0", "");
                    hours = StringUtils.trim(hours);
                    LessonPlan plan = new LessonPlan();
                    plan.setGrade(grade);
//                plan.setAdminclass(grade + adminclass);
                    plan.setAdminclass(adminclass);
                    plan.setHours(hours);
                    plan.setHour(PaikeUtil.getHour(hours));
                    plan.setCourse(course);
                    plan.setTeacher(teacher);
                    plans.add(plan);
                }
            }
        }
        return plans;
    }

    private List<String> getCourses(Sheet sheet) {
        Row row = sheet.getRow(1);
        List<String> courses = new ArrayList<>();
        for (int i = 2; i < row.getLastCellNum(); i += 2) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                break;
            }
            courses.add(getCellValue(row, i));
        }
        return courses;
    }

    public String getCellValue(Row row, int column) {
        if (row == null) {
            return null;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                    val = cell.getNumericCellValue();
                } else {
                    val = cell.getStringCellValue();
                }

            }
        } catch (Exception e) {
        }
        return val.toString();
    }

    @Override
    public AjaxResult exportExcel(List<LessonPlan> list) {
        SXSSFWorkbook wb = new SXSSFWorkbook(list.size() + 1);
        CellStyle titleStyle = createTitileStyle(wb);
        CellStyle cellStyle = createCellStyle(wb);
        OutputStream out = null;
        try {
            List<String> grades = getGrades(list);
            for (String grade : grades) {
                Sheet sheet = wb.createSheet(grade);
                List<LessonPlan> plans = getPlans(list, grade);
                List<String> adminclasses = getAdminclasses(plans);
                List<String> courses = getCourses(plans);
                Row row = sheet.createRow(0);
                row.setHeightInPoints(25);
                row.createCell(0).setCellValue(grade);
                row = sheet.createRow(1);
                row.setHeightInPoints(25);
                createCell(row, 0, titleStyle, "年级");
                createCell(row, 1, titleStyle, "班级");
                for (int i = 0; i < courses.size(); i++) {
                    createCell(row, i * 2 + 2, titleStyle, courses.get(i));
                    createCell(row, i * 2 + 3, titleStyle, "教师姓名");
                }
                for (int i = 0; i < adminclasses.size(); i++) {
                    String adminclass = adminclasses.get(i);
                    row = sheet.createRow(i + 2);
                    row.setHeightInPoints(25);
                    createCell(row, 0, cellStyle, grade);
                    createCell(row, 1, cellStyle, adminclass);
                    for (int i1 = 0; i1 < courses.size(); i1++) {
                        String course = courses.get(i1);
                        LessonPlan plan = getLessonPlan(plans, adminclass, course);
                        if (plan != null) {
                            createCell(row, i1 * 2 + 2, cellStyle, plan.getHours());
                            createCell(row, i1 * 2 + 3, cellStyle, plan.getTeacher());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String filename = UUID.randomUUID() + "_任课信息数据.xlsx";
            String file = RuoYiUtil.getAbsoluteFile(filename);
            out = new FileOutputStream(file);
            wb.write(out);
            return AjaxResult.success(filename);
        } catch (Exception e) {
            throw new UtilException("导出Excel失败，请联系网站管理员！");
        } finally {
            IOUtils.closeQuietly(wb);
            IOUtils.closeQuietly(out);
        }
    }

    private void createCell(Row row, int i, CellStyle cellStyle, String value) {
        Cell cell = row.createCell(i);
        cell.setCellStyle(cellStyle);
        cell.setCellValue(value);
    }

    private CellStyle createTitileStyle(SXSSFWorkbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        return cellStyle;
    }

    private CellStyle createCellStyle(SXSSFWorkbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private List<String> getGrades(List<LessonPlan> plans) {
        return plans.stream().map(p -> p.getGrade()).distinct().collect(Collectors.toList());
    }

    private List<LessonPlan> getPlans(List<LessonPlan> list, String grade) {
        return list.stream().filter(p -> p.getGrade().equals(grade)).collect(Collectors.toList());
    }

    private List<String> getAdminclasses(List<LessonPlan> plans) {
        List<String> list = plans.stream().map(p -> p.getAdminclass()).distinct().collect(Collectors.toList());
        Collections.sort(list);
        return list;
    }

    private List<String> getCourses(List<LessonPlan> plans) {
        return plans.stream().map(p -> p.getCourse()).distinct().collect(Collectors.toList());
    }

    private LessonPlan getLessonPlan(List<LessonPlan> plans, String adminclass, String course) {
        return plans.stream().filter(p ->
                p.getAdminclass().equals(adminclass) && p.getCourse().equals(course)).findAny().orElse(null);
    }

}
