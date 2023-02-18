package org.flowwork.controller;

import lombok.extern.slf4j.Slf4j;
import org.flowwork.model.dto.LOGIN_STATUS;
import org.flowwork.model.dto.LoginRequest;
import org.flowwork.model.dto.LoginResult;
import org.flowwork.service.UserService;
import org.flowwork.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricky Zhang
 * @date 2023/2/10 16:54
 */
@RestController
@RequestMapping(value = "/api/login")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/account")
    public ResponseWrapper<LoginResult> accountLogin(@RequestBody LoginRequest loginRequest) {
        boolean success = userService.validatePassword(loginRequest);
        String type = loginRequest.getType();
        String username = loginRequest.getUsername();
        LoginResult result = new LoginResult();

        result.setType(type);
        result.setCurrentAuthority(username);
        if (!success) {
            result.setStatus(LOGIN_STATUS.fail);
        } else {
            result.setStatus(LOGIN_STATUS.ok);
        }
        return new ResponseWrapper<>(result);
    }
}
