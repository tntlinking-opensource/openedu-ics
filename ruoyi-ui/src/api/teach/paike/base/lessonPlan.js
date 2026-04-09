import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询任课信息列表
export function listLessonPlan(query) {
  return request({
    url: '/teach/paike/lessonPlan/list',
    method: 'get',
    params: query
  })
}

// 查询任课信息列表
export function listLessonPlanPage(query) {
  return request({
    url: '/teach/paike/lessonPlan/listPage',
    method: 'get',
    params: query
  })
}

// 查询任课信息详细
export function getLessonPlan(id) {
  return request({
    url: '/teach/paike/lessonPlan/' + praseStrEmpty(id),
    method: 'get'
  })
}

export function getIndexDatas() {
  return request({
    url: '/teach/paike/lessonPlan/getIndexDatas',
    method: 'get'
  })
}

export function getEditDatas() {
  return request({
    url: '/teach/paike/lessonPlan/getEditDatas',
    method: 'get'
  })
}

export function getPlanDatas(data) {
  return request({
    url: '/teach/paike/lessonPlan/getPlanDatas',
    method: 'post',
    data: data
  })
}

// 保存任课信息
export function saveOrUpdate(data) {
  return request({
    url: '/teach/paike/lessonPlan',
    method: 'post',
    data: data
  })
}

// 保存任课信息
export function saveBatch(data) {
  return request({
    url: '/teach/paike/lessonPlan/saveBatch',
    method: 'post',
    data: data
  })
}

// 保存任课信息
export function saveTeacher(data) {
  return request({
    url: '/teach/paike/lessonPlan/saveTeacher',
    method: 'post',
    data: data
  })
}

// 删除任课信息
export function delLessonPlan(data) {
  return request({
    url: '/teach/paike/lessonPlan',
    method: 'delete',
    data
  })
}

// 删除任课信息
export function deleteByAdminclass(data) {
  console.log('deleteByAdminclass', data)
  return request({
    url: '/teach/paike/lessonPlan/deleteByAdminclass',
    method: 'post',
    data: data
  })
}

// 导出任课信息
export function exportLessonPlan(query) {
  return request({
    url: '/teach/paike/lessonPlan/export',
    method: 'get',
    params: query
  })
}

// 下载任课信息导入模板
export function importTemplate() {
  return request({
    url: '/teach/paike/lessonPlan/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
