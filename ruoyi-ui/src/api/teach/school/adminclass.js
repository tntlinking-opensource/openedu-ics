import request from '@/utils/request'

// 查询班级列表
export function listAdminclass(query) {
  return request({
    url: '/teach/school/adminclass/list',
    method: 'get',
    params: query
  })
}

// 查询班级详细
export function getAdminclass(id) {
  return request({
    url: '/teach/school/adminclass/' + id,
    method: 'get'
  })
}

// 查询班级详细
export function getEditDatas() {
  return request({
    url: '/teach/school/adminclass/getEditDatas',
    method: 'get'
  })
}

// 新增班级
export function addAdminclass(data) {
  return request({
    url: '/teach/school/adminclass',
    method: 'post',
    data: data
  })
}

// 修改班级
export function updateAdminclass(data) {
  return request({
    url: '/teach/school/adminclass',
    method: 'put',
    data: data
  })
}

// 删除班级
export function delAdminclass(data) {
  return request({
    url: '/teach/school/adminclass',
    method: 'delete',
    data
  })
}

// 导出班级
export function exportAdminclass(query) {
  return request({
    url: '/teach/school/adminclass/export',
    method: 'get',
    params: query
  })
}
// 查询教师
export function getTeachers() {
  return request({
    url: '/teach/school/adminclass/getTeachers',
    method: 'get'
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/teach/school/adminclass/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
