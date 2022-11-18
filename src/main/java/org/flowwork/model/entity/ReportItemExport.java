package org.flowwork.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReportItemExport implements Serializable {

    @ExcelProperty(value = "设备类型")
    private String scope;

    @ExcelProperty("设备名称")
    private String deviceName;

    @ExcelProperty("设备属性组")
    private String groupName;

    @ExcelProperty("设备属性序号")
    private int itemId;

    @ExcelProperty(value = "设备属性名称")
    private String itemName;

    @ExcelProperty("设备详情")
    private String itemValue;
}
