import request from '@/utils/request'

const api = {
  add: '/base/ss-payment-points/add',
  edit: '/base/ss-payment-points/edit',
  list: '/base/ss-payment-points/list',
  listAll: '/base/ss-payment-points/listAll',
  batchDel: '/base/ss-payment-points/batchDelete',
  del: '/base/ss-payment-points/delete'
}

export default api

export function getSsPaymentPointsList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsPaymentPoints (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsPaymentPoints (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsPaymentPoints (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
