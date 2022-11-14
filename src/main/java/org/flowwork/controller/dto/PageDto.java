package org.flowwork.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Ricky Zhang
 * @date 2022/11/14 14:30
 */
@Getter
@Setter
public class PageDto<T> {
    private long page;
    private long total;
    private List<T> data;
}
