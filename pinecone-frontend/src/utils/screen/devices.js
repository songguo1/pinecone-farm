import { getAllMonitoring } from "@/api/devices/monitoring";
import { getAllSensors } from "@/api/devices/sensors";
import { getAllWeather } from "@/api/devices/weather";

// 处理图片URL的函数
function processImageUrl(url) {
  if (!url) return "";

  // 检查URL是否是阿里云OSS的URL
  if (url.includes("beson-bucket.oss-cn-beijing.aliyuncs.com")) {
    // 使用配置好的代理
    return url.replace(
      "https://beson-bucket.oss-cn-beijing.aliyuncs.com",
      "/api/proxy/oss"
    );
  }

  return url;
}

function extractLonLat(pointString) {
  // 检查pointString是否为null或undefined
  if (!pointString) {
    console.warn("传入的点位数据为空");
    return { lon: 0, lat: 0 }; // 返回默认值
  }

  try {
    // 输出原始字符串以便调试
    console.log("解析点位数据:", pointString);

    let coordinates;

    // 处理标准WKT格式："POINT(116.404 39.915)"
    if (pointString.trim().startsWith("POINT")) {
      coordinates = pointString
        .replace("POINT(", "")
        .replace(")", "")
        .trim()
        .split(/\s+/); // 使用正则表达式匹配任意空白字符
    }
    // 处理可能的扩展WKT格式："POINT(116.404, 39.915)"
    else if (pointString.includes(",")) {
      coordinates = pointString.split(",").map((coord) => coord.trim());
    }
    // 处理简单的坐标对："116.404 39.915"
    else {
      coordinates = pointString.trim().split(/\s+/);
    }

    if (!coordinates || coordinates.length < 2) {
      console.error("无法从字符串中提取坐标:", pointString);
      return { lon: 0, lat: 0 };
    }

    // 提取经度和纬度，并确保它们是数字
    const lon = parseFloat(coordinates[0].replace(/[^0-9.-]/g, "")); // 经度
    const lat = parseFloat(coordinates[1].replace(/[^0-9.-]/g, "")); // 纬度

    // 验证经纬度是否有效
    if (isNaN(lon) || isNaN(lat)) {
      console.error("解析坐标失败，得到非数字值:", {
        lon,
        lat,
        original: pointString,
      });
      return { lon: 0, lat: 0 };
    }

    // 基本验证经纬度范围
    if (Math.abs(lon) > 180 || Math.abs(lat) > 90) {
      console.warn("解析得到的坐标超出正常范围:", {
        lon,
        lat,
        original: pointString,
      });
    }

    console.log("成功解析坐标:", { lon, lat });
    return { lon, lat };
  } catch (error) {
    console.error("解析点位数据时出错:", error, "原始数据:", pointString);
    return { lon: 0, lat: 0 };
  }
}

const postions = ref([]);
export const loadDevices = async (viewer) => {
  const response1 = await getAllMonitoring();
  const listMonitoring = response1.rows;
  console.log(
    "监控设备图标URLs:",
    listMonitoring.map((item) => item.icon)
  );

  const MonitoringPositions = listMonitoring.forEach((item) => {
    postions.value.push({
      lon: extractLonLat(item.location).lon,
      lat: extractLonLat(item.location).lat,
      height: 10,
      text: item.deviceName,
      iconUrl: processImageUrl(item.icon),
    });
  });

  const response2 = await getAllWeather();
  const listWeather = response2.rows;
  console.log(
    "气象站图标URLs:",
    listWeather.map((item) => item.icon)
  );

  const WeatherPositions = listWeather.forEach((item) => {
    postions.value.push({
      lon: extractLonLat(item.location).lon,
      lat: extractLonLat(item.location).lat,
      height: 10,
      text: item.stationName,
      iconUrl: processImageUrl(item.icon),
    });
  });

  const response3 = await getAllSensors();
  const listSensors = response3.rows;
  console.log(
    "传感器图标URLs:",
    listSensors.map((item) => item.icon)
  );

  const SensorsPositions = listSensors.forEach((item) => {
    postions.value.push({
      lon: extractLonLat(item.location).lon,
      lat: extractLonLat(item.location).lat,
      height: 10,
      text: item.sensorName,
      iconUrl: processImageUrl(item.icon),
    });
  });
  return postions;
};
