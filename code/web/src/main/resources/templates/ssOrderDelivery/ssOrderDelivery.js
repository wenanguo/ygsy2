import request from '@/utils/request'

const api = {
  add: '/base/ss-order-delivery/add',
  edit: '/base/ss-order-delivery/edit',
  list: '/base/ss-order-delivery/list',
  listAll: '/base/ss-order-delivery/listAll',
  batchDel: '/base/ss-order-delivery/batchDelete',
  del: '/base/ss-order-delivery/delete'
}

export default api

export function getSsOrderDeliveryList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsOrderDelivery (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsOrderDelivery (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsOrderDelivery (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
