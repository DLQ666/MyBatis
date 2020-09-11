package com.dlq.myssm.controller;

import com.dlq.myssm.bean.Employee;
import com.dlq.myssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-11 19:42
 */
@Controller
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/emps")
    public String emps(Map<String,Object> map){
        List<Employee> emps = employeeService.getEmps();
        map.put("allEmps",emps);
        return "list";
    }
}
