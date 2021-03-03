import request from '@/utils/request'

const api = {
  add: '/base/ss-water-ticket/add',
  edit: '/base/ss-water-ticket/edit',
  list: '/base/ss-water-ticket/list',
  listAll: '/base/ss-water-ticket/listAll',
  batchDel: '/base/ss-water-ticket/batchDelete',
  del: '/base/ss-water-ticket/delete',
  ticketDict: '/base/ss-water-ticket/ticketDict'
}

export default api

export function getSsWaterTicketList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsWaterTicket (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsWaterTicket (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsWaterTicket (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}

export function getTicketDict (parameter) {
  return request({
    url: api.ticketDict,
    method: 'get',
    params: parameter
  })
}