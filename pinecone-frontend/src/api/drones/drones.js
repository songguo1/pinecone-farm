import request from '@/utils/request'

const AllParams={
  pageNum: 1,
  pageSize: 10000,
  droneModel: null,
  serialNumber: null,
  status: null,
}

//查询所有无人机信息
export function getAllDrones(){
  return request({
    url: '/drones/drones/list',
    method: 'get',
    params: AllParams
  })
}

// 查询无人机信息，记录所有无人机的详细信息列表
export function listDrones(query) {
  return request({
    url: '/drones/drones/list',
    method: 'get',
    params: query
  })
}

// 查询无人机信息，记录所有无人机的详细信息详细
export function getDrones(id) {
  return request({
    url: '/drones/drones/' + id,
    method: 'get'
  })
}

// 新增无人机信息，记录所有无人机的详细信息
export function addDrones(data) {
  return request({
    url: '/drones/drones',
    method: 'post',
    data: data
  })
}

// 修改无人机信息，记录所有无人机的详细信息
export function updateDrones(data) {
  return request({
    url: '/drones/drones',
    method: 'put',
    data: data
  })
}

// 删除无人机信息，记录所有无人机的详细信息
export function delDrones(id) {
  return request({
    url: '/drones/drones/' + id,
    method: 'delete'
  })
}
