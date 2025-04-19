package com.farm.manage.service;

import java.util.List;
import com.farm.manage.domain.CropBatch;

/**
 * 作物批次Service接口
 * 
 * @author beson
 * @date 2025-04-18
 */
public interface ICropBatchService 
{
    /**
     * 查询作物批次
     * 
     * @param batchId 作物批次主键
     * @return 作物批次
     */
    public CropBatch selectCropBatchByBatchId(Long batchId);

    /**
     * 查询作物批次列表
     * 
     * @param cropBatch 作物批次
     * @return 作物批次集合
     */
    public List<CropBatch> selectCropBatchList(CropBatch cropBatch);

    /**
     * 新增作物批次
     * 
     * @param cropBatch 作物批次
     * @return 结果
     */
    public int insertCropBatch(CropBatch cropBatch);

    /**
     * 修改作物批次
     * 
     * @param cropBatch 作物批次
     * @return 结果
     */
    public int updateCropBatch(CropBatch cropBatch);

    /**
     * 批量删除作物批次
     * 
     * @param batchIds 需要删除的作物批次主键集合
     * @return 结果
     */
    public int deleteCropBatchByBatchIds(Long[] batchIds);

    /**
     * 删除作物批次信息
     * 
     * @param batchId 作物批次主键
     * @return 结果
     */
    public int deleteCropBatchByBatchId(Long batchId);
}
