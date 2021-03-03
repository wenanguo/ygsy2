import request from '@/utils/request'

const api = {
  add: '/base/ss-customer-info/add',
  edit: '/base/ss-customer-info/edit',
  list: '/base/ss-customer-info/list',
  listAll: '/base/ss-customer-info/listAll',
  batchDel: '/base/ss-customer-info/batchDelete',
  del: '/base/ss-customer-info/delete'
}

export default api

export function getSsCustomerInfoList (parameter) {
  return request({
    url: api.listAll,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsCustomerInfo (parameter) {

  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsCustomerInfo (parameter) {
 
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsCustomerInfo (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
