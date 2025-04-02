import * as Cesium from "cesium";

export const configureTianDiTuLayers = (viewer: Cesium.Viewer) => {
  const imageLayers = viewer.scene.imageryLayers;
  imageLayers.remove(imageLayers.get(0));
  
  const TDTTK = "a9783be3ba8bec546e8ea23c4b1fd6a3";
  
  // 天地图影像
  const tdtLayer = new Cesium.WebMapTileServiceImageryProvider({
    url: `http://t0.tianditu.com/img_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={TileMatrix}&TILEROW={TileRow}&TILECOL={TileCol}&tk=${TDTTK}`,
    layer: "tdt",
    style: "default",
    format: "image/jpeg",
    tileMatrixSetID: "w",
    maximumLevel: 18,
    show: false,
  });
  viewer.imageryLayers.addImageryProvider(tdtLayer);

  // 天地图注记
  const tdtAnnotionLayer = new Cesium.WebMapTileServiceImageryProvider({
    url: `http://t0.tianditu.com/cia_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cia&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={TileMatrix}&TILEROW={TileRow}&TILECOL={TileCol}&tk=${TDTTK}`,
    layer: "tdtAnno",
    style: "default",
    format: "image/jpeg",
    tileMatrixSetID: "w",
    maximumLevel: 18,
    show: false,
  });
  viewer.imageryLayers.addImageryProvider(tdtAnnotionLayer);
}; 