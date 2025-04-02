package com.farm.drones.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.drones.mapper.DronesTaskMapper;
import com.farm.drones.domain.DronesTask;
import com.farm.drones.service.IDronesTaskService;

/**
 * 无人机任务Service业务层处理
 * 
 * @author beson
 * @date 2025-03-01
 */
@Service
public class DronesTaskServiceImpl implements IDronesTaskService 
{
    @Autowired
    private DronesTaskMapper dronesTaskMapper;

    /**
     * 查询无人机任务
     * 
     * @param id 无人机任务主键
     * @return 无人机任务
     */
    @Override
    public DronesTask selectDronesTaskById(Long id)
    {
        return dronesTaskMapper.selectDronesTaskById(id);
    }

    /**
     * 查询无人机任务列表
     * 
     * @param dronesTask 无人机任务
     * @return 无人机任务
     */
    @Override
    public List<DronesTask> selectDronesTaskList(DronesTask dronesTask)
    {
        return dronesTaskMapper.selectDronesTaskList(dronesTask);
    }

    /**
     * 新增无人机任务
     * 
     * @param dronesTask 无人机任务
     * @return 结果
     */
    @Override
    public int insertDronesTask(DronesTask dronesTask)
    {
        return dronesTaskMapper.insertDronesTask(dronesTask);
    }

    /**
     * 修改无人机任务
     * 
     * @param dronesTask 无人机任务
     * @return 结果
     */
    @Override
    public int updateDronesTask(DronesTask dronesTask)
    {
        return dronesTaskMapper.updateDronesTask(dronesTask);
    }

    /**
     * 批量删除无人机任务
     * 
     * @param ids 需要删除的无人机任务主键
     * @return 结果
     */
    @Override
    public int deleteDronesTaskByIds(Long[] ids)
    {
        return dronesTaskMapper.deleteDronesTaskByIds(ids);
    }

    /**
     * 删除无人机任务信息
     * 
     * @param id 无人机任务主键
     * @return 结果
     */
    @Override
    public int deleteDronesTaskById(Long id)
    {
        return dronesTaskMapper.deleteDronesTaskById(id);
    }
}
