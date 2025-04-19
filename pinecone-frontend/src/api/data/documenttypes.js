import request from '@/utils/request'

// 查询文档类型列表
export function listDocumenttypes(query) {
  return request({
    url: '/data/documenttypes/list',
    method: 'get',
    params: query
  })
}

// 查询文档类型详细
export function getDocumenttypes(documentTypeId) {
  return request({
    url: '/data/documenttypes/' + documentTypeId,
    method: 'get'
  })
}

// 新增文档类型
export function addDocumenttypes(data) {
  return request({
    url: '/data/documenttypes',
    method: 'post',
    data: data
  })
}

// 修改文档类型
export function updateDocumenttypes(data) {
  return request({
    url: '/data/documenttypes',
    method: 'put',
    data: data
  })
}

// 删除文档类型
export function delDocumenttypes(documentTypeId) {
  return request({
    url: '/data/documenttypes/' + documentTypeId,
    method: 'delete'
  })
}
