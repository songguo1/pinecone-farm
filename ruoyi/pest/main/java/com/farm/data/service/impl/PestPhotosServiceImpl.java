package com.farm.data.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.data.mapper.PestPhotosMapper;
import com.farm.data.domain.PestPhotos;
import com.farm.data.service.IPestPhotosService;

/**
 * 病虫害照片Service业务层处理
 * 
 * @author beson
 * @date 2025-04-14
 */
@Service
public class PestPhotosServiceImpl implements IPestPhotosService 
{
    @Autowired
    private PestPhotosMapper pestPhotosMapper;

    /**
     * 查询病虫害照片
     * 
     * @param photoId 病虫害照片主键
     * @return 病虫害照片
     */
    @Override
    public PestPhotos selectPestPhotosByPhotoId(Long photoId)
    {
        return pestPhotosMapper.selectPestPhotosByPhotoId(photoId);
    }

    /**
     * 查询病虫害照片列表
     * 
     * @param pestPhotos 病虫害照片
     * @return 病虫害照片
     */
    @Override
    public List<PestPhotos> selectPestPhotosList(PestPhotos pestPhotos)
    {
        return pestPhotosMapper.selectPestPhotosList(pestPhotos);
    }

    /**
     * 新增病虫害照片
     * 
     * @param pestPhotos 病虫害照片
     * @return 结果
     */
    @Override
    public int insertPestPhotos(PestPhotos pestPhotos)
    {
        pestPhotos.setCreateTime(DateUtils.getNowDate());
        return pestPhotosMapper.insertPestPhotos(pestPhotos);
    }

    /**
     * 修改病虫害照片
     * 
     * @param pestPhotos 病虫害照片
     * @return 结果
     */
    @Override
    public int updatePestPhotos(PestPhotos pestPhotos)
    {
        pestPhotos.setUpdateTime(DateUtils.getNowDate());
        return pestPhotosMapper.updatePestPhotos(pestPhotos);
    }

    /**
     * 批量删除病虫害照片
     * 
     * @param photoIds 需要删除的病虫害照片主键
     * @return 结果
     */
    @Override
    public int deletePestPhotosByPhotoIds(Long[] photoIds)
    {
        return pestPhotosMapper.deletePestPhotosByPhotoIds(photoIds);
    }

    /**
     * 删除病虫害照片信息
     * 
     * @param photoId 病虫害照片主键
     * @return 结果
     */
    @Override
    public int deletePestPhotosByPhotoId(Long photoId)
    {
        return pestPhotosMapper.deletePestPhotosByPhotoId(photoId);
    }
}
