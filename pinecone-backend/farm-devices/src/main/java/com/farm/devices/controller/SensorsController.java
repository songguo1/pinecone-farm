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
import com.farm.devices.domain.Sensors;
import com.farm.devices.service.ISensorsService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 传感器设备Controller
 * 
 * @author beson
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/devices/sensors")
public class SensorsController extends BaseController
{
    @Autowired
    private ISensorsService sensorsService;

    /**
     * 查询传感器设备列表
     */
    @PreAuthorize("@ss.hasPermi('devices:sensors:list')")
    @GetMapping("/list")
    public TableDataInfo list(Sensors sensors)
    {
        startPage();
        List<Sensors> list = sensorsService.selectSensorsList(sensors);
        return getDataTable(list);
    }

    /**
     * 导出传感器设备列表
     */
    @PreAuthorize("@ss.hasPermi('devices:sensors:export')")
    @Log(title = "传感器设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sensors sensors)
    {
        List<Sensors> list = sensorsService.selectSensorsList(sensors);
        ExcelUtil<Sensors> util = new ExcelUtil<Sensors>(Sensors.class);
        util.exportExcel(response, list, "传感器设备数据");
    }

    /**
     * 获取传感器设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('devices:sensors:query')")
    @GetMapping(value = "/{sensorId}")
    public AjaxResult getInfo(@PathVariable("sensorId") Long sensorId)
    {
        return success(sensorsService.selectSensorsBySensorId(sensorId));
    }

    /**
     * 新增传感器设备
     */
    @PreAuthorize("@ss.hasPermi('devices:sensors:add')")
    @Log(title = "传感器设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sensors sensors)
    {
        return toAjax(sensorsService.insertSensors(sensors));
    }

    /**
     * 修改传感器设备
     */
    @PreAuthorize("@ss.hasPermi('devices:sensors:edit')")
    @Log(title = "传感器设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sensors sensors)
    {
        return toAjax(sensorsService.updateSensors(sensors));
    }

    /**
     * 删除传感器设备
     */
    @PreAuthorize("@ss.hasPermi('devices:sensors:remove')")
    @Log(title = "传感器设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{sensorIds}")
    public AjaxResult remove(@PathVariable Long[] sensorIds)
    {
        return toAjax(sensorsService.deleteSensorsBySensorIds(sensorIds));
    }
}
