package com.dlq.myssm.dao;


import com.dlq.myssm.bean.Employee;

import java.util.List;

/**
 *@description:
 *@author: Hasee
 *@create: 2020-09-06 16:51
 */
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public List<Employee> getEmpsAll();

    public Long addEmp(Employee employee);

    public void updateEmp(Employee employee);

    public Boolean deleteEmpById(Integer id);

}
