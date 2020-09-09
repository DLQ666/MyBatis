package com.dlq.mybatis.dao;

import com.dlq.mybatis.bean.Employee;

import java.util.List;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-09 14:59
 */
public interface EmployeeMapperPlus {

    public Employee getEmpById(Integer id);

    public Employee getEmpAndDept(Integer id);

    public Employee getEmpByIdStep(Integer id);

    public List<Employee> getEmpByDeptId(Integer deptId);
}

