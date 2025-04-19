package com.farm.data.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 系统文档对象 agri_documents
 * 
 * @author beson
 * @date 2025-04-17
 */
public class Documents extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long documentId;

    /** 文档类型 */
    @Excel(name = "文档类型")
    private Long documentTypeId;

    /** 文档标题 */
    @Excel(name = "文档标题")
    private String title;

    /** 文档文件 */
    @Excel(name = "文档文件")
    private String filePath;

    public void setDocumentId(Long documentId) 
    {
        this.documentId = documentId;
    }

    public Long getDocumentId() 
    {
        return documentId;
    }

    public void setDocumentTypeId(Long documentTypeId) 
    {
        this.documentTypeId = documentTypeId;
    }

    public Long getDocumentTypeId() 
    {
        return documentTypeId;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("documentId", getDocumentId())
            .append("documentTypeId", getDocumentTypeId())
            .append("title", getTitle())
            .append("filePath", getFilePath())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
