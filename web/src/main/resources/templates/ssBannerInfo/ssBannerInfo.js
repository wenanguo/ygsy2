import request from '@/utils/request'

const api = {
  add: '/base/ss-banner-info/add',
  edit: '/base/ss-banner-info/edit',
  list: '/base/ss-banner-info/list',
  listAll: '/base/ss-banner-info/listAll',
  batchDel: '/base/ss-banner-info/batchDelete',
  del: '/base/ss-banner-info/delete'
}

export default api

export function getSsBannerInfoList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsBannerInfo (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsBannerInfo (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsBannerInfo (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
