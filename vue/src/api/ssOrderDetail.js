import request from '@/utils/request'

const api = {
  add: '/base/ss-order-detail/add',
  edit: '/base/ss-order-detail/edit',
  list: '/base/ss-order-detail/list',
  listAll: '/base/ss-order-detail/listAll',
  batchDel: '/base/ss-order-detail/batchDelete',
  del: '/base/ss-order-detail/delete'
}

export default api

export function getSsOrderDetailList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsOrderDetail (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsOrderDetail (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsOrderDetail (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
