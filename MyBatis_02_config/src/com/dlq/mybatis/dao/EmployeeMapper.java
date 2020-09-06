package com.dlq.mybatis.dao;


import com.dlq.mybatis.bean.Employee;

/**
 *@description:
 *@author: Hasee
 *@create: 2020-09-06 16:51
 */
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

}
