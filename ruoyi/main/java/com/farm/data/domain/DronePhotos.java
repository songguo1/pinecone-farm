package com.farm.data.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 航拍图片对象 agri_drone_photos
 * 
 * @author beson
 * @date 2025-04-17
 */
public class DronePhotos extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long photoId;

    /** 航拍图片 */
    @Excel(name = "航拍图片")
    private String photoPath;

    /** 任务调度 */
    @Excel(name = "任务调度")
    private String taskId;

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

    public void setTaskId(String taskId) 
    {
        this.taskId = taskId;
    }

    public String getTaskId() 
    {
        return taskId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("photoId", getPhotoId())
            .append("photoPath", getPhotoPath())
            .append("taskId", getTaskId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
