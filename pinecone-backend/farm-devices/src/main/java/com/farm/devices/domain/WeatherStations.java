package com.farm.devices.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 气象站设备对象 agri_weather_stations
 * 
 * @author beson
 * @date 2025-03-05
 */
public class WeatherStations extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long weatherStationId;

    /** 气象站名称 */
    @Excel(name = "气象站名称")
    private String stationName;

    /** 气象站的地理坐标 */
    private String location;

    /** 气象站状态 */
    @Excel(name = "气象站状态")
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

    /** 最后一次数据更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后一次数据更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdate;

    public void setWeatherStationId(Long weatherStationId) 
    {
        this.weatherStationId = weatherStationId;
    }

    public Long getWeatherStationId() 
    {
        return weatherStationId;
    }
    public void setStationName(String stationName) 
    {
        this.stationName = stationName;
    }

    public String getStationName() 
    {
        return stationName;
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
    public void setLastUpdate(Date lastUpdate) 
    {
        this.lastUpdate = lastUpdate;
    }

    public Date getLastUpdate() 
    {
        return lastUpdate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("weatherStationId", getWeatherStationId())
            .append("stationName", getStationName())
            .append("location", getLocation())
            .append("status", getStatus())
            .append("icon", getIcon())
            .append("deviceImage", getDeviceImage())
            .append("remarks", getRemarks())
            .append("lastUpdate", getLastUpdate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
