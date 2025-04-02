import request from '@/utils/request'

const AllParams = {
  pageNum: 1,
  pageSize: 10000,
  stationName: null,
  status: null,
};

export function getAllWeather() {
  return request({
    url: '/devices/weather/list',
    method: 'get',
    params: AllParams
  })
}

// 查询气象站设备列表
export function listWeather(query) {
  return request({
    url: '/devices/weather/list',
    method: 'get',
    params: query
  })
}

// 查询气象站设备详细
export function getWeather(weatherStationId) {
  return request({
    url: '/devices/weather/' + weatherStationId,
    method: 'get'
  })
}

// 新增气象站设备
export function addWeather(data) {
  return request({
    url: '/devices/weather',
    method: 'post',
    data: data
  })
}

// 修改气象站设备
export function updateWeather(data) {
  return request({
    url: '/devices/weather',
    method: 'put',
    data: data
  })
}

// 删除气象站设备
export function delWeather(weatherStationId) {
  return request({
    url: '/devices/weather/' + weatherStationId,
    method: 'delete'
  })
}
