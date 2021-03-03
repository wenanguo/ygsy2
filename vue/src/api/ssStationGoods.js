import request from '@/utils/request'

const api = {
  add: '/base/ss-station-goods/add',
  edit: '/base/ss-station-goods/edit',
  list: '/base/ss-station-goods/list',
  listAll: '/base/ss-station-goods/listAll',
  batchDel: '/base/ss-station-goods/batchDelete',
  del: '/base/ss-station-goods/delete',
  updateSalePrice: '/base/ss-station-goods/updateSalePrice',
  goodsDict: '/base/ss-station-goods/goodsDict'
}

export default api

export function getSsStationGoodsList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsStationGoods (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsStationGoods (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsStationGoods (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}

export function updateSalePrice (parameter) {
  return request({
    url: api.updateSalePrice,
    method: 'put',
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
