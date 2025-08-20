-- 2025-08-20
alter table tpk_classroom_special
    change course course varchar (255) not null comment '课程';

-- 2025-08-19
alter table tpk_lesson_schedule_record
    change json json longtext not null comment '数据';

-- 2025-08-07
alter table ts_teacher
    add name2 varchar(255) comment '别名';

-- 2022-01-04
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('排课', '0', '400', 'paike', null, 1, 0, 'M', '1', '0', '', '#', 'admin', sysdate(), null, null, null);

SELECT @parentId := (select min(menu_id) from sys_menu where path = 'paike');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('排课任务', @parentId, '1', 'lessonSchedule', 'teach/paike/lessonSchedule/index', 1, 0, 'C', '0', '0',
        'teach.paike:lessonSchedule', '#', 'admin', sysdate(), '', null, '排课任务菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('基础信息', @parentId, '2', 'base', 'teach/paike/base/index', 1, 0, 'C', '0', '0', '', '#', 'admin', sysdate(),
        null, null, null);

SELECT @parentId2 := (select min(menu_id) from sys_menu where parent_id = @parentId and path = 'base');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('作息安排', @parentId2, '1', 'time', 'teach/paike/base/time/index', 1, 0, 'C', '0', '0', 'teach.paike:time',
        '#',
        'admin',
        sysdate(), '', null, '作息安排菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('任课信息', @parentId2, '2', 'lessonPlan', 'teach/paike/base/lessonPlan/index', 1, 0, 'C', '0', '0',
        'teach.paike:lessonPlan', '#', 'admin', sysdate(), '', null, '任课信息菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('班级教室', @parentId2, '3', 'classroom', 'teach/paike/base/classroom/index', 1, 0, 'C', '0', '0',
        'teach.paike:classroom',
        '#', 'admin', sysdate(), '', null, '班级教室菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('特殊教室', @parentId2, '4', 'classroomSpecial', 'teach/paike/base/classroomSpecial/index', 1, 0, 'C', '0', '0',
        'teach.paike:classroomSpecial', '#', 'admin', sysdate(), '', null, '特殊教室菜单');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('智能排课', @parentId, '3', 'intellect', 'teach/paike/intellect/index', 1, 0, 'C', '0', '0', '', '#', 'admin',
        sysdate(), null, null, null);

SELECT @parentId2 := (select min(menu_id) from sys_menu where parent_id = @parentId and path = 'intellect');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('全校排课时间', @parentId2, '1', 'schoolTimes', 'teach/paike/intellect/schoolTimes/index', 1, 0, 'C', '0', '0',
        'teach.paike:schoolTimes', '#', 'admin', sysdate(), '', null, '全校排课时间菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('课程排课时间', @parentId2, '2', 'courseTimes', 'teach/paike/intellect/courseTimes/index', 1, 0, 'C', '0', '0',
        'teach.paike:courseTimes', '#', 'admin', sysdate(), '', null, '课程排课时间菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('班级排课时间', @parentId2, '3', 'adminclassTimes', 'teach/paike/intellect/adminclassTimes/index', 1, 0, 'C',
        '0',
        '0',
        'teach.paike:adminclassTimes', '#', 'admin', sysdate(), '', null, '班级排课时间菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('教师排课时间', @parentId2, '4', 'teacherTimes', 'teach/paike/intellect/teacherTimes/index', 1, 0, 'C', '0',
        '0',
        'teach.paike:teacherTimes', '#', 'admin', sysdate(), '', null, '教师排课时间菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('合班', @parentId2, '5', 'mixedClass', 'teach/paike/intellect/mixedClass/index', 1, 0, 'C', '0', '0',
        'teach.paike:mixedClass', '#', 'admin', sysdate(), '', null, '合班菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('预排', @parentId2, '6', 'lessonSchedulePre', 'teach/paike/intellect/lessonSchedulePre/index', 1, 0, 'C', '0',
        '0',
        'teach.paike:lessonSchedulePre', '#', 'admin', sysdate(), '', null, '排课预排菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('自动排课', @parentId2, '7', 'lessonScheduleAuto', 'teach/paike/intellect/lessonScheduleAuto/index', 1, 0, 'C',
        '0',
        '0',
        'teach.paike:lessonScheduleAuto', '#', 'admin', sysdate(), '', null, '排课自动排课菜单');
-- 课表调整
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('课表调整', @parentId, '4', 'adjust', 'teach/paike/view/index', 1, 0, 'C', '0', '0', '', '#', 'admin',
        sysdate(), null, null, null);

SELECT @parentId2 := (select min(menu_id) from sys_menu where parent_id = @parentId and path = 'adjust');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('按班级调课', @parentId2, '1', 'adminclass', 'teach/paike/adjust/adminclass', 1, 0, 'C', '0', '0',
        'teach.paike:adjust', '#', 'admin', sysdate(), '', null, '按班级调课菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('按教师调课', @parentId2, '2', 'teacher', 'teach/paike/adjust/teacher', 1, 0, 'C', '0', '0',
        'teach.paike:adjust', '#', 'admin', sysdate(), '', null, '按教师调课菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('排课存档', @parentId2, '3', 'record', 'teach/paike/adjust/record/index', 1, 0, 'C', '0', '0',
        'teach.paike:record', '#', 'admin', sysdate(), '', null, '排课存档菜单');
