package com.farm.devices.mapper;

import java.util.List;
import com.farm.devices.domain.WeatherStations;

/**
 * 气象站设备Mapper接口
 * 
 * @author beson
 * @date 2025-03-05
 */
public interface WeatherStationsMapper 
{
    /**
     * 查询气象站设备
     * 
     * @param weatherStationId 气象站设备主键
     * @return 气象站设备
     */
    public WeatherStations selectWeatherStationsByWeatherStationId(Long weatherStationId);

    /**
     * 查询气象站设备列表
     * 
     * @param weatherStations 气象站设备
     * @return 气象站设备集合
     */
    public List<WeatherStations> selectWeatherStationsList(WeatherStations weatherStations);

    /**
     * 新增气象站设备
     * 
     * @param weatherStations 气象站设备
     * @return 结果
     */
    public int insertWeatherStations(WeatherStations weatherStations);

    /**
     * 修改气象站设备
     * 
     * @param weatherStations 气象站设备
     * @return 结果
     */
    public int updateWeatherStations(WeatherStations weatherStations);

    /**
     * 删除气象站设备
     * 
     * @param weatherStationId 气象站设备主键
     * @return 结果
     */
    public int deleteWeatherStationsByWeatherStationId(Long weatherStationId);

    /**
     * 批量删除气象站设备
     * 
     * @param weatherStationIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWeatherStationsByWeatherStationIds(Long[] weatherStationIds);
}
