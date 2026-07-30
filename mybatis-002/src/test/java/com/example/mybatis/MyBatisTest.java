package com.example.mybatis;

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
        data.put("k1", "hjj");
        data.put("k2", 18);
        data.put("k3", "男");

        try(SqlSession sqlSession = SqlSessionUtil.openSession()) {

            int count = sqlSession.insert("insertUser", data);
            System.out.println(count == 1 ? "保存成功" : "保存失败");

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
