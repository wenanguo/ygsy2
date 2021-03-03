import request from '@/utils/request'

const api = {
  add: '/base/ss-waterstation-price/add',
  edit: '/base/ss-waterstation-price/edit',
  list: '/base/ss-waterstation-price/list',
  listAll: '/base/ss-waterstation-price/listAll',
  batchDel: '/base/ss-waterstation-price/batchDelete',
  del: '/base/ss-waterstation-price/delete'
}

export default api

export function getSsWaterstationPriceList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsWaterstationPrice (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsWaterstationPrice (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsWaterstationPrice (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
