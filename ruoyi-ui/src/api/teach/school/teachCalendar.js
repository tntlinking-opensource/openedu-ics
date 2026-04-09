import request from '../../../utils/request'

// 查询学年学期列表
export function listTeachCalendar(query) {
  return request({
    url: '/school/teachCalendar/list',
    method: 'get',
    params: query
  })
}

// 查询学年学期详细
export function getTeachCalendar(id) {
  return request({
    url: '/school/teachCalendar/' + id,
    method: 'get'
  })
}

// 新增学年学期
export function addTeachCalendar(data) {
  return request({
    url: '/school/teachCalendar',
    method: 'post',
    data: data
  })
}

// 修改学年学期
export function updateTeachCalendar(data) {
  return request({
    url: '/school/teachCalendar',
    method: 'put',
    data: data
  })
}

// 删除学年学期
export function delTeachCalendar(data) {
  return request({
    url: '/school/teachCalendar',
    method: 'delete',
    data
  })
}

// 导出学年学期
export function exportTeachCalendar(query) {
  return request({
    url: '/school/teachCalendar/export',
    method: 'get',
    params: query
  })
}
