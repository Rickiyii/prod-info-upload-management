package org.flowwork.model.dto;

import lombok.Data;

/**
 * @author Ricky Zhang
 * @date 2023/2/10 17:00
 */
@Data
public class LoginRequest {
    private boolean autoLogin;
    private String password;
    private String type;
    private String username;
}
