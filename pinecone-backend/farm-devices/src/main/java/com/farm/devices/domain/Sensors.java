package com.farm.devices.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 传感器设备对象 agri_sensors
 * 
 * @author beson
 * @date 2025-03-05
 */
public class Sensors extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long sensorId;

    /** 传感器名称 */
    @Excel(name = "传感器名称")
    private String sensorName;

    /** 传感器类型 */
    @Excel(name = "传感器类型")
    private Integer sensorType;

    /** 测量单位 */
    @Excel(name = "测量单位")
    private Integer measurementUnit;

    /** 传感器的地理坐标 */
    private String location;

    /** 传感器状态 */
    @Excel(name = "传感器状态")
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

    /** 最后一次读取时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后一次读取时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastReading;

    /** 最后一次测量值 */
    @Excel(name = "最后一次测量值")
    private Double lastValue;

    public void setSensorId(Long sensorId) 
    {
        this.sensorId = sensorId;
    }

    public Long getSensorId() 
    {
        return sensorId;
    }
    public void setSensorName(String sensorName) 
    {
        this.sensorName = sensorName;
    }

    public String getSensorName() 
    {
        return sensorName;
    }
    public void setSensorType(Integer sensorType) 
    {
        this.sensorType = sensorType;
    }

    public Integer getSensorType() 
    {
        return sensorType;
    }
    public void setMeasurementUnit(Integer measurementUnit) 
    {
        this.measurementUnit = measurementUnit;
    }

    public Integer getMeasurementUnit() 
    {
        return measurementUnit;
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
    public void setLastReading(Date lastReading) 
    {
        this.lastReading = lastReading;
    }

    public Date getLastReading() 
    {
        return lastReading;
    }
    public void setLastValue(Double lastValue)
    {
        this.lastValue = lastValue;
    }

    public Double getLastValue()
    {
        return lastValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sensorId", getSensorId())
            .append("sensorName", getSensorName())
            .append("sensorType", getSensorType())
            .append("measurementUnit", getMeasurementUnit())
            .append("location", getLocation())
            .append("status", getStatus())
            .append("icon", getIcon())
            .append("deviceImage", getDeviceImage())
            .append("remarks", getRemarks())
            .append("lastReading", getLastReading())
            .append("lastValue", getLastValue())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
