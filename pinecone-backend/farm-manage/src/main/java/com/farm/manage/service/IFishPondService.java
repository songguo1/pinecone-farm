package com.farm.manage.service;

import java.util.List;
import com.farm.manage.domain.FishPond;

/**
 * 鱼塘信息Service接口
 * 
 * @author beson
 * @date 2025-02-27
 */
public interface IFishPondService 
{
    /**
     * 查询鱼塘信息
     * 
     * @param pondId 鱼塘信息主键
     * @return 鱼塘信息
     */
    public FishPond selectFishPondByPondId(Long pondId);

    public List<FishPond> getAllFishPond();

    /**
     * 查询鱼塘信息列表
     * 
     * @param fishPond 鱼塘信息
     * @return 鱼塘信息集合
     */
    public List<FishPond> selectFishPondList(FishPond fishPond);

    /**
     * 新增鱼塘信息
     * 
     * @param fishPond 鱼塘信息
     * @return 结果
     */
    public int insertFishPond(FishPond fishPond);

    /**
     * 修改鱼塘信息
     * 
     * @param fishPond 鱼塘信息
     * @return 结果
     */
    public int updateFishPond(FishPond fishPond);

    /**
     * 批量删除鱼塘信息
     * 
     * @param pondIds 需要删除的鱼塘信息主键集合
     * @return 结果
     */
    public int deleteFishPondByPondIds(Long[] pondIds);

    /**
     * 删除鱼塘信息信息
     * 
     * @param pondId 鱼塘信息主键
     * @return 结果
     */
    public int deleteFishPondByPondId(Long pondId);
}
