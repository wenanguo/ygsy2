import request from '@/utils/request'

const api = {
  add: '/base/ss-region-info/add',
  edit: '/base/ss-region-info/edit',
  list: '/base/ss-region-info/list',
  listAll: '/base/ss-region-info/listAll',
  batchDel: '/base/ss-region-info/batchDelete',
  del: '/base/ss-region-info/delete'
}

export default api

export function getSsRegionInfoList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsRegionInfo (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsRegionInfo (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsRegionInfo (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
