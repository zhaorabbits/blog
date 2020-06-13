package com.zl.service;

import com.zl.mapper.UserMapper;
import com.zl.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User checkUser(String username, String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User result = userMapper.selectOne(user);
        return result;
    }

    public User getUser(Long id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
