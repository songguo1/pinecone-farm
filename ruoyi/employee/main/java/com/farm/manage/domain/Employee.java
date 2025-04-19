package com.farm.manage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.farm.common.annotation.Excel;
import com.farm.common.core.domain.BaseEntity;

/**
 * 员工管理对象 agri_employee
 * 
 * @author beson
 * @date 2025-04-14
 */
public class Employee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 雇员ID */
    private Long employeeId;

    /** 编码 */
    @Excel(name = "编码")
    private String employeeCode;

    /** 姓名 */
    @Excel(name = "姓名")
    private String employeeName;

    /** 员工类型 */
    @Excel(name = "员工类型")
    private String employeeType;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String employeeTel;

    /** 性别 */
    @Excel(name = "性别")
    private String employeeSex;

    /** 地址 */
    @Excel(name = "地址")
    private String employeeAddress;

    /** 状态 */
    private String status;

    /** 排序 */
    private Long orderNum;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setEmployeeId(Long employeeId) 
    {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() 
    {
        return employeeId;
    }

    public void setEmployeeCode(String employeeCode) 
    {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeCode() 
    {
        return employeeCode;
    }

    public void setEmployeeName(String employeeName) 
    {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() 
    {
        return employeeName;
    }

    public void setEmployeeType(String employeeType) 
    {
        this.employeeType = employeeType;
    }

    public String getEmployeeType() 
    {
        return employeeType;
    }

    public void setEmployeeTel(String employeeTel) 
    {
        this.employeeTel = employeeTel;
    }

    public String getEmployeeTel() 
    {
        return employeeTel;
    }

    public void setEmployeeSex(String employeeSex) 
    {
        this.employeeSex = employeeSex;
    }

    public String getEmployeeSex() 
    {
        return employeeSex;
    }

    public void setEmployeeAddress(String employeeAddress) 
    {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeeAddress() 
    {
        return employeeAddress;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setOrderNum(Long orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() 
    {
        return orderNum;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("employeeId", getEmployeeId())
            .append("employeeCode", getEmployeeCode())
            .append("employeeName", getEmployeeName())
            .append("employeeType", getEmployeeType())
            .append("employeeTel", getEmployeeTel())
            .append("employeeSex", getEmployeeSex())
            .append("employeeAddress", getEmployeeAddress())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("orderNum", getOrderNum())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
