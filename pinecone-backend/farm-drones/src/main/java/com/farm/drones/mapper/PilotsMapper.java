package com.farm.drones.mapper;

import java.util.List;
import com.farm.drones.domain.Pilots;

/**
 * 飞手信息Mapper接口
 * 
 * @author beson
 * @date 2025-02-28
 */
public interface PilotsMapper 
{
    /**
     * 查询飞手信息
     * 
     * @param id 飞手信息主键
     * @return 飞手信息
     */
    public Pilots selectPilotsById(Long id);

    /**
     * 查询飞手信息列表
     * 
     * @param pilots 飞手信息
     * @return 飞手信息集合
     */
    public List<Pilots> selectPilotsList(Pilots pilots);

    /**
     * 新增飞手信息
     * 
     * @param pilots 飞手信息
     * @return 结果
     */
    public int insertPilots(Pilots pilots);

    /**
     * 修改飞手信息
     * 
     * @param pilots 飞手信息
     * @return 结果
     */
    public int updatePilots(Pilots pilots);

    /**
     * 删除飞手信息
     * 
     * @param id 飞手信息主键
     * @return 结果
     */
    public int deletePilotsById(Long id);

    /**
     * 批量删除飞手信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePilotsByIds(Long[] ids);
}
