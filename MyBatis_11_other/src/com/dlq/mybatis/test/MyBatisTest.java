package com.dlq.mybatis.test;


import com.dlq.mybatis.bean.EmpStatus;
import com.dlq.mybatis.bean.Employee;
import com.dlq.mybatis.bean.OraclePage;
import com.dlq.mybatis.dao.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import sun.font.LayoutPathImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;


public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    @Test
    public void test01() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            PageHelper.startPage(4, 2);
            List<Employee> list = mapper.getEmpAll();
            //传入连续显示多少页
            PageInfo page = new PageInfo(list, 5);
            for (Employee employee : list) {
                System.out.println(employee);
            }
            System.out.println("当前页码：" + page.getPageNum());
            System.out.println("总记录数：" + page.getTotal());
            System.out.println("每页记录数：" + page.getPageSize());
            System.out.println("总页码：" + page.getPages());
            System.out.println("是否第一页：" + page.isIsFirstPage());
            System.out.println("连续显示的页码：");
            int[] nums = page.getNavigatepageNums();
            for (int i = 0; i < nums.length; i++) {
                System.out.println(nums[i]);
            }
        } finally {
            if (openSession != null) {
                openSession.close();
            }
        }
    }

    @Test
    public void testBatch() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //可以执行批量操作的sqlSession
        SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        long start = System.currentTimeMillis();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            for (int i = 0; i < 10000; i++) {
                mapper.addEmp(new Employee(UUID.randomUUID().toString().substring(0,5),  "z"+UUID.randomUUID().toString().substring(0,1)+"@qq.com", "1"));
            }
            System.out.println("添加成功");
            openSession.commit();
            long end = System.currentTimeMillis();
            //批量：（预编译sql一次==>设置参数-->10000次==>执行（1次））Parameters: c4b72(String), ze@qq.com(String), 1(String)  (BaseJdbcLogger.java:137)
            //非批量：（预编译sql==设置参数==执行）==>执行10000次 执行时长：15239
            System.out.println("执行时长："+(end-start));
        } finally {
            if (openSession != null) {
                openSession.close();
            }
        }
    }

    /**
     * oracle分页：
     * 		借助rownum：行号；子查询；
     * 存储过程包装分页逻辑
     * @throws IOException
     */
    @Test
    public void testProcedure() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try{
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            OraclePage page = new OraclePage();
            page.setStart(1);
            page.setEnd(5);
            mapper.getPageByProcedure(page);

            System.out.println("总记录数："+page.getCount());
            System.out.println("查出的数据："+page.getDept().size());
            System.out.println("查出的数据：\n"+page.getDept());
        }finally {
            if (openSession!=null){
                openSession.close();
            }
        }
    }

    @Test
    public void testEnumUse(){
        EmpStatus login = EmpStatus.LOGIN;
        System.out.println("枚举的索引："+login.ordinal());
        System.out.println("枚举的名字："+login.name());

        System.out.println("枚举的状态码："+login.getCode());
        System.out.println("枚举的提示消息："+login.getMsg());
    }

    /**
     * 默认mybatis在处理枚举对象的时候保存的是枚举的名字：EnumTypeHandler
     * 改变使用：EnumOrdinalTypeHandler：
     * @throws IOException
     */
    @Test
    public void testEnum() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee("test_enum", "enum@qq.com", "1");
            mapper.addEmp(employee);
            System.out.println("保存成功"+employee.getId());
            openSession.commit();
        }finally {
            openSession.close();
        }
    }

    @Test
    public void testEnum2() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee empById = mapper.getEmpById(40022);
            System.out.println(empById);
        }finally {
            openSession.close();
        }
    }
}
