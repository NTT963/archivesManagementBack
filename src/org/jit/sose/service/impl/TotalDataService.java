package org.jit.sose.service.impl;

import org.jit.sose.mapper.ITotalChart;
import org.jit.sose.service.ITotalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunwujun on 2018/9/8 13:29
 */
@Service
public class TotalDataService implements ITotalDataService {

    @Autowired
    ITotalChart iTotalChart;

    @Override
    public Map<String, Object> getWeekData() {
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String, Object>> LineGroup = iTotalChart.getLineGroudByDate();
        List<Map<String, Object>> weekExtraData = iTotalChart.getWeekEtraData();
        List<String> titles = iTotalChart.getBarTitle();
        String[] XData = new String[7];
        long[] YTotal = new long[7];
        int index = 0;
        for (Map<String, Object> item : LineGroup) {
            XData[index] = (String) item.get("data");
            YTotal[index] = (Long) item.get("total");
            index++;
        }
        boolean isFound = false;
        Map<String,Object> weekData = new HashMap<>();
        for (String title : titles) {
            long[] counts = new long[7];
            for (int i = 0; i < XData.length; i++) {
                System.out.println(title + " : " + XData[i]);
                for (Map<String, Object> item : weekExtraData) {
                    if (title.equals(item.get("title")) && XData[i].equals(item.get("data"))) {
                        isFound = true;
                        counts[i] = (long) item.get("count");
                        break;
                    }
                }
                if (!isFound) {
                    counts[i] = 0;
                }
            }
            isFound = false;
            weekData.put(title,counts);
        }
        String archiveCount = iTotalChart.getArchiveCount();
        resultMap.put("xData",XData);
        resultMap.put("legend",titles);
        resultMap.put("weekData",weekData);
        resultMap.put("total",YTotal);
        resultMap.put("archiveCount",archiveCount);
        return resultMap;
    }
}
