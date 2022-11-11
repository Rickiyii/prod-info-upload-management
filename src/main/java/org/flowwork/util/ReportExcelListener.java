package org.flowwork.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.model.entity.ReportItem;
import org.flowwork.service.ReportPattern;
import org.flowwork.service.ReportService;
import org.springframework.context.ApplicationContext;

import java.util.*;

@Slf4j
public class ReportExcelListener implements ReadListener<ReportItem> {

    private final String snNumber;

    private final ReportService reportService;

    private final ReportPattern reportPattern;

    public ReportExcelListener(String snNumber, ApplicationContext applicationContext) {
        this.reportService = applicationContext.getBean(ReportService.class);
        this.reportPattern = applicationContext.getBean(ReportPattern.class);
        this.snNumber = snNumber;
    }

    /**
     *临时存储
     */
    private final List<ReportItem> cachedDataList = new ArrayList<>();

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        ReadListener.super.onException(exception, context);
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        ReadListener.super.invokeHead(headMap, context);
    }

    @Override
    public void invoke(ReportItem row, AnalysisContext analysisContext) {
        if (reportPattern.match(row)) {
            cachedDataList.add(row);
            log.info("matched data: {}", JSON.toJSONString(row));
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private synchronized void saveData() {
        log.info("matched size: {}", cachedDataList.size());
        reportService.save(snNumber, cachedDataList);
    }
}
