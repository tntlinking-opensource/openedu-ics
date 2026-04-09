import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询课表管理列表
export function listLesson(query) {
  return request({
    url: '/teach/school/lesson/list',
    method: 'get',
    params: query
  })
}

// 查询课表管理详细
export function getLesson(id) {
  return request({
    url: '/teach/school/lesson/' + praseStrEmpty(id),
    method: 'get'
  })
}

// 新增课表管理
export function addLesson(data) {
  return request({
    url: '/teach/school/lesson',
    method: 'post',
    data: data
  })
}

// 修改课表管理
export function updateLesson(data) {
  return request({
    url: '/teach/school/lesson',
    method: 'put',
    data: data
  })
}

// 删除课表管理
export function delLesson(data) {
  return request({
    url: '/teach/school/lesson',
    method: 'delete',
    data
  })
}

// 导出课表管理
export function exportLesson(query) {
  return request({
    url: '/teach/school/lesson/export',
    method: 'get',
    params: query
  })
}

// 课表相关数据
export function getDatas() {
  return request({
    url: '/teach/school/lesson/datas',
    method: 'get',
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: '/teach/school/lesson/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
