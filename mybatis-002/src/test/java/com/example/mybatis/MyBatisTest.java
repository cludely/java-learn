package com.example.mybatis;

import com.example.mybatis.entity.User;
import com.example.mybatis.util.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MyBatisTest {
    @Test
    public void testInsertWithMap() {
        Map<String, Object> data = new HashMap<>();
        // k1\k2\k3 对应 UserMapper 中的 k1\k2\k3 占位符
        data.put("name", "hjj");
        data.put("age", 18);
        data.put("sex", "男");

        try(SqlSession sqlSession = SqlSessionUtil.openSession()) {

            int count = sqlSession.insert("insertUser", data);
            System.out.println(count == 1 ? "保存成功" : "保存失败");

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertWithEntity() {
    //     准备数据
        User user = new User();
        user.setAge(5);
        user.setName("张三");
        user.setSex("女");

        try(SqlSession sqlSession = SqlSessionUtil.openSession()) {

            int count = sqlSession.insert("insertUser2", user);

            System.out.println(count == 1 ? "保存成功" : "保存失败");

            sqlSession.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
