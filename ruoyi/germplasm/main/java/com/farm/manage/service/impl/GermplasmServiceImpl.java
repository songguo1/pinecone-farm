package com.farm.manange.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manange.mapper.GermplasmMapper;
import com.farm.manange.domain.Germplasm;
import com.farm.manange.service.IGermplasmService;

/**
 * 种质系统Service业务层处理
 * 
 * @author beson
 * @date 2025-04-14
 */
@Service
public class GermplasmServiceImpl implements IGermplasmService 
{
    @Autowired
    private GermplasmMapper germplasmMapper;

    /**
     * 查询种质系统
     * 
     * @param germplasmId 种质系统主键
     * @return 种质系统
     */
    @Override
    public Germplasm selectGermplasmByGermplasmId(Long germplasmId)
    {
        return germplasmMapper.selectGermplasmByGermplasmId(germplasmId);
    }

    /**
     * 查询种质系统列表
     * 
     * @param germplasm 种质系统
     * @return 种质系统
     */
    @Override
    public List<Germplasm> selectGermplasmList(Germplasm germplasm)
    {
        return germplasmMapper.selectGermplasmList(germplasm);
    }

    /**
     * 新增种质系统
     * 
     * @param germplasm 种质系统
     * @return 结果
     */
    @Override
    public int insertGermplasm(Germplasm germplasm)
    {
        germplasm.setCreateTime(DateUtils.getNowDate());
        return germplasmMapper.insertGermplasm(germplasm);
    }

    /**
     * 修改种质系统
     * 
     * @param germplasm 种质系统
     * @return 结果
     */
    @Override
    public int updateGermplasm(Germplasm germplasm)
    {
        germplasm.setUpdateTime(DateUtils.getNowDate());
        return germplasmMapper.updateGermplasm(germplasm);
    }

    /**
     * 批量删除种质系统
     * 
     * @param germplasmIds 需要删除的种质系统主键
     * @return 结果
     */
    @Override
    public int deleteGermplasmByGermplasmIds(Long[] germplasmIds)
    {
        return germplasmMapper.deleteGermplasmByGermplasmIds(germplasmIds);
    }

    /**
     * 删除种质系统信息
     * 
     * @param germplasmId 种质系统主键
     * @return 结果
     */
    @Override
    public int deleteGermplasmByGermplasmId(Long germplasmId)
    {
        return germplasmMapper.deleteGermplasmByGermplasmId(germplasmId);
    }
}
