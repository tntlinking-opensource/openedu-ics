import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询任课信息列表
export function listLessonInfo(query) {
  return request({
    url: '/teach/paike/lessonInfo/list',
    method: 'get',
    params: query
  })
}

// 查询任课信息详细
export function getLessonInfo(id) {
  return request({
    url: '/teach/paike/lessonInfo/' + praseStrEmpty(id),
    method: 'get'
  })
}

export function getIndexDatas() {
  return request({
    url: '/teach/paike/lessonInfo/getIndexDatas',
    method: 'get'
  })
}

export function getEditDatas() {
  return request({
    url: '/teach/paike/lessonInfo/getEditDatas',
    method: 'get'
  })
}

// 新增任课信息
export function addLessonInfo(data) {
  return request({
    url: '/teach/paike/lessonInfo',
    method: 'post',
    data: data
  })
}

// 修改任课信息
export function updateLessonInfo(data) {
  return request({
    url: '/teach/paike/lessonInfo',
    method: 'put',
    data: data
  })
}

// 删除任课信息
export function delLessonInfo(data) {
  return request({
    url: '/teach/paike/lessonInfo',
    method: 'delete',
    data
  })
}

// 导出任课信息
export function exportLessonInfo(query) {
  return request({
    url: '/teach/paike/lessonInfo/export',
    method: 'get',
    params: query
  })
}

// 下载任课信息导入模板
export function importTemplate() {
  return request({
    url: '/teach/paike/lessonInfo/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
