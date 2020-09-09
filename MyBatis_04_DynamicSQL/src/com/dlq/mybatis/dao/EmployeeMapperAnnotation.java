package com.dlq.mybatis.dao;

import com.dlq.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-07 16:12
 */
public interface EmployeeMapperAnnotation {

    @Select(" select * from tbl_employee where id = #{id} ")
    public Employee getEmpById(Integer id);
}
