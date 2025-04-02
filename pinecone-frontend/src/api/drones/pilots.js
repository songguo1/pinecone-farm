import request from '@/utils/request'

const AllParams={
  pageNum: 1,
  pageSize: 10000,
  name: null,
}

//查询所有飞手信息
export function getAllPilots(){
  return request({
    url:'/drones/pilots/list',
    method:'get',
    params:AllParams
  })
}
// 查询飞手信息列表
export function listPilots(query) {
  return request({
    url: '/drones/pilots/list',
    method: 'get',
    params: query
  })
}

// 查询飞手信息详细
export function getPilots(id) {
  return request({
    url: '/drones/pilots/' + id,
    method: 'get'
  })
}

// 新增飞手信息
export function addPilots(data) {
  return request({
    url: '/drones/pilots',
    method: 'post',
    data: data
  })
}

// 修改飞手信息
export function updatePilots(data) {
  return request({
    url: '/drones/pilots',
    method: 'put',
    data: data
  })
}

// 删除飞手信息
export function delPilots(id) {
  return request({
    url: '/drones/pilots/' + id,
    method: 'delete'
  })
}
