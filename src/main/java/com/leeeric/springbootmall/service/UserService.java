package com.leeeric.springbootmall.service;

import com.leeeric.springbootmall.dto.UserRegisterRequest;
import com.leeeric.springbootmall.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
