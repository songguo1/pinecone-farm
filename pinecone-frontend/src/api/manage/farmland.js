import request from '@/utils/request'

// 查询农田信息列表
export function listFarmland(query) {
  return request({
    url: '/manage/farmland/list',
    method: 'get',
    params: query
  })
}

// 查询所有农田信息
export function getAllFarmland(){
  return request({
    url: '/manage/farmland/getAll',
    method: 'get'
  })
}

// 查询农田信息详细
export function getFarmland(landId) {
  return request({
    url: '/manage/farmland/' + landId,
    method: 'get'
  })
}

// 新增农田信息
export function addFarmland(data) {
  return request({
    url: '/manage/farmland',
    method: 'post',
    data: data
  })
}

// 修改农田信息
export function updateFarmland(data) {
  return request({
    url: '/manage/farmland',
    method: 'put',
    data: data
  })
}

// 删除农田信息
export function delFarmland(landId) {
  return request({
    url: '/manage/farmland/' + landId,
    method: 'delete'
  })
}
