import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询课程排课时间列表
export function listCourseTimes(query) {
    return request({
        url: '/teach/paike/courseTimes/list',
        method: 'get',
        params: query
    })
}

// 查询课程排课时间详细
export function getCourseTimes(id) {
    return request({
        url: '/teach/paike/courseTimes/' + praseStrEmpty(id),
        method: 'get'
    })
}

export function getIndexDatas() {
    return request({
        url: '/teach/paike/courseTimes/getIndexDatas',
        method: 'get'
    })
}

export function getEditDatas() {
    return request({
        url: '/teach/paike/courseTimes/getEditDatas',
        method: 'get'
    })
}

// 保存课程排课时间
export function saveOrUpdate(data) {
    return request({
        url: '/teach/paike/courseTimes',
        method: 'post',
        data: data
    })
}

// 删除课程排课时间
export function delCourseTimes(data) {
    return request({
        url: '/teach/paike/courseTimes',
        method: 'delete',
      data
    })
}

// 导出课程排课时间
export function exportCourseTimes(query) {
    return request({
        url: '/teach/paike/courseTimes/export',
        method: 'get',
        params: query
    })
}

// 下载课程排课时间导入模板
export function importTemplate() {
    return request({
        url: '/teach/paike/courseTimes/importTemplate',
        method: 'get'
    })
}

export default {
    importTemplate,
}
