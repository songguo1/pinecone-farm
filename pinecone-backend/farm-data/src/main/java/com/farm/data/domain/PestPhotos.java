package com.farm.data.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 病虫害照片对象 agri_pest_photos
 * 
 * @author beson
 * @date 2025-04-14
 */
public class PestPhotos extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long photoId;

    /** 病虫害照片 */
    @Excel(name = "病虫害照片")
    private String photoPath;

    /** 照片拍摄时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "照片拍摄时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date captureTime;

    /** 病虫害识别 */
    @Excel(name = "病虫害识别")
    private Long status;

    public void setPhotoId(Long photoId) 
    {
        this.photoId = photoId;
    }

    public Long getPhotoId() 
    {
        return photoId;
    }

    public void setPhotoPath(String photoPath) 
    {
        this.photoPath = photoPath;
    }

    public String getPhotoPath() 
    {
        return photoPath;
    }

    public void setCaptureTime(Date captureTime) 
    {
        this.captureTime = captureTime;
    }

    public Date getCaptureTime() 
    {
        return captureTime;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("photoId", getPhotoId())
            .append("photoPath", getPhotoPath())
            .append("captureTime", getCaptureTime())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
