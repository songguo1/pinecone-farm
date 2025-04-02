package com.farm.devices.service;

import java.util.List;
import com.farm.devices.domain.Sensors;

/**
 * 传感器设备Service接口
 * 
 * @author beson
 * @date 2025-03-05
 */
public interface ISensorsService 
{
    /**
     * 查询传感器设备
     * 
     * @param sensorId 传感器设备主键
     * @return 传感器设备
     */
    public Sensors selectSensorsBySensorId(Long sensorId);

    /**
     * 查询传感器设备列表
     * 
     * @param sensors 传感器设备
     * @return 传感器设备集合
     */
    public List<Sensors> selectSensorsList(Sensors sensors);

    /**
     * 新增传感器设备
     * 
     * @param sensors 传感器设备
     * @return 结果
     */
    public int insertSensors(Sensors sensors);

    /**
     * 修改传感器设备
     * 
     * @param sensors 传感器设备
     * @return 结果
     */
    public int updateSensors(Sensors sensors);

    /**
     * 批量删除传感器设备
     * 
     * @param sensorIds 需要删除的传感器设备主键集合
     * @return 结果
     */
    public int deleteSensorsBySensorIds(Long[] sensorIds);

    /**
     * 删除传感器设备信息
     * 
     * @param sensorId 传感器设备主键
     * @return 结果
     */
    public int deleteSensorsBySensorId(Long sensorId);
}
