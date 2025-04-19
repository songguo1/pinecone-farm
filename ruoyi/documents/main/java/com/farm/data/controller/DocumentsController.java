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
import com.farm.data.domain.Documents;
import com.farm.data.service.IDocumentsService;
import com.farm.common.utils.poi.ExcelUtil;
import com.farm.common.core.page.TableDataInfo;

/**
 * 系统文档Controller
 * 
 * @author beson
 * @date 2025-04-14
 */
@RestController
@RequestMapping("/data/documents")
public class DocumentsController extends BaseController
{
    @Autowired
    private IDocumentsService documentsService;

    /**
     * 查询系统文档列表
     */
    @PreAuthorize("@ss.hasPermi('data:documents:list')")
    @GetMapping("/list")
    public TableDataInfo list(Documents documents)
    {
        startPage();
        List<Documents> list = documentsService.selectDocumentsList(documents);
        return getDataTable(list);
    }

    /**
     * 导出系统文档列表
     */
    @PreAuthorize("@ss.hasPermi('data:documents:export')")
    @Log(title = "系统文档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Documents documents)
    {
        List<Documents> list = documentsService.selectDocumentsList(documents);
        ExcelUtil<Documents> util = new ExcelUtil<Documents>(Documents.class);
        util.exportExcel(response, list, "系统文档数据");
    }

    /**
     * 获取系统文档详细信息
     */
    @PreAuthorize("@ss.hasPermi('data:documents:query')")
    @GetMapping(value = "/{documentId}")
    public AjaxResult getInfo(@PathVariable("documentId") Long documentId)
    {
        return success(documentsService.selectDocumentsByDocumentId(documentId));
    }

    /**
     * 新增系统文档
     */
    @PreAuthorize("@ss.hasPermi('data:documents:add')")
    @Log(title = "系统文档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Documents documents)
    {
        return toAjax(documentsService.insertDocuments(documents));
    }

    /**
     * 修改系统文档
     */
    @PreAuthorize("@ss.hasPermi('data:documents:edit')")
    @Log(title = "系统文档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Documents documents)
    {
        return toAjax(documentsService.updateDocuments(documents));
    }

    /**
     * 删除系统文档
     */
    @PreAuthorize("@ss.hasPermi('data:documents:remove')")
    @Log(title = "系统文档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{documentIds}")
    public AjaxResult remove(@PathVariable Long[] documentIds)
    {
        return toAjax(documentsService.deleteDocumentsByDocumentIds(documentIds));
    }
}
