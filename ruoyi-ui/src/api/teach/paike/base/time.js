import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询作息安排列表
export function listTime(query) {
    return request({
        url: '/teach/paike/time/list',
        method: 'get',
        params: query
    })
}

// 查询作息安排详细
export function getTime() {
    return request({
        url: '/teach/paike/time/getInfo',
        method: 'get'
    })
}

export function getIndexDatas() {
    return request({
        url: '/teach/paike/time/getIndexDatas',
        method: 'get'
    })
}

export function getEditDatas() {
    return request({
        url: '/teach/paike/time/getEditDatas',
        method: 'get'
    })
}

// 新增作息安排
export function addTime(data) {
    return request({
        url: '/teach/paike/time',
        method: 'post',
        data: data
    })
}

// 修改作息安排
export function updateTime(data) {
    return request({
        url: '/teach/paike/time',
        method: 'put',
        data: data
    })
}

// 删除作息安排
export function delTime(data) {
    return request({
        url: '/teach/paike/time',
        method: 'delete',
      data
    })
}

// 导出作息安排
export function exportTime(query) {
    return request({
        url: '/teach/paike/time/export',
        method: 'get',
        params: query
    })
}

// 下载作息安排导入模板
export function importTemplate() {
    return request({
        url: '/teach/paike/time/importTemplate',
        method: 'get'
    })
}

export default {
    importTemplate,
}
