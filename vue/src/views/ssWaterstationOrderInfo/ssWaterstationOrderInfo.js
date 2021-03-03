import request from '@/utils/request'

const api = {
  add: '/base/ss-waterstation-order-info/add',
  edit: '/base/ss-waterstation-order-info/edit',
  list: '/base/ss-waterstation-order-info/list',
  listAll: '/base/ss-waterstation-order-info/listAll',
  batchDel: '/base/ss-waterstation-order-info/batchDelete',
  del: '/base/ss-waterstation-order-info/delete'
}

export default api

export function getSsWaterstationOrderInfoList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsWaterstationOrderInfo (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsWaterstationOrderInfo (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsWaterstationOrderInfo (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
