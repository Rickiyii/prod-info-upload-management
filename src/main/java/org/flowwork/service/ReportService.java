package org.flowwork.service;

import org.flowwork.model.entity.Report;
import org.flowwork.model.entity.ReportItem;

import java.util.List;

public interface ReportService {
    void save(String snNumber, List<ReportItem> cachedDataList);

    Report getBySn(String snNumber);

    void insert(Report report);

    Integer upsert(Report report);
}
