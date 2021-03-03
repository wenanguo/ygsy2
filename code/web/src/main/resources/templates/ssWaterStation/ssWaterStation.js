import request from '@/utils/request'

const api = {
  add: '/base/ss-water-station/add',
  edit: '/base/ss-water-station/edit',
  list: '/base/ss-water-station/list',
  listAll: '/base/ss-water-station/listAll',
  batchDel: '/base/ss-water-station/batchDelete',
  del: '/base/ss-water-station/delete'
}

export default api

export function getSsWaterStationList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsWaterStation (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsWaterStation (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsWaterStation (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
