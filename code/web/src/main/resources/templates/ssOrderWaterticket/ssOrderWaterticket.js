import request from '@/utils/request'

const api = {
  add: '/base/ss-order-waterticket/add',
  edit: '/base/ss-order-waterticket/edit',
  list: '/base/ss-order-waterticket/list',
  listAll: '/base/ss-order-waterticket/listAll',
  batchDel: '/base/ss-order-waterticket/batchDelete',
  del: '/base/ss-order-waterticket/delete'
}

export default api

export function getSsOrderWaterticketList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsOrderWaterticket (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsOrderWaterticket (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsOrderWaterticket (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
