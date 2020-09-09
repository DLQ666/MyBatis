package com.dlq.mybatis.dao;

import com.dlq.mybatis.bean.Department;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-09 17:37
 */
public interface DepartmentMapper {

    public Department getDeptById(Integer id);

    public Department getDeptByIdPlus(Integer id);

    public Department getDeptByIdStep(Integer id);

}

