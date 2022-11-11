package org.flowwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.mapper.ReportDetailMapper;
import org.flowwork.mapper.ReportMapper;
import org.flowwork.model.entity.Report;
import org.flowwork.model.entity.ReportDetail;
import org.flowwork.model.entity.ReportItem;
import org.flowwork.service.ReportService;
import org.flowwork.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportDetailMapper reportDetailMapper;

    @Autowired
    ReportMapper reportMapper;

    @Override
    public void save(String snNumber, List<ReportItem> cachedDataList) {

        try {
            // 插入报告数据
            Report report = extractReportBrief(snNumber, cachedDataList);
            Integer reportId = upsert(report);

            ReportDetail reportDetail = new ReportDetail();
            reportDetail.setReportId(reportId);
            reportDetail.setSnNumber(snNumber);
            reportDetail.setDetail(JSON.toJSONString(cachedDataList));
            QueryWrapper<Report> wrapper=new QueryWrapper<>();
            wrapper.eq("sn", snNumber);

            upsertDetail(reportDetail);

            log.info("报告 {} 数据已保存，共{}条", snNumber, cachedDataList.size());
        } catch (Exception e) {
            log.error("报告 {} 保存出错", snNumber);
            e.printStackTrace();
        } finally {
            log.info("报告记录：{} 已保存", snNumber);
        }
    }

    private Report extractReportBrief(String snNumber, List<ReportItem> cachedDataList) {
        Report report = new Report();
        report.setSnNumber(snNumber);
        for (ReportItem item : cachedDataList) {

        }
        return null;
    }

    @Override
    public Report getBySn(String snNumber) {
        QueryWrapper<Report> query = new QueryWrapper<>();
        query.eq("sn", snNumber);
        return reportMapper.selectOne(query);
    }

    @Override
    public void insert(Report report) {

    }

    @Override
    public Integer upsert(Report report) {
        Report exist = getBySn(report.getSnNumber());
        Date now = new Date();
        if (exist == null) {
            report = new Report();
            report.setCheckCode(UuidUtil.generate16());
            report.setCreateTime(now);
            report.setUpdateTime(now);
            reportMapper.insert(report);
            report = getBySn(report.getSnNumber());
        } else {
            exist.setAtaSns(report.getAtaSns());
            exist.setMacs(report.getMacs());
            exist.setUpdateTime(now);
            reportMapper.updateById(report);
        }
        return report.getReportId();
    }

    public void upsertDetail(ReportDetail reportDetail) {
        Integer reportId = reportDetail.getReportId();

        Date now = new Date();
        QueryWrapper<ReportDetail> query = new QueryWrapper<>();
        query.eq("reportId", reportId);
        ReportDetail exist = reportDetailMapper.selectOne(query);

        if (exist == null) {
            reportDetail.setCreateTime(now);
            reportDetail.setUpdateTime(now);
            reportDetailMapper.insert(reportDetail);
        } else {
            exist.setDetail(reportDetail.getDetail());
            exist.setUpdateTime(now);
            reportDetailMapper.updateById(exist);
        }
        log.info("报告：{} 详情已保存 ", reportDetail.getSnNumber());
    }
}
