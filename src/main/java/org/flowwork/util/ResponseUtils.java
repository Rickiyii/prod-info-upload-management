package org.flowwork.util;

import org.flowwork.exception.ServiceWaringException;

public class ResponseUtils {
    public static <T> T result(ResponseWrapper<T> response) {
        if (response.getCode() == 200||response.getCode()==204) {
            return response.getData();
        } else {
            throw new ServiceWaringException(response.getCode(), response.getMessage());
        }
    }
}
