package com.dlq.mybatis.dao;


import com.dlq.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 *@description:
 *@author: Hasee
 *@create: 2020-09-06 16:51
 */
public interface EmployeeMapper {

    public Employee getEmpByMap(Map<String,Object> map);

    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    public Employee getEmpById(Integer id);

    public Long addEmp(Employee employee);

    public void updateEmp(Employee employee);

    public Boolean deleteEmpById(Integer id);

}
