package com.farm.devices.controller;

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
import com.farm.devices.domain.MonitoringDevices;
import com.farm.devices.service.IMonitoringDevicesService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 监控设备Controller
 * 
 * @author beson
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/devices/monitoring")
public class MonitoringDevicesController extends BaseController
{
    @Autowired
    private IMonitoringDevicesService monitoringDevicesService;

    /**
     * 查询监控设备列表
     */
    @PreAuthorize("@ss.hasPermi('devices:monitoring:list')")
    @GetMapping("/list")
    public TableDataInfo list(MonitoringDevices monitoringDevices)
    {
        startPage();
        List<MonitoringDevices> list = monitoringDevicesService.selectMonitoringDevicesList(monitoringDevices);
        return getDataTable(list);
    }

    /**
     * 导出监控设备列表
     */
    @PreAuthorize("@ss.hasPermi('devices:monitoring:export')")
    @Log(title = "监控设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MonitoringDevices monitoringDevices)
    {
        List<MonitoringDevices> list = monitoringDevicesService.selectMonitoringDevicesList(monitoringDevices);
        ExcelUtil<MonitoringDevices> util = new ExcelUtil<MonitoringDevices>(MonitoringDevices.class);
        util.exportExcel(response, list, "监控设备数据");
    }

    /**
     * 获取监控设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('devices:monitoring:query')")
    @GetMapping(value = "/{monitoringDeviceId}")
    public AjaxResult getInfo(@PathVariable("monitoringDeviceId") Long monitoringDeviceId)
    {
        return success(monitoringDevicesService.selectMonitoringDevicesByMonitoringDeviceId(monitoringDeviceId));
    }

    /**
     * 新增监控设备
     */
    @PreAuthorize("@ss.hasPermi('devices:monitoring:add')")
    @Log(title = "监控设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MonitoringDevices monitoringDevices)
    {
        return toAjax(monitoringDevicesService.insertMonitoringDevices(monitoringDevices));
    }

    /**
     * 修改监控设备
     */
    @PreAuthorize("@ss.hasPermi('devices:monitoring:edit')")
    @Log(title = "监控设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MonitoringDevices monitoringDevices)
    {
        return toAjax(monitoringDevicesService.updateMonitoringDevices(monitoringDevices));
    }

    /**
     * 删除监控设备
     */
    @PreAuthorize("@ss.hasPermi('devices:monitoring:remove')")
    @Log(title = "监控设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{monitoringDeviceIds}")
    public AjaxResult remove(@PathVariable Long[] monitoringDeviceIds)
    {
        return toAjax(monitoringDevicesService.deleteMonitoringDevicesByMonitoringDeviceIds(monitoringDeviceIds));
    }
}
