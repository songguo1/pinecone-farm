package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.GreenhouseMapper;
import com.farm.manage.domain.Greenhouse;
import com.farm.manage.service.IGreenhouseService;

/**
 * 大棚信息Service业务层处理
 * 
 * @author beson
 * @date 2025-04-18
 */
@Service
public class GreenhouseServiceImpl implements IGreenhouseService 
{
    @Autowired
    private GreenhouseMapper greenhouseMapper;

    /**
     * 查询大棚信息
     * 
     * @param greenhouseId 大棚信息主键
     * @return 大棚信息
     */
    @Override
    public Greenhouse selectGreenhouseByGreenhouseId(Long greenhouseId)
    {
        return greenhouseMapper.selectGreenhouseByGreenhouseId(greenhouseId);
    }

    /**
     * 查询大棚信息列表
     * 
     * @param greenhouse 大棚信息
     * @return 大棚信息
     */
    @Override
    public List<Greenhouse> selectGreenhouseList(Greenhouse greenhouse)
    {
        return greenhouseMapper.selectGreenhouseList(greenhouse);
    }

    /**
     * 新增大棚信息
     * 
     * @param greenhouse 大棚信息
     * @return 结果
     */
    @Override
    public int insertGreenhouse(Greenhouse greenhouse)
    {
        greenhouse.setCreateTime(DateUtils.getNowDate());
        return greenhouseMapper.insertGreenhouse(greenhouse);
    }

    /**
     * 修改大棚信息
     * 
     * @param greenhouse 大棚信息
     * @return 结果
     */
    @Override
    public int updateGreenhouse(Greenhouse greenhouse)
    {
        greenhouse.setUpdateTime(DateUtils.getNowDate());
        return greenhouseMapper.updateGreenhouse(greenhouse);
    }

    /**
     * 批量删除大棚信息
     * 
     * @param greenhouseIds 需要删除的大棚信息主键
     * @return 结果
     */
    @Override
    public int deleteGreenhouseByGreenhouseIds(Long[] greenhouseIds)
    {
        return greenhouseMapper.deleteGreenhouseByGreenhouseIds(greenhouseIds);
    }

    /**
     * 删除大棚信息信息
     * 
     * @param greenhouseId 大棚信息主键
     * @return 结果
     */
    @Override
    public int deleteGreenhouseByGreenhouseId(Long greenhouseId)
    {
        return greenhouseMapper.deleteGreenhouseByGreenhouseId(greenhouseId);
    }
}
