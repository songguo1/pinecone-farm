package com.farm.drones.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 无人机任务对象 agri_drones_task
 * 
 * @author beson
 * @date 2025-03-01
 */
public class DronesTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private String taskType;

    /** 任务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date taskDate;

    /** 关联无人机表的ID */
    @Excel(name = "关联无人机表的ID")
    private Long droneId;

    /** 关联飞手表的ID */
    @Excel(name = "关联飞手表的ID")
    private Long pilotId;

    /** 飞行高度（单位：米） */
    @Excel(name = "飞行高度", readConverterExp = "单=位：米")
    private Double dronesHeight;

    /** 飞行路径 */
    @Excel(name = "飞行路径")
    private String dronesPath;

    /** 任务记录创建时间 */
    private Date createdTime;

    /** 任务记录更新时间 */
    private Date updatedTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setTaskType(String taskType) 
    {
        this.taskType = taskType;
    }

    public String getTaskType() 
    {
        return taskType;
    }
    public void setTaskDate(Date taskDate) 
    {
        this.taskDate = taskDate;
    }

    public Date getTaskDate() 
    {
        return taskDate;
    }
    public void setDroneId(Long droneId) 
    {
        this.droneId = droneId;
    }

    public Long getDroneId() 
    {
        return droneId;
    }
    public void setPilotId(Long pilotId) 
    {
        this.pilotId = pilotId;
    }

    public Long getPilotId() 
    {
        return pilotId;
    }
    public void setDronesHeight(Double dronesHeight) 
    {
        this.dronesHeight = dronesHeight;
    }

    public Double getDronesHeight() 
    {
        return dronesHeight;
    }
    public void setDronesPath(String dronesPath) 
    {
        this.dronesPath = dronesPath;
    }

    public String getDronesPath() 
    {
        return dronesPath;
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
            .append("taskName", getTaskName())
            .append("taskType", getTaskType())
            .append("taskDate", getTaskDate())
            .append("droneId", getDroneId())
            .append("pilotId", getPilotId())
            .append("dronesHeight", getDronesHeight())
            .append("dronesPath", getDronesPath())
            .append("createdTime", getCreatedTime())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
