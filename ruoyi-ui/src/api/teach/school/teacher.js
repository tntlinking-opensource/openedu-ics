import request from '@/utils/request'

// 查询教师列表
export function listTeacher(query) {
  return request({
    url: '/teach/school/teacher/list',
    method: 'get',
    params: query
  })
}

// 查询教师列表
export function getTeacherList(query) {
  return request({
    url: '/common/school/list',
    method: 'get',
    params: query
  })
}

// 查询班级详细
export function getEditDatas() {
  return request({
    url: '/teach/school/teacher/getEditDatas',
    method: 'get'
  })
}

// 查询教师详细
export function getTeacher(id) {
  return request({
    url: '/teach/school/teacher/' + id,
    method: 'get'
  })
}

// 新增教师
export function addTeacher(data) {
  return request({
    url: '/teach/school/teacher',
    method: 'post',
    data: data
  })
}

// 修改教师
export function updateTeacher(data) {
  return request({
    url: '/teach/school/teacher',
    method: 'put',
    data: data
  })
}

// 删除教师
export function delTeacher(data) {
  return request({
    url: '/teach/school/teacher',
    method: 'delete',
    data
  })
}

// 导出教师
export function exportTeacher(query) {
  return request({
    url: '/teach/school/teacher/export',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/teach/school/teacher/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
