package com.farm.data.service;

import java.util.List;
import com.farm.data.domain.DocumentTypes;

/**
 * 文档类型Service接口
 * 
 * @author beson
 * @date 2025-04-14
 */
public interface IDocumentTypesService 
{
    /**
     * 查询文档类型
     * 
     * @param documentTypeId 文档类型主键
     * @return 文档类型
     */
    public DocumentTypes selectDocumentTypesByDocumentTypeId(Long documentTypeId);

    /**
     * 查询文档类型列表
     * 
     * @param documentTypes 文档类型
     * @return 文档类型集合
     */
    public List<DocumentTypes> selectDocumentTypesList(DocumentTypes documentTypes);

    /**
     * 新增文档类型
     * 
     * @param documentTypes 文档类型
     * @return 结果
     */
    public int insertDocumentTypes(DocumentTypes documentTypes);

    /**
     * 修改文档类型
     * 
     * @param documentTypes 文档类型
     * @return 结果
     */
    public int updateDocumentTypes(DocumentTypes documentTypes);

    /**
     * 批量删除文档类型
     * 
     * @param documentTypeIds 需要删除的文档类型主键集合
     * @return 结果
     */
    public int deleteDocumentTypesByDocumentTypeIds(Long[] documentTypeIds);

    /**
     * 删除文档类型信息
     * 
     * @param documentTypeId 文档类型主键
     * @return 结果
     */
    public int deleteDocumentTypesByDocumentTypeId(Long documentTypeId);
}
