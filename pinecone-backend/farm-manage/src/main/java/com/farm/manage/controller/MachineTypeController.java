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
import com.farm.manage.domain.MachineType;
import com.farm.manage.service.IMachineTypeService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 机械类别Controller
 * 
 * @author beson
 * @date 2025-04-15
 */
@RestController
@RequestMapping("/manage/machineType")
public class MachineTypeController extends BaseController
{
    @Autowired
    private IMachineTypeService machineTypeService;

    /**
     * 查询机械类别列表
     */
    @PreAuthorize("@ss.hasPermi('manage:machineType:list')")
    @GetMapping("/list")
    public TableDataInfo list(MachineType machineType)
    {
        startPage();
        List<MachineType> list = machineTypeService.selectMachineTypeList(machineType);
        return getDataTable(list);
    }

    /**
     * 导出机械类别列表
     */
    @PreAuthorize("@ss.hasPermi('manage:machineType:export')")
    @Log(title = "机械类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MachineType machineType)
    {
        List<MachineType> list = machineTypeService.selectMachineTypeList(machineType);
        ExcelUtil<MachineType> util = new ExcelUtil<MachineType>(MachineType.class);
        util.exportExcel(response, list, "机械类别数据");
    }

    /**
     * 获取机械类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:machineType:query')")
    @GetMapping(value = "/{machineTypeId}")
    public AjaxResult getInfo(@PathVariable("machineTypeId") Long machineTypeId)
    {
        return success(machineTypeService.selectMachineTypeByMachineTypeId(machineTypeId));
    }

    /**
     * 新增机械类别
     */
    @PreAuthorize("@ss.hasPermi('manage:machineType:add')")
    @Log(title = "机械类别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MachineType machineType)
    {
        return toAjax(machineTypeService.insertMachineType(machineType));
    }

    /**
     * 修改机械类别
     */
    @PreAuthorize("@ss.hasPermi('manage:machineType:edit')")
    @Log(title = "机械类别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MachineType machineType)
    {
        return toAjax(machineTypeService.updateMachineType(machineType));
    }

    /**
     * 删除机械类别
     */
    @PreAuthorize("@ss.hasPermi('manage:machineType:remove')")
    @Log(title = "机械类别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{machineTypeIds}")
    public AjaxResult remove(@PathVariable Long[] machineTypeIds)
    {
        return toAjax(machineTypeService.deleteMachineTypeByMachineTypeIds(machineTypeIds));
    }
}
