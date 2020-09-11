package com.dlq.myssm.bean;

import java.io.Serializable;
import java.util.List;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-09 15:43
 */
public class Department implements Serializable {

    private static final long serialVersionUID = 8948990075413977714L;
    private Integer id;
    private String departmentName;
    private List<Employee> emps;

    public Department() {
    }

    public Department(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }

    public List<Employee> getEmps() {
        return emps;
    }

    public void setEmps(List<Employee> emps) {
        this.emps = emps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
