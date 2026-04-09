import request from '@/utils/request'

// 查询全校排课时间列表
export function listSchoolTimes(query) {
  return request({
    url: '/teach/paike/schoolTimes/list',
    method: 'get',
    params: query
  })
}

// 查询全校排课时间详细
export function getSchoolTimes() {
  return request({
    url: '/teach/paike/schoolTimes',
    method: 'get'
  })
}

export function getIndexDatas() {
  return request({
    url: '/teach/paike/schoolTimes/getIndexDatas',
    method: 'get'
  })
}

export function getEditDatas() {
  return request({
    url: '/teach/paike/schoolTimes/getEditDatas',
    method: 'get'
  })
}

// 保存全校排课时间
export function saveOrUpdate(data) {
  return request({
    url: '/teach/paike/schoolTimes',
    method: 'post',
    data: data
  })
}

// 删除全校排课时间
export function delSchoolTimes(data) {
  return request({
    url: '/teach/paike/schoolTimes',
    method: 'delete',
    data
  })
}

// 导出全校排课时间
export function exportSchoolTimes(query) {
  return request({
    url: '/teach/paike/schoolTimes/export',
    method: 'get',
    params: query
  })
}

// 下载全校排课时间导入模板
export function importTemplate() {
  return request({
    url: '/teach/paike/schoolTimes/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
