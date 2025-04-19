package com.farm.manage.mapper;

import java.util.List;
import com.farm.manage.domain.Employee;

/**
 * 员工管理Mapper接口
 * 
 * @author beson
 * @date 2025-04-14
 */
public interface EmployeeMapper 
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
     * 删除员工管理
     * 
     * @param employeeId 员工管理主键
     * @return 结果
     */
    public int deleteEmployeeByEmployeeId(Long employeeId);

    /**
     * 批量删除员工管理
     * 
     * @param employeeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEmployeeByEmployeeIds(Long[] employeeIds);
}
