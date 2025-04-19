package com.farm.data.controller;

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
import com.farm.data.domain.DocumentTypes;
import com.farm.data.service.IDocumentTypesService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 文档类型Controller
 * 
 * @author beson
 * @date 2025-04-14
 */
@RestController
@RequestMapping("/data/documenttypes")
public class DocumentTypesController extends BaseController
{
    @Autowired
    private IDocumentTypesService documentTypesService;

    /**
     * 查询文档类型列表
     */
    @PreAuthorize("@ss.hasPermi('data:documenttypes:list')")
    @GetMapping("/list")
    public TableDataInfo list(DocumentTypes documentTypes)
    {
        startPage();
        List<DocumentTypes> list = documentTypesService.selectDocumentTypesList(documentTypes);
        return getDataTable(list);
    }

    /**
     * 导出文档类型列表
     */
    @PreAuthorize("@ss.hasPermi('data:documenttypes:export')")
    @Log(title = "文档类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DocumentTypes documentTypes)
    {
        List<DocumentTypes> list = documentTypesService.selectDocumentTypesList(documentTypes);
        ExcelUtil<DocumentTypes> util = new ExcelUtil<DocumentTypes>(DocumentTypes.class);
        util.exportExcel(response, list, "文档类型数据");
    }

    /**
     * 获取文档类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('data:documenttypes:query')")
    @GetMapping(value = "/{documentTypeId}")
    public AjaxResult getInfo(@PathVariable("documentTypeId") Long documentTypeId)
    {
        return success(documentTypesService.selectDocumentTypesByDocumentTypeId(documentTypeId));
    }

    /**
     * 新增文档类型
     */
    @PreAuthorize("@ss.hasPermi('data:documenttypes:add')")
    @Log(title = "文档类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DocumentTypes documentTypes)
    {
        return toAjax(documentTypesService.insertDocumentTypes(documentTypes));
    }

    /**
     * 修改文档类型
     */
    @PreAuthorize("@ss.hasPermi('data:documenttypes:edit')")
    @Log(title = "文档类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DocumentTypes documentTypes)
    {
        return toAjax(documentTypesService.updateDocumentTypes(documentTypes));
    }

    /**
     * 删除文档类型
     */
    @PreAuthorize("@ss.hasPermi('data:documenttypes:remove')")
    @Log(title = "文档类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{documentTypeIds}")
    public AjaxResult remove(@PathVariable Long[] documentTypeIds)
    {
        return toAjax(documentTypesService.deleteDocumentTypesByDocumentTypeIds(documentTypeIds));
    }
}
