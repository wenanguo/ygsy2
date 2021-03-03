import request from '@/utils/request'

const api = {
  add: '/base/ss-goods-tag/add',
  edit: '/base/ss-goods-tag/edit',
  list: '/base/ss-goods-tag/list',
  listAll: '/base/ss-goods-tag/listAll',
  batchDel: '/base/ss-goods-tag/batchDelete',
  del: '/base/ss-goods-tag/delete',
  tagIdList: '/base/ss-goods-tag/tagIdList'
}

export default api

export function getSsGoodsTagList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsGoodsTag (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsGoodsTag (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsGoodsTag (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}

export function getTagIdList (parameter) {
  return request({
    url: api.tagIdList,
    method: 'get',
    params: parameter
  })
}
