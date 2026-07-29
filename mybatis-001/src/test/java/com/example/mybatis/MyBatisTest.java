package com.example.mybatis;

import com.example.mybatis.util.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class MyBatisTest {
    @Test
    public void testFirst() {
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

    @Test
    public void testSqlSeesionUtil() {
        try(SqlSession sqlSession = SqlSessionUtil.openSession()) {
            int count = sqlSession.insert("insertUser");
            System.out.println(count == 1 ? "保存成功" : "保存失败");
            sqlSession.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
