import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

export function getIndexDatas() {
  return request({
    url: '/paike/record/getIndexDatas',
    method: 'get'
  })
}

// 查询排课存档列表
export function listRecord(query) {
  return request({
    url: '/paike/record/list',
    method: 'get',
    params: query
  })
}

export function getEditDatas() {
  return request({
    url: '/paike/record/getEditDatas',
    method: 'get'
  })
}

// 查询排课存档详细
export function getRecord(id) {
  return request({
    url: '/paike/record/' + praseStrEmpty(id),
    method: 'get'
  })
}

// 保存排课存档
export function saveOrUpdate(data) {
  return request({
    url: '/paike/record',
    method: 'post',
    data: data
  })
}

// 删除排课存档
export function delRecord(data) {
  return request({
    url: '/paike/record',
    method: 'delete',
    data
  })
}

// 删除排课存档
export function reloadRecord(id) {
  return request({
    url: '/paike/record/reload/' + id,
    method: 'get'
  })
}

export default {
  saveOrUpdate,
}
