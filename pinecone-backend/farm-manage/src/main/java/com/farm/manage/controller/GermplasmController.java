package com.farm.manange.controller;

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
import com.farm.manange.domain.Germplasm;
import com.farm.manange.service.IGermplasmService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 种质系统Controller
 * 
 * @author beson
 * @date 2025-04-14
 */
@RestController
@RequestMapping("/manage/germplasm")
public class GermplasmController extends BaseController
{
    @Autowired
    private IGermplasmService germplasmService;

    /**
     * 查询种质系统列表
     */
    @PreAuthorize("@ss.hasPermi('manage:germplasm:list')")
    @GetMapping("/list")
    public TableDataInfo list(Germplasm germplasm)
    {
        startPage();
        List<Germplasm> list = germplasmService.selectGermplasmList(germplasm);
        return getDataTable(list);
    }

    /**
     * 导出种质系统列表
     */
    @PreAuthorize("@ss.hasPermi('manage:germplasm:export')")
    @Log(title = "种质系统", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Germplasm germplasm)
    {
        List<Germplasm> list = germplasmService.selectGermplasmList(germplasm);
        ExcelUtil<Germplasm> util = new ExcelUtil<Germplasm>(Germplasm.class);
        util.exportExcel(response, list, "种质系统数据");
    }

    /**
     * 获取种质系统详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:germplasm:query')")
    @GetMapping(value = "/{germplasmId}")
    public AjaxResult getInfo(@PathVariable("germplasmId") Long germplasmId)
    {
        return success(germplasmService.selectGermplasmByGermplasmId(germplasmId));
    }

    /**
     * 新增种质系统
     */
    @PreAuthorize("@ss.hasPermi('manage:germplasm:add')")
    @Log(title = "种质系统", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Germplasm germplasm)
    {
        return toAjax(germplasmService.insertGermplasm(germplasm));
    }

    /**
     * 修改种质系统
     */
    @PreAuthorize("@ss.hasPermi('manage:germplasm:edit')")
    @Log(title = "种质系统", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Germplasm germplasm)
    {
        return toAjax(germplasmService.updateGermplasm(germplasm));
    }

    /**
     * 删除种质系统
     */
    @PreAuthorize("@ss.hasPermi('manage:germplasm:remove')")
    @Log(title = "种质系统", businessType = BusinessType.DELETE)
	@DeleteMapping("/{germplasmIds}")
    public AjaxResult remove(@PathVariable Long[] germplasmIds)
    {
        return toAjax(germplasmService.deleteGermplasmByGermplasmIds(germplasmIds));
    }
}
