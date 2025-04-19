package com.farm.manage.service;

import java.util.List;
import com.farm.manage.domain.Employee;

/**
 * 员工管理Service接口
 * 
 * @author beson
 * @date 2025-04-14
 */
public interface IEmployeeService 
{
    /**
     * 查询员工管理
     * 
     * @param employeeId 员工管理主键
     * @return 员工管理
     */
    public Employee selectEmployeeByEmployeeId(Long employeeId);

    /**
     * 查询员工管理列表
     * 
     * @param employee 员工管理
     * @return 员工管理集合
     */
    public List<Employee> selectEmployeeList(Employee employee);

    /**
     * 新增员工管理
     * 
     * @param employee 员工管理
     * @return 结果
     */
    public int insertEmployee(Employee employee);

    /**
     * 修改员工管理
     * 
     * @param employee 员工管理
     * @return 结果
     */
    public int updateEmployee(Employee employee);

    /**
     * 批量删除员工管理
     * 
     * @param employeeIds 需要删除的员工管理主键集合
     * @return 结果
     */
    public int deleteEmployeeByEmployeeIds(Long[] employeeIds);

    /**
     * 删除员工管理信息
     * 
     * @param employeeId 员工管理主键
     * @return 结果
     */
    public int deleteEmployeeByEmployeeId(Long employeeId);
}
