import * as Cesium from "cesium";

export const addWaterEffect = async (viewer: Cesium.Viewer) => {
  viewer.scene.globe.depthTestAgainstTerrain = false;
  
  const response = await fetch("/static/geojson/water.geojson");
  const waterData = await response.json();

  waterData.features.forEach((feature) => {
    if (feature.geometry.type === "MultiPolygon") {
      feature.geometry.coordinates.forEach((polygon) => {
        createWaterPolygon(viewer, polygon[0]);
      });
    }
  });
};

const createWaterPolygon = (viewer: Cesium.Viewer, coordinates: number[][]) => {
  const waterCoords = coordinates.flatMap(item => [item[0], item[1]]);
  
  const waterPolygon = new Cesium.PolygonGeometry({
    polygonHierarchy: new Cesium.PolygonHierarchy(
      Cesium.Cartesian3.fromDegreesArray(waterCoords)
    ),
    height: 1,
    extrudedHeight: 0,
    ellipsoid: Cesium.Ellipsoid.WGS84,
  });

  const waterMaterial = createWaterMaterial();

  const waterPrimitive = new Cesium.Primitive({
    geometryInstances: new Cesium.GeometryInstance({
      id: "water",
      geometry: waterPolygon,
    }),
    appearance: new Cesium.EllipsoidSurfaceAppearance({
      material: waterMaterial,
    }),
    show: true,
    allowPicking: false,
    asynchronous: false,
  });

  viewer.scene.primitives.add(waterPrimitive);
};

const createWaterMaterial = () => {
  return new Cesium.Material({
    fabric: {
      type: "Water",
      uniforms: {
        baseWaterColor: new Cesium.Color(0.2, 0.3, 0.6, 0.7),
        normalMap: "/static/img/waterNormals.jpg",
        frequency: 1000.0,
        animationSpeed: 0.01,
        amplitude: 10.0,
        specularIntensity: 1.0,
      },
    },
  });
}; 