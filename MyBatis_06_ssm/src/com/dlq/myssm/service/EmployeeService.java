package com.dlq.myssm.service;

import com.dlq.myssm.bean.Employee;
import com.dlq.myssm.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-11 19:45
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    public List<Employee> getEmps(){
        return employeeMapper.getEmpsAll();
    }
}
