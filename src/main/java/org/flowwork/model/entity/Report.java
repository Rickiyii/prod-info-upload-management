package org.flowwork.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
@TableName("report")
public class Report {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer reportId;
    @TableField(value = "sn")
    private String snNumber;
    @TableField(value = "checkCode")
    private String checkCode;

    @TableField("ataSns")
    private List<String> ataSns;

    @TableField("macs")
    private List<String> macs;

    @TableField(value = "createTime")
    private Date createTime;
    @TableField(value = "updateTime")
    private Date updateTime;
}
