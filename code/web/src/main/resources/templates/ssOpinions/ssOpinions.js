import request from '@/utils/request'

const api = {
  add: '/base/ss-opinions/add',
  edit: '/base/ss-opinions/edit',
  list: '/base/ss-opinions/list',
  listAll: '/base/ss-opinions/listAll',
  batchDel: '/base/ss-opinions/batchDelete',
  del: '/base/ss-opinions/delete'
}

export default api

export function getSsOpinionsList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsOpinions (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsOpinions (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsOpinions (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
