import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询全校排课时间列表
export function listMixedClass(query) {
    return request({
        url: '/teach/paike/mixedClass/list',
        method: 'get',
        params: query
    })
}

// 查询全校排课时间详细
export function getMixedClass() {
    return request({
        url: '/teach/paike/mixedClass',
        method: 'get'
    })
}

export function getIndexDatas() {
    return request({
        url: '/teach/paike/mixedClass/getIndexDatas',
        method: 'get'
    })
}

export function getEditDatas() {
    return request({
        url: '/teach/paike/mixedClass/getEditDatas',
        method: 'get'
    })
}

// 保存全校排课时间
export function saveOrUpdate(data) {
    return request({
        url: '/teach/paike/mixedClass',
        method: 'post',
        data: data
    })
}

// 删除全校排课时间
export function delMixedClass(data) {
    return request({
        url: '/teach/paike/mixedClass',
        method: 'delete',
      data
    })
}

// 导出全校排课时间
export function exportMixedClass(query) {
    return request({
        url: '/teach/paike/mixedClass/export',
        method: 'get',
        params: query
    })
}

// 下载全校排课时间导入模板
export function importTemplate() {
    return request({
        url: '/teach/paike/mixedClass/importTemplate',
        method: 'get'
    })
}

export default {
    importTemplate,
}
