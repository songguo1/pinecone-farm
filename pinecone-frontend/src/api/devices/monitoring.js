import request from '@/utils/request'

const AllParams = {
  pageNum: 1,
  pageSize: 10000,
  deviceName: null,
  status: null,
};

export function getAllMonitoring() {
  return request({
    url: '/devices/monitoring/list',
    method: 'get',
    params: AllParams
  })
}

// 查询监控设备列表
export function listMonitoring(query) {
  return request({
    url: '/devices/monitoring/list',
    method: 'get',
    params: query
  })
}

// 查询监控设备详细
export function getMonitoring(monitoringDeviceId) {
  return request({
    url: '/devices/monitoring/' + monitoringDeviceId,
    method: 'get'
  })
}

// 新增监控设备
export function addMonitoring(data) {
  return request({
    url: '/devices/monitoring',
    method: 'post',
    data: data
  })
}

// 修改监控设备
export function updateMonitoring(data) {
  return request({
    url: '/devices/monitoring',
    method: 'put',
    data: data
  })
}

// 删除监控设备
export function delMonitoring(monitoringDeviceId) {
  return request({
    url: '/devices/monitoring/' + monitoringDeviceId,
    method: 'delete'
  })
}
