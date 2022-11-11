package org.flowwork.configs;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.configs.model.DevicePattern;
import org.flowwork.configs.model.PagePattern;
import org.flowwork.model.entity.ReportItem;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@ConfigurationProperties(prefix = "report-patterns")
@Data
public class ReportProperties {

    private List<PagePattern> pages;

    public List<PagePattern> getPages() {
        return this.pages;
    }
}
