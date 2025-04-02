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
import com.farm.drones.domain.Drones;
import com.farm.drones.service.IDronesService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 无人机信息，记录所有无人机的详细信息Controller
 * 
 * @author beson
 * @date 2025-02-28
 */
@RestController
@RequestMapping("/drones/drones")
public class DronesController extends BaseController
{
    @Autowired
    private IDronesService dronesService;

    /**
     * 查询无人机信息，记录所有无人机的详细信息列表
     */
    @PreAuthorize("@ss.hasPermi('drones:drones:list')")
    @GetMapping("/list")
    public TableDataInfo list(Drones drones)
    {
        startPage();
        List<Drones> list = dronesService.selectDronesList(drones);
        return getDataTable(list);
    }

    /**
     * 导出无人机信息，记录所有无人机的详细信息列表
     */
    @PreAuthorize("@ss.hasPermi('drones:drones:export')")
    @Log(title = "无人机信息，记录所有无人机的详细信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Drones drones)
    {
        List<Drones> list = dronesService.selectDronesList(drones);
        ExcelUtil<Drones> util = new ExcelUtil<Drones>(Drones.class);
        util.exportExcel(response, list, "无人机信息，记录所有无人机的详细信息数据");
    }

    /**
     * 获取无人机信息，记录所有无人机的详细信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('drones:drones:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dronesService.selectDronesById(id));
    }

    /**
     * 新增无人机信息，记录所有无人机的详细信息
     */
    @PreAuthorize("@ss.hasPermi('drones:drones:add')")
    @Log(title = "无人机信息，记录所有无人机的详细信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Drones drones)
    {
        return toAjax(dronesService.insertDrones(drones));
    }

    /**
     * 修改无人机信息，记录所有无人机的详细信息
     */
    @PreAuthorize("@ss.hasPermi('drones:drones:edit')")
    @Log(title = "无人机信息，记录所有无人机的详细信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Drones drones)
    {
        return toAjax(dronesService.updateDrones(drones));
    }

    /**
     * 删除无人机信息，记录所有无人机的详细信息
     */
    @PreAuthorize("@ss.hasPermi('drones:drones:remove')")
    @Log(title = "无人机信息，记录所有无人机的详细信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dronesService.deleteDronesByIds(ids));
    }
}
