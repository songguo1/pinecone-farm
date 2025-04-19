package com.farm.data.service;

import java.util.List;
import com.farm.data.domain.DronePhotos;

/**
 * 航拍图片Service接口
 * 
 * @author beson
 * @date 2025-04-17
 */
public interface IDronePhotosService 
{
    /**
     * 查询航拍图片
     * 
     * @param photoId 航拍图片主键
     * @return 航拍图片
     */
    public DronePhotos selectDronePhotosByPhotoId(Long photoId);

    /**
     * 查询航拍图片列表
     * 
     * @param dronePhotos 航拍图片
     * @return 航拍图片集合
     */
    public List<DronePhotos> selectDronePhotosList(DronePhotos dronePhotos);

    /**
     * 新增航拍图片
     * 
     * @param dronePhotos 航拍图片
     * @return 结果
     */
    public int insertDronePhotos(DronePhotos dronePhotos);

    /**
     * 修改航拍图片
     * 
     * @param dronePhotos 航拍图片
     * @return 结果
     */
    public int updateDronePhotos(DronePhotos dronePhotos);

    /**
     * 批量删除航拍图片
     * 
     * @param photoIds 需要删除的航拍图片主键集合
     * @return 结果
     */
    public int deleteDronePhotosByPhotoIds(Long[] photoIds);

    /**
     * 删除航拍图片信息
     * 
     * @param photoId 航拍图片主键
     * @return 结果
     */
    public int deleteDronePhotosByPhotoId(Long photoId);
}
