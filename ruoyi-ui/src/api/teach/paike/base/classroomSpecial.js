import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询特殊教室列表
export function listClassroomSpecial(query) {
    return request({
        url: '/teach/paike/classroomSpecial/list',
        method: 'get',
        params: query
    })
}

// 查询特殊教室详细
export function getClassroomSpecial(id) {
    return request({
        url: '/teach/paike/classroomSpecial/' + praseStrEmpty(id),
        method: 'get'
    })
}

export function getIndexDatas() {
    return request({
        url: '/teach/paike/classroomSpecial/getIndexDatas',
        method: 'get'
    })
}

export function getEditDatas() {
    return request({
        url: '/teach/paike/classroomSpecial/getEditDatas',
        method: 'get'
    })
}

// 保存特殊教室
export function saveOrUpdate(data) {
    return request({
        url: '/teach/paike/classroomSpecial',
        method: 'post',
        data: data
    })
}

// 删除特殊教室
export function delClassroomSpecial(data) {
    return request({
        url: '/teach/paike/classroomSpecial',
        method: 'delete',
      data
    })
}

// 导出特殊教室
export function exportClassroomSpecial(query) {
    return request({
        url: '/teach/paike/classroomSpecial/export',
        method: 'get',
        params: query
    })
}

// 下载特殊教室导入模板
export function importTemplate() {
    return request({
        url: '/teach/paike/classroomSpecial/importTemplate',
        method: 'get'
    })
}

export default {
    importTemplate,
}
