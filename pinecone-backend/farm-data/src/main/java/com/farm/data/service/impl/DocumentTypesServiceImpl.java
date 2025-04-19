package com.farm.data.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.data.mapper.DocumentTypesMapper;
import com.farm.data.domain.DocumentTypes;
import com.farm.data.service.IDocumentTypesService;

/**
 * 文档类型Service业务层处理
 * 
 * @author beson
 * @date 2025-04-14
 */
@Service
public class DocumentTypesServiceImpl implements IDocumentTypesService 
{
    @Autowired
    private DocumentTypesMapper documentTypesMapper;

    /**
     * 查询文档类型
     * 
     * @param documentTypeId 文档类型主键
     * @return 文档类型
     */
    @Override
    public DocumentTypes selectDocumentTypesByDocumentTypeId(Long documentTypeId)
    {
        return documentTypesMapper.selectDocumentTypesByDocumentTypeId(documentTypeId);
    }

    /**
     * 查询文档类型列表
     * 
     * @param documentTypes 文档类型
     * @return 文档类型
     */
    @Override
    public List<DocumentTypes> selectDocumentTypesList(DocumentTypes documentTypes)
    {
        return documentTypesMapper.selectDocumentTypesList(documentTypes);
    }

    /**
     * 新增文档类型
     * 
     * @param documentTypes 文档类型
     * @return 结果
     */
    @Override
    public int insertDocumentTypes(DocumentTypes documentTypes)
    {
        documentTypes.setCreateTime(DateUtils.getNowDate());
        return documentTypesMapper.insertDocumentTypes(documentTypes);
    }

    /**
     * 修改文档类型
     * 
     * @param documentTypes 文档类型
     * @return 结果
     */
    @Override
    public int updateDocumentTypes(DocumentTypes documentTypes)
    {
        documentTypes.setUpdateTime(DateUtils.getNowDate());
        return documentTypesMapper.updateDocumentTypes(documentTypes);
    }

    /**
     * 批量删除文档类型
     * 
     * @param documentTypeIds 需要删除的文档类型主键
     * @return 结果
     */
    @Override
    public int deleteDocumentTypesByDocumentTypeIds(Long[] documentTypeIds)
    {
        return documentTypesMapper.deleteDocumentTypesByDocumentTypeIds(documentTypeIds);
    }

    /**
     * 删除文档类型信息
     * 
     * @param documentTypeId 文档类型主键
     * @return 结果
     */
    @Override
    public int deleteDocumentTypesByDocumentTypeId(Long documentTypeId)
    {
        return documentTypesMapper.deleteDocumentTypesByDocumentTypeId(documentTypeId);
    }
}
