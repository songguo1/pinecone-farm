package com.farm.data.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 文档类型对象 agri_document_types
 * 
 * @author beson
 * @date 2025-04-14
 */
public class DocumentTypes extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Excel(name = "ID")
    private Long documentTypeId;

    /** 文档类型名称 */
    @Excel(name = "文档类型名称")
    private String documentTypeName;

    public void setDocumentTypeId(Long documentTypeId) 
    {
        this.documentTypeId = documentTypeId;
    }

    public Long getDocumentTypeId() 
    {
        return documentTypeId;
    }

    public void setDocumentTypeName(String documentTypeName) 
    {
        this.documentTypeName = documentTypeName;
    }

    public String getDocumentTypeName() 
    {
        return documentTypeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("documentTypeId", getDocumentTypeId())
            .append("documentTypeName", getDocumentTypeName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .toString();
    }
}
