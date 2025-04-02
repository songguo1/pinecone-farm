import { getAllMonitoring } from "@/api/devices/monitoring";
import { getAllSensors } from "@/api/devices/sensors";
import {getAllWeather} from "@/api/devices/weather";
function extractLonLat(pointString) {
  // 去掉字符串中的 "POINT(" 和 ")"，并按空格分割
  const coordinates = pointString.replace("POINT(", "").replace(")", "").split(" ");
  
  // 提取经度和纬度
  const lon = parseFloat(coordinates[0]); // 经度
  const lat = parseFloat(coordinates[1]); // 纬度

  return { lon, lat };
}


const postions=ref([])
export const loadDevices = async (viewer) => {
  const response1 = await getAllMonitoring();
  const listMonitoring=response1.rows
  const MonitoringPositions=listMonitoring.forEach(item=>{
    postions.value.push({
      lon:extractLonLat(item.location).lon,
      lat:extractLonLat(item.location).lat,
      height:10,
      text:item.deviceName,
      iconUrl:item.icon
    })
  })

  const response2 = await getAllWeather();
  const listWeather=response2.rows
  const WeatherPositions=listWeather.forEach(item=>{
    postions.value.push({
      lon:extractLonLat(item.location).lon,
      lat:extractLonLat(item.location).lat,
      height:10,
      text:item.stationName,
      iconUrl:item.icon
    })
  })
  
  const response3 = await getAllSensors();
  const listSensors=response3.rows
  const SensorsPositions=listSensors.forEach(item=>{
    postions.value.push({
      lon:extractLonLat(item.location).lon,
      lat:extractLonLat(item.location).lat,
      height:10,
      text:item.sensorName,
      iconUrl:item.icon
    })
  })
  return postions
};

