package org.flowwork.service;

import org.flowwork.model.dto.LoginRequest;
import org.flowwork.model.entity.User;

/**
 * @author Ricky Zhang
 * @date 2023/2/10 17:56
 */
public interface UserService {
    User getUser(String userName);

    boolean validatePassword(LoginRequest request);
}
