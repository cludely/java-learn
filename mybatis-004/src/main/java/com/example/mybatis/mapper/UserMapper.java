package com.example.mybatis.mapper;

import com.example.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * 多条件查询：用户可以根据多个条件查询
     * @Param 映射 mapper 文件中 sql 的参数
     */
    public List<User> selectByCondition(@Param("name") String name, @Param("age") int age, @Param("sex") String Sex);
    public List<User> selectByCondition2(@Param("name") String name, @Param("age") int age, @Param("sex") String Sex);
}
