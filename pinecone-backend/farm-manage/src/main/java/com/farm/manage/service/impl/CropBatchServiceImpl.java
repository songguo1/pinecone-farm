package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.CropBatchMapper;
import com.farm.manage.domain.CropBatch;
import com.farm.manage.service.ICropBatchService;

/**
 * 作物批次Service业务层处理
 * 
 * @author beson
 * @date 2025-04-18
 */
@Service
public class CropBatchServiceImpl implements ICropBatchService 
{
    @Autowired
    private CropBatchMapper cropBatchMapper;

    /**
     * 查询作物批次
     * 
     * @param batchId 作物批次主键
     * @return 作物批次
     */
    @Override
    public CropBatch selectCropBatchByBatchId(Long batchId)
    {
        return cropBatchMapper.selectCropBatchByBatchId(batchId);
    }

    /**
     * 查询作物批次列表
     * 
     * @param cropBatch 作物批次
     * @return 作物批次
     */
    @Override
    public List<CropBatch> selectCropBatchList(CropBatch cropBatch)
    {
        return cropBatchMapper.selectCropBatchList(cropBatch);
    }

    /**
     * 新增作物批次
     * 
     * @param cropBatch 作物批次
     * @return 结果
     */
    @Override
    public int insertCropBatch(CropBatch cropBatch)
    {
        cropBatch.setCreateTime(DateUtils.getNowDate());
        return cropBatchMapper.insertCropBatch(cropBatch);
    }

    /**
     * 修改作物批次
     * 
     * @param cropBatch 作物批次
     * @return 结果
     */
    @Override
    public int updateCropBatch(CropBatch cropBatch)
    {
        cropBatch.setUpdateTime(DateUtils.getNowDate());
        return cropBatchMapper.updateCropBatch(cropBatch);
    }

    /**
     * 批量删除作物批次
     * 
     * @param batchIds 需要删除的作物批次主键
     * @return 结果
     */
    @Override
    public int deleteCropBatchByBatchIds(Long[] batchIds)
    {
        return cropBatchMapper.deleteCropBatchByBatchIds(batchIds);
    }

    /**
     * 删除作物批次信息
     * 
     * @param batchId 作物批次主键
     * @return 结果
     */
    @Override
    public int deleteCropBatchByBatchId(Long batchId)
    {
        return cropBatchMapper.deleteCropBatchByBatchId(batchId);
    }
}
