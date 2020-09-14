package com.dlq.mybatis.dao;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.Properties;

/**
 *@program: MyBatis
 *@description:
 *@author: Hasee
 *@create: 2020-09-14 13:17
 */
//完成插件签名：告诉MyBatis当前插件用来拦截哪个对象的哪个方法
@Intercepts({
        @Signature(type = StatementHandler.class,method = "parameterize",args = java.sql.Statement.class)
})
public class MyFirstPlugin implements Interceptor {

    /**
     * intercept：拦截
     *            拦截目标对象的目标方法的执行：
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MyFirstPlugin...intercept："+invocation.getMethod());
        //修改sql运行参数
        Object target = invocation.getTarget();
        System.out.println("以前拦截到的对象："+target);
        //拿到：StatementHandler ===> ParameterHandler ==> parameterObject
        //拿到target的元数据
        MetaObject metaObject = SystemMetaObject.forObject(target);
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        System.out.println("sql语句用的参数是："+value);
        metaObject.setValue("parameterHandler.parameterObject",4);
        //修改完sql语句要用的参数
        //执行目标方法
        Object proceed = invocation.proceed();
        //返回执行后的返回值
        return proceed;
    }

    /**
     * plugin：
     *      包装目标对象的：包装；为目标对象创建一个代理对象
     */
    @Override
    public Object plugin(Object target) {
        System.out.println("MyFirstPlugin...plugin：mybatis将要包装的对象"+target);
        //我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
        Object wrap = Plugin.wrap(target, this);
        //返回为当前target创建的动态代理
        return wrap;
    }

    /**
     * setProperties：
     *              将插件注册时的property属性设置进来
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("插件配置的信息："+properties);
    }
}
