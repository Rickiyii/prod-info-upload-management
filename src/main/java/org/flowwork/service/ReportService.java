package org.flowwork.service;

import org.flowwork.controller.dto.*;
import org.flowwork.model.entity.Report;
import org.flowwork.model.entity.ReportDetail;
import org.flowwork.model.entity.ReportItem;

import java.util.List;

public interface ReportService {
    void save(String snNumber, List<ReportItem> cachedDataList);

    Report getReportBySnNumber(String snNumber);

    void insert(Report report);

    Integer upsert(Report report);

    PageDto<Report> findByPage(PageRequest<ReportDto> pageRequest);

    ReportDetail getReportDetail(String snNumber);

    ReportItemInfo getReportDetailList(ReportDetailRequest request);
}
