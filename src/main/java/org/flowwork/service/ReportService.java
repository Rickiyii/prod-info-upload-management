package org.flowwork.service;

import com.baomidou.mybatisplus.core.metadata.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.flowwork.controller.dto.PageRequest;
import org.flowwork.controller.dto.ReportDto;
import org.flowwork.model.entity.Report;
import org.flowwork.model.entity.ReportItem;

import java.util.List;

public interface ReportService {
    void save(String snNumber, List<ReportItem> cachedDataList);

    Report getReportBySnNumber(String snNumber);

    void insert(Report report);

    Integer upsert(Report report);

    Page<Report> findByPage(PageRequest<ReportDto> pageRequest);
}
