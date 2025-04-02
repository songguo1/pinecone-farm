package com.farm.manage.mapper;

import java.util.List;
import com.farm.manage.domain.Farmland;

/**
 * 农田信息Mapper接口
 * 
 * @author beson
 * @date 2025-02-25
 */
public interface FarmlandMapper 
{
    /**
     * 查询农田信息
     * 
     * @param landId 农田信息主键
     * @return 农田信息
     */
    public Farmland selectFarmlandByLandId(Long landId);

    /**
     * 查询所有农田信息
     *
     * @return 农田信息集合
     */
    public List<Farmland> getAllFarmland();

    /**
     * 查询农田信息列表
     * 
     * @param farmland 农田信息
     * @return 农田信息集合
     */
    public List<Farmland> selectFarmlandList(Farmland farmland);

    /**
     * 新增农田信息
     * 
     * @param farmland 农田信息
     * @return 结果
     */
    public int insertFarmland(Farmland farmland);

    /**
     * 修改农田信息
     * 
     * @param farmland 农田信息
     * @return 结果
     */
    public int updateFarmland(Farmland farmland);

    /**
     * 删除农田信息
     * 
     * @param landId 农田信息主键
     * @return 结果
     */
    public int deleteFarmlandByLandId(Long landId);

    /**
     * 批量删除农田信息
     * 
     * @param landIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFarmlandByLandIds(Long[] landIds);
}
