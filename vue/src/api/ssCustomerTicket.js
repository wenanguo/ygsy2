import request from '@/utils/request'

const api = {
  add: '/base/ss-customer-ticket/add',
  edit: '/base/ss-customer-ticket/edit',
  list: '/base/ss-customer-ticket/list',
  listAll: '/base/ss-customer-ticket/listAll',
  batchDel: '/base/ss-customer-ticket/batchDelete',
  del: '/base/ss-customer-ticket/delete'
}

export default api

export function getSsCustomerTicketList (parameter) {
  return request({
    url: api.listAll,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsCustomerTicket (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsCustomerTicket (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsCustomerTicket (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
