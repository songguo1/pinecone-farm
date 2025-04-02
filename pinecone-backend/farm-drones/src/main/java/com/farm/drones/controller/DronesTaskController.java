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
import com.farm.drones.domain.DronesTask;
import com.farm.drones.service.IDronesTaskService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 无人机任务Controller
 * 
 * @author beson
 * @date 2025-03-01
 */
@RestController
@RequestMapping("/drones/task")
public class DronesTaskController extends BaseController
{
    @Autowired
    private IDronesTaskService dronesTaskService;

    /**
     * 查询无人机任务列表
     */
    @PreAuthorize("@ss.hasPermi('drones:task:list')")
    @GetMapping("/list")
    public TableDataInfo list(DronesTask dronesTask)
    {
        startPage();
        List<DronesTask> list = dronesTaskService.selectDronesTaskList(dronesTask);
        return getDataTable(list);
    }

    /**
     * 导出无人机任务列表
     */
    @PreAuthorize("@ss.hasPermi('drones:task:export')")
    @Log(title = "无人机任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DronesTask dronesTask)
    {
        List<DronesTask> list = dronesTaskService.selectDronesTaskList(dronesTask);
        ExcelUtil<DronesTask> util = new ExcelUtil<DronesTask>(DronesTask.class);
        util.exportExcel(response, list, "无人机任务数据");
    }

    /**
     * 获取无人机任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('drones:task:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(dronesTaskService.selectDronesTaskById(id));
    }

    /**
     * 新增无人机任务
     */
    @PreAuthorize("@ss.hasPermi('drones:task:add')")
    @Log(title = "无人机任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DronesTask dronesTask)
    {
        return toAjax(dronesTaskService.insertDronesTask(dronesTask));
    }

    /**
     * 修改无人机任务
     */
    @PreAuthorize("@ss.hasPermi('drones:task:edit')")
    @Log(title = "无人机任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DronesTask dronesTask)
    {
        return toAjax(dronesTaskService.updateDronesTask(dronesTask));
    }

    /**
     * 删除无人机任务
     */
    @PreAuthorize("@ss.hasPermi('drones:task:remove')")
    @Log(title = "无人机任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(dronesTaskService.deleteDronesTaskByIds(ids));
    }
}
