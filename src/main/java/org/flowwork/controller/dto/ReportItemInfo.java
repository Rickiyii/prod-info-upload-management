package org.flowwork.controller.dto;

import lombok.Data;
import org.flowwork.model.entity.ReportItem;

import java.util.List;

/**
 * @author Ricky Zhang
 * @date 2022/11/18 10:21
 */
@Data
public class ReportItemInfo {
    private List<MenuGroup> menuGroups;
    private List<ReportItem> details;
}
