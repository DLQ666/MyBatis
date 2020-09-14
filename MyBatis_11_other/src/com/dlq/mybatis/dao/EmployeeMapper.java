package com.dlq.mybatis.dao;

import com.dlq.mybatis.bean.Employee;
import com.dlq.mybatis.bean.OraclePage;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@description:
 *@author: Hasee
 *@create: 2020-09-06 16:51
 */
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public List<Employee> getEmpAll();

    public Long addEmp(Employee employee);

    public void getPageByProcedure(OraclePage page);

}
