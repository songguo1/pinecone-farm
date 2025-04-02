import request from '@/utils/request'

// 查询大棚信息列表
export function listGreenhouse(query) {
  return request({
    url: '/manage/greenhouse/list',
    method: 'get',
    params: query
  })
}

export function getAllGreenhouse(){
  return request({
    url: '/manage/greenhouse/getAll',
    method: 'get'
  })
}

// 查询大棚信息详细
export function getGreenhouse(greenhouseId) {
  return request({
    url: '/manage/greenhouse/' + greenhouseId,
    method: 'get'
  })
}

// 新增大棚信息
export function addGreenhouse(data) {
  return request({
    url: '/manage/greenhouse',
    method: 'post',
    data: data
  })
}

// 修改大棚信息
export function updateGreenhouse(data) {
  return request({
    url: '/manage/greenhouse',
    method: 'put',
    data: data
  })
}

// 删除大棚信息
export function delGreenhouse(greenhouseId) {
  return request({
    url: '/manage/greenhouse/' + greenhouseId,
    method: 'delete'
  })
}
