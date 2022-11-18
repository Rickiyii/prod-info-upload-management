package org.flowwork.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.controller.dto.*;
import org.flowwork.exception.MessageKeys;
import org.flowwork.exception.ServiceWaringException;
import org.flowwork.model.entity.Report;
import org.flowwork.model.entity.ReportDetail;
import org.flowwork.model.entity.ReportItem;
import org.flowwork.model.entity.ReportItemExport;
import org.flowwork.service.ReportService;
import org.flowwork.util.ReportExcelListener;
import org.flowwork.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

@RestController
@RequestMapping(value = "/prod/report")
@Slf4j
public class ReportController extends BaseController {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    ReportService reportService;

    @PostMapping("/upload/{snNumber}")
    public ResponseWrapper<Boolean> upload(@PathVariable("snNumber") String snNumber, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ServiceWaringException(MessageKeys.FILE_EMPTY);
        }
        try {
            log.info("report of sn: {} start uploading", snNumber);
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ReportItem.class, new ReportExcelListener(snNumber, applicationContext))
                    .excelType(ExcelTypeEnum.CSV).charset(Charset.forName("GBK"))
                    .sheet().doRead();
            log.info("report of sn: {} upload success", snNumber);
        } catch (Exception e) {
            log.error("upload failed: {}", e.getMessage());
            throw new ServiceWaringException(MessageKeys.UPLOAD_FAILED, e.getMessage());
        }
        return new ResponseWrapper<>(true);
    }

    @PostMapping("/list")
    public ResponseWrapper<PageDto<Report>> list(@RequestBody PageRequest<ReportDto> pageRequest) {
        PageDto<Report> page = reportService.findByPage(pageRequest);
        return new ResponseWrapper<>(page);
    }

    @PostMapping("/detail")
    public ResponseWrapper<ReportDetail> getDetail(@RequestBody ReportDetailRequest request) {
        ReportDetail reportDetail = reportService.getReportDetail(request.getSnNumber());
        return new ResponseWrapper<>(reportDetail);
    }

    @PostMapping("/detail/list/{snNumber}")
    public ResponseWrapper<ReportItemInfo> getDetailedList(@PathVariable("snNumber") String snNumber, @RequestBody ReportDetailRequest request) {
        request.setSnNumber(snNumber);
        ReportItemInfo itemInfo = reportService.getReportDetailList(request);
        return new ResponseWrapper<>(itemInfo);
    }

    @PostMapping("/download/{snNumber}")
    public void download(@PathVariable("snNumber") String snNumber, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode(snNumber, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        ReportDetail reportDetail = reportService.getReportDetail(snNumber);
        String detailStr = reportDetail.getDetail();
        List<ReportItemExport> reportItems = JSON.parseArray(detailStr, ReportItemExport.class);
        EasyExcel.write(response.getOutputStream(), ReportItemExport.class).sheet(snNumber).doWrite(reportItems);
    }
}
