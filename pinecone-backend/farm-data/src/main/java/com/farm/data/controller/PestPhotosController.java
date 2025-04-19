package com.farm.data.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.farm.common.annotation.Log;
import com.farm.common.core.controller.BaseController;
import com.farm.common.core.domain.AjaxResult;
import com.farm.common.enums.BusinessType;
import com.farm.data.domain.PestPhotos;
import com.farm.data.service.IPestPhotosService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 病虫害照片Controller
 * 
 * @author beson
 * @date 2025-04-14
 */
@RestController
@RequestMapping("/data/pest")
public class PestPhotosController extends BaseController
{
    @Autowired
    private IPestPhotosService pestPhotosService;

    /**
     * 查询病虫害照片列表
     */
    @PreAuthorize("@ss.hasPermi('data:pest:list')")
    @GetMapping("/list")
    public TableDataInfo list(PestPhotos pestPhotos)
    {
        startPage();
        List<PestPhotos> list = pestPhotosService.selectPestPhotosList(pestPhotos);
        return getDataTable(list);
    }

    /**
     * 导出病虫害照片列表
     */
    @PreAuthorize("@ss.hasPermi('data:pest:export')")
    @Log(title = "病虫害照片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PestPhotos pestPhotos)
    {
        List<PestPhotos> list = pestPhotosService.selectPestPhotosList(pestPhotos);
        ExcelUtil<PestPhotos> util = new ExcelUtil<PestPhotos>(PestPhotos.class);
        util.exportExcel(response, list, "病虫害照片数据");
    }

    /**
     * 获取病虫害照片详细信息
     */
    @PreAuthorize("@ss.hasPermi('data:pest:query')")
    @GetMapping(value = "/{photoId}")
    public AjaxResult getInfo(@PathVariable("photoId") Long photoId)
    {
        return success(pestPhotosService.selectPestPhotosByPhotoId(photoId));
    }

    /**
     * 新增病虫害照片
     */
    @PreAuthorize("@ss.hasPermi('data:pest:add')")
    @Log(title = "病虫害照片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PestPhotos pestPhotos)
    {
        return toAjax(pestPhotosService.insertPestPhotos(pestPhotos));
    }

    /**
     * 修改病虫害照片
     */
    @PreAuthorize("@ss.hasPermi('data:pest:edit')")
    @Log(title = "病虫害照片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PestPhotos pestPhotos)
    {
        return toAjax(pestPhotosService.updatePestPhotos(pestPhotos));
    }

    /**
     * 删除病虫害照片
     */
    @PreAuthorize("@ss.hasPermi('data:pest:remove')")
    @Log(title = "病虫害照片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{photoIds}")
    public AjaxResult remove(@PathVariable Long[] photoIds)
    {
        return toAjax(pestPhotosService.deletePestPhotosByPhotoIds(photoIds));
    }
}
