import request from '@/utils/request'

const api = {
  add: '/base/ss-goods-type/add',
  edit: '/base/ss-goods-type/edit',
  list: '/base/ss-goods-type/list',
  listAll: '/base/ss-goods-type/listAll',
  batchDel: '/base/ss-goods-type/batchDelete',
  del: '/base/ss-goods-type/delete'
}

export default api

export function getSsGoodsTypeList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsGoodsType (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsGoodsType (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsGoodsType (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
