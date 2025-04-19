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
import com.farm.manage.domain.MachineInfo;
import com.farm.manage.service.IMachineInfoService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 机械信息Controller
 * 
 * @author beson
 * @date 2025-04-14
 */
@RestController
@RequestMapping("/manage/machineInfo")
public class MachineInfoController extends BaseController
{
    @Autowired
    private IMachineInfoService machineInfoService;

    /**
     * 查询机械信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:machineInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(MachineInfo machineInfo)
    {
        startPage();
        List<MachineInfo> list = machineInfoService.selectMachineInfoList(machineInfo);
        return getDataTable(list);
    }

    /**
     * 导出机械信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:machineInfo:export')")
    @Log(title = "机械信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MachineInfo machineInfo)
    {
        List<MachineInfo> list = machineInfoService.selectMachineInfoList(machineInfo);
        ExcelUtil<MachineInfo> util = new ExcelUtil<MachineInfo>(MachineInfo.class);
        util.exportExcel(response, list, "机械信息数据");
    }

    /**
     * 获取机械信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:machineInfo:query')")
    @GetMapping(value = "/{machineId}")
    public AjaxResult getInfo(@PathVariable("machineId") Long machineId)
    {
        return success(machineInfoService.selectMachineInfoByMachineId(machineId));
    }

    /**
     * 新增机械信息
     */
    @PreAuthorize("@ss.hasPermi('manage:machineInfo:add')")
    @Log(title = "机械信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MachineInfo machineInfo)
    {
        return toAjax(machineInfoService.insertMachineInfo(machineInfo));
    }

    /**
     * 修改机械信息
     */
    @PreAuthorize("@ss.hasPermi('manage:machineInfo:edit')")
    @Log(title = "机械信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MachineInfo machineInfo)
    {
        return toAjax(machineInfoService.updateMachineInfo(machineInfo));
    }

    /**
     * 删除机械信息
     */
    @PreAuthorize("@ss.hasPermi('manage:machineInfo:remove')")
    @Log(title = "机械信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{machineIds}")
    public AjaxResult remove(@PathVariable Long[] machineIds)
    {
        return toAjax(machineInfoService.deleteMachineInfoByMachineIds(machineIds));
    }
}