-- 查看课表
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('查看课表', @parentId, '5', 'view', 'teach/paike/view/index', 1, 0, 'C', '0', '0', '', '#', 'admin', sysdate(),
        null, null, null);

SELECT @parentId2 := (select min(menu_id) from sys_menu where parent_id = @parentId and path = 'view');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('班级课表', @parentId2, '1', 'adminclass', 'teach/paike/view/adminclass', 1, 0, 'C', '0', '0',
        'teach.paike:view', '#', 'admin', sysdate(), '', null, '班级课表菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('教师课表', @parentId2, '2', 'teacher', 'teach/paike/view/teacher', 1, 0, 'C', '0', '0',
        'teach.paike:view', '#', 'admin', sysdate(), '', null, '教师课表菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('教室课表', @parentId2, '3', 'classroom', 'teach/paike/view/classroom', 1, 0, 'C', '0', '0',
        'teach.paike:view', '#', 'admin', sysdate(), '', null, '教室课表菜单');


drop table if exists tpk_lesson_schedule;
create table tpk_lesson_schedule
(
    id                bigint(20)   not null auto_increment comment 'ID',
    school_id         bigint(20)   not null comment '学校ID',
    teach_calender_id bigint(20)   not null comment '学年学期',
    name              varchar(255) not null comment '名称',
    is_conflict       varchar(1) default 'N' comment '状态',
    is_auto_schedule  varchar(1) default 'N' comment '是否自动排课',
    is_current        varchar(1)   not null comment '是否当前排课',
    create_time       timestamp  default CURRENT_TIMESTAMP comment '创建时间',
    update_time       timestamp  default CURRENT_TIMESTAMP comment '最后更新时间',
    primary key (id)
) comment = '排课任务';

drop table if exists tpk_lesson_schedule_time;
create table tpk_lesson_schedule_time
(
    id                 BIGINT not null auto_increment comment 'ID',
    school_id          BIGINT not null comment '学校ID',
    lesson_schedule_id BIGINT not null comment '排课任务ID',
    weekdays           int    not null comment '每周天数',
    morning            int    not null comment '上午节数',
    afternoon          int    not null comment '下午节数',
    night              int    not null comment '晚上节数',
    primary key (id),
    UNIQUE INDEX `lesson_schedule_id_UNIQUE` (`lesson_schedule_id` ASC)
) comment = '作息安排';

drop table if exists tpk_lesson_plan;
create table tpk_lesson_plan
(
    id                 BIGINT      not null auto_increment comment 'ID',
    school_id          BIGINT      not null comment '学校ID',
    lesson_schedule_id BIGINT      not null comment '排课任务ID',
    grade              varchar(8)  not null comment '年级',
    adminclass         varchar(8)  not null comment '班级',
    course             varchar(16) not null comment '课程',
    hours              varchar(16) not null comment '课时',
    hour               int         not null comment '课时',
    teacher            varchar(16) not null comment '任课教师',
    times_pres         varchar(255) comment '预排',
    times              varchar(255) comment '排课',
    primary key (id)
) comment = '任课信息';

drop table if exists tpk_classroom;
create table tpk_classroom
(
    id                 BIGINT      not null auto_increment comment 'ID',
    school_id          BIGINT      not null comment '学校ID',
    lesson_schedule_id BIGINT      not null comment '排课任务ID',
    grade              varchar(10) not null comment '年级',
    adminclass         varchar(10) comment '班级',
    building           varchar(10) not null comment '教学楼',
    room               varchar(10) not null comment '教室号',
    primary key (id)
) comment = '班级教室';

drop table if exists tpk_classroom_special;
create table tpk_classroom_special
(
    id                 BIGINT       not null auto_increment comment 'ID',
    school_id          BIGINT       not null comment '学校ID',
    lesson_schedule_id BIGINT       not null comment '排课任务ID',
    course             varchar(10)  not null comment '课程',
    building           varchar(10)  not null comment '教学楼',
    room               varchar(10) comment '教室号',
    lesson_limit       int          not null comment '最大开班数',
    grade              varchar(255) not null comment '年级',
    adminclass         varchar(255) comment '班级',
    primary key (id)
) comment = '特殊教室';

drop table if exists tpk_lesson_schedule_rule;
create table tpk_lesson_schedule_rule
(
    id                 BIGINT       not null auto_increment comment 'ID',
    school_id          BIGINT       not null comment '学校ID',
    lesson_schedule_id BIGINT       not null comment '排课任务ID',
    name               varchar(32)  not null comment '名称',
    code               varchar(255) not null comment '代码',
    content            varchar(255) not null comment '内容',
    json               text         not null comment '数据',
    primary key (id)
) comment = '排课规则';

drop table if exists tpk_lesson_schedule_record;
create table tpk_lesson_schedule_record
(
    id                 BIGINT       not null auto_increment comment 'ID',
    lesson_schedule_id BIGINT       not null comment '排课任务ID',
    name               varchar(255) not null comment '名称',
    json               text         not null comment '数据',
    create_time        timestamp    not null comment '创建时间',
    primary key (id)
) comment = '排课存档';