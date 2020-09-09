package com.dlq.mybatis.test;


import com.dlq.mybatis.bean.Department;
import com.dlq.mybatis.bean.Employee;
import com.dlq.mybatis.dao.DepartmentMapper;
import com.dlq.mybatis.dao.EmployeeMapper;
import com.dlq.mybatis.dao.EmployeeMapperAnnotation;
import com.dlq.mybatis.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1、接口式编程
 * 	原生：		Dao		====>  DaoImpl
 * 	mybatis：	Mapper	====>  xxMapper.xml
 *
 * 2、SqlSession代表和数据库的一次会话；用完必须关闭；
 * 3、SqlSession和connection一样她都是非线程安全。每次使用都应该去获取新的对象。
 * 4、mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 * 		（将接口和xml进行绑定）
 * 		EmployeeMapper empMapper =	sqlSession.getMapper(EmployeeMapper.class);
 * 5、两个重要的配置文件：
 * 		mybatis的全局配置文件：包含数据库连接池信息，事务管理器信息等...系统运行环境信息
 * 		sql映射文件：保存了每一个sql语句的映射信息：
 * 					将sql抽取出来。
 */
public class MyBatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory;
    }

    /**
     * 1、根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象 有数据源一些运行环境信息
     * 2、sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3、将sql映射文件注册在全局配置文件中
     * 4、写代码：
     * 		1）、根据全局配置文件得到SqlSessionFactory；
     * 		2）、使用sqlSession工厂，获取到sqlSession对象使用他来执行增删改查
     * 			一个sqlSession就是代表和数据库的一次会话，用完关闭
     * 		3）、使用sql的唯一标志来告诉MyBatis执行哪个sql。sql都是保存在sql映射文件中的。
     */
    @Test
    public void testMyBatis1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2、获取sqlSession实例，能直接执行已经映射的sql语句
        //sql的唯一标识： statement Unique identifier matching the statement to use.
        //执行sql要用的参数： parameter A parameter object to pass to the statement.
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Employee employee = openSession.selectOne("com.dlq.mybatis.dao.EmployeeMapper.getEmpById", 1);
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testMyBatis2() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            //3、获取接口实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmpById(2);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    @Test
    public void testMyBatis3() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            //3、获取接口实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
            Employee employee = mapper.getEmpById(2);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        } finally {
            openSession.close();
        }
    }

    //测试增删改
    @Test
    public void testCRUD() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取到的sqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            //3、获取接口实现类对象
            //会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            //添加
            Employee employee = new Employee();
            employee.setLastName("技术部");
            employee.setEmail("123123");
            Long result = mapper.addEmp(employee);
            System.out.println(result);
            System.out.println(employee.getId());

            //修改
            /*employee.setId(3);
            employee.setLastName("扎克伯格");
            employee.setEmail("boge@qq.com");
            employee.setGender("1");
            mapper.updateEmp(employee);*/

            //删除
            /*Boolean delResult = mapper.deleteEmpById(6);
            System.out.println(delResult);*/

            //手动提交数据
            /*openSession.commit();*/
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void testSelect4() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取到的sqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            //3、获取接口实现建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Employee tom = mapper.getEmpByIdAndLastName(1, "Tom");
            System.out.println(tom);
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void testMapSelect5() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取到的sqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            //3、获取接口实现建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<String,Object> map = new HashMap<>();
            map.put("id",1);
            map.put("lastName","Tom");
            map.put("tableName","tbl_employee");
            Employee employee = mapper.getEmpByMap(map);
            System.out.println(employee);
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void testListLike6() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取到的sqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            //3、获取接口实现建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            List<Employee> list = mapper.getEmpByLastNameLike("%码%");
            for (Employee employee : list) {
                System.out.println(employee);
            }
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void testReturnMap() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取到的sqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            //3、获取接口实现建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<String, Object> returnMap = mapper.getEmpByIdReturnMap(8);
            System.out.println(returnMap);
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void testReturnMap2() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取到的sqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            //3、获取接口实现建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<String, Object> returnMap = mapper.getEmpByIdReturnMap(8);
            System.out.println(returnMap);
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void testReturnLikeMap() throws IOException {
        //1、获取sqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2、获取到的sqlSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession(true);
        try {
            //3、获取接口实现建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
            Map<Integer, Employee> employeeMap = mapper.getEmpByLastNameLikeReturnMap("%码%");
            System.out.println(employeeMap);
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
            Employee empById = mapper.getEmpById(1);
            System.out.println(empById);
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
            Employee empAndDept = mapper.getEmpAndDept(1);
            System.out.println(empAndDept);
            System.out.println(empAndDept.getDept());
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
            Employee empByIdStep = mapper.getEmpByIdStep(1);
            System.out.println(empByIdStep);
            System.out.println(empByIdStep.getDept());
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);
            Employee empByIdStep = mapper.getEmpByIdStep(4);
            System.out.println(empByIdStep);
            System.out.println(empByIdStep.getDept());
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void testOneToMore() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
            Department department = mapper.getDeptByIdPlus(1);
            System.out.println(department);
            System.out.println(department.getEmps());
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

    @Test
    public void testOneToMoreByStep() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
            Department department = mapper.getDeptByIdStep(1);
            System.out.println(department);
            System.out.println(department.getEmps());
        } finally {
            if (openSession != null){
                openSession.close();
            }
        }
    }

}
