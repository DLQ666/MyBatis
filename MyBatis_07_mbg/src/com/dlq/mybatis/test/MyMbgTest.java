package com.dlq.mybatis.test;

import com.dlq.mybatis.bean.Employee;
import com.dlq.mybatis.bean.EmployeeExample;
import com.dlq.mybatis.dao.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-11 21:37
 */
public class MyMbgTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    @Test
    public void test() throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    @Test
    public void testCRUD() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            //没有查询条件就是查询所有
            List<Employee> employees = mapper.selectByExample(null);
            for (Employee employee : employees) {
                System.out.println(employee.getId());
            }
        }finally {
            if (openSession!=null){
                openSession.close();
            }
        }
    }

    @Test
    public void testMyBatis3() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            //xxxExample就是封装查询条件的,
            // 1、没有查询条件就是查询所有
            //List<Employee> employees = mapper.selectByExample(null);

            // 2、查询员工名字中有t字母的，和员工性别为1的
            //封装员工查询条件的example
            EmployeeExample example = new EmployeeExample();
            //创建一个Criteria，这个Criteria就是拼装查询条件
            //select id, last_name, email, gender, d_id from tbl_employee
            //WHERE ( last_name like ? and gender = ? ) or email like "%t%"
            EmployeeExample.Criteria criteria = example.createCriteria();
            criteria.andLastNameLike("%t%");
            criteria.andGenderEqualTo("1");

            //or条件需要创建新的Criteria用EmployeeExample中的or方法连接
            //select id, last_name, email, gender, d_id from tbl_employee
            //WHERE ( last_name like ? and gender = ? ) or( email like ? )
            EmployeeExample.Criteria criteria2 = example.createCriteria();
            criteria2.andEmailLike("%t%");
            example.or(criteria2);

            List<Employee> list = mapper.selectByExample(example);
            for (Employee employee : list) {
                System.out.println(employee.getId());
            }
        }finally {
            if (openSession!=null){
                openSession.close();
            }
        }
    }
}
