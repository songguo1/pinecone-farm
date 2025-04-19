package com.farm.manage.service.impl;

import java.util.List;
import com.farm.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.farm.manage.mapper.EmployeeMapper;
import com.farm.manage.domain.Employee;
import com.farm.manage.service.IEmployeeService;

/**
 * 员工管理Service业务层处理
 * 
 * @author beson
 * @date 2025-04-14
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService 
{
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 查询员工管理
     * 
     * @param employeeId 员工管理主键
     * @return 员工管理
     */
    @Override
    public Employee selectEmployeeByEmployeeId(Long employeeId)
    {
        return employeeMapper.selectEmployeeByEmployeeId(employeeId);
    }

    /**
     * 查询员工管理列表
     * 
     * @param employee 员工管理
     * @return 员工管理
     */
    @Override
    public List<Employee> selectEmployeeList(Employee employee)
    {
        return employeeMapper.selectEmployeeList(employee);
    }

    /**
     * 新增员工管理
     * 
     * @param employee 员工管理
     * @return 结果
     */
    @Override
    public int insertEmployee(Employee employee)
    {
        employee.setCreateTime(DateUtils.getNowDate());
        return employeeMapper.insertEmployee(employee);
    }

    /**
     * 修改员工管理
     * 
     * @param employee 员工管理
     * @return 结果
     */
    @Override
    public int updateEmployee(Employee employee)
    {
        employee.setUpdateTime(DateUtils.getNowDate());
        return employeeMapper.updateEmployee(employee);
    }

    /**
     * 批量删除员工管理
     * 
     * @param employeeIds 需要删除的员工管理主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeByEmployeeIds(Long[] employeeIds)
    {
        return employeeMapper.deleteEmployeeByEmployeeIds(employeeIds);
    }

    /**
     * 删除员工管理信息
     * 
     * @param employeeId 员工管理主键
     * @return 结果
     */
    @Override
    public int deleteEmployeeByEmployeeId(Long employeeId)
    {
        return employeeMapper.deleteEmployeeByEmployeeId(employeeId);
    }
}
