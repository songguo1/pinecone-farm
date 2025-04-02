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
import com.farm.manage.domain.Farmland;
import com.farm.manage.service.IFarmlandService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 农田信息Controller
 * 
 * @author beson
 * @date 2025-02-25
 */
@RestController
@RequestMapping("/manage/farmland")
public class FarmlandController extends BaseController
{
    @Autowired
    private IFarmlandService farmlandService;

    /**
     * 查询农田信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:farmland:list')")
    @GetMapping("/list")
    public TableDataInfo list(Farmland farmland)
    {
        startPage();
        List<Farmland> list = farmlandService.selectFarmlandList(farmland);
        return getDataTable(list);
    }

    /**
     * 导出农田信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:farmland:export')")
    @Log(title = "农田信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Farmland farmland)
    {
        List<Farmland> list = farmlandService.selectFarmlandList(farmland);
        ExcelUtil<Farmland> util = new ExcelUtil<Farmland>(Farmland.class);
        util.exportExcel(response, list, "农田信息数据");
    }

    /**
     * 获取全部农田信息
     */
    @PreAuthorize("@ss.hasPermi('manage:farmland:getAll')")
    @Log(title = "农田信息", businessType = BusinessType.OTHER)
    @GetMapping("/getAll")
    public AjaxResult getAllFarmland()
    {
        List<Farmland> list = farmlandService.getAllFarmland();
        return success(list);
    }


    /**
     * 获取农田信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:farmland:query')")
    @GetMapping(value = "/{landId}")
    public AjaxResult getInfo(@PathVariable("landId") Long landId)
    {
        return success(farmlandService.selectFarmlandByLandId(landId));
    }

    /**
     * 新增农田信息
     */
    @PreAuthorize("@ss.hasPermi('manage:farmland:add')")
    @Log(title = "农田信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Farmland farmland)
    {
        return toAjax(farmlandService.insertFarmland(farmland));
    }

    /**
     * 修改农田信息
     */
    @PreAuthorize("@ss.hasPermi('manage:farmland:edit')")
    @Log(title = "农田信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Farmland farmland)
    {
        return toAjax(farmlandService.updateFarmland(farmland));
    }

    /**
     * 删除农田信息
     */
    @PreAuthorize("@ss.hasPermi('manage:farmland:remove')")
    @Log(title = "农田信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{landIds}")
    public AjaxResult remove(@PathVariable Long[] landIds)
    {
        return toAjax(farmlandService.deleteFarmlandByLandIds(landIds));
    }
}
