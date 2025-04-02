package com.farm.drones.mapper;

import java.util.List;
import com.farm.drones.domain.DronesTask;

/**
 * 无人机任务Mapper接口
 * 
 * @author beson
 * @date 2025-03-01
 */
public interface DronesTaskMapper 
{
    /**
     * 查询无人机任务
     * 
     * @param id 无人机任务主键
     * @return 无人机任务
     */
    public DronesTask selectDronesTaskById(Long id);

    /**
     * 查询无人机任务列表
     * 
     * @param dronesTask 无人机任务
     * @return 无人机任务集合
     */
    public List<DronesTask> selectDronesTaskList(DronesTask dronesTask);

    /**
     * 新增无人机任务
     * 
     * @param dronesTask 无人机任务
     * @return 结果
     */
    public int insertDronesTask(DronesTask dronesTask);

    /**
     * 修改无人机任务
     * 
     * @param dronesTask 无人机任务
     * @return 结果
     */
    public int updateDronesTask(DronesTask dronesTask);

    /**
     * 删除无人机任务
     * 
     * @param id 无人机任务主键
     * @return 结果
     */
    public int deleteDronesTaskById(Long id);

    /**
     * 批量删除无人机任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDronesTaskByIds(Long[] ids);
}
