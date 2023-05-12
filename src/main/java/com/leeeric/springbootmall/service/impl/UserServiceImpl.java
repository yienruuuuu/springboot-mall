package com.leeeric.springbootmall.service.impl;

import com.leeeric.springbootmall.dao.UserDao;
import com.leeeric.springbootmall.dto.UserLoginRequest;
import com.leeeric.springbootmall.dto.UserRegisterRequest;
import com.leeeric.springbootmall.model.User;
import com.leeeric.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //檢查註冊的email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user != null) {
            logger.warn("該email {} 已經被註冊 ", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //創建帳號
        return userDao.creatUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User userLogin(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if (user == null){
            logger.info("該 email: {} ,尚未註冊",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (user.getPassword().equals(userLoginRequest.getPassword())){
            return user;
        }else {
            logger.info("email: {} ,輸入密碼錯誤",userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);        }
    }
}
