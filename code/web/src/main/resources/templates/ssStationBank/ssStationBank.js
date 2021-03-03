import request from '@/utils/request'

const api = {
  add: '/base/ss-station-bank/add',
  edit: '/base/ss-station-bank/edit',
  list: '/base/ss-station-bank/list',
  listAll: '/base/ss-station-bank/listAll',
  batchDel: '/base/ss-station-bank/batchDelete',
  del: '/base/ss-station-bank/delete'
}

export default api

export function getSsStationBankList (parameter) {
  return request({
    url: api.list,
    method: 'get',
    params: parameter
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveSsStationBank (parameter) {
  return request({
    url: parameter.id === 0 ? api.add : api.edit,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter
  })
}

export function delSsStationBank (parameter) {
  return request({
    url: api.del,
    method: 'delete',
    data: parameter
  })
}

export function batchDelSsStationBank (parameter) {
  return request({
    url: api.batchDel,
    method: 'delete',
    data: parameter
  })
}
