package com.farm.manage.controller;

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
import com.farm.manage.domain.CropBatch;
import com.farm.manage.service.ICropBatchService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 作物批次Controller
 * 
 * @author beson
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/manage/batch")
public class CropBatchController extends BaseController
{
    @Autowired
    private ICropBatchService cropBatchService;

    /**
     * 查询作物批次列表
     */
    @PreAuthorize("@ss.hasPermi('manage:batch:list')")
    @GetMapping("/list")
    public TableDataInfo list(CropBatch cropBatch)
    {
        startPage();
        List<CropBatch> list = cropBatchService.selectCropBatchList(cropBatch);
        return getDataTable(list);
    }

    /**
     * 导出作物批次列表
     */
    @PreAuthorize("@ss.hasPermi('manage:batch:export')")
    @Log(title = "作物批次", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CropBatch cropBatch)
    {
        List<CropBatch> list = cropBatchService.selectCropBatchList(cropBatch);
        ExcelUtil<CropBatch> util = new ExcelUtil<CropBatch>(CropBatch.class);
        util.exportExcel(response, list, "作物批次数据");
    }

    /**
     * 获取作物批次详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:batch:query')")
    @GetMapping(value = "/{batchId}")
    public AjaxResult getInfo(@PathVariable("batchId") Long batchId)
    {
        return success(cropBatchService.selectCropBatchByBatchId(batchId));
    }

    /**
     * 新增作物批次
     */
    @PreAuthorize("@ss.hasPermi('manage:batch:add')")
    @Log(title = "作物批次", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CropBatch cropBatch)
    {
        return toAjax(cropBatchService.insertCropBatch(cropBatch));
    }

    /**
     * 修改作物批次
     */
    @PreAuthorize("@ss.hasPermi('manage:batch:edit')")
    @Log(title = "作物批次", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CropBatch cropBatch)
    {
        return toAjax(cropBatchService.updateCropBatch(cropBatch));
    }

    /**
     * 删除作物批次
     */
    @PreAuthorize("@ss.hasPermi('manage:batch:remove')")
    @Log(title = "作物批次", businessType = BusinessType.DELETE)
	@DeleteMapping("/{batchIds}")
    public AjaxResult remove(@PathVariable Long[] batchIds)
    {
        return toAjax(cropBatchService.deleteCropBatchByBatchIds(batchIds));
    }
}
