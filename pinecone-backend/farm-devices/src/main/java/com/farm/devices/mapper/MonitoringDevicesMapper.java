package com.farm.devices.mapper;

import java.util.List;
import com.farm.devices.domain.MonitoringDevices;

/**
 * 监控设备Mapper接口
 * 
 * @author beson
 * @date 2025-03-05
 */
public interface MonitoringDevicesMapper 
{
    /**
     * 查询监控设备
     * 
     * @param monitoringDeviceId 监控设备主键
     * @return 监控设备
     */
    public MonitoringDevices selectMonitoringDevicesByMonitoringDeviceId(Long monitoringDeviceId);

    /**
     * 查询监控设备列表
     * 
     * @param monitoringDevices 监控设备
     * @return 监控设备集合
     */
    public List<MonitoringDevices> selectMonitoringDevicesList(MonitoringDevices monitoringDevices);

    /**
     * 新增监控设备
     * 
     * @param monitoringDevices 监控设备
     * @return 结果
     */
    public int insertMonitoringDevices(MonitoringDevices monitoringDevices);

    /**
     * 修改监控设备
     * 
     * @param monitoringDevices 监控设备
     * @return 结果
     */
    public int updateMonitoringDevices(MonitoringDevices monitoringDevices);

    /**
     * 删除监控设备
     * 
     * @param monitoringDeviceId 监控设备主键
     * @return 结果
     */
    public int deleteMonitoringDevicesByMonitoringDeviceId(Long monitoringDeviceId);

    /**
     * 批量删除监控设备
     * 
     * @param monitoringDeviceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMonitoringDevicesByMonitoringDeviceIds(Long[] monitoringDeviceIds);
}
