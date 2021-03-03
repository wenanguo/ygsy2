import request from '@/utils/request'

const api = {
  add: '/base/ss-customer-address/add',
  edit: '/base/ss-customer-address/edit',
  list: '/base/ss-customer-address/list',
  listAll: '/base/ss-customer-address/listAll',
  batchDel: '/base/ss-customer-address/batchDelete',
  del: '/base/ss-customer-address/delete',
  updateStation: '/base/ss-customer-address/updateStation'
}

export default api

export function getSsCustomerAddressList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsCustomerAddress (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsCustomerAddress (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsCustomerAddress (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}

export function editStation (parameter) {
  return request({
    url: api.updateStation,
    method: 'put',
    data: parameter
  })
}
