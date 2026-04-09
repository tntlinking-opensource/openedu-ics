package com.ruoyi.teach.school.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.teach.school.domain.Adminclass;
import com.ruoyi.teach.school.domain.TeachCalendar;
import com.ruoyi.teach.school.domain.Teacher;
import com.ruoyi.teach.school.service.AdminclassService;
import com.ruoyi.teach.school.service.GradeService;
import com.ruoyi.teach.school.service.TeachCalendarService;
import com.ruoyi.teach.school.service.TeacherService;
import com.ruoyi.teach.school.vo.DepartmentType;
import com.ruoyi.teach.system.domain.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 班级Controller
 *
 * @author beangle
 * @date 2021-07-07
 */
@RestController
@RequestMapping("/teach/school/adminclass")
public class AdminclassController extends SchoolBaseController {
	@Autowired
	private GradeService gradeService;
	@Autowired
	private AdminclassService adminclassService;
	@Autowired
	private TeacherService teacherService;
	@Autowired
	private TeachCalendarService teachCalendarService;

	/**
	 * 查询班级列表
	 */
	@PreAuthorize("@ss.hasPermi('teach.school:adminclass')")
	@GetMapping("/list")
	public TableDataInfo list(Adminclass adminclass) {
		startPage();
		List<Adminclass> list = adminclassService.selectAdminclassList(adminclass);
		return getDataTable(list);
	}

	/**
	 * 导出班级列表
	 */
	@PreAuthorize("@ss.hasPermi('teach.school:adminclass')")
	@Log(title = "班级", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(Adminclass adminclass) {
		List<Adminclass> list = adminclassService.selectAdminclassList(adminclass);
		ExcelUtil<Adminclass> util = new ExcelUtil<Adminclass>(Adminclass.class);
		return util.exportExcel(list, "班级数据");
	}

	/**
	 * 获取班级详细信息
	 */
	@PreAuthorize("@ss.hasPermi('teach.school:adminclass')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(adminclassService.selectAdminclassById(id));
	}

	@GetMapping(value = {"/getEditDatas"})
	public AjaxResult getEditDatas() {
		AjaxResult result = AjaxResult.success();
		Long schoolId = getSchoolId();
		TeachCalendar calendar = teachCalendarService.getTeachCalendarCurrent(schoolId);
		result.put("years", teachCalendarService.getYears(schoolId));
		result.put("year", calendar.getYear());
		result.put("teachers", teacherService.getBySchoolId(schoolId));
		result.put("grades", gradeService.getBySchool(schoolId));
		return result;
	}

	/**
	 * 新增班级
	 */
	@PreAuthorize("@ss.hasPermi('teach.school:adminclass')")
	@Log(title = "班级", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody Adminclass adminclass) {
		School school = getSchool();
		return toAjax(adminclassService.insertAdminclass(school.getId(), adminclass));
	}

	/**
	 * 修改班级
	 */
	@PreAuthorize("@ss.hasPermi('teach.school:adminclass')")
	@Log(title = "班级", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody Adminclass adminclass) {
		School school = getSchool();
		return toAjax(adminclassService.updateAdminclass(school.getId(), adminclass));
	}

	/**
	 * 删除班级
	 */
	@PreAuthorize("@ss.hasPermi('teach.school:adminclass')")
	@Log(title = "班级", businessType = BusinessType.DELETE)
	@DeleteMapping
	public AjaxResult remove(@RequestBody Long[] ids) {
		School school = getSchool();
		return toAjax(adminclassService.deleteAdminclassByIds(school.getId(), ids));
	}

	@Log(title = "用户管理", businessType = BusinessType.IMPORT)
	@PreAuthorize("@ss.hasPermi('teach.school:adminclass')")
	@PostMapping("/importData")
	public AjaxResult importData(MultipartFile file) throws Exception {
		ExcelUtil<Adminclass> util = new ExcelUtil<>(Adminclass.class);
		List<Adminclass> adminclasses = util.importExcel(file.getInputStream());
		String message = adminclassService.importAdminclass(adminclasses);
		return AjaxResult.success(message);
	}

	@GetMapping("/importTemplate")
	public AjaxResult importTemplate() {
		ExcelUtil<Adminclass> util = new ExcelUtil<>(Adminclass.class);
		return util.importTemplateExcel("班级导入模板");
	}

	@GetMapping("/getTeachers")
	public AjaxResult getTeachers() {
		List<Teacher> teachers = teacherService.getTeachers();
		return AjaxResult.success(teachers);
	}

}
