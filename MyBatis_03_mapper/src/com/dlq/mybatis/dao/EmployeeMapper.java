package com.dlq.mybatis.dao;


import com.dlq.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *@description:
 *@author: Hasee
 *@create: 2020-09-06 16:51
 */
public interface EmployeeMapper {

    //多条记录封装一个map：Map<Integer,Employee>:键是这条记录的主键，值是记录封装后的javaBean
    //告诉mybatis封装这个map的时候用哪个属性作为主键
    @MapKey("id")
    public Map<Integer,Employee> getEmpByLastNameLikeReturnMap(String lastName);

    //返回一条记录的Map：key就是列名，值就是对应的值
    public Map<String,Object> getEmpByIdReturnMap(Integer id);

    public List<Employee> getEmpByLastNameLike(String lastName);

    public Employee getEmpByMap(Map<String,Object> map);

    public Employee getEmpByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    public Employee getEmpById(Integer id);

    public Long addEmp(Employee employee);

    public void updateEmp(Employee employee);

    public Boolean deleteEmpById(Integer id);

}
