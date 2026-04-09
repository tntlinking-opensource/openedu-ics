package com.ruoyi.teach.paike.intellect.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.teach.paike.controller.LessonScheduleRuleBaseController;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import com.ruoyi.teach.paike.domain.LessonScheduleTime;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程排课时间Controller
 *
 * @author ruoyi
 * @date 2021-10-28
 */
@RestController
@RequestMapping("/teach/paike/courseTimes")
public class CourseTimesController extends LessonScheduleRuleBaseController {

	@Override
	protected String getName() {
		return "课程不排课时间";
	}

	/**
	 * 获取班级教室详细信息
	 */
	@PreAuthorize("@ss.hasPermi('teach.paike:classroom')")
	@GetMapping(value = {"/getIndexDatas"})
	public AjaxResult getIndexDatas() {
		AjaxResult ajax = AjaxResult.success();
		ajax.put("timeConfig", lessonScheduleTimeService.getByLessonScheduleId(getLessonScheduleId()));
		ajax.put("courses", lessonPlanService.getGradeAndCourse(getLessonScheduleId()));
		return ajax;
	}

	/**
	 * 查询课程排课时间列表
	 */
	@PreAuthorize("@ss.hasPermi('teach.paike:courseTimes')")
	@GetMapping("/list")
	public TableDataInfo list(LessonScheduleRule form) {
		TableDataInfo list = super.list(form);
		List<LessonScheduleRule> rules = (List<LessonScheduleRule>) list.getRows();
		Long lessonScheduleId = getLessonScheduleId();
		List<String> grades = lessonPlanService.getGrades(lessonScheduleId);
		LessonScheduleTime time = lessonScheduleTimeService.getByLessonScheduleId(lessonScheduleId);
		String[] courses = new String[]{"语文", "数学", "英语"};
		for (String grade : grades) {
			for (String course : courses) {
				String code = grade + course;
				if (list.getRows().stream().filter(v -> code.equals(((LessonScheduleRule) v).getCode())).count() > 0) {
					continue;
				}
				LessonScheduleRule rule = new LessonScheduleRule();
				rule.setCode(code);
				rule.setContent(rule.getCode() + "下午");
//				rule.setJson("{\"times\":\"1111000;1111000;1111000;1111000;1111000\"}");
				StringBuilder sb = new StringBuilder("{\"times\":\"");
				for (int i = 0; i < 5; i++) {
					if (i > 0) {
						sb.append(";");
					}
					for (int j = 0; j < time.getMorning(); j++) {
						sb.append("1");
					}
					for (int j = 0; j < time.getAfternoon(); j++) {
						sb.append("0");
					}
				}
				sb.append("\"}");
				rule.setJson(sb.toString());
				rule.setName(getName());
				rules.add(rule);
			}
		}
		return list;
	}

	/**
	 * 修改课程排课时间
	 */
	@PreAuthorize("@ss.hasPermi('teach.paike:courseTimes')")
	@Log(title = "课程排课时间", businessType = BusinessType.UPDATE)
	@PostMapping
	public AjaxResult saveOrUpdate(@RequestBody List<LessonScheduleRule> rules) {
		return super.saveOrUpdate(rules);
	}

}
