package com.dlq.mybatis.dao;

import com.dlq.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@description:
 *@author: Hasee
 *@create: 2020-09-09 22:19
 */
public interface EmployeeMapperDynamicSQL {

    //携带了哪个字段查询条件就带上这个字段的值
    public List<Employee> getEmpByConditionIf(Employee employee);

    public List<Employee> getEmpByConditionTrim(Employee employee);

    public List<Employee> getEmpByConditionChoose(Employee employee);

    public void updateEmp(Employee employee);

    //查询员工id'在给定集合中的
    public List<Employee> getEmpByConditionForeach(@Param("ids") List<Integer> ids);

    public List<Employee> getEmp_foreach(Map<String,Object> map);

    //批量插入
    public void addEmps(@Param("emps") List<Employee> emps);

    //_databaseId
    public List<Employee> getEmpsTestInnerParameter(Employee employee);
}
