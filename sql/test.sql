-- 重置密码
update sys_user
set password = '$2a$10$JmzFN5s0YrNLrU1J9Bdt5us99nZWrl8LdyMep3EytrWqlJPjHEuH6'
where user_id > 0;

-- 重置admin密码：Yzsoft@20250808
update sys_user
set password = '$2a$10$E2qK2lasv7US8576EX4ztOa/HwfiLEDFfMiJeQAR9LNfmWutdewu6'
where user_id = 1;

select *
from sys_user where user_id = 1;
