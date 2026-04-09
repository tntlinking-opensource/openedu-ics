package com.ruoyi.teach.paike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.teach.paike.domain.LessonScheduleRule;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 排课规则Mapper接口
 *
 * @author yzsoft
 * @date 2021-11-01
 */
public interface LessonScheduleRuleMapper extends BaseMapper<LessonScheduleRule> {

    /**
     * 查询排课规则列表
     *
     * @param lessonScheduleRule 排课规则
     * @return 排课规则集合
     */
    List<LessonScheduleRule> selectLessonScheduleRuleList(LessonScheduleRule lessonScheduleRule);

    @Select("select * from tpk_lesson_schedule_rule" +
            " where lesson_schedule_id = #{param1}")
    List<LessonScheduleRule> selectAll(Long lessonScheduleId);

    @Delete("delete from tpk_lesson_schedule_rule" +
            " where lesson_schedule_id = #{param1} and name = #{param2}")
    void deleteByLessonScheduleIdAndName(Long lessonScheduleId, String name);

    void copy(Long sourceId, Long targetId);
}
