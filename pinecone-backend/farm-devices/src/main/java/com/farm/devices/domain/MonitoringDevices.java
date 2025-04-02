package com.farm.devices.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 监控设备对象 agri_monitoring_devices
 * 
 * @author beson
 * @date 2025-03-05
 */
public class MonitoringDevices extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long monitoringDeviceId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 存储容量（单位：GB） */
    @Excel(name = "存储容量", readConverterExp = "单=位：GB")
    private Double storageCapacity;

    /** 设备的地理坐标 */
    private String location;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private Integer status;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 设备图片 */
    @Excel(name = "设备图片")
    private String deviceImage;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String remarks;

    /** 最后一次记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后一次记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastRecord;

    public void setMonitoringDeviceId(Long monitoringDeviceId) 
    {
        this.monitoringDeviceId = monitoringDeviceId;
    }

    public Long getMonitoringDeviceId() 
    {
        return monitoringDeviceId;
    }
    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }
    public void setStorageCapacity(Double storageCapacity) 
    {
        this.storageCapacity = storageCapacity;
    }

    public Double getStorageCapacity() 
    {
        return storageCapacity;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }
    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }
    public void setDeviceImage(String deviceImage) 
    {
        this.deviceImage = deviceImage;
    }

    public String getDeviceImage() 
    {
        return deviceImage;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setLastRecord(Date lastRecord) 
    {
        this.lastRecord = lastRecord;
    }

    public Date getLastRecord() 
    {
        return lastRecord;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("monitoringDeviceId", getMonitoringDeviceId())
            .append("deviceName", getDeviceName())
            .append("storageCapacity", getStorageCapacity())
            .append("location", getLocation())
            .append("status", getStatus())
            .append("icon", getIcon())
            .append("deviceImage", getDeviceImage())
            .append("remarks", getRemarks())
            .append("lastRecord", getLastRecord())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
