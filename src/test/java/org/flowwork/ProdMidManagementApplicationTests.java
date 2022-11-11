package org.flowwork;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.configs.ReportProperties;
import org.flowwork.configs.model.PagePattern;
import org.flowwork.service.ReportPattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
@Slf4j
class ProdMidManagementApplicationTests {

    @Autowired
    ReportProperties properties;

    @Autowired
    ReportPattern reportPattern;

    @Test
    void contextLoads() {
    }


    @Test
    void loadProperties() {
        List<PagePattern> pages = properties.getPages();
        log.info(JSON.toJSONString(pages));
    }

    @Test
    void loadPattern() {
        Set<String> pageSet = reportPattern.getPageSet();
        log.info("pageSet: {}", JSON.toJSONString(pageSet));
        Map<String, Set<String>> pageGroupMap = reportPattern.getPageGroupMap();
        log.info("pageGroupMap: {}", JSON.toJSONString(pageGroupMap));
        Map<String, Set<String>> groupItemMap = reportPattern.getGroupItemMap();
        log.info("groupItemMap: {}", JSON.toJSONString(groupItemMap));
        Map<String, Set<String>> pageItemMap = reportPattern.getPageItemMap();
        log.info("pageItemMap: {}", JSON.toJSONString(pageItemMap));
    }
}
