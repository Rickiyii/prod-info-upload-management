package org.flowwork.configs.model;

import lombok.Data;

import java.util.List;

@Data
public class PagePattern {
    private String scope;
    private List<DevicePattern> devices;
}
