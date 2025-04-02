
import GeoJSON from 'ol/format/GeoJSON';
import WKT from "terraformer-wkt-parser";

// 创建格式转换器实例
const geoJsonFormat = new GeoJSON();

export function wktToGeoJSON(wktString, options = {}) {
  if (!wktString) {
    return null;
  }

  try {
    const geoJson = WKT.parse(wktString);
    return geoJson;
  } catch (error) {
    console.error('WKT转GeoJSON失败:', error);
    return null;
  }
}

/**
 * GeoJSON 转 WKT
 * @param {Object} geoJson - GeoJSON对象
 * @param {Object} options - 转换选项
 * @param {string} options.dataProjection - 数据的投影坐标系，默认 'EPSG:4326'
 * @param {string} options.featureProjection - 要转换的目标坐标系，默认 'EPSG:3857'
 * @returns {string|null} WKT字符串或null
 */
export function geoJSONToWKT(geoJson, options = {}) {
  if (!geoJson) {
    return null;
  }

  try {
    // 设置默认值
    const defaultOptions = {
      dataProjection: 'EPSG:4326',
      featureProjection: 'EPSG:3857',
      ...options
    };

    // GeoJSON转换为Feature
    const feature = geoJsonFormat.readFeature(geoJson, defaultOptions);
    
    // Feature转换为WKT
    const wktString = wktFormat.writeFeature(feature, defaultOptions);
    
    return wktString;
  } catch (error) {
    console.error('GeoJSON转WKT失败:', error);
    return null;
  }
}

/**
 * 批量转换WKT数组为GeoJSON数组
 * @param {string[]} wktArray - WKT字符串数组
 * @param {Object} options - 转换选项
 * @returns {Object[]} GeoJSON对象数组
 */
export function batchWKTToGeoJSON(wktArray, options = {}) {
  if (!Array.isArray(wktArray)) {
    return [];
  }
  
  return wktArray
    .map(wkt => wktToGeoJSON(wkt, options))
    .filter(Boolean); // 过滤掉null结果
}

/**
 * 批量转换GeoJSON数组为WKT数组
 * @param {Object[]} geoJsonArray - GeoJSON对象数组
 * @param {Object} options - 转换选项
 * @returns {string[]} WKT字符串数组
 */
export function batchGeoJSONToWKT(geoJsonArray, options = {}) {
  if (!Array.isArray(geoJsonArray)) {
    return [];
  }
  
  return geoJsonArray
    .map(geojson => geoJSONToWKT(geojson, options))
    .filter(Boolean); // 过滤掉null结果
}

// 使用示例：
const examples = {
  // WKT示例
  wktPoint: 'POINT(116.404 39.915)',
  wktLineString: 'LINESTRING(116.404 39.915, 116.504 39.915)',
  wktPolygon: 'POLYGON((116.404 39.915, 116.504 39.915, 116.504 39.815, 116.404 39.815, 116.404 39.915))',
  
  // GeoJSON示例
  geoJsonPoint: {
    "type": "Feature",
    "geometry": {
      "type": "Point",
      "coordinates": [116.404, 39.915]
    }
  },
  geoJsonLineString: {
    "type": "Feature",
    "geometry": {
      "type": "LineString",
      "coordinates": [[116.404, 39.915], [116.504, 39.915]]
    }
  },
  geoJsonPolygon: {
    "type": "Feature",
    "geometry": {
      "type": "Polygon",
      "coordinates": [[[116.404, 39.915], [116.504, 39.915], [116.504, 39.815], [116.404, 39.815], [116.404, 39.915]]]
    }
  }
};
