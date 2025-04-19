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
import com.farm.manage.domain.MaterialInfo;
import com.farm.manage.service.IMaterialInfoService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 农资信息Controller
 * 
 * @author beson
 * @date 2025-04-15
 */
@RestController
@RequestMapping("/manage/materialInfo")
public class MaterialInfoController extends BaseController
{
    @Autowired
    private IMaterialInfoService materialInfoService;

    /**
     * 查询农资信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:materialInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(MaterialInfo materialInfo)
    {
        startPage();
        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
        return getDataTable(list);
    }

    /**
     * 导出农资信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:materialInfo:export')")
    @Log(title = "农资信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MaterialInfo materialInfo)
    {
        List<MaterialInfo> list = materialInfoService.selectMaterialInfoList(materialInfo);
        ExcelUtil<MaterialInfo> util = new ExcelUtil<MaterialInfo>(MaterialInfo.class);
        util.exportExcel(response, list, "农资信息数据");
    }

    /**
     * 获取农资信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:materialInfo:query')")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return success(materialInfoService.selectMaterialInfoByMaterialId(materialId));
    }

    /**
     * 新增农资信息
     */
    @PreAuthorize("@ss.hasPermi('manage:materialInfo:add')")
    @Log(title = "农资信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialInfo materialInfo)
    {
        return toAjax(materialInfoService.insertMaterialInfo(materialInfo));
    }

    /**
     * 修改农资信息
     */
    @PreAuthorize("@ss.hasPermi('manage:materialInfo:edit')")
    @Log(title = "农资信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialInfo materialInfo)
    {
        return toAjax(materialInfoService.updateMaterialInfo(materialInfo));
    }

    /**
     * 删除农资信息
     */
    @PreAuthorize("@ss.hasPermi('manage:materialInfo:remove')")
    @Log(title = "农资信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(materialInfoService.deleteMaterialInfoByMaterialIds(materialIds));
    }
}
