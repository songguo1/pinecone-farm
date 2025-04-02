import request from '@/utils/request'

// 查询鱼塘信息列表
export function listPond(query) {
  return request({
    url: '/manage/pond/list',
    method: 'get',
    params: query
  })
}

// 查询鱼塘信息详细
export function getPond(pondId) {
  return request({
    url: '/manage/pond/' + pondId,
    method: 'get'
  })
}

export function getAllPond(){
  return request({
    url: '/manage/pond/getAll',
    method: 'get'
  })
}

// 新增鱼塘信息
export function addPond(data) {
  return request({
    url: '/manage/pond',
    method: 'post',
    data: data
  })
}

// 修改鱼塘信息
export function updatePond(data) {
  return request({
    url: '/manage/pond',
    method: 'put',
    data: data
  })
}

// 删除鱼塘信息
export function delPond(pondId) {
  return request({
    url: '/manage/pond/' + pondId,
    method: 'delete'
  })
}
