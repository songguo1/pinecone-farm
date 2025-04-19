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
import com.farm.manage.domain.BatchTask;
import com.farm.manage.service.IBatchTaskService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 批次任务Controller
 * 
 * @author beson
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/manage/batchTask")
public class BatchTaskController extends BaseController
{
    @Autowired
    private IBatchTaskService batchTaskService;

    /**
     * 查询批次任务列表
     */
    @PreAuthorize("@ss.hasPermi('manage:batchTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BatchTask batchTask)
    {
        startPage();
        List<BatchTask> list = batchTaskService.selectBatchTaskList(batchTask);
        return getDataTable(list);
    }

    /**
     * 导出批次任务列表
     */
    @PreAuthorize("@ss.hasPermi('manage:batchTask:export')")
    @Log(title = "批次任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BatchTask batchTask)
    {
        List<BatchTask> list = batchTaskService.selectBatchTaskList(batchTask);
        ExcelUtil<BatchTask> util = new ExcelUtil<BatchTask>(BatchTask.class);
        util.exportExcel(response, list, "批次任务数据");
    }

    /**
     * 获取批次任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:batchTask:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return success(batchTaskService.selectBatchTaskByTaskId(taskId));
    }

    /**
     * 新增批次任务
     */
    @PreAuthorize("@ss.hasPermi('manage:batchTask:add')")
    @Log(title = "批次任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BatchTask batchTask)
    {
        return toAjax(batchTaskService.insertBatchTask(batchTask));
    }

    /**
     * 修改批次任务
     */
    @PreAuthorize("@ss.hasPermi('manage:batchTask:edit')")
    @Log(title = "批次任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BatchTask batchTask)
    {
        return toAjax(batchTaskService.updateBatchTask(batchTask));
    }

    /**
     * 删除批次任务
     */
    @PreAuthorize("@ss.hasPermi('manage:batchTask:remove')")
    @Log(title = "批次任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        return toAjax(batchTaskService.deleteBatchTaskByTaskIds(taskIds));
    }
}
