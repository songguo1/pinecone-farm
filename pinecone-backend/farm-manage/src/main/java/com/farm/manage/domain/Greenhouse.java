package com.farm.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;


/**
 * 大棚信息对象 agri_greenhouse
 * 
 * @author beson
 * @date 2025-02-27
 */
public class Greenhouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 大棚唯一标识 */
    private Long greenhouseId;

    /** 大棚名称 */
    @Excel(name = "大棚名称")
    private String greenhouseName;

    /** 大棚结构类型 */
    @Excel(name = "大棚结构类型")
    private String structureType;

    /** 是否配备加热系统 */
    @Excel(name = "是否配备加热系统")
    private Integer heatingSystem;

    /** 覆盖材料 */
    @Excel(name = "覆盖材料")
    private String coverMaterial;

    /** 地理数据 */
    private String geom;

    public void setGreenhouseId(Long greenhouseId) 
    {
        this.greenhouseId = greenhouseId;
    }

    public Long getGreenhouseId() 
    {
        return greenhouseId;
    }
    public void setGreenhouseName(String greenhouseName) 
    {
        this.greenhouseName = greenhouseName;
    }

    public String getGreenhouseName() 
    {
        return greenhouseName;
    }
    public void setStructureType(String structureType) 
    {
        this.structureType = structureType;
    }

    public String getStructureType() 
    {
        return structureType;
    }
    public void setHeatingSystem(Integer heatingSystem)
    {
        this.heatingSystem = heatingSystem;
    }

    public Integer getHeatingSystem()
    {
        return heatingSystem;
    }
    public void setCoverMaterial(String coverMaterial) 
    {
        this.coverMaterial = coverMaterial;
    }

    public String getCoverMaterial() 
    {
        return coverMaterial;
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
            .append("greenhouseId", getGreenhouseId())
            .append("greenhouseName", getGreenhouseName())
            .append("structureType", getStructureType())
            .append("heatingSystem", getHeatingSystem())
            .append("coverMaterial", getCoverMaterial())
            .append("geom", getGeom())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
