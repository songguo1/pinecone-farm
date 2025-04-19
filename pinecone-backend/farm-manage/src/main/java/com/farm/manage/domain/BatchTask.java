package com.farm.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 批次任务对象 agri_batch_task
 * 
 * @author beson
 * @date 2025-04-18
 */
public class BatchTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long taskId;

    /** 批次ID */
    private Long batchId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 计划开始日期 */
    @Excel(name = "计划开始日期")
    private String planStart;

    /** 计划结束日期 */
    @Excel(name = "计划结束日期")
    private String planFinish;

    /** 实际开始日期 */
    @Excel(name = "实际开始日期")
    private String actualStart;

    /** 实际结束日期 */
    @Excel(name = "实际结束日期")
    private String actualFinish;

    /** 任务详情 */
    @Excel(name = "任务详情")
    private String taskDetail;

    /** 图片资料 */
    private String taskImages;

    /** 视频资料 */
    private String taskVideos;

    /** 0未分配 1已分配 2进行中 3已完成 */
    private Long status;

    /** 排序 */
    private Long orderNum;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setTaskId(Long taskId) 
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }

    public void setBatchId(Long batchId) 
    {
        this.batchId = batchId;
    }

    public Long getBatchId() 
    {
        return batchId;
    }

    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }

    public void setPlanStart(String planStart) 
    {
        this.planStart = planStart;
    }

    public String getPlanStart() 
    {
        return planStart;
    }

    public void setPlanFinish(String planFinish) 
    {
        this.planFinish = planFinish;
    }

    public String getPlanFinish() 
    {
        return planFinish;
    }

    public void setActualStart(String actualStart) 
    {
        this.actualStart = actualStart;
    }

    public String getActualStart() 
    {
        return actualStart;
    }

    public void setActualFinish(String actualFinish) 
    {
        this.actualFinish = actualFinish;
    }

    public String getActualFinish() 
    {
        return actualFinish;
    }

    public void setTaskDetail(String taskDetail) 
    {
        this.taskDetail = taskDetail;
    }

    public String getTaskDetail() 
    {
        return taskDetail;
    }

    public void setTaskImages(String taskImages) 
    {
        this.taskImages = taskImages;
    }

    public String getTaskImages() 
    {
        return taskImages;
    }

    public void setTaskVideos(String taskVideos) 
    {
        this.taskVideos = taskVideos;
    }

    public String getTaskVideos() 
    {
        return taskVideos;
    }

    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public void setOrderNum(Long orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() 
    {
        return orderNum;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskId", getTaskId())
            .append("batchId", getBatchId())
            .append("taskName", getTaskName())
            .append("planStart", getPlanStart())
            .append("planFinish", getPlanFinish())
            .append("actualStart", getActualStart())
            .append("actualFinish", getActualFinish())
            .append("taskDetail", getTaskDetail())
            .append("taskImages", getTaskImages())
            .append("taskVideos", getTaskVideos())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("orderNum", getOrderNum())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
