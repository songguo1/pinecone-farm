package com.farm.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;
import org.locationtech.jts.geom.Geometry;

/**
 * 农田信息对象 agri_farmland
 * 
 * @author beson
 * @date 2025-02-25
 */
public class Farmland extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 农田唯一标识 */
    private Long landId;

    /** 农田名称 */
    @Excel(name = "农田名称")
    private String landName;

    /** 土壤类型 */
    @Excel(name = "土壤类型")
    private String soilType;

    /** 种植作物种类 */
    @Excel(name = "种植作物种类")
    private String cropsType;

    /** 灌溉方式 */
    @Excel(name = "灌溉方式")
    private String irrigationType;

    /** 状态 */
    private String status;

    /** 地理数据 */
    private String geom;

    public void setLandId(Long landId) 
    {
        this.landId = landId;
    }

    public Long getLandId() 
    {
        return landId;
    }
    public void setLandName(String landName) 
    {
        this.landName = landName;
    }

    public String getLandName() 
    {
        return landName;
    }
    public void setSoilType(String soilType) 
    {
        this.soilType = soilType;
    }

    public String getSoilType() 
    {
        return soilType;
    }
    public void setCropsType(String cropsType) 
    {
        this.cropsType = cropsType;
    }

    public String getCropsType() 
    {
        return cropsType;
    }
    public void setIrrigationType(String irrigationType) 
    {
        this.irrigationType = irrigationType;
    }

    public String getIrrigationType() 
    {
        return irrigationType;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setGeom(String geom)
    {
        this.geom = geom;
    }

    public String getGeom()
    {
        return geom;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("landId", getLandId())
            .append("landName", getLandName())
            .append("soilType", getSoilType())
            .append("cropsType", getCropsType())
            .append("irrigationType", getIrrigationType())
            .append("status", getStatus())
            .append("geom", getGeom())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
