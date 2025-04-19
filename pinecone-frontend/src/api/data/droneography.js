import request from '@/utils/request'

// 查询航拍图片列表
export function listDroneography(query) {
  return request({
    url: '/data/droneography/list',
    method: 'get',
    params: query
  })
}

// 查询航拍图片详细
export function getDroneography(photoId) {
  return request({
    url: '/data/droneography/' + photoId,
    method: 'get'
  })
}

// 新增航拍图片
export function addDroneography(data) {
  return request({
    url: '/data/droneography',
    method: 'post',
    data: data
  })
}

// 修改航拍图片
export function updateDroneography(data) {
  return request({
    url: '/data/droneography',
    method: 'put',
    data: data
  })
}

// 删除航拍图片
export function delDroneography(photoId) {
  return request({
    url: '/data/droneography/' + photoId,
    method: 'delete'
  })
}
