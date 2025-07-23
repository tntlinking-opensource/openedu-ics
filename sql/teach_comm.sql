insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('基础数据', '0', '0', 'school', null, 1, 0, 'M', '0', '0', '', '#', 'admin', sysdate(), null, null, null);

SELECT @parentId := (select min(menu_id) from sys_menu where path = 'school');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('学年学期', @parentId, '1', 'teachCalendar', 'teach/school/teachCalendar/index', 1, 0, 'C', '0', '0',
        'teach.school:teachCalendar', '#', 'admin', sysdate(), '', null, '学年学期菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('年级', @parentId, '2', 'grade', 'teach/school/grade/index', 1, 0, 'C', '0', '0',
        'teach.school:grade', '#', 'admin', sysdate(), '', null, '年级菜单');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('课程', @parentId, '3', 'course', 'teach/school/course/index', 1, 0, 'C', '0', '0',
        'teach.school:course', '#', 'admin', sysdate(), '', null, '课程菜单');

drop table if exists ts_teach_calendar;
create table ts_teach_calendar
(
    id         bigint(20)  not null auto_increment comment 'ID',
    school_id  bigint(20)  not null comment '学校ID',
    year       varchar(64) not null comment '学年',
    term       varchar(64) not null comment '学期',
    start_date date        not null comment '开始日期',
    end_date   date        not null comment '开始日期',
    current    varchar(10) not null comment '是否当前学期',
    week_num   int         not null comment '周数',
    del_flag   int default 0 comment '删除标志 0:正常 1:已删除',
    primary key (id)
) comment = '学年学期';

drop table if exists ts_grade;
create table ts_grade
(
    id         bigint      not null auto_increment comment 'ID',
    school_id  bigint      not null comment '学校ID',
    code       varchar(16) not null comment '编号',
    name       varchar(32) not null comment '名称',
    teacher_id bigint comment '年级组长',
    deleted    tinyint default 0 comment '是否删除',
    primary key (id)
) comment = '年级';

drop table if exists ts_teacher;
create table ts_teacher
(
    id        bigint(20)  not null auto_increment comment 'ID',
    school_id bigint(20)  not null comment '学校ID',
    user_id   bigint(20)  not null comment '用户ID',
    code      varchar(20) not null comment '工号',
    name      varchar(64) not null comment '姓名',
    phone     varchar(20) comment '手机号',
    email     varchar(100) comment '邮箱',
    card      varchar(30) comment '卡号',
    status    varchar(1) default '0' comment '状态',
    primary key (id)
) comment = '教师';

alter table ts_teacher
    add unique index ts_teacher_code (school_id, code);

drop table if exists ts_course;
create table ts_course
(
    id        bigint(20)  not null auto_increment comment 'ID',
    school_id bigint(20)  not null comment '学校ID',
    sort      int         not null comment '排序',
    code      varchar(64) not null comment '代码',
    name      varchar(64) not null comment '名称',
    criterion float      default 1.00 comment '系数',
    status    varchar(1) default '0' comment '状态',
    primary key (id)
) comment = '课程';

drop table if exists ts_lesson_time;
create table ts_lesson_time
(
    id         bigint(20)  not null auto_increment comment 'ID',
    school_id  bigint(20)  not null comment '学校ID',
    sort       int         not null comment '排序',
    name       varchar(64) not null comment '名称',
    start_time varchar(10)  comment '开始时间',
    end_time   varchar(10)  comment '结束时间',
    category   varchar(64) not null comment '上午/下午',
    primary key (id)
) comment = '上课节次';
ALTER TABLE ts_lesson_time
    add lesson_type varchar(255) default '课程' comment '节次类型';
ALTER TABLE ts_lesson_time
    add time_index int comment '节次顺序';
