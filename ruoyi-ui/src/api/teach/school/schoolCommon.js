import request from '@/utils/request'
import {praseStrEmpty, praseUrl} from "@/utils/ruoyi";

export function getIndex() {
  return request({
    url: '/common/school/index',
    method: 'get',
  })
}

export function getIndexMessage() {
  return request({
    url: '/common/school/indexMessage',
    method: 'get',
  })
}

export function getAppRole(appId) {
  return request({
    url: '/common/school/getAppRole/' + appId,
    method: 'get',
  })
}

export function getSchoolRole(appId) {
  return request({
    url: '/common/school/getSchoolRole/',
    method: 'get',
  })
}

export function getUsers(key) {
  if (key == null || key == '') {
    return Promise.resolve({data: []})
  }
  return request({
    url: '/common/school/getUsers/' + praseStrEmpty(key),
    method: 'get',
  })
}

export function getTeachers(key) {
  return request({
    url: '/common/school/getTeachers/' + praseStrEmpty(key),
    method: 'get',
  })
}


export function getTeacherById(id) {
  return request({
    url: '/common/school/getTeacherById?teacherId=' + id,
    method: 'get',
  })
}

export function getStudentsByKey(key) {
  return request({
    url: '/common/school/getStudentsByKey?key=' + key,
    method: 'get',
  })
}

export function getAllStudentsByKey(key) {
  return request({
    url: '/common/school/getAllStudentsByKey?key=' + key,
    method: 'get',
  })
}

export function getSchoolApps() {
  return new Promise(resolve => {
    request({
      url: '/common/school/getSchoolApps',
      method: 'get',
    }).then(res => {
      // console.log(11)
      res.data.forEach(v => {
        v.apps.forEach(a => {
          a.icon = a.icon ? praseUrl(a.icon) : require('@/assets/images/icon_2.png')
        })
      })
      resolve(res)
    })
  })
}

export function trialApp(app) {
  return request({
    url: '/common/school/trialApp?app=' + app,
    method: 'get',
  })
}


export function getTaskCount() {
  return request({
    url: '/common/school/taskCount',
    method: 'get',
  })
}

export function getTeachDepartments() {
  return request({
    url: '/common/school/getTeachDepartments',
    method: 'get',
  })
}

export function getAdminDepartments() {
  return request({
    url: '/common/school/getAdminDepartments',
    method: 'get',
  })
}
