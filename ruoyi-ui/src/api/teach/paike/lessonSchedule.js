import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询排课任务列表
export function listLessonSchedule(query) {
  return request({
    url: '/teach/paike/lessonSchedule/list',
    method: 'get',
    params: query
  })
}

// 查询排课任务详细
export function getLessonSchedule(id) {
  return request({
    url: '/teach/paike/lessonSchedule/' + praseStrEmpty(id),
    method: 'get'
  })
}

export function getIndexDatas() {
  return request({
    url: '/teach/paike/lessonSchedule/getIndexDatas',
    method: 'get'
  })
}

export function getEditDatas() {
  return request({
    url: '/teach/paike/lessonSchedule/getEditDatas',
    method: 'get'
  })
}

// 新增排课任务
export function addLessonSchedule(data) {
  return request({
    url: '/teach/paike/lessonSchedule',
    method: 'post',
    data: data
  })
}

// 修改排课任务
export function updateLessonSchedule(data) {
  return request({
    url: '/teach/paike/lessonSchedule',
    method: 'put',
    data: data
  })
}

// 删除排课任务
export function delLessonSchedule(data) {
  return request({
    url: '/teach/paike/lessonSchedule',
    method: 'delete',
    data
  })
}

// 导出排课任务
export function exportLessonSchedule(query) {
  return request({
    url: '/teach/paike/lessonSchedule/export',
    method: 'get',
    params: query
  })
}

// 下载排课任务导入模板
export function importTemplate() {
  return request({
    url: '/teach/paike/lessonSchedule/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
