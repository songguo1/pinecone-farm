import request from '@/utils/request'

// 查询系统文档列表
export function listDocuments(query) {
  return request({
    url: '/data/documents/list',
    method: 'get',
    params: query
  })
}

// 查询系统文档详细
export function getDocuments(documentId) {
  return request({
    url: '/data/documents/' + documentId,
    method: 'get'
  })
}

// 新增系统文档
export function addDocuments(data) {
  return request({
    url: '/data/documents',
    method: 'post',
    data: data
  })
}

// 修改系统文档
export function updateDocuments(data) {
  return request({
    url: '/data/documents',
    method: 'put',
    data: data
  })
}

// 删除系统文档
export function delDocuments(documentId) {
  return request({
    url: '/data/documents/' + documentId,
    method: 'delete'
  })
}
