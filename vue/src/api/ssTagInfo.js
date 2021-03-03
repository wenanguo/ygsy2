import request from '@/utils/request'

const api = {
  add: '/base/ss-tag-info/add',
  edit: '/base/ss-tag-info/edit',
  list: '/base/ss-tag-info/list',
  listAll: '/base/ss-tag-info/listAll',
  batchDel: '/base/ss-tag-info/batchDelete',
  del: '/base/ss-tag-info/delete',
  tagDict: '/base/ss-tag-info/tagDict'
}

export default api

export function getSsTagInfoList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsTagInfo (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsTagInfo (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsTagInfo (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}

export function getTagDict (parameter) {
  return request({
    url: api.tagDict,
    method: 'get',
    params: parameter
  })
}