package org.flowwork.controller.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Ricky Zhang
 * @date 2022/11/11 18:46
 */
@Getter
@Setter
public class ReportDto {
    private Integer reportId;
    private String snNumber;
    private String checkCode;
    private Date createTimeStart;
    private Date createTimeEnd;
    private Date updateTimeStart;
    private Date updateTimeEnd;
}
