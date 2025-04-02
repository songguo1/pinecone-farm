package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.FarmlandMapper;
import com.farm.manage.domain.Farmland;
import com.farm.manage.service.IFarmlandService;

/**
 * 农田信息Service业务层处理
 * 
 * @author beson
 * @date 2025-02-25
 */
@Service
public class FarmlandServiceImpl implements IFarmlandService 
{
    @Autowired
    private FarmlandMapper farmlandMapper;

    /**
     * 查询农田信息
     * 
     * @param landId 农田信息主键
     * @return 农田信息
     */
    @Override
    public Farmland selectFarmlandByLandId(Long landId)
    {
        return farmlandMapper.selectFarmlandByLandId(landId);
    }

    /**
     * 查询所有农田信息
     * @return
     */
    @Override
    public List<Farmland> getAllFarmland() {
        return farmlandMapper.getAllFarmland();
    }

    /**
     * 查询农田信息列表
     * 
     * @param farmland 农田信息
     * @return 农田信息
     */
    @Override
    public List<Farmland> selectFarmlandList(Farmland farmland)
    {
        return farmlandMapper.selectFarmlandList(farmland);
    }

    /**
     * 新增农田信息
     * 
     * @param farmland 农田信息
     * @return 结果
     */
    @Override
    public int insertFarmland(Farmland farmland)
    {
        farmland.setCreateTime(DateUtils.getNowDate());
        return farmlandMapper.insertFarmland(farmland);
    }

    /**
     * 修改农田信息
     * 
     * @param farmland 农田信息
     * @return 结果
     */
    @Override
    public int updateFarmland(Farmland farmland)
    {
        farmland.setUpdateTime(DateUtils.getNowDate());
        return farmlandMapper.updateFarmland(farmland);
    }

    /**
     * 批量删除农田信息
     * 
     * @param landIds 需要删除的农田信息主键
     * @return 结果
     */
    @Override
    public int deleteFarmlandByLandIds(Long[] landIds)
    {
        return farmlandMapper.deleteFarmlandByLandIds(landIds);
    }

    /**
     * 删除农田信息信息
     * 
     * @param landId 农田信息主键
     * @return 结果
     */
    @Override
    public int deleteFarmlandByLandId(Long landId)
    {
        return farmlandMapper.deleteFarmlandByLandId(landId);
    }
}
