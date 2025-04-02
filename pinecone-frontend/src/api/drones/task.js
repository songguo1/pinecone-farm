import request from '@/utils/request'

const AllParams={
  pageNum: 1,
  pageSize: 10000,
  taskName: null,
  taskType: null,
  taskDate: null,
  droneId: null,
  pilotId: null,
  dronesHeight: null,
}

//查询所有无人机任务
export function getAllTasks(){
  return request({
    url: '/drones/task/list',
    method: 'get',
    params: AllParams
  })
}
// 查询无人机任务列表
export function listTask(query) {
  return request({
    url: '/drones/task/list',
    method: 'get',
    params: query
  })
}

// 查询无人机任务详细
export function getTask(id) {
  return request({
    url: '/drones/task/' + id,
    method: 'get'
  })
}

// 新增无人机任务
export function addTask(data) {
  return request({
    url: '/drones/task',
    method: 'post',
    data: data
  })
}

// 修改无人机任务
export function updateTask(data) {
  return request({
    url: '/drones/task',
    method: 'put',
    data: data
  })
}

// 删除无人机任务
export function delTask(id) {
  return request({
    url: '/drones/task/' + id,
    method: 'delete'
  })
}
