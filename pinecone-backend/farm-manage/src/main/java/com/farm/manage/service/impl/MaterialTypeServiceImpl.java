package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.MaterialTypeMapper;
import com.farm.manage.domain.MaterialType;
import com.farm.manage.service.IMaterialTypeService;

/**
 * 农资类别Service业务层处理
 * 
 * @author beson
 * @date 2025-04-14
 */
@Service
public class MaterialTypeServiceImpl implements IMaterialTypeService 
{
    @Autowired
    private MaterialTypeMapper materialTypeMapper;

    /**
     * 查询农资类别
     * 
     * @param materialTypeId 农资类别主键
     * @return 农资类别
     */
    @Override
    public MaterialType selectMaterialTypeByMaterialTypeId(Long materialTypeId)
    {
        return materialTypeMapper.selectMaterialTypeByMaterialTypeId(materialTypeId);
    }

    /**
     * 查询农资类别列表
     * 
     * @param materialType 农资类别
     * @return 农资类别
     */
    @Override
    public List<MaterialType> selectMaterialTypeList(MaterialType materialType)
    {
        return materialTypeMapper.selectMaterialTypeList(materialType);
    }

    /**
     * 新增农资类别
     * 
     * @param materialType 农资类别
     * @return 结果
     */
    @Override
    public int insertMaterialType(MaterialType materialType)
    {
        materialType.setCreateTime(DateUtils.getNowDate());
        return materialTypeMapper.insertMaterialType(materialType);
    }

    /**
     * 修改农资类别
     * 
     * @param materialType 农资类别
     * @return 结果
     */
    @Override
    public int updateMaterialType(MaterialType materialType)
    {
        materialType.setUpdateTime(DateUtils.getNowDate());
        return materialTypeMapper.updateMaterialType(materialType);
    }

    /**
     * 批量删除农资类别
     * 
     * @param materialTypeIds 需要删除的农资类别主键
     * @return 结果
     */
    @Override
    public int deleteMaterialTypeByMaterialTypeIds(Long[] materialTypeIds)
    {
        return materialTypeMapper.deleteMaterialTypeByMaterialTypeIds(materialTypeIds);
    }

    /**
     * 删除农资类别信息
     * 
     * @param materialTypeId 农资类别主键
     * @return 结果
     */
    @Override
    public int deleteMaterialTypeByMaterialTypeId(Long materialTypeId)
    {
        return materialTypeMapper.deleteMaterialTypeByMaterialTypeId(materialTypeId);
    }
}
