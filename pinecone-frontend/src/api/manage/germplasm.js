import request from '@/utils/request'

// 查询种质系统列表
export function listGermplasm(query) {
  return request({
    url: '/manage/germplasm/list',
    method: 'get',
    params: query
  })
}

// 查询种质系统详细
export function getGermplasm(germplasmId) {
  return request({
    url: '/manage/germplasm/' + germplasmId,
    method: 'get'
  })
}

// 新增种质系统
export function addGermplasm(data) {
  return request({
    url: '/manage/germplasm',
    method: 'post',
    data: data
  })
}

// 修改种质系统
export function updateGermplasm(data) {
  return request({
    url: '/manage/germplasm',
    method: 'put',
    data: data
  })
}

// 删除种质系统
export function delGermplasm(germplasmId) {
  return request({
    url: '/manage/germplasm/' + germplasmId,
    method: 'delete'
  })
}
