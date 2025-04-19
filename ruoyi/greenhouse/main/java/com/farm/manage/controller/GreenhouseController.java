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
import com.farm.manage.domain.Greenhouse;
import com.farm.manage.service.IGreenhouseService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 大棚信息Controller
 * 
 * @author beson
 * @date 2025-04-18
 */
@RestController
@RequestMapping("/manage/greenhouse")
public class GreenhouseController extends BaseController
{
    @Autowired
    private IGreenhouseService greenhouseService;

    /**
     * 查询大棚信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:greenhouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(Greenhouse greenhouse)
    {
        startPage();
        List<Greenhouse> list = greenhouseService.selectGreenhouseList(greenhouse);
        return getDataTable(list);
    }

    /**
     * 导出大棚信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:greenhouse:export')")
    @Log(title = "大棚信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Greenhouse greenhouse)
    {
        List<Greenhouse> list = greenhouseService.selectGreenhouseList(greenhouse);
        ExcelUtil<Greenhouse> util = new ExcelUtil<Greenhouse>(Greenhouse.class);
        util.exportExcel(response, list, "大棚信息数据");
    }

    /**
     * 获取大棚信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:greenhouse:query')")
    @GetMapping(value = "/{greenhouseId}")
    public AjaxResult getInfo(@PathVariable("greenhouseId") Long greenhouseId)
    {
        return success(greenhouseService.selectGreenhouseByGreenhouseId(greenhouseId));
    }

    /**
     * 新增大棚信息
     */
    @PreAuthorize("@ss.hasPermi('manage:greenhouse:add')")
    @Log(title = "大棚信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Greenhouse greenhouse)
    {
        return toAjax(greenhouseService.insertGreenhouse(greenhouse));
    }

    /**
     * 修改大棚信息
     */
    @PreAuthorize("@ss.hasPermi('manage:greenhouse:edit')")
    @Log(title = "大棚信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Greenhouse greenhouse)
    {
        return toAjax(greenhouseService.updateGreenhouse(greenhouse));
    }

    /**
     * 删除大棚信息
     */
    @PreAuthorize("@ss.hasPermi('manage:greenhouse:remove')")
    @Log(title = "大棚信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{greenhouseIds}")
    public AjaxResult remove(@PathVariable Long[] greenhouseIds)
    {
        return toAjax(greenhouseService.deleteGreenhouseByGreenhouseIds(greenhouseIds));
    }
}
