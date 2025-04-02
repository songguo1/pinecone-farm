package com.farm.drones.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.drones.mapper.DronesMapper;
import com.farm.drones.domain.Drones;
import com.farm.drones.service.IDronesService;

/**
 * 无人机信息，记录所有无人机的详细信息Service业务层处理
 * 
 * @author beson
 * @date 2025-02-28
 */
@Service
public class DronesServiceImpl implements IDronesService 
{
    @Autowired
    private DronesMapper dronesMapper;

    /**
     * 查询无人机信息，记录所有无人机的详细信息
     * 
     * @param id 无人机信息，记录所有无人机的详细信息主键
     * @return 无人机信息，记录所有无人机的详细信息
     */
    @Override
    public Drones selectDronesById(Long id)
    {
        return dronesMapper.selectDronesById(id);
    }

    /**
     * 查询无人机信息，记录所有无人机的详细信息列表
     * 
     * @param drones 无人机信息，记录所有无人机的详细信息
     * @return 无人机信息，记录所有无人机的详细信息
     */
    @Override
    public List<Drones> selectDronesList(Drones drones)
    {
        return dronesMapper.selectDronesList(drones);
    }

    /**
     * 新增无人机信息，记录所有无人机的详细信息
     * 
     * @param drones 无人机信息，记录所有无人机的详细信息
     * @return 结果
     */
    @Override
    public int insertDrones(Drones drones)
    {
        return dronesMapper.insertDrones(drones);
    }

    /**
     * 修改无人机信息，记录所有无人机的详细信息
     * 
     * @param drones 无人机信息，记录所有无人机的详细信息
     * @return 结果
     */
    @Override
    public int updateDrones(Drones drones)
    {
        return dronesMapper.updateDrones(drones);
    }

    /**
     * 批量删除无人机信息，记录所有无人机的详细信息
     * 
     * @param ids 需要删除的无人机信息，记录所有无人机的详细信息主键
     * @return 结果
     */
    @Override
    public int deleteDronesByIds(Long[] ids)
    {
        return dronesMapper.deleteDronesByIds(ids);
    }

    /**
     * 删除无人机信息，记录所有无人机的详细信息信息
     * 
     * @param id 无人机信息，记录所有无人机的详细信息主键
     * @return 结果
     */
    @Override
    public int deleteDronesById(Long id)
    {
        return dronesMapper.deleteDronesById(id);
    }
}
