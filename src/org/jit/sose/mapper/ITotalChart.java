package org.jit.sose.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by sunwujun on 2018/9/7 8:05
 */
public interface ITotalChart {
    List<Map<String,Object>> getArchivePie();

    List<Map<String,Object>> getLineGroudByDate();

    List<Map<String,Object>> getWeekEtraData();

    List<String> getBarTitle();

    String getArchiveCount();
}
