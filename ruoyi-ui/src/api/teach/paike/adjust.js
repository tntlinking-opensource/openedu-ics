import request from '@/utils/request'

export function getIndex() {
  return request({
    url: '/teach/paike/adjust',
    method: 'get'
  })
}

// 保存排课预排
export function saveOrUpdate(plans) {
  let data = []
  plans.forEach(p => p.plans.forEach(pp => {
    pp.times = p.planTimes.join('、')
    data.push(pp)
  }))
  if (data.length == 0) {
    return
  }
  return request({
    url: '/teach/paike/adjust',
    method: 'post',
    data: data
  })
}
