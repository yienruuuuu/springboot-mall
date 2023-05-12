package com.leeeric.springbootmall.dao;

import com.leeeric.springbootmall.dto.UserRegisterRequest;
import com.leeeric.springbootmall.model.User;

public interface UserDao {
    Integer creatUser(UserRegisterRequest userRegisterRequest);
    User getUserById(Integer userId);
    User getUserByEmail(String email);
}
