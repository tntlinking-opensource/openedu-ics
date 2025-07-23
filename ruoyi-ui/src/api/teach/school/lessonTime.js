import request from '@/utils/request'

export function getIndexDatas() {
  return request({
    url: '/teach/lessonTime/getIndexDatas',
    method: 'get'
  })
}

// 查询上课节次列表
export function listLessonTime(query) {
  return request({
    url: '/teach/lessonTime/list',
    method: 'get',
    params: query
  })
}

// 查询上课节次详细
export function getLessonTime(id) {
  return request({
    url: '/teach/lessonTime/' + id,
    method: 'get'
  })
}

// 新增上课节次
export function addLessonTime(data) {
  return request({
    url: '/teach/lessonTime',
    method: 'post',
    data: data
  })
}

// 修改上课节次
export function updateLessonTime(data) {
  return request({
    url: '/teach/lessonTime',
    method: 'put',
    data: data
  })
}

// 删除上课节次
export function delLessonTime(data) {
  return request({
    url: '/teach/lessonTime',
    method: 'delete',
    data
  })
}

// 导出上课节次
export function exportLessonTime(query) {
  return request({
    url: '/teach/lessonTime/export',
    method: 'get',
    params: query
  })
}
