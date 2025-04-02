package com.farm.drones.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.drones.mapper.PilotsMapper;
import com.farm.drones.domain.Pilots;
import com.farm.drones.service.IPilotsService;

/**
 * 飞手信息Service业务层处理
 * 
 * @author beson
 * @date 2025-02-28
 */
@Service
public class PilotsServiceImpl implements IPilotsService 
{
    @Autowired
    private PilotsMapper pilotsMapper;

    /**
     * 查询飞手信息
     * 
     * @param id 飞手信息主键
     * @return 飞手信息
     */
    @Override
    public Pilots selectPilotsById(Long id)
    {
        return pilotsMapper.selectPilotsById(id);
    }

    /**
     * 查询飞手信息列表
     * 
     * @param pilots 飞手信息
     * @return 飞手信息
     */
    @Override
    public List<Pilots> selectPilotsList(Pilots pilots)
    {
        return pilotsMapper.selectPilotsList(pilots);
    }

    /**
     * 新增飞手信息
     * 
     * @param pilots 飞手信息
     * @return 结果
     */
    @Override
    public int insertPilots(Pilots pilots)
    {
        return pilotsMapper.insertPilots(pilots);
    }

    /**
     * 修改飞手信息
     * 
     * @param pilots 飞手信息
     * @return 结果
     */
    @Override
    public int updatePilots(Pilots pilots)
    {
        return pilotsMapper.updatePilots(pilots);
    }

    /**
     * 批量删除飞手信息
     * 
     * @param ids 需要删除的飞手信息主键
     * @return 结果
     */
    @Override
    public int deletePilotsByIds(Long[] ids)
    {
        return pilotsMapper.deletePilotsByIds(ids);
    }

    /**
     * 删除飞手信息信息
     * 
     * @param id 飞手信息主键
     * @return 结果
     */
    @Override
    public int deletePilotsById(Long id)
    {
        return pilotsMapper.deletePilotsById(id);
    }
}
