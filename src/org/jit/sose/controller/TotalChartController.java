package org.jit.sose.controller;

import org.jit.sose.mapper.ITotalChart;
import org.jit.sose.service.ITotalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunwujun on 2018/9/7 8:56
 */
@Controller
@RequestMapping("chart")
public class TotalChartController {

    @Autowired
    ITotalChart iTotalChart;

    @Autowired
    ITotalDataService iTotalDataService;

    @RequestMapping(value = "getClassifyPie.do")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Map<String,Object> getClassifyPie(){
        Map<String,Object> resultMap = new HashMap<>();
        String archiveCount = iTotalChart.getArchiveCount();
        resultMap.put("pieData",iTotalChart.getArchivePie());
        resultMap.put("archiveCount",archiveCount);
        return resultMap;
    }

    @RequestMapping(value = "getWeekTotal.do")
    @ResponseBody
    @CrossOrigin(origins = "*",maxAge = 3600)
    public Map<String,Object> getWeekTotal(){
        return iTotalDataService.getWeekData();
    }
}
