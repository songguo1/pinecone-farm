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
import com.farm.manage.domain.MaterialType;
import com.farm.manage.service.IMaterialTypeService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 农资类别Controller
 * 
 * @author beson
 * @date 2025-04-14
 */
@RestController
@RequestMapping("/manage/materialType")
public class MaterialTypeController extends BaseController
{
    @Autowired
    private IMaterialTypeService materialTypeService;

    /**
     * 查询农资类别列表
     */
    @PreAuthorize("@ss.hasPermi('manage:materialType:list')")
    @GetMapping("/list")
    public TableDataInfo list(MaterialType materialType)
    {
        startPage();
        List<MaterialType> list = materialTypeService.selectMaterialTypeList(materialType);
        return getDataTable(list);
    }

    /**
     * 导出农资类别列表
     */
    @PreAuthorize("@ss.hasPermi('manage:materialType:export')")
    @Log(title = "农资类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialType materialType)
    {
        List<MaterialType> list = materialTypeService.selectMaterialTypeList(materialType);
        ExcelUtil<MaterialType> util = new ExcelUtil<MaterialType>(MaterialType.class);
        util.exportExcel(response, list, "农资类别数据");
    }

    /**
     * 获取农资类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:materialType:query')")
    @GetMapping(value = "/{materialTypeId}")
    public AjaxResult getInfo(@PathVariable("materialTypeId") Long materialTypeId)
    {
        return success(materialTypeService.selectMaterialTypeByMaterialTypeId(materialTypeId));
    }

    /**
     * 新增农资类别
     */
    @PreAuthorize("@ss.hasPermi('manage:materialType:add')")
    @Log(title = "农资类别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialType materialType)
    {
        return toAjax(materialTypeService.insertMaterialType(materialType));
    }

    /**
     * 修改农资类别
     */
    @PreAuthorize("@ss.hasPermi('manage:materialType:edit')")
    @Log(title = "农资类别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialType materialType)
    {
        return toAjax(materialTypeService.updateMaterialType(materialType));
    }

    /**
     * 删除农资类别
     */
    @PreAuthorize("@ss.hasPermi('manage:materialType:remove')")
    @Log(title = "农资类别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialTypeIds}")
    public AjaxResult remove(@PathVariable Long[] materialTypeIds)
    {
        return toAjax(materialTypeService.deleteMaterialTypeByMaterialTypeIds(materialTypeIds));
    }
}
