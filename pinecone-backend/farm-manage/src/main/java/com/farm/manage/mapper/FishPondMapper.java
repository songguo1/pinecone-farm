package com.farm.manage.mapper;

import java.util.List;
import com.farm.manage.domain.FishPond;

/**
 * 鱼塘信息Mapper接口
 * 
 * @author beson
 * @date 2025-02-27
 */
public interface FishPondMapper 
{
    /**
     * 查询鱼塘信息
     * 
     * @param pondId 鱼塘信息主键
     * @return 鱼塘信息
     */
    public FishPond selectFishPondByPondId(Long pondId);

    /**
     * 查询鱼塘信息列表
     * 
     * @param fishPond 鱼塘信息
     * @return 鱼塘信息集合
     */
    public List<FishPond> selectFishPondList(FishPond fishPond);

    public List<FishPond> getAllFishPond();
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
     * 删除鱼塘信息
     * 
     * @param pondId 鱼塘信息主键
     * @return 结果
     */
    public int deleteFishPondByPondId(Long pondId);

    /**
     * 批量删除鱼塘信息
     * 
     * @param pondIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteFishPondByPondIds(Long[] pondIds);
}
