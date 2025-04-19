package com.farm.data.mapper;

import java.util.List;
import com.farm.data.domain.PestPhotos;

/**
 * 病虫害照片Mapper接口
 * 
 * @author beson
 * @date 2025-04-14
 */
public interface PestPhotosMapper 
{
    /**
     * 查询病虫害照片
     * 
     * @param photoId 病虫害照片主键
     * @return 病虫害照片
     */
    public PestPhotos selectPestPhotosByPhotoId(Long photoId);

    /**
     * 查询病虫害照片列表
     * 
     * @param pestPhotos 病虫害照片
     * @return 病虫害照片集合
     */
    public List<PestPhotos> selectPestPhotosList(PestPhotos pestPhotos);

    /**
     * 新增病虫害照片
     * 
     * @param pestPhotos 病虫害照片
     * @return 结果
     */
    public int insertPestPhotos(PestPhotos pestPhotos);

    /**
     * 修改病虫害照片
     * 
     * @param pestPhotos 病虫害照片
     * @return 结果
     */
    public int updatePestPhotos(PestPhotos pestPhotos);

    /**
     * 删除病虫害照片
     * 
     * @param photoId 病虫害照片主键
     * @return 结果
     */
    public int deletePestPhotosByPhotoId(Long photoId);

    /**
     * 批量删除病虫害照片
     * 
     * @param photoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePestPhotosByPhotoIds(Long[] photoIds);
}
