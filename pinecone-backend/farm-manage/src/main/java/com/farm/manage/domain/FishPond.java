package com.farm.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 鱼塘信息对象 agri_fish_pond
 * 
 * @author beson
 * @date 2025-02-27
 */
public class FishPond extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 鱼塘唯一标识 */
    private Long pondId;

    /** 鱼塘名称 */
    @Excel(name = "鱼塘名称")
    private String pondName;

    /** 平均水深（单位：米） */
    @Excel(name = "平均水深", readConverterExp = "单=位：米")
    private Double waterDepth;

    /** 水质等级 */
    @Excel(name = "水质等级")
    private String waterQuality;

    /** 水体溶氧量（单位：mg/L） */
    @Excel(name = "水体溶氧量", readConverterExp = "单=位：mg/L")
    private Double oxygenLevel;

    /** 饲料类型 */
    @Excel(name = "饲料类型")
    private String feedType;

    /** 地理数据 */
    private String geom;

    public void setPondId(Long pondId) 
    {
        this.pondId = pondId;
    }

    public Long getPondId() 
    {
        return pondId;
    }
    public void setPondName(String pondName) 
    {
        this.pondName = pondName;
    }

    public String getPondName() 
    {
        return pondName;
    }
    public void setWaterDepth(Double waterDepth) 
    {
        this.waterDepth = waterDepth;
    }

    public Double getWaterDepth() 
    {
        return waterDepth;
    }
    public void setWaterQuality(String waterQuality) 
    {
        this.waterQuality = waterQuality;
    }

    public String getWaterQuality() 
    {
        return waterQuality;
    }
    public void setOxygenLevel(Double oxygenLevel) 
    {
        this.oxygenLevel = oxygenLevel;
    }

    public Double getOxygenLevel() 
    {
        return oxygenLevel;
    }
    public void setFeedType(String feedType) 
    {
        this.feedType = feedType;
    }

    public String getFeedType() 
    {
        return feedType;
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
            .append("pondId", getPondId())
            .append("pondName", getPondName())
            .append("waterDepth", getWaterDepth())
            .append("waterQuality", getWaterQuality())
            .append("oxygenLevel", getOxygenLevel())
            .append("feedType", getFeedType())
            .append("geom", getGeom())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
