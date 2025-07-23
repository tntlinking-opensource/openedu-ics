import request from '@/utils/request'

export function lessonScheduleSync() {
  return request({
    url: '/teach/paike/sync',
    method: 'post',
    timeout: 60000,
  })
}
