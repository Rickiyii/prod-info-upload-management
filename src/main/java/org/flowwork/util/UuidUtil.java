package org.flowwork.util;

import java.util.UUID;

public class UuidUtil {
    public static String generate16() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }
}
