package com.farm.manage.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.farm.manage.domain.Farmland;
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
import com.farm.manage.domain.FishPond;
import com.farm.manage.service.IFishPondService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 鱼塘信息Controller
 * 
 * @author beson
 * @date 2025-02-27
 */
@RestController
@RequestMapping("/manage/pond")
public class FishPondController extends BaseController
{
    @Autowired
    private IFishPondService fishPondService;

    /**
     * 查询鱼塘信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:pond:list')")
    @GetMapping("/list")
    public TableDataInfo list(FishPond fishPond)
    {
        startPage();
        List<FishPond> list = fishPondService.selectFishPondList(fishPond);
        return getDataTable(list);
    }

    /**
     * 导出鱼塘信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:pond:export')")
    @Log(title = "鱼塘信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FishPond fishPond)
    {
        List<FishPond> list = fishPondService.selectFishPondList(fishPond);
        ExcelUtil<FishPond> util = new ExcelUtil<FishPond>(FishPond.class);
        util.exportExcel(response, list, "鱼塘信息数据");
    }

    @PreAuthorize("@ss.hasPermi('manage:pond:getAll')")
    @Log(title = "鱼塘信息", businessType = BusinessType.OTHER)
    @GetMapping("/getAll")
    public AjaxResult getAllFishPond()
    {
        List<FishPond> list = fishPondService.getAllFishPond();
        return success(list);
    }

    /**
     * 获取鱼塘信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:pond:query')")
    @GetMapping(value = "/{pondId}")
    public AjaxResult getInfo(@PathVariable("pondId") Long pondId)
    {
        return success(fishPondService.selectFishPondByPondId(pondId));
    }

    /**
     * 新增鱼塘信息
     */
    @PreAuthorize("@ss.hasPermi('manage:pond:add')")
    @Log(title = "鱼塘信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FishPond fishPond)
    {
        return toAjax(fishPondService.insertFishPond(fishPond));
    }

    /**
     * 修改鱼塘信息
     */
    @PreAuthorize("@ss.hasPermi('manage:pond:edit')")
    @Log(title = "鱼塘信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FishPond fishPond)
    {
        return toAjax(fishPondService.updateFishPond(fishPond));
    }

    /**
     * 删除鱼塘信息
     */
    @PreAuthorize("@ss.hasPermi('manage:pond:remove')")
    @Log(title = "鱼塘信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pondIds}")
    public AjaxResult remove(@PathVariable Long[] pondIds)
    {
        return toAjax(fishPondService.deleteFishPondByPondIds(pondIds));
    }
}
