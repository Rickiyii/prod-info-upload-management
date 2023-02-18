package org.flowwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.mapper.UserMapper;
import org.flowwork.model.dto.LoginRequest;
import org.flowwork.model.entity.User;
import org.flowwork.service.UserService;
import org.flowwork.util.PasswordHashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ricky Zhang
 * @date 2023/2/10 17:56
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser(String userName) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean validatePassword(LoginRequest loginRequest) {
        User user = getUser(loginRequest.getUsername());
        if (user == null) {
            return false;
        }
        String password = user.getPassword();
        String plainPwd = loginRequest.getPassword();
        return PasswordHashing.validate(plainPwd, password);
    }

    private String encryptPwd(String plainPwd) {
        return plainPwd;
    }
}
