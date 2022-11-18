package org.flowwork.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Ricky Zhang
 * @date 2022/11/18 10:25
 */
@Data
public class MenuGroup {
    private String type;
    private String key;
    private String label;
    private List<MenuGroupChild> children;
}
