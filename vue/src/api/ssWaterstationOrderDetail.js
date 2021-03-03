import request from '@/utils/request'

const api = {
  add: '/base/ss-waterstation-order-detail/add',
  edit: '/base/ss-waterstation-order-detail/edit',
  list: '/base/ss-waterstation-order-info/listAllDetail',
  listAll: '/base/ss-waterstation-order-detail/listAll',
  batchDel: '/base/ss-waterstation-order-detail/batchDelete',
  del: '/base/ss-waterstation-order-detail/delete'
}

export default api

export function getSsWaterstationOrderDetailList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsWaterstationOrderDetail (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsWaterstationOrderDetail (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsWaterstationOrderDetail (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
