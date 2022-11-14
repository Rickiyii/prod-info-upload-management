package org.flowwork.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ricky Zhang
 * @date 2022/11/11 18:44
 */
@Getter
@Setter
public class PageRequest<T> {
    private int page;
    private int size;
    private T queryParam;
}
