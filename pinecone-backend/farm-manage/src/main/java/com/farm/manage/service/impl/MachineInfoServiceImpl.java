package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.MachineInfoMapper;
import com.farm.manage.domain.MachineInfo;
import com.farm.manage.service.IMachineInfoService;

/**
 * 机械信息Service业务层处理
 * 
 * @author beson
 * @date 2025-04-14
 */
@Service
public class MachineInfoServiceImpl implements IMachineInfoService 
{
    @Autowired
    private MachineInfoMapper machineInfoMapper;

    /**
     * 查询机械信息
     * 
     * @param machineId 机械信息主键
     * @return 机械信息
     */
    @Override
    public MachineInfo selectMachineInfoByMachineId(Long machineId)
    {
        return machineInfoMapper.selectMachineInfoByMachineId(machineId);
    }

    /**
     * 查询机械信息列表
     * 
     * @param machineInfo 机械信息
     * @return 机械信息
     */
    @Override
    public List<MachineInfo> selectMachineInfoList(MachineInfo machineInfo)
    {
        return machineInfoMapper.selectMachineInfoList(machineInfo);
    }

    /**
     * 新增机械信息
     * 
     * @param machineInfo 机械信息
     * @return 结果
     */
    @Override
    public int insertMachineInfo(MachineInfo machineInfo)
    {
        machineInfo.setCreateTime(DateUtils.getNowDate());
        return machineInfoMapper.insertMachineInfo(machineInfo);
    }

    /**
     * 修改机械信息
     * 
     * @param machineInfo 机械信息
     * @return 结果
     */
    @Override
    public int updateMachineInfo(MachineInfo machineInfo)
    {
        machineInfo.setUpdateTime(DateUtils.getNowDate());
        return machineInfoMapper.updateMachineInfo(machineInfo);
    }

    /**
     * 批量删除机械信息
     * 
     * @param machineIds 需要删除的机械信息主键
     * @return 结果
     */
    @Override
    public int deleteMachineInfoByMachineIds(Long[] machineIds)
    {
        return machineInfoMapper.deleteMachineInfoByMachineIds(machineIds);
    }

    /**
     * 删除机械信息信息
     * 
     * @param machineId 机械信息主键
     * @return 结果
     */
    @Override
    public int deleteMachineInfoByMachineId(Long machineId)
    {
        return machineInfoMapper.deleteMachineInfoByMachineId(machineId);
    }
}
