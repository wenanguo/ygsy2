import request from '@/utils/request'

const api = {
  add: '/base/ss-order-info/add',
  edit: '/base/ss-order-info/edit',
  list: '/base/ss-order-info/list',
  listAll: '/base/ss-order-info/listAll',
  batchDel: '/base/ss-order-info/batchDelete',
  del: '/base/ss-order-info/delete'
}

export default api

export function getSsOrderInfoList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

export function getSsOrderInfoListById (parameter) {
  return request({
    url: api.listAll,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsOrderInfo (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsOrderInfo (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsOrderInfo (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
