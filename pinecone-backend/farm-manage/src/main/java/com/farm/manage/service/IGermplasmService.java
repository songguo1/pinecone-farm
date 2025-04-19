package com.farm.manange.service;

import java.util.List;
import com.farm.manange.domain.Germplasm;

/**
 * 种质系统Service接口
 * 
 * @author beson
 * @date 2025-04-14
 */
public interface IGermplasmService 
{
    /**
     * 查询种质系统
     * 
     * @param germplasmId 种质系统主键
     * @return 种质系统
     */
    public Germplasm selectGermplasmByGermplasmId(Long germplasmId);

    /**
     * 查询种质系统列表
     * 
     * @param germplasm 种质系统
     * @return 种质系统集合
     */
    public List<Germplasm> selectGermplasmList(Germplasm germplasm);

    /**
     * 新增种质系统
     * 
     * @param germplasm 种质系统
     * @return 结果
     */
    public int insertGermplasm(Germplasm germplasm);

    /**
     * 修改种质系统
     * 
     * @param germplasm 种质系统
     * @return 结果
     */
    public int updateGermplasm(Germplasm germplasm);

    /**
     * 批量删除种质系统
     * 
     * @param germplasmIds 需要删除的种质系统主键集合
     * @return 结果
     */
    public int deleteGermplasmByGermplasmIds(Long[] germplasmIds);

    /**
     * 删除种质系统信息
     * 
     * @param germplasmId 种质系统主键
     * @return 结果
     */
    public int deleteGermplasmByGermplasmId(Long germplasmId);
}
