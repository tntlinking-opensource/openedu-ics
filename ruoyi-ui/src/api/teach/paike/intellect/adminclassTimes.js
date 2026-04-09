import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询班级排课时间列表
export function listAdminclassTimes(query) {
    return request({
        url: '/teach/paike/adminclassTimes/list',
        method: 'get',
        params: query
    })
}

export function getIndexDatas() {
    return request({
        url: '/teach/paike/adminclassTimes/getIndexDatas',
        method: 'get'
    })
}

// 保存班级排课时间
export function saveOrUpdate(data) {
    return request({
        url: '/teach/paike/adminclassTimes',
        method: 'post',
        data: data
    })
}
