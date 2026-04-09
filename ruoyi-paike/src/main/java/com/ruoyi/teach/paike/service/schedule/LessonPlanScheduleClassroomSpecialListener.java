package com.ruoyi.teach.paike.service.schedule;

import com.ruoyi.teach.paike.entity.ScheduleClassroom;
import com.ruoyi.teach.paike.entity.ScheduleData;
import com.ruoyi.teach.paike.entity.ScheduleTask;
import com.ruoyi.teach.paike.service.LessonScheduleConstant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 特殊教室
 */
@Component
public class LessonPlanScheduleClassroomSpecialListener extends LessonPlanScheduleListenerParent {

    @Override
    protected String getRuleName() {
        return LessonScheduleConstant.RULE_CLASSROOM_SPECIALS;
    }

    /**
     * 当某个时间排课数达到限制数时，将其它任务的该时间标记为不可用
     * @param data
     * @param lessonTasks
     * @param task
     */
    @Override
    public void afterScheduling(ScheduleData data, List<ScheduleTask> lessonTasks, ScheduleTask task) {
        long time = task.getTime();
        for (ScheduleClassroom room : data.getRooms()) {
            if (!room.match(task.getCourseName(), task.getGrade(), task.getAdminclasses())) {
                continue;
            }
            int count = 0;
            List<ScheduleTask> tasks = data.getTasks().stream()
                    .filter(t -> room.match(t.getCourseName(), t.getGrade(), t.getAdminclasses()))
                    .collect(Collectors.toList());
            for (ScheduleTask lessonTask : tasks) {
                if ((lessonTask.getTime() & time) > 0) {
                    count++;
                }
            }
            if (count >= room.getLessonLimit()) {
                tasks.forEach(t -> {
                    t.setTimeSuggest(t.getTimeSuggest() & (~time));
                    t.setTimeAvailable(t.getTimeAvailable() & (~time));
                });
            }
        }
    }
}
