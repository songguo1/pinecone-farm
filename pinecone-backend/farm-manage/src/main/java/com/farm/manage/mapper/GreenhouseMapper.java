package com.farm.manage.mapper;

import java.util.List;
import com.farm.manage.domain.Greenhouse;

/**
 * 大棚信息Mapper接口
 * 
 * @author beson
 * @date 2025-02-27
 */
public interface GreenhouseMapper 
{
    /**
     * 查询大棚信息
     * 
     * @param greenhouseId 大棚信息主键
     * @return 大棚信息
     */
    public Greenhouse selectGreenhouseByGreenhouseId(Long greenhouseId);

    /**
     * 查询大棚信息列表
     * 
     * @param greenhouse 大棚信息
     * @return 大棚信息集合
     */
    public List<Greenhouse> selectGreenhouseList(Greenhouse greenhouse);

    public List<Greenhouse> getAllGreenhouse();
    /**
     * 新增大棚信息
     * 
     * @param greenhouse 大棚信息
     * @return 结果
     */
    public int insertGreenhouse(Greenhouse greenhouse);

    /**
     * 修改大棚信息
     * 
     * @param greenhouse 大棚信息
     * @return 结果
     */
    public int updateGreenhouse(Greenhouse greenhouse);

    /**
     * 删除大棚信息
     * 
     * @param greenhouseId 大棚信息主键
     * @return 结果
     */
    public int deleteGreenhouseByGreenhouseId(Long greenhouseId);

    /**
     * 批量删除大棚信息
     * 
     * @param greenhouseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGreenhouseByGreenhouseIds(Long[] greenhouseIds);
}
