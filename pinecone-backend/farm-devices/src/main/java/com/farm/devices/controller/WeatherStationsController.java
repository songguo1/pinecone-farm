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
import com.farm.devices.domain.WeatherStations;
import com.farm.devices.service.IWeatherStationsService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 气象站设备Controller
 * 
 * @author beson
 * @date 2025-03-05
 */
@RestController
@RequestMapping("/devices/weather")
public class WeatherStationsController extends BaseController
{
    @Autowired
    private IWeatherStationsService weatherStationsService;

    /**
     * 查询气象站设备列表
     */
    @PreAuthorize("@ss.hasPermi('devices:weather:list')")
    @GetMapping("/list")
    public TableDataInfo list(WeatherStations weatherStations)
    {
        startPage();
        List<WeatherStations> list = weatherStationsService.selectWeatherStationsList(weatherStations);
        return getDataTable(list);
    }

    /**
     * 导出气象站设备列表
     */
    @PreAuthorize("@ss.hasPermi('devices:weather:export')")
    @Log(title = "气象站设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WeatherStations weatherStations)
    {
        List<WeatherStations> list = weatherStationsService.selectWeatherStationsList(weatherStations);
        ExcelUtil<WeatherStations> util = new ExcelUtil<WeatherStations>(WeatherStations.class);
        util.exportExcel(response, list, "气象站设备数据");
    }

    /**
     * 获取气象站设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('devices:weather:query')")
    @GetMapping(value = "/{weatherStationId}")
    public AjaxResult getInfo(@PathVariable("weatherStationId") Long weatherStationId)
    {
        return success(weatherStationsService.selectWeatherStationsByWeatherStationId(weatherStationId));
    }

    /**
     * 新增气象站设备
     */
    @PreAuthorize("@ss.hasPermi('devices:weather:add')")
    @Log(title = "气象站设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WeatherStations weatherStations)
    {
        return toAjax(weatherStationsService.insertWeatherStations(weatherStations));
    }

    /**
     * 修改气象站设备
     */
    @PreAuthorize("@ss.hasPermi('devices:weather:edit')")
    @Log(title = "气象站设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WeatherStations weatherStations)
    {
        return toAjax(weatherStationsService.updateWeatherStations(weatherStations));
    }

    /**
     * 删除气象站设备
     */
    @PreAuthorize("@ss.hasPermi('devices:weather:remove')")
    @Log(title = "气象站设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{weatherStationIds}")
    public AjaxResult remove(@PathVariable Long[] weatherStationIds)
    {
        return toAjax(weatherStationsService.deleteWeatherStationsByWeatherStationIds(weatherStationIds));
    }
}
