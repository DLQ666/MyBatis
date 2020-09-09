package com.dlq.mybatis.dao;

import com.dlq.mybatis.bean.Employee;

import java.util.List;

/**
 *@description:
 *@author: Hasee
 *@create: 2020-09-09 22:19
 */
public interface EmployeeMapperDynamicSQL {

    //携带了哪个字段查询条件就带上这个字段的值
    public List<Employee> getEmpByConditionIf(Employee employee);

}
