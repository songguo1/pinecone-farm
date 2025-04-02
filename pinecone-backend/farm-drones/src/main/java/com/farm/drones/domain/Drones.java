package com.farm.drones.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 无人机信息，记录所有无人机的详细信息对象 agri_drones
 * 
 * @author beson
 * @date 2025-02-28
 */
public class Drones extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 无人机表主键，自动递增 */
    private Long id;

    /** 无人机型号 */
    @Excel(name = "无人机型号")
    private String droneModel;

    /** 无人机序列号（唯一标识） */
    @Excel(name = "无人机序列号", readConverterExp = "唯=一标识")
    private String serialNumber;

    /** 无人机制造商 */
    @Excel(name = "无人机制造商")
    private String manufacturer;

    /** 无人机购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "无人机购买日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 无人机当前状态（激活、未激活、维修中） */
    @Excel(name = "无人机当前状态", readConverterExp = "激=活、未激活、维修中")
    private String status;

    /** 无人机电池容量（单位：mAh） */
    @Excel(name = "无人机电池容量", readConverterExp = "单=位：mAh")
    private Double batteryCapacity;

    /** 无人机最大飞行时间（单位：分钟） */
    @Excel(name = "无人机最大飞行时间", readConverterExp = "单=位：分钟")
    private Double maxFlightTime;

    /** 无人机最大载荷（单位：千克） */
    @Excel(name = "无人机最大载荷", readConverterExp = "单=位：千克")
    private Double maxPayload;

    /** 无人机记录创建时间 */
    private Date createdTime;

    /** 无人机记录更新时间 */
    private Date updatedTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDroneModel(String droneModel) 
    {
        this.droneModel = droneModel;
    }

    public String getDroneModel() 
    {
        return droneModel;
    }
    public void setSerialNumber(String serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() 
    {
        return serialNumber;
    }
    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }
    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setBatteryCapacity(Double batteryCapacity) 
    {
        this.batteryCapacity = batteryCapacity;
    }

    public Double getBatteryCapacity() 
    {
        return batteryCapacity;
    }
    public void setMaxFlightTime(Double maxFlightTime) 
    {
        this.maxFlightTime = maxFlightTime;
    }

    public Double getMaxFlightTime() 
    {
        return maxFlightTime;
    }
    public void setMaxPayload(Double maxPayload) 
    {
        this.maxPayload = maxPayload;
    }

    public Double getMaxPayload() 
    {
        return maxPayload;
    }
    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }
    public void setUpdatedTime(Date updatedTime) 
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() 
    {
        return updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("droneModel", getDroneModel())
            .append("serialNumber", getSerialNumber())
            .append("manufacturer", getManufacturer())
            .append("purchaseDate", getPurchaseDate())
            .append("status", getStatus())
            .append("batteryCapacity", getBatteryCapacity())
            .append("maxFlightTime", getMaxFlightTime())
            .append("maxPayload", getMaxPayload())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
