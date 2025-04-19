package com.farm.data.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.data.mapper.DronePhotosMapper;
import com.farm.data.domain.DronePhotos;
import com.farm.data.service.IDronePhotosService;

/**
 * 航拍图片Service业务层处理
 * 
 * @author beson
 * @date 2025-04-14
 */
@Service
public class DronePhotosServiceImpl implements IDronePhotosService 
{
    @Autowired
    private DronePhotosMapper dronePhotosMapper;

    /**
     * 查询航拍图片
     * 
     * @param photoId 航拍图片主键
     * @return 航拍图片
     */
    @Override
    public DronePhotos selectDronePhotosByPhotoId(Long photoId)
    {
        return dronePhotosMapper.selectDronePhotosByPhotoId(photoId);
    }

    /**
     * 查询航拍图片列表
     * 
     * @param dronePhotos 航拍图片
     * @return 航拍图片
     */
    @Override
    public List<DronePhotos> selectDronePhotosList(DronePhotos dronePhotos)
    {
        return dronePhotosMapper.selectDronePhotosList(dronePhotos);
    }

    /**
     * 新增航拍图片
     * 
     * @param dronePhotos 航拍图片
     * @return 结果
     */
    @Override
    public int insertDronePhotos(DronePhotos dronePhotos)
    {
        dronePhotos.setCreateTime(DateUtils.getNowDate());
        return dronePhotosMapper.insertDronePhotos(dronePhotos);
    }

    /**
     * 修改航拍图片
     * 
     * @param dronePhotos 航拍图片
     * @return 结果
     */
    @Override
    public int updateDronePhotos(DronePhotos dronePhotos)
    {
        dronePhotos.setUpdateTime(DateUtils.getNowDate());
        return dronePhotosMapper.updateDronePhotos(dronePhotos);
    }

    /**
     * 批量删除航拍图片
     * 
     * @param photoIds 需要删除的航拍图片主键
     * @return 结果
     */
    @Override
    public int deleteDronePhotosByPhotoIds(Long[] photoIds)
    {
        return dronePhotosMapper.deleteDronePhotosByPhotoIds(photoIds);
    }

    /**
     * 删除航拍图片信息
     * 
     * @param photoId 航拍图片主键
     * @return 结果
     */
    @Override
    public int deleteDronePhotosByPhotoId(Long photoId)
    {
        return dronePhotosMapper.deleteDronePhotosByPhotoId(photoId);
    }
}
