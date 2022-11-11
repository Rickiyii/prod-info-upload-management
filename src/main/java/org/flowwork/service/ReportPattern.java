package org.flowwork.service;

import org.flowwork.model.entity.ReportItem;

import java.util.Map;
import java.util.Set;

public interface ReportPattern {
    Set<String> getPageSet();

    Map<String, Set<String>> getPageGroupMap();

    Map<String, Set<String>> getPageItemMap();

    Map<String, Set<String>> getGroupItemMap();

    boolean match(ReportItem row);
}
