import request from '@/utils/request'

const api = {
  add: '/base/ss-clockin-info/add',
  edit: '/base/ss-clockin-info/edit',
  list: '/base/ss-clockin-info/list',
  listAll: '/base/ss-clockin-info/listAll',
  batchDel: '/base/ss-clockin-info/batchDelete',
  del: '/base/ss-clockin-info/delete'
}

export default api

export function getSsClockinInfoList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsClockinInfo (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsClockinInfo (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsClockinInfo (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
