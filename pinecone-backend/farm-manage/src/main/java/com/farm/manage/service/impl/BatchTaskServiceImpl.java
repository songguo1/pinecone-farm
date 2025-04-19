package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.BatchTaskMapper;
import com.farm.manage.domain.BatchTask;
import com.farm.manage.service.IBatchTaskService;

/**
 * 批次任务Service业务层处理
 * 
 * @author beson
 * @date 2025-04-18
 */
@Service
public class BatchTaskServiceImpl implements IBatchTaskService 
{
    @Autowired
    private BatchTaskMapper batchTaskMapper;

    /**
     * 查询批次任务
     * 
     * @param taskId 批次任务主键
     * @return 批次任务
     */
    @Override
    public BatchTask selectBatchTaskByTaskId(Long taskId)
    {
        return batchTaskMapper.selectBatchTaskByTaskId(taskId);
    }

    /**
     * 查询批次任务列表
     * 
     * @param batchTask 批次任务
     * @return 批次任务
     */
    @Override
    public List<BatchTask> selectBatchTaskList(BatchTask batchTask)
    {
        return batchTaskMapper.selectBatchTaskList(batchTask);
    }

    /**
     * 新增批次任务
     * 
     * @param batchTask 批次任务
     * @return 结果
     */
    @Override
    public int insertBatchTask(BatchTask batchTask)
    {
        batchTask.setCreateTime(DateUtils.getNowDate());
        return batchTaskMapper.insertBatchTask(batchTask);
    }

    /**
     * 修改批次任务
     * 
     * @param batchTask 批次任务
     * @return 结果
     */
    @Override
    public int updateBatchTask(BatchTask batchTask)
    {
        batchTask.setUpdateTime(DateUtils.getNowDate());
        return batchTaskMapper.updateBatchTask(batchTask);
    }

    /**
     * 批量删除批次任务
     * 
     * @param taskIds 需要删除的批次任务主键
     * @return 结果
     */
    @Override
    public int deleteBatchTaskByTaskIds(Long[] taskIds)
    {
        return batchTaskMapper.deleteBatchTaskByTaskIds(taskIds);
    }

    /**
     * 删除批次任务信息
     * 
     * @param taskId 批次任务主键
     * @return 结果
     */
    @Override
    public int deleteBatchTaskByTaskId(Long taskId)
    {
        return batchTaskMapper.deleteBatchTaskByTaskId(taskId);
    }
}
