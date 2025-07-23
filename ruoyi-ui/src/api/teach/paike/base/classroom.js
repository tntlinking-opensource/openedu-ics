import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询班级教室列表
export function listClassroom(query) {
    return request({
        url: '/teach/paike/classroom/list',
        method: 'get',
        params: query
    })
}

// 查询班级教室详细
export function getClassroom(id) {
    return request({
        url: '/teach/paike/classroom/' + praseStrEmpty(id),
        method: 'get'
    })
}

export function getIndexDatas() {
    return request({
        url: '/teach/paike/classroom/getIndexDatas',
        method: 'get'
    })
}

export function getEditDatas() {
    return request({
        url: '/teach/paike/classroom/getEditDatas',
        method: 'get'
    })
}

// 保存班级教室
export function saveOrUpdate(data) {
    return request({
        url: '/teach/paike/classroom',
        method: 'post',
        data: data
    })
}

// 删除班级教室
export function delClassroom(data) {
    return request({
        url: '/teach/paike/classroom',
        method: 'delete',
      data
    })
}

// 导出班级教室
export function exportClassroom(query) {
    return request({
        url: '/teach/paike/classroom/export',
        method: 'get',
        params: query
    })
}

// 下载班级教室导入模板
export function importTemplate() {
    return request({
        url: '/teach/paike/classroom/importTemplate',
        method: 'get'
    })
}

export default {
    importTemplate,
}
