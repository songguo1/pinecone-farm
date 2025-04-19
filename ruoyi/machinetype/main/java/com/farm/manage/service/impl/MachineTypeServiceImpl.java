package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.MachineTypeMapper;
import com.farm.manage.domain.MachineType;
import com.farm.manage.service.IMachineTypeService;

/**
 * 机械类别Service业务层处理
 * 
 * @author beson
 * @date 2025-04-15
 */
@Service
public class MachineTypeServiceImpl implements IMachineTypeService 
{
    @Autowired
    private MachineTypeMapper machineTypeMapper;

    /**
     * 查询机械类别
     * 
     * @param machineTypeId 机械类别主键
     * @return 机械类别
     */
    @Override
    public MachineType selectMachineTypeByMachineTypeId(Long machineTypeId)
    {
        return machineTypeMapper.selectMachineTypeByMachineTypeId(machineTypeId);
    }

    /**
     * 查询机械类别列表
     * 
     * @param machineType 机械类别
     * @return 机械类别
     */
    @Override
    public List<MachineType> selectMachineTypeList(MachineType machineType)
    {
        return machineTypeMapper.selectMachineTypeList(machineType);
    }

    /**
     * 新增机械类别
     * 
     * @param machineType 机械类别
     * @return 结果
     */
    @Override
    public int insertMachineType(MachineType machineType)
    {
        machineType.setCreateTime(DateUtils.getNowDate());
        return machineTypeMapper.insertMachineType(machineType);
    }

    /**
     * 修改机械类别
     * 
     * @param machineType 机械类别
     * @return 结果
     */
    @Override
    public int updateMachineType(MachineType machineType)
    {
        machineType.setUpdateTime(DateUtils.getNowDate());
        return machineTypeMapper.updateMachineType(machineType);
    }

    /**
     * 批量删除机械类别
     * 
     * @param machineTypeIds 需要删除的机械类别主键
     * @return 结果
     */
    @Override
    public int deleteMachineTypeByMachineTypeIds(Long[] machineTypeIds)
    {
        return machineTypeMapper.deleteMachineTypeByMachineTypeIds(machineTypeIds);
    }

    /**
     * 删除机械类别信息
     * 
     * @param machineTypeId 机械类别主键
     * @return 结果
     */
    @Override
    public int deleteMachineTypeByMachineTypeId(Long machineTypeId)
    {
        return machineTypeMapper.deleteMachineTypeByMachineTypeId(machineTypeId);
    }
}
