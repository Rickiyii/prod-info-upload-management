package org.flowwork.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.exception.MessageKeys;
import org.flowwork.exception.ServiceWaringException;
import org.flowwork.model.entity.ReportItem;
import org.flowwork.util.ReportExcelListener;
import org.flowwork.util.ResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.Charset;

@RestController
@RequestMapping(value = "/prod/mid")
@Slf4j
public class ReportUploadController extends BaseController {

    @Autowired
    ApplicationContext applicationContext;

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
}
