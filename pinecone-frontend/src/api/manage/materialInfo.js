import request from '@/utils/request'

// 查询农资信息列表
export function listMaterialInfo(query) {
  return request({
    url: '/manage/materialInfo/list',
    method: 'get',
    params: query
  })
}

// 查询农资信息详细
export function getMaterialInfo(materialId) {
  return request({
    url: '/manage/materialInfo/' + materialId,
    method: 'get'
  })
}

// 新增农资信息
export function addMaterialInfo(data) {
  return request({
    url: '/manage/materialInfo',
    method: 'post',
    data: data
  })
}

// 修改农资信息
export function updateMaterialInfo(data) {
  return request({
    url: '/manage/materialInfo',
    method: 'put',
    data: data
  })
}

// 删除农资信息
export function delMaterialInfo(materialId) {
  return request({
    url: '/manage/materialInfo/' + materialId,
    method: 'delete'
  })
}
