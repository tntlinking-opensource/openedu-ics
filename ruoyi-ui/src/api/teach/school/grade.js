import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

const base = '/teach/school/grade';

export function getIndexDatas() {
    return request({
	    url: base + '/getIndexDatas',
        method: 'get'
    })
}

// 查询年级列表
export function listGrade(query) {
    return request({
	    url: base + '/list',
        method: 'get',
        params: query
    })
}

export function getEditDatas() {
    return request({
	    url: base + '/getEditDatas',
        method: 'get'
    })
}

// 查询年级详细
export function getGrade(id) {
    return request({
        url: base + '/' + praseStrEmpty(id),
        method: 'get'
    })
}

// 保存年级
export function saveOrUpdateGrade(data) {
    return request({
	    url: base,
        method: 'post',
        data: data
    })
}

// 删除年级
export function delGrade(data) {
    return request({
	    url: base,
        method: 'delete',
	    data
    })
}

// 导出年级
export function exportGrade(query) {
    return request({
	    url: base + '/export',
        method: 'get',
        params: query
    })
}

// 下载年级导入模板
export function importTemplate() {
    return request({
	    url: base + '/importTemplate',
        method: 'get'
    })
}

export default {
    importTemplate,
}