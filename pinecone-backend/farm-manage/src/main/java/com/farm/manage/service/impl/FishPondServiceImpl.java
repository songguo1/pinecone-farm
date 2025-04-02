package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.FishPondMapper;
import com.farm.manage.domain.FishPond;
import com.farm.manage.service.IFishPondService;

/**
 * 鱼塘信息Service业务层处理
 * 
 * @author beson
 * @date 2025-02-27
 */
@Service
public class FishPondServiceImpl implements IFishPondService 
{
    @Autowired
    private FishPondMapper fishPondMapper;

    /**
     * 查询鱼塘信息
     * 
     * @param pondId 鱼塘信息主键
     * @return 鱼塘信息
     */
    @Override
    public FishPond selectFishPondByPondId(Long pondId)
    {
        return fishPondMapper.selectFishPondByPondId(pondId);
    }

    /**
     * 查询鱼塘信息列表
     * 
     * @param fishPond 鱼塘信息
     * @return 鱼塘信息
     */
    @Override
    public List<FishPond> selectFishPondList(FishPond fishPond)
    {
        return fishPondMapper.selectFishPondList(fishPond);
    }

    @Override
    public List<FishPond> getAllFishPond() {
        return fishPondMapper.getAllFishPond();
    }

    /**
     * 新增鱼塘信息
     * 
     * @param fishPond 鱼塘信息
     * @return 结果
     */
    @Override
    public int insertFishPond(FishPond fishPond)
    {
        fishPond.setCreateTime(DateUtils.getNowDate());
        return fishPondMapper.insertFishPond(fishPond);
    }

    /**
     * 修改鱼塘信息
     * 
     * @param fishPond 鱼塘信息
     * @return 结果
     */
    @Override
    public int updateFishPond(FishPond fishPond)
    {
        fishPond.setUpdateTime(DateUtils.getNowDate());
        return fishPondMapper.updateFishPond(fishPond);
    }

    /**
     * 批量删除鱼塘信息
     * 
     * @param pondIds 需要删除的鱼塘信息主键
     * @return 结果
     */
    @Override
    public int deleteFishPondByPondIds(Long[] pondIds)
    {
        return fishPondMapper.deleteFishPondByPondIds(pondIds);
    }

    /**
     * 删除鱼塘信息信息
     * 
     * @param pondId 鱼塘信息主键
     * @return 结果
     */
    @Override
    public int deleteFishPondByPondId(Long pondId)
    {
        return fishPondMapper.deleteFishPondByPondId(pondId);
    }
}
