package org.flowwork.service.impl;

import com.alibaba.excel.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.flowwork.configs.ReportProperties;
import org.flowwork.configs.model.DevicePattern;
import org.flowwork.configs.model.ItemGroupPattern;
import org.flowwork.configs.model.PagePattern;
import org.flowwork.model.entity.ReportItem;
import org.flowwork.service.ReportPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
@Slf4j
public class ReportPatternImpl implements ReportPattern {

    @Autowired
    ReportProperties properties;

    private Set<String> pageSet = null;

    private Map<String, Set<String>> pageGroupMap = null;

    private Map<String, Set<String>> groupItemMap = null;

    private Map<String, Set<String>> pageItemMap = null;

    @Override
    public Set<String> getPageSet() {
        if (pageSet == null) {
            List<PagePattern> tPages = properties.getPages();
            pageSet = tPages.stream().map(PagePattern::getScope).collect(Collectors.toSet());
        }
        return pageSet;
    }

    @Override
    public Map<String, Set<String>> getPageGroupMap() {
        if (pageGroupMap == null) {
            pageGroupMap = new HashMap<>();
            List<PagePattern> tPages = properties.getPages();
            Set<String> groupSet;
            for (PagePattern page : tPages) {
                groupSet = new HashSet<>();
                String scope = page.getScope();
                if (StringUtils.isEmpty(scope)) {
                    continue;
                }
                List<DevicePattern> devices = page.getDevices();
                // 整个范围所有数据，直接返回
                if (devices == null || devices.isEmpty()) {
                    continue;
                }
                for (DevicePattern device : devices) {
                    List<ItemGroupPattern> itemGroups = device.getItemGroups();
                    if (itemGroups == null || itemGroups.isEmpty()) {
                        continue;
                    }
                    for (ItemGroupPattern itemGroup : itemGroups) {
                        String groupName = itemGroup.getGroupName();
                        if (StringUtils.isEmpty(groupName)) {
                            continue;
                        }
                        groupSet.add(itemGroup.getGroupName());
                    }
                }
                if (groupSet.isEmpty()) {
                    continue;
                }
                pageGroupMap.put(scope, groupSet);
            }
        }
        return pageGroupMap;
    }

    @Override
    public Map<String, Set<String>> getPageItemMap() {
        if (pageItemMap == null) {
            pageItemMap = new HashMap<>();
            List<PagePattern> tPages = properties.getPages();
            Set<String> itemSet;
            for (PagePattern page : tPages) {
                itemSet = new HashSet<>();
                String scope = page.getScope();
                List<DevicePattern> devices = page.getDevices();
                // 整个范围所有数据，直接返回
                if (devices == null || devices.isEmpty()) {
                    continue;
                }
                for (DevicePattern device : devices) {
                    List<ItemGroupPattern> itemGroups = device.getItemGroups();
                    if (itemGroups == null || itemGroups.isEmpty()) {
                        continue;
                    }
                    for (ItemGroupPattern itemGroup : itemGroups) {
                        String groupName = itemGroup.getGroupName();
                        if (!StringUtils.isEmpty(groupName)) {
                            continue;
                        }
                        itemSet.addAll(itemGroup.getItemNames());
                    }
                }
                if (itemSet.isEmpty()) {
                    continue;
                }
                pageItemMap.put(scope, itemSet);
            }
        }
        return pageItemMap;
    }

    @Override
    public Map<String, Set<String>> getGroupItemMap() {
        if (groupItemMap == null) {
            groupItemMap = new HashMap<>();
            List<PagePattern> tPages = properties.getPages();
            for (PagePattern page : tPages) {
                List<DevicePattern> devices = page.getDevices();
                // 整个范围所有数据，直接返回
                if (devices == null || devices.isEmpty()) {
                    continue;
                }
                for (DevicePattern device : devices) {
                    List<ItemGroupPattern> itemGroups = device.getItemGroups();
                    if (itemGroups == null || itemGroups.isEmpty()) {
                        continue;
                    }
                    for (ItemGroupPattern itemGroup : itemGroups) {
                        String groupName = itemGroup.getGroupName();
                        if (StringUtils.isEmpty(groupName)) {
                            continue;
                        }
                        Set<String> itemNames = itemGroup.getItemNames();
                        if (itemNames == null || itemNames.isEmpty()) {
                            continue;
                        }
                        groupItemMap.put(groupName, itemNames);
                    }
                }
            }
        }
        return groupItemMap;
    }

    @Override
    public boolean match(ReportItem row) {
        String scope = row.getScope();
        String deviceName = row.getDeviceName();
        String groupName = row.getGroupName();
        String itemName = row.getItemName();

        Set<String> pages = getPageSet();

        // 不在设备范围内，不匹配
        if (!pages.contains(scope)) {
            return false;
        }

        Map<String, Set<String>> pageGroupMap = getPageGroupMap();
        // 在设备范围内，但是没找到设备信息组信息，匹配，表示全部都要
        if (!pageGroupMap.containsKey(scope)) {
            Map<String, Set<String>> pageItemMap = getPageItemMap();
            // 在设备范围内，但是没找到直属设备信息组信息，匹配，表示全部都要
            if (!pageItemMap.containsKey(scope)) {
                return true;
            }
            Set<String> items = pageItemMap.get(scope);
            return items.contains(itemName);
        }
        Set<String> groups = pageGroupMap.get(scope);
        // 在设备范围内，但是不在设备信息组，不匹配，表示非需要的信息组
        if (!groups.contains(groupName)) {
            return false;
        }
        Map<String, Set<String>> groupItemMap = getGroupItemMap();
        // 在设备范围内，在设备信息组内，但是没找到设备详情属性，匹配，表示全部都要
        if (!groupItemMap.containsKey(groupName)) {
            return true;
        }
        Set<String> items = groupItemMap.get(groupName);
        // 在设备范围内，在设备信息组内，有设备详情属性，匹配与否看是否在详情集合中
        return items.contains(itemName);
    }
}
