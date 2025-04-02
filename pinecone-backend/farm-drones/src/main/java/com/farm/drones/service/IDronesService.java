package com.farm.drones.service;

import java.util.List;
import com.farm.drones.domain.Drones;

/**
 * 无人机信息，记录所有无人机的详细信息Service接口
 * 
 * @author beson
 * @date 2025-02-28
 */
public interface IDronesService 
{
    /**
     * 查询无人机信息，记录所有无人机的详细信息
     * 
     * @param id 无人机信息，记录所有无人机的详细信息主键
     * @return 无人机信息，记录所有无人机的详细信息
     */
    public Drones selectDronesById(Long id);

    /**
     * 查询无人机信息，记录所有无人机的详细信息列表
     * 
     * @param drones 无人机信息，记录所有无人机的详细信息
     * @return 无人机信息，记录所有无人机的详细信息集合
     */
    public List<Drones> selectDronesList(Drones drones);

    /**
     * 新增无人机信息，记录所有无人机的详细信息
     * 
     * @param drones 无人机信息，记录所有无人机的详细信息
     * @return 结果
     */
    public int insertDrones(Drones drones);

    /**
     * 修改无人机信息，记录所有无人机的详细信息
     * 
     * @param drones 无人机信息，记录所有无人机的详细信息
     * @return 结果
     */
    public int updateDrones(Drones drones);

    /**
     * 批量删除无人机信息，记录所有无人机的详细信息
     * 
     * @param ids 需要删除的无人机信息，记录所有无人机的详细信息主键集合
     * @return 结果
     */
    public int deleteDronesByIds(Long[] ids);

    /**
     * 删除无人机信息，记录所有无人机的详细信息信息
     * 
     * @param id 无人机信息，记录所有无人机的详细信息主键
     * @return 结果
     */
    public int deleteDronesById(Long id);
}
