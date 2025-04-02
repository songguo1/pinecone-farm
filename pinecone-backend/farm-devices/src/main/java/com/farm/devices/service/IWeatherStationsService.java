package com.farm.devices.service;

import java.util.List;
import com.farm.devices.domain.WeatherStations;

/**
 * 气象站设备Service接口
 * 
 * @author beson
 * @date 2025-03-05
 */
public interface IWeatherStationsService 
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
     * 批量删除气象站设备
     * 
     * @param weatherStationIds 需要删除的气象站设备主键集合
     * @return 结果
     */
    public int deleteWeatherStationsByWeatherStationIds(Long[] weatherStationIds);

    /**
     * 删除气象站设备信息
     * 
     * @param weatherStationId 气象站设备主键
     * @return 结果
     */
    public int deleteWeatherStationsByWeatherStationId(Long weatherStationId);
}
