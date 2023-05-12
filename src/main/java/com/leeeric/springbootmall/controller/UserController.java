package com.leeeric.springbootmall.controller;

import com.leeeric.springbootmall.dto.UserLoginRequest;
import com.leeeric.springbootmall.dto.UserRegisterRequest;
import com.leeeric.springbootmall.model.User;
import com.leeeric.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        Integer userId = userService.register(userRegisterRequest);
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    //基於帳號密碼屬於隱私資訊，所以要使用POST方法進行登入
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        User user = userService.userLogin(userLoginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
