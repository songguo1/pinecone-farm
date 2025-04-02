import axios from 'axios'
import {
  GeometryInstance,
  WallGeometry,
  Cartesian3,
  Cartesian2,
  EllipsoidSurfaceAppearance,
  Material,
  Primitive,
  Color,
} from 'cesium'

export async function initBorderLayer(viewer: any) {
  try {
    const response = await axios.get('/static/geojson/border.geojson');
    const geometries = response.data.features;
    
    if (!geometries || geometries.length === 0) {
      console.error('未找到边界数据');
      return;
    }

    const geometryInstances = geometries.flatMap((geometry: any) => {
      const coordinates = geometry.geometry.coordinates;
      const height = 100;
      
      // 将多维数组展平成一维数组，并添加高度值
      const flatCoords = coordinates.flat(Infinity); // 完全展平嵌套数组
      const positions: number[] = [];
      
      // 每两个值（经度、纬度）添加一个高度值
      for (let i = 0; i < flatCoords.length; i += 2) {
        positions.push(
          flatCoords[i],     // 经度
          flatCoords[i + 1], // 纬度
          height            // 高度
        );
      }

      return new GeometryInstance({
        geometry: new WallGeometry({
          positions: Cartesian3.fromDegreesArrayHeights(positions),
        }),
      });
    });

    const primitive = new Primitive({
      geometryInstances,
      appearance: new EllipsoidSurfaceAppearance({
        material: Material.fromType('Fade', {
          fadeInColor: new Color(22 / 255, 132 / 255, 129 / 255, 0.0),
          fadeOutColor: Color.BISQUE,
          repeat: false,
          fadeDirection: new Cartesian2(0.0, 1.0),
          maximumDistance: 1.0,
          time: {
            x: 0.0,
            y: 0.0,
          },
        }),
        aboveGround: true,
      }),
    });

    viewer.scene.primitives.add(primitive);
    console.log('边界层加载成功');
  } catch (error) {
    console.error('加载边界层时出错:', error);
  }
}
