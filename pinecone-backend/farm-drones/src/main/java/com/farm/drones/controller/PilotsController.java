package com.farm.drones.controller;

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
import com.farm.drones.domain.Pilots;
import com.farm.drones.service.IPilotsService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 飞手信息Controller
 * 
 * @author beson
 * @date 2025-02-28
 */
@RestController
@RequestMapping("/drones/pilots")
public class PilotsController extends BaseController
{
    @Autowired
    private IPilotsService pilotsService;

    /**
     * 查询飞手信息列表
     */
    @PreAuthorize("@ss.hasPermi('drones:pilots:list')")
    @GetMapping("/list")
    public TableDataInfo list(Pilots pilots)
    {
        startPage();
        List<Pilots> list = pilotsService.selectPilotsList(pilots);
        return getDataTable(list);
    }

    /**
     * 导出飞手信息列表
     */
    @PreAuthorize("@ss.hasPermi('drones:pilots:export')")
    @Log(title = "飞手信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Pilots pilots)
    {
        List<Pilots> list = pilotsService.selectPilotsList(pilots);
        ExcelUtil<Pilots> util = new ExcelUtil<Pilots>(Pilots.class);
        util.exportExcel(response, list, "飞手信息数据");
    }

    /**
     * 获取飞手信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('drones:pilots:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(pilotsService.selectPilotsById(id));
    }

    /**
     * 新增飞手信息
     */
    @PreAuthorize("@ss.hasPermi('drones:pilots:add')")
    @Log(title = "飞手信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Pilots pilots)
    {
        return toAjax(pilotsService.insertPilots(pilots));
    }

    /**
     * 修改飞手信息
     */
    @PreAuthorize("@ss.hasPermi('drones:pilots:edit')")
    @Log(title = "飞手信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Pilots pilots)
    {
        return toAjax(pilotsService.updatePilots(pilots));
    }

    /**
     * 删除飞手信息
     */
    @PreAuthorize("@ss.hasPermi('drones:pilots:remove')")
    @Log(title = "飞手信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(pilotsService.deletePilotsByIds(ids));
    }
}
