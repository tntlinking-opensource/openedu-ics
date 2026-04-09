import request from '@/utils/request'


// 查询部门成员
export function updateTeacher(data) {
  return request({
    url: '/teach/school/department/updateTeacher',
    data: data,
    method: 'post'
  })
}

// 查询部门列表
export function listDepartment(query) {
  return request({
    url: '/teach/school/department/list',
    method: 'get',
    params: query
  })
}

// 查询部门详细
export function getDepartment(id) {
  return request({
    url: '/teach/school/department/' + id,
    method: 'get'
  })
}

export function getEditDatas() {
  return request({
    url: '/teach/school/department/getEditDatas',
    method: 'get'
  })
}

// 新增部门
export function addDepartment(data) {
  return request({
    url: '/teach/school/department',
    method: 'post',
    data: data
  })
}

// 修改部门
export function updateDepartment(data) {
  return request({
    url: '/teach/school/department',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delDepartment(data) {
  return request({
    url: '/teach/school/department',
    method: 'delete',
    data
  })
}

function getCascaderChildren(datas, parentId) {
  let childrens = datas.filter(item => item.parentId === parentId).map(item =>{
    return {
      value: item.id,
      label: item.name,
    }
  })
  childrens.forEach(item => {
    let children = getCascaderChildren(datas, item.value)
    if(children.length > 0){
      item.children = children
    }
  })
  return childrens;
}

export function getCascaderOptions(datas) {
  return getCascaderChildren(datas, null)[0].children
}


// 导出部门
export function exportDepartment(query) {
  return request({
    url: '/teach/school/department/export',
    method: 'get',
    params: query
  })
}

// 下载部门导入模板
export function importTemplate() {
  return request({
    url: '/teach/school/department/importTemplate',
    method: 'get'
  })
}

export default {
  importTemplate,
}
