package org.flowwork.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReportItem implements Serializable {

    private Integer id;

    private Integer reportId;

    @ExcelProperty(value = "Page")
    private String scope;

    @ExcelProperty("Device")
    private String deviceName;

    @ExcelProperty("Group")
    private String groupName;

    @ExcelProperty("ItemID")
    private int itemId;

    @ExcelProperty(value = "Item")
    private String itemName;

    @ExcelProperty("Value")
    private String itemValue;
}
