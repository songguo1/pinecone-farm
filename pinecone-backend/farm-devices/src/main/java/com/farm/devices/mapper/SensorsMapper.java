package com.farm.devices.mapper;

import java.util.List;
import com.farm.devices.domain.Sensors;

/**
 * 传感器设备Mapper接口
 * 
 * @author beson
 * @date 2025-03-05
 */
public interface SensorsMapper 
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
     * 删除传感器设备
     * 
     * @param sensorId 传感器设备主键
     * @return 结果
     */
    public int deleteSensorsBySensorId(Long sensorId);

    /**
     * 批量删除传感器设备
     * 
     * @param sensorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSensorsBySensorIds(Long[] sensorIds);
}
