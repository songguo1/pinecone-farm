import request from '@/utils/request'

// 查询病虫害照片列表
export function listPest(query) {
  return request({
    url: '/data/pest/list',
    method: 'get',
    params: query
  })
}

// 查询病虫害照片详细
export function getPest(photoId) {
  return request({
    url: '/data/pest/' + photoId,
    method: 'get'
  })
}

// 新增病虫害照片
export function addPest(data) {
  return request({
    url: '/data/pest',
    method: 'post',
    data: data
  })
}

// 修改病虫害照片
export function updatePest(data) {
  return request({
    url: '/data/pest',
    method: 'put',
    data: data
  })
}

// 删除病虫害照片
export function delPest(photoId) {
  return request({
    url: '/data/pest/' + photoId,
    method: 'delete'
  })
}
