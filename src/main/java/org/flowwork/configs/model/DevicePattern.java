package org.flowwork.configs.model;

import lombok.Data;

import java.util.List;

@Data
public class DevicePattern {
    private String deviceName;
    private List<ItemGroupPattern> itemGroups;
}
