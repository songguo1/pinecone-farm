package com.farm.devices.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.devices.mapper.MonitoringDevicesMapper;
import com.farm.devices.domain.MonitoringDevices;
import com.farm.devices.service.IMonitoringDevicesService;

/**
 * 监控设备Service业务层处理
 * 
 * @author beson
 * @date 2025-03-05
 */
@Service
public class MonitoringDevicesServiceImpl implements IMonitoringDevicesService 
{
    @Autowired
    private MonitoringDevicesMapper monitoringDevicesMapper;

    /**
     * 查询监控设备
     * 
     * @param monitoringDeviceId 监控设备主键
     * @return 监控设备
     */
    @Override
    public MonitoringDevices selectMonitoringDevicesByMonitoringDeviceId(Long monitoringDeviceId)
    {
        return monitoringDevicesMapper.selectMonitoringDevicesByMonitoringDeviceId(monitoringDeviceId);
    }

    /**
     * 查询监控设备列表
     * 
     * @param monitoringDevices 监控设备
     * @return 监控设备
     */
    @Override
    public List<MonitoringDevices> selectMonitoringDevicesList(MonitoringDevices monitoringDevices)
    {
        return monitoringDevicesMapper.selectMonitoringDevicesList(monitoringDevices);
    }

    /**
     * 新增监控设备
     * 
     * @param monitoringDevices 监控设备
     * @return 结果
     */
    @Override
    public int insertMonitoringDevices(MonitoringDevices monitoringDevices)
    {
        monitoringDevices.setCreateTime(DateUtils.getNowDate());
        return monitoringDevicesMapper.insertMonitoringDevices(monitoringDevices);
    }

    /**
     * 修改监控设备
     * 
     * @param monitoringDevices 监控设备
     * @return 结果
     */
    @Override
    public int updateMonitoringDevices(MonitoringDevices monitoringDevices)
    {
        monitoringDevices.setUpdateTime(DateUtils.getNowDate());
        return monitoringDevicesMapper.updateMonitoringDevices(monitoringDevices);
    }

    /**
     * 批量删除监控设备
     * 
     * @param monitoringDeviceIds 需要删除的监控设备主键
     * @return 结果
     */
    @Override
    public int deleteMonitoringDevicesByMonitoringDeviceIds(Long[] monitoringDeviceIds)
    {
        return monitoringDevicesMapper.deleteMonitoringDevicesByMonitoringDeviceIds(monitoringDeviceIds);
    }

    /**
     * 删除监控设备信息
     * 
     * @param monitoringDeviceId 监控设备主键
     * @return 结果
     */
    @Override
    public int deleteMonitoringDevicesByMonitoringDeviceId(Long monitoringDeviceId)
    {
        return monitoringDevicesMapper.deleteMonitoringDevicesByMonitoringDeviceId(monitoringDeviceId);
    }
}
