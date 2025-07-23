import request from '@/utils/request'

export function getAdminclass() {
  return request({
    url: '/teach/paike/view/adminclass',
    method: 'get'
  })
}

export function exportAdminclass(data) {
  return request({
    url: '/teach/paike/view/adminclass',
    data: data,
    method: 'post'
  })
}

export function getTeacher() {
  return request({
    url: '/teach/paike/view/teacher',
    method: 'get'
  })
}

export function exportTeacher(data) {
  return request({
    url: '/teach/paike/view/teacher',
    data: data,
    method: 'post'
  })
}

export function getClassroom() {
  return request({
    url: '/teach/paike/view/classroom',
    method: 'get'
  })
}

export function exportClassroom(data) {
  return request({
    url: '/teach/paike/view/classroom',
    data: data,
    method: 'post'
  })
}

export function getAdminclasses() {
  return request({
    url: '/teach/paike/view/adminclasses',
    method: 'get'
  })
}

export function exportAdminclasses(data) {
  return request({
    url: '/teach/paike/view/adminclasses',
    data: data,
    method: 'post'
  })
}


export function getTeachers() {
  return request({
    url: '/teach/paike/view/teachers',
    method: 'get'
  })
}

export function exportTeachers(data) {
  return request({
    url: '/teach/paike/view/teachers',
    data: data,
    method: 'post'
  })
}


export function getTimes() {
  return request({
    url: '/teach/paike/view/times',
    method: 'get'
  })
}
