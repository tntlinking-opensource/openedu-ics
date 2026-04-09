import request from '@/utils/request'

export function getIndexDatas() {
  return request({
    url: '/teach/school/course/getIndexDatas',
    method: 'get',
  })
}

// 查询课程列表
export function listCourse(query) {
  return request({
    url: '/teach/school/course/list',
    method: 'get',
    params: query
  })
}

// 查询课程详细
export function getCourse(id) {
  return request({
    url: '/teach/school/course/' + id,
    method: 'get'
  })
}

// 新增课程
export function addCourse(data) {
  return request({
    url: '/teach/school/course',
    method: 'post',
    data: data
  })
}

// 修改课程
export function updateCourse(data) {
  return request({
    url: '/teach/school/course',
    method: 'put',
    data: data
  })
}

// 删除课程
export function delCourse(data) {
  return request({
    url: '/teach/school/course',
    method: 'delete',
    data
  })
}

// 导出课程
export function exportCourse(query) {
  return request({
    url: '/teach/school/course/export',
    method: 'get',
    params: query
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/teach/school/course/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}

