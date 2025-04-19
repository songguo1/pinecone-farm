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
import com.farm.data.domain.DronePhotos;
import com.farm.data.service.IDronePhotosService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 航拍图片Controller
 * 
 * @author beson
 * @date 2025-04-14
 */
@RestController
@RequestMapping("/data/droneography")
public class DronePhotosController extends BaseController
{
    @Autowired
    private IDronePhotosService dronePhotosService;

    /**
     * 查询航拍图片列表
     */
    @PreAuthorize("@ss.hasPermi('data:droneography:list')")
    @GetMapping("/list")
    public TableDataInfo list(DronePhotos dronePhotos)
    {
        startPage();
        List<DronePhotos> list = dronePhotosService.selectDronePhotosList(dronePhotos);
        return getDataTable(list);
    }

    /**
     * 导出航拍图片列表
     */
    @PreAuthorize("@ss.hasPermi('data:droneography:export')")
    @Log(title = "航拍图片", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DronePhotos dronePhotos)
    {
        List<DronePhotos> list = dronePhotosService.selectDronePhotosList(dronePhotos);
        ExcelUtil<DronePhotos> util = new ExcelUtil<DronePhotos>(DronePhotos.class);
        util.exportExcel(response, list, "航拍图片数据");
    }

    /**
     * 获取航拍图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('data:droneography:query')")
    @GetMapping(value = "/{photoId}")
    public AjaxResult getInfo(@PathVariable("photoId") Long photoId)
    {
        return success(dronePhotosService.selectDronePhotosByPhotoId(photoId));
    }

    /**
     * 新增航拍图片
     */
    @PreAuthorize("@ss.hasPermi('data:droneography:add')")
    @Log(title = "航拍图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DronePhotos dronePhotos)
    {
        return toAjax(dronePhotosService.insertDronePhotos(dronePhotos));
    }

    /**
     * 修改航拍图片
     */
    @PreAuthorize("@ss.hasPermi('data:droneography:edit')")
    @Log(title = "航拍图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DronePhotos dronePhotos)
    {
        return toAjax(dronePhotosService.updateDronePhotos(dronePhotos));
    }

    /**
     * 删除航拍图片
     */
    @PreAuthorize("@ss.hasPermi('data:droneography:remove')")
    @Log(title = "航拍图片", businessType = BusinessType.DELETE)
	@DeleteMapping("/{photoIds}")
    public AjaxResult remove(@PathVariable Long[] photoIds)
    {
        return toAjax(dronePhotosService.deleteDronePhotosByPhotoIds(photoIds));
    }
}
