import request from '@/utils/request'

export function getIndexDatas() {
  return request({
    url: '/teach/paike/lessonScheduleAuto/getIndexDatas',
    method: 'get'
  })
}

export function startSchedule() {
  return request({
    url: '/teach/paike/lessonScheduleAuto/startSchedule',
    method: 'post',
  })
}
