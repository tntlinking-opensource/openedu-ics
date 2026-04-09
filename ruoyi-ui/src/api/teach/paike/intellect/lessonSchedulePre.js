import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

export function getIndexDatas() {
    return request({
        url: '/teach/paike/lessonSchedulePre/getIndexDatas',
        method: 'get'
    })
}

// 查询排课预排列表
export function listLessonSchedulePre(query) {
    return request({
        url: '/teach/paike/lessonSchedulePre/list',
        method: 'get',
        params: query
    })
}

export function getEditDatas() {
    return request({
        url: '/teach/paike/lessonSchedulePre/getEditDatas',
        method: 'get'
    })
}

// 查询排课预排详细
export function getLessonSchedulePre(id) {
    return request({
        url: '/teach/paike/lessonSchedulePre/' + praseStrEmpty(id),
        method: 'get'
    })
}

// 保存排课预排
export function saveOrUpdate(data) {
    return request({
        url: '/teach/paike/lessonSchedulePre',
        method: 'post',
        data: data
    })
}

// 删除排课预排
export function delLessonSchedulePre(data) {
    return request({
        url: '/teach/paike/lessonSchedulePre',
        method: 'delete',
      data
    })
}

// 导出排课预排
export function exportLessonSchedulePre(query) {
    return request({
        url: '/teach/paike/lessonSchedulePre/export',
        method: 'get',
        params: query
    })
}

// 下载排课预排导入模板
export function importTemplate() {
    return request({
        url: '/teach/paike/lessonSchedulePre/importTemplate',
        method: 'get'
    })
}

export default {
    importTemplate,
}
