package org.jit.sose.controller;

import java.util.List;

import org.jit.sose.entity.WXPagePic;
import org.jit.sose.service.WXPagePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Controller
public class WXPageInfoController {

    @Autowired
    private WXPagePicService WXPagePicService;

    @RequestMapping(value = "/getBannerPics.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONObject getBannerPics() {
        JSONObject result = new JSONObject();
        JSONArray bannerPicsArray = new JSONArray();
        List<WXPagePic> bannerList = WXPagePicService.getBannerPics();
        System.out.println(bannerList);
        if (bannerList.size() > 0) {
            result.put("result", "success");
        } else {
            result.put("result", "failed");
        }

        for (WXPagePic pic : bannerList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("pic_url", pic.getUrl());
            bannerPicsArray.add(jsonObject);
        }
        result.put("banner", bannerPicsArray);
        return result;
    }

}
