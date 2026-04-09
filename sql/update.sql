-- 2025-10-09
alter table ts_teacher
    add openid varchar(64);
alter table ts_teacher
    add activate varchar(8) default 'yes' comment '是否激活';
alter table ts_teacher
    add del_flag varchar(8) default '0' comment '是否删除';
alter table ts_teacher
    add college_id bigint comment '所属学院';
alter table ts_teacher
    add faculty_id bigint comment '所属院系';