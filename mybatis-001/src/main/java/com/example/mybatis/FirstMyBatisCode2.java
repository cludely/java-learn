package com.example.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class FirstMyBatisCode2 {
    public static void main(String[] args) throws IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

        // try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis-config.xml")) {
        //     SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
        //
        //     try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
        //         int count = sqlSession.insert("insertUser");
        //         System.out.println(count == 1 ? "保存成功" : "保存失败");
        //         sqlSession.commit();
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        try (InputStream in = Resources.getResourceAsStream("mybatis-config.xml")) {
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);

            try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
                int count = sqlSession.insert("insertUser");
                System.out.println(count == 1 ? "保存成功" : "保存失败");
                sqlSession.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
