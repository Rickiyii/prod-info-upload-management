package org.flowwork.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.controller.dto.PageRequest;
import org.flowwork.controller.dto.ReportDto;
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
            Report report = extractBriefReport(snNumber, cachedDataList);
            Integer reportId = upsert(report);

            ReportDetail reportDetail = new ReportDetail();
            reportDetail.setReportId(reportId);
            reportDetail.setSnNumber(snNumber);
            reportDetail.setDetail(JSON.toJSONString(cachedDataList));

            upsertDetail(reportDetail);

            log.info("报告 {} 数据已保存，共{}条", snNumber, cachedDataList.size());
        } catch (Exception e) {
            log.error("报告 {} 保存出错", snNumber);
            e.printStackTrace();
        } finally {
            log.info("报告记录：{} 已保存", snNumber);
        }
    }

    private Report extractBriefReport(String snNumber, List<ReportItem> cachedDataList) {
        Report report = new Report();
        StringBuilder ataSns = new StringBuilder();
        StringBuilder macs = new StringBuilder();
        for (ReportItem item : cachedDataList) {
            if ("ATA".equals(item.getScope()) && "序列号".equals(item.getItemName())) {
                if (ataSns.length() != 0) {
                    ataSns.append(",");
                }
                ataSns.append(item.getItemValue());
            }
            if ("Windows 网络".equals(item.getScope()) && "硬件地址(MAC)".equals(item.getItemName())) {
                if (!"00-00-00-00-00-00".equals(item.getItemValue())) {
                    if (macs.length() != 0) {
                        macs.append(",");
                    }
                    macs.append(item.getItemValue());
                }
            }
        }
        report.setCheckCode(UuidUtil.generate16());
        report.setSnNumber(snNumber);
        report.setAtaSns(ataSns.toString());
        report.setMacs(macs.toString());
        return report;
    }

    @Override
    public Report getReportBySnNumber(String snNumber) {
        QueryWrapper<Report> query = new QueryWrapper<>();
        query.eq("sn", snNumber);
        return reportMapper.selectOne(query);
    }

    @Override
    public void insert(Report report) {

    }

    @Override
    public Integer upsert(Report report) {
        Report exist = getReportBySnNumber(report.getSnNumber());
        Date now = new Date();
        if (exist == null) {
            report.setCreateTime(now);
            report.setUpdateTime(now);
            reportMapper.insert(report);
            report = getReportBySnNumber(report.getSnNumber());
        } else {
            exist.setAtaSns(report.getAtaSns());
            exist.setMacs(report.getMacs());
            exist.setUpdateTime(now);
            reportMapper.updateById(report);
            report = exist;
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

    @Override
    public Page<Report> findByPage(PageRequest<ReportDto> pageRequest) {
        Page<Report> page = new Page<>();
        page.setCurrent(pageRequest.getCurrent());
        page.setSize(pageRequest.getSize());
        ReportDto queryParam = pageRequest.getQueryParam();
        LambdaQueryWrapper<Report> query = new LambdaQueryWrapper<>();
        if (queryParam != null) {
            query.eq(Report::getReportId, queryParam.getReportId());
            query.eq(Report::getSnNumber, queryParam.getSnNumber());
            query.eq(Report::getCheckCode, queryParam.getCheckCode());
            query.in(Report::getCreateTime, queryParam.getCreateTimeStart(), queryParam.getCreateTimeEnd());
            query.in(Report::getUpdateTime, queryParam.getUpdateTimeStart(), queryParam.getUpdateTimeEnd());
        }
        return reportMapper.selectPage(page, query);
    }
}
