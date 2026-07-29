package com.example.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class FirstMyBatisCode {
    public static void main(String[] args) {
    //     1. 创建 SqlSessionFactoryBuilder 对象
    //     SqlSessionFactoryBuilder 是一个建造器对象，用来创建 SqlSessionFactory 对象。
    //     SqlSession 对象相当于是和数据库的一次会话，用它来执行sql语句
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 对于 SqlSessionFactiory 对象来说，整个应用应保持只有一个
        // 2。 创建输入流，高输入流指向 mybatis-config.xml 配置文件
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = builder.build(in);
        // 3. 通过 SqlSessionFactory 对象来获取 SqlSeesion 对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4. 执行sql语句
        int count = sqlSession.insert("insertUser");

        if (count == 1) {
            System.out.println("保存成功");
        } else {
            System.out.println("保存失败");
        }
        sqlSession.commit();

        // 5. 关闭对象
        sqlSession.close();
    }
}
