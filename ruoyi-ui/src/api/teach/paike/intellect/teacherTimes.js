import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

export function getIndexDatas() {
  return request({
    url: '/teach/paike/teacherTimes/getIndexDatas',
    method: 'get'
  })
}

// 查询教师排课时间列表
export function listTeacherTimes(query) {
  return request({
    url: '/teach/paike/teacherTimes/list',
    method: 'get',
    params: query
  })
}

export function getEditDatas() {
  return request({
    url: '/teach/paike/teacherTimes/getEditDatas',
    method: 'get'
  })
}

// 查询教师排课时间详细
export function getTeacherTimes(id) {
  return request({
    url: '/teach/paike/teacherTimes/' + praseStrEmpty(id),
    method: 'get'
  })
}

// 保存教师排课时间
export function saveOrUpdate(data) {
  return request({
    url: '/teach/paike/teacherTimes',
    method: 'post',
    data: data
  })
}

// 删除教师排课时间
export function delTeacherTimes(data) {
  return request({
    url: '/teach/paike/teacherTimes',
    method: 'delete',
    data
  })
}

// 导出教师排课时间
export function exportTeacherTimes(query) {
  return request({
    url: '/teach/paike/teacherTimes/export',
    method: 'get',
    params: query
  })
}

// 下载教师排课时间导入模板
export function importTemplate() {
  return request({
    url: '/teach/paike/teacherTimes/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
