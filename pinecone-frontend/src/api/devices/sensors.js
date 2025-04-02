import request from '@/utils/request'

const AllParams = {
  pageNum: 1,
  pageSize: 10000,
  sensorName: null,
  sensorType: null,
  status: null,
};

export function getAllSensors() {
  return request({
    url: '/devices/sensors/list',
    method: 'get',
    params: AllParams
  })
}

// 查询传感器设备列表
export function listSensors(query) {
  return request({
    url: '/devices/sensors/list',
    method: 'get',
    params: query
  })
}

// 查询传感器设备详细
export function getSensors(sensorId) {
  return request({
    url: '/devices/sensors/' + sensorId,
    method: 'get'
  })
}

// 新增传感器设备
export function addSensors(data) {
  return request({
    url: '/devices/sensors',
    method: 'post',
    data: data
  })
}

// 修改传感器设备
export function updateSensors(data) {
  return request({
    url: '/devices/sensors',
    method: 'put',
    data: data
  })
}

// 删除传感器设备
export function delSensors(sensorId) {
  return request({
    url: '/devices/sensors/' + sensorId,
    method: 'delete'
  })
}
