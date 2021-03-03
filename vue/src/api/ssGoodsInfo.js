import request from '@/utils/request'

const api = {
  add: '/base/ss-goods-info/add',
  edit: '/base/ss-goods-info/edit',
  list: '/base/ss-goods-info/list',
  listAll: '/base/ss-goods-info/listAll',
  batchDel: '/base/ss-goods-info/batchDelete',
  del: '/base/ss-goods-info/delete',
  goodsDict: '/base/ss-goods-info/goodsDict'
}

export default api

export function getSsGoodsInfoList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsGoodsInfo (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter,
    params: {'tagIdArray': parameter.tagIdArray.toString() }
  })
}

export function delSsGoodsInfo (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsGoodsInfo (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}

export function getGoodsInfoDict (parameter) {
  return request({
    url: api.goodsDict,
    method: 'get',
    params: parameter
  })
}
