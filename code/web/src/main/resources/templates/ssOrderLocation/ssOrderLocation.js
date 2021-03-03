import request from '@/utils/request'

const api = {
  add: '/base/ss-order-location/add',
  edit: '/base/ss-order-location/edit',
  list: '/base/ss-order-location/list',
  listAll: '/base/ss-order-location/listAll',
  batchDel: '/base/ss-order-location/batchDelete',
  del: '/base/ss-order-location/delete'
}

export default api

export function getSsOrderLocationList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsOrderLocation (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsOrderLocation (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsOrderLocation (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
