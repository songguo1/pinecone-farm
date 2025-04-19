package com.farm.data.service;

import java.util.List;
import com.farm.data.domain.Documents;

/**
 * 系统文档Service接口
 * 
 * @author beson
 * @date 2025-04-17
 */
public interface IDocumentsService 
{
    /**
     * 查询系统文档
     * 
     * @param documentId 系统文档主键
     * @return 系统文档
     */
    public Documents selectDocumentsByDocumentId(Long documentId);

    /**
     * 查询系统文档列表
     * 
     * @param documents 系统文档
     * @return 系统文档集合
     */
    public List<Documents> selectDocumentsList(Documents documents);

    /**
     * 新增系统文档
     * 
     * @param documents 系统文档
     * @return 结果
     */
    public int insertDocuments(Documents documents);

    /**
     * 修改系统文档
     * 
     * @param documents 系统文档
     * @return 结果
     */
    public int updateDocuments(Documents documents);

    /**
     * 批量删除系统文档
     * 
     * @param documentIds 需要删除的系统文档主键集合
     * @return 结果
     */
    public int deleteDocumentsByDocumentIds(Long[] documentIds);

    /**
     * 删除系统文档信息
     * 
     * @param documentId 系统文档主键
     * @return 结果
     */
    public int deleteDocumentsByDocumentId(Long documentId);
}
