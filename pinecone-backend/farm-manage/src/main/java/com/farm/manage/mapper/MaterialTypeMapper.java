package com.farm.manage.mapper;

import java.util.List;
import com.farm.manage.domain.MaterialType;

/**
 * 农资类别Mapper接口
 * 
 * @author beson
 * @date 2025-04-14
 */
public interface MaterialTypeMapper 
{
    /**
     * 查询农资类别
     * 
     * @param materialTypeId 农资类别主键
     * @return 农资类别
     */
    public MaterialType selectMaterialTypeByMaterialTypeId(Long materialTypeId);

    /**
     * 查询农资类别列表
     * 
     * @param materialType 农资类别
     * @return 农资类别集合
     */
    public List<MaterialType> selectMaterialTypeList(MaterialType materialType);

    /**
     * 新增农资类别
     * 
     * @param materialType 农资类别
     * @return 结果
     */
    public int insertMaterialType(MaterialType materialType);

    /**
     * 修改农资类别
     * 
     * @param materialType 农资类别
     * @return 结果
     */
    public int updateMaterialType(MaterialType materialType);

    /**
     * 删除农资类别
     * 
     * @param materialTypeId 农资类别主键
     * @return 结果
     */
    public int deleteMaterialTypeByMaterialTypeId(Long materialTypeId);

    /**
     * 批量删除农资类别
     * 
     * @param materialTypeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialTypeByMaterialTypeIds(Long[] materialTypeIds);
}
