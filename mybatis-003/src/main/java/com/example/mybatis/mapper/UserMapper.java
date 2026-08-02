package com.example.mybatis.mapper;

import com.example.mybatis.entity.User;

public interface UserMapper {
    public User selectUserById(Long id);
}
