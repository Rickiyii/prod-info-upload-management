package org.flowwork.model.dto;

import lombok.Data;

/**
 * @author Ricky Zhang
 * @date 2023/2/10 16:57
 */
@Data
public class LoginResult {
    private LOGIN_STATUS status;
    private String type;
    private String currentAuthority;
}
