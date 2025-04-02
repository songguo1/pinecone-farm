import {
  getAllFarmland,
  listFarmland,
  getFarmland,
  delFarmland,
  addFarmland,
  updateFarmland,
} from "@/api/manage/farmland";

import { getAllGreenhouse } from "@/api/manage/greenhouse";
import { getAllPond } from "@/api/manage/pond";
import * as Cesium from "cesium";

import WKT from "terraformer-wkt-parser";
const color = {
  玉米: {
    fill: new Cesium.Color(1, 0.843, 0, 0.6), // 金黄色填充
    border: new Cesium.Color(1, 0.843, 0, 1.0), // 金黄色边框
  },
  水稻: {
    fill: new Cesium.Color(0.678, 0.847, 0.902, 0.6), // 浅蓝色填充
    border: new Cesium.Color(0.678, 0.847, 0.902, 1.0), // 浅蓝色边框
  },
  蔬菜: {
    fill: new Cesium.Color(0.196, 0.804, 0.196, 0.6), // 绿色填充
    border: new Cesium.Color(0.196, 0.804, 0.196, 1.0), // 绿色边框
  },
  荔枝: {
    fill: new Cesium.Color(0.863, 0.078, 0.235, 0.6), // 红色填充
    border: new Cesium.Color(0.863, 0.078, 0.235, 1.0), // 红色边框
  },
  香蕉: {
    fill: new Cesium.Color(1, 1, 0, 0.6), // 黄色填充
    border: new Cesium.Color(1, 1, 0, 1.0), // 黄色边框
  },
};
export const loadFarmland = async (viewer) => {
  try {
    const farmlandList = await getAllFarmland();
    for (const farmland of farmlandList.data) {
      if (!farmland.geom) {
        console.warn(`Farmland ${farmland.farmland_id} has no geometry data`);
        continue;
      }

      try {
        const farmlandGeom = WKT.parse(farmland.geom);
        await Cesium.GeoJsonDataSource.load(farmlandGeom, {
          clampToGround: true,
          fill: color[farmland.cropsType].fill, // 使用填充颜色
          strokeWidth: 2,
        }).then((dataSource) => {
          viewer.dataSources.add(dataSource);
          var entities = dataSource.entities.values;
          for (let i = 0; i < entities.length; i++) {
            let entity = entities[i];
            let polyPositions = entity.polygon.hierarchy.getValue(
              Cesium.JulianDate.now()
            ).positions;
            //单独设置线条样式
            var positions = entity.polygon.hierarchy._value.positions;
            entity.polyline = {
              positions: positions,
              width: 2,
              material: color[farmland.cropsType].border, // 使用边框颜色
            };
          }
        });
      } catch (parseError) {
        console.error(
          `Error parsing geometry for farmland ${farmland.farmland_id}:`,
          parseError
        );
        continue;
      }
    }
  } catch (error) {
    console.error("Error loading farmland data:", error);
  }
};

export const loadGreenhouse = async (viewer) => {
  try {
    const greenhouseList = await getAllGreenhouse();
    for (const greenhouse of greenhouseList.data) {
      if (!greenhouse.geom) {
        console.warn(`Greenhouse ${greenhouse.id} has no geometry data`);
        continue;
      }

      try {
        const greenhouseGeom = WKT.parse(greenhouse.geom);
        await Cesium.GeoJsonDataSource.load(greenhouseGeom, {
          clampToGround: true,
          fill: true,
          stroke: true,
          strokeWidth: 2,
          strokeColor: Cesium.Color.RED,
        }).then((dataSource) => {
          viewer.dataSources.add(dataSource);
          var entities = dataSource.entities.values;
          for (let i = 0; i < entities.length; i++) {
            let entity = entities[i];
            let polyPositions = entity.polygon.hierarchy.getValue(
              Cesium.JulianDate.now()
            ).positions;
            //单独设置线条样式
            var positions = entity.polygon.hierarchy._value.positions;
            entity.polyline = {
              positions: positions,
              width: 2,
              material: Cesium.Color.fromBytes(96, 150, 250),
            };
          }
        });
      } catch (parseError) {
        console.error(
          `Error parsing geometry for greenhouse ${greenhouse.id}:`,
          parseError
        );
        continue;
      }
    }
  } catch (error) {
    console.error("Error loading greenhouse data:", error);
  }
};

export const loadPond = async (viewer) => {
  try {
    const pondList = await getAllPond();
    for (const pond of pondList.data) {
      if (!pond.geom) {
        console.warn(`Pond ${pond.id} has no geometry data`);
        continue;
      }

      try {
        const pondGeom = WKT.parse(pond.geom);
        await Cesium.GeoJsonDataSource.load(pondGeom, {
          clampToGround: true,
          fill: new Cesium.Color(0.678, 0.855, 0.902, 0.5),
          stroke: true,
          strokeWidth: 5,
          strokeColor: new Cesium.Color(0.529, 0.812, 0.922, 1),
        }).then((dataSource) => {
          viewer.dataSources.add(dataSource);
          var entities = dataSource.entities.values;
          for (let i = 0; i < entities.length; i++) {
            let entity = entities[i];
            let polyPositions = entity.polygon.hierarchy.getValue(
              Cesium.JulianDate.now()
            ).positions;
            //单独设置线条样式
            var positions = entity.polygon.hierarchy._value.positions;
            entity.polyline = {
              positions: positions,
              width: 2,
              material: Cesium.Color.fromBytes(96, 200, 250),
            };
          }
        });
      } catch (parseError) {
        console.error(
          `Error parsing geometry for pond ${pond.id}:`,
          parseError
        );
        continue;
      }
    }
  } catch (error) {
    console.error("Error loading pond data:", error);
  }
};
