package com.farm.data.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.data.mapper.DocumentsMapper;
import com.farm.data.domain.Documents;
import com.farm.data.service.IDocumentsService;

/**
 * 系统文档Service业务层处理
 * 
 * @author beson
 * @date 2025-04-14
 */
@Service
public class DocumentsServiceImpl implements IDocumentsService 
{
    @Autowired
    private DocumentsMapper documentsMapper;

    /**
     * 查询系统文档
     * 
     * @param documentId 系统文档主键
     * @return 系统文档
     */
    @Override
    public Documents selectDocumentsByDocumentId(Long documentId)
    {
        return documentsMapper.selectDocumentsByDocumentId(documentId);
    }

    /**
     * 查询系统文档列表
     * 
     * @param documents 系统文档
     * @return 系统文档
     */
    @Override
    public List<Documents> selectDocumentsList(Documents documents)
    {
        return documentsMapper.selectDocumentsList(documents);
    }

    /**
     * 新增系统文档
     * 
     * @param documents 系统文档
     * @return 结果
     */
    @Override
    public int insertDocuments(Documents documents)
    {
        documents.setCreateTime(DateUtils.getNowDate());
        return documentsMapper.insertDocuments(documents);
    }

    /**
     * 修改系统文档
     * 
     * @param documents 系统文档
     * @return 结果
     */
    @Override
    public int updateDocuments(Documents documents)
    {
        documents.setUpdateTime(DateUtils.getNowDate());
        return documentsMapper.updateDocuments(documents);
    }

    /**
     * 批量删除系统文档
     * 
     * @param documentIds 需要删除的系统文档主键
     * @return 结果
     */
    @Override
    public int deleteDocumentsByDocumentIds(Long[] documentIds)
    {
        return documentsMapper.deleteDocumentsByDocumentIds(documentIds);
    }

    /**
     * 删除系统文档信息
     * 
     * @param documentId 系统文档主键
     * @return 结果
     */
    @Override
    public int deleteDocumentsByDocumentId(Long documentId)
    {
        return documentsMapper.deleteDocumentsByDocumentId(documentId);
    }
}
