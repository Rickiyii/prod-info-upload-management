package org.flowwork.configs.model;

import lombok.Data;

import java.util.Set;

@Data
public class ItemGroupPattern {
    private String groupName;
    private Set<String> itemNames;
}
