import request from '@/utils/request'

const api = {
  add: '/base/ss-delivery-man/add',
  edit: '/base/ss-delivery-man/edit',
  list: '/base/ss-delivery-man/list',
  listAll: '/base/ss-delivery-man/listAll',
  batchDel: '/base/ss-delivery-man/batchDelete',
  del: '/base/ss-delivery-man/delete'
}

export default api

export function getSsDeliveryManList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsDeliveryMan (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsDeliveryMan (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsDeliveryMan (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
