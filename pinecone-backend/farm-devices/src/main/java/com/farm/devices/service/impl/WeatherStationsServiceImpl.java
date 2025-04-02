package com.farm.devices.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.devices.mapper.WeatherStationsMapper;
import com.farm.devices.domain.WeatherStations;
import com.farm.devices.service.IWeatherStationsService;

/**
 * 气象站设备Service业务层处理
 * 
 * @author beson
 * @date 2025-03-05
 */
@Service
public class WeatherStationsServiceImpl implements IWeatherStationsService 
{
    @Autowired
    private WeatherStationsMapper weatherStationsMapper;

    /**
     * 查询气象站设备
     * 
     * @param weatherStationId 气象站设备主键
     * @return 气象站设备
     */
    @Override
    public WeatherStations selectWeatherStationsByWeatherStationId(Long weatherStationId)
    {
        return weatherStationsMapper.selectWeatherStationsByWeatherStationId(weatherStationId);
    }

    /**
     * 查询气象站设备列表
     * 
     * @param weatherStations 气象站设备
     * @return 气象站设备
     */
    @Override
    public List<WeatherStations> selectWeatherStationsList(WeatherStations weatherStations)
    {
        return weatherStationsMapper.selectWeatherStationsList(weatherStations);
    }

    /**
     * 新增气象站设备
     * 
     * @param weatherStations 气象站设备
     * @return 结果
     */
    @Override
    public int insertWeatherStations(WeatherStations weatherStations)
    {
        weatherStations.setCreateTime(DateUtils.getNowDate());
        return weatherStationsMapper.insertWeatherStations(weatherStations);
    }

    /**
     * 修改气象站设备
     * 
     * @param weatherStations 气象站设备
     * @return 结果
     */
    @Override
    public int updateWeatherStations(WeatherStations weatherStations)
    {
        weatherStations.setUpdateTime(DateUtils.getNowDate());
        return weatherStationsMapper.updateWeatherStations(weatherStations);
    }

    /**
     * 批量删除气象站设备
     * 
     * @param weatherStationIds 需要删除的气象站设备主键
     * @return 结果
     */
    @Override
    public int deleteWeatherStationsByWeatherStationIds(Long[] weatherStationIds)
    {
        return weatherStationsMapper.deleteWeatherStationsByWeatherStationIds(weatherStationIds);
    }

    /**
     * 删除气象站设备信息
     * 
     * @param weatherStationId 气象站设备主键
     * @return 结果
     */
    @Override
    public int deleteWeatherStationsByWeatherStationId(Long weatherStationId)
    {
        return weatherStationsMapper.deleteWeatherStationsByWeatherStationId(weatherStationId);
    }
}
