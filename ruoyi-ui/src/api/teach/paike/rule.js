import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

export function getIndexDatas() {
  return request({
    url: '/teach/paike/rule/getIndexDatas',
    method: 'get'
  })
}

// 查询排课规则列表
export function listRule(query) {
  return request({
    url: '/teach/paike/rule/list',
    method: 'get',
    params: query
  })
}

export function getEditDatas() {
  return request({
    url: '/teach/paike/rule/getEditDatas',
    method: 'get'
  })
}

// 查询排课规则详细
export function getRule(id) {
  return request({
    url: '/teach/paike/rule/' + praseStrEmpty(id),
    method: 'get'
  })
}

// 保存排课规则
export function saveOrUpdate(data) {
  return request({
    url: '/teach/paike/rule',
    method: 'post',
    data: data
  })
}

// 删除排课规则
export function delRule(data) {
  return request({
    url: '/teach/paike/rule',
    method: 'delete',
    data
  })
}

// 导出排课规则
export function exportRule(query) {
  return request({
    url: '/teach/paike/rule/export',
    method: 'get',
    params: query
  })
}

// 下载排课规则导入模板
export function importTemplate() {
  return request({
    url: '/teach/paike/rule/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
