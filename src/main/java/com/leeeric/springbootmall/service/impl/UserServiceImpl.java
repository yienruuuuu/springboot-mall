package com.leeeric.springbootmall.service.impl;

import com.leeeric.springbootmall.dao.UserDao;
import com.leeeric.springbootmall.dto.UserRegisterRequest;
import com.leeeric.springbootmall.model.User;
import com.leeeric.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.creatUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
