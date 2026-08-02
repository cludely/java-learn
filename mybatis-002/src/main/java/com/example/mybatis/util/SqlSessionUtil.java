package com.example.mybatis.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * MyBatis 工具类
 */
public class SqlSessionUtil {
    private static SqlSessionFactory sqlSessionFactory;

    private SqlSessionUtil() {}

    private static ThreadLocal<SqlSession> sqlSessionThreadLocal = new ThreadLocal<>();

    // 静态代码块：类加载时执行，创建 SqlSessionFactory 对象
    // 因为整个项目只需要1个SqlSessionFactory对象
    static {
        try(InputStream in = Resources.getResourceAsStream("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 SqlSession 对象
     * @return 返回一个 SqlSession 对象
     */
    public static SqlSession openSession() {
        // 一个线程绑定一个 SqlSession 对象
        SqlSession sqlSession = sqlSessionThreadLocal.get();
        if(sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
            sqlSessionThreadLocal.set(sqlSession);
        }
        return sqlSession;
    }

    // 解绑
    public static void closeSession() {
        sqlSessionThreadLocal.remove();
    }
}
