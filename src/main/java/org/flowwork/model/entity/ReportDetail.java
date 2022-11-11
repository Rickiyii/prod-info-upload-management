package org.flowwork.model.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("report_detail")
public class ReportDetail implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField(value = "reportId")
    private Integer reportId;

    @TableField("sn")
    private String snNumber;

    @TableField("detail")
    private String detail;

    @TableField("createTime")
    private Date createTime;

    @TableField("updateTime")
    private Date updateTime;
}
