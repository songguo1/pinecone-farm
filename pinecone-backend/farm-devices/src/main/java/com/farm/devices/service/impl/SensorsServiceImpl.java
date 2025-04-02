package com.farm.devices.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.devices.mapper.SensorsMapper;
import com.farm.devices.domain.Sensors;
import com.farm.devices.service.ISensorsService;

/**
 * 传感器设备Service业务层处理
 * 
 * @author beson
 * @date 2025-03-05
 */
@Service
public class SensorsServiceImpl implements ISensorsService 
{
    @Autowired
    private SensorsMapper sensorsMapper;

    /**
     * 查询传感器设备
     * 
     * @param sensorId 传感器设备主键
     * @return 传感器设备
     */
    @Override
    public Sensors selectSensorsBySensorId(Long sensorId)
    {
        return sensorsMapper.selectSensorsBySensorId(sensorId);
    }

    /**
     * 查询传感器设备列表
     * 
     * @param sensors 传感器设备
     * @return 传感器设备
     */
    @Override
    public List<Sensors> selectSensorsList(Sensors sensors)
    {
        return sensorsMapper.selectSensorsList(sensors);
    }

    /**
     * 新增传感器设备
     * 
     * @param sensors 传感器设备
     * @return 结果
     */
    @Override
    public int insertSensors(Sensors sensors)
    {
        sensors.setCreateTime(DateUtils.getNowDate());
        return sensorsMapper.insertSensors(sensors);
    }

    /**
     * 修改传感器设备
     * 
     * @param sensors 传感器设备
     * @return 结果
     */
    @Override
    public int updateSensors(Sensors sensors)
    {
        sensors.setUpdateTime(DateUtils.getNowDate());
        return sensorsMapper.updateSensors(sensors);
    }

    /**
     * 批量删除传感器设备
     * 
     * @param sensorIds 需要删除的传感器设备主键
     * @return 结果
     */
    @Override
    public int deleteSensorsBySensorIds(Long[] sensorIds)
    {
        return sensorsMapper.deleteSensorsBySensorIds(sensorIds);
    }

    /**
     * 删除传感器设备信息
     * 
     * @param sensorId 传感器设备主键
     * @return 结果
     */
    @Override
    public int deleteSensorsBySensorId(Long sensorId)
    {
        return sensorsMapper.deleteSensorsBySensorId(sensorId);
    }
}
