package org.jit.sose.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jit.sose.entity.ArchiveModel;
import org.jit.sose.entity.BannerPic;
import org.jit.sose.entity.IconInfo;
import org.jit.sose.entity.Notice;
import org.jit.sose.service.ArchiveModelService;
import org.jit.sose.service.IWXConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class WeixinConfigController {

    @Autowired
    IWXConfigService iwxConfigService;

    @Autowired
    ArchiveModelService archiveModelService;

//
//    @RequestMapping(value = "/weixinLogin.do")
//    @ResponseBody
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public JSONObject weixinLogin(String code,HttpServletRequest request) {
//    	HttpSession httpSession = request.getSession();
//        JSONObject loginResult = new JSONObject();
//        final String APPID = "wx62014884e4e1d695";
//        final String SECRET = "1748f5271a09934bbe1143a63284718c";
//        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
//        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
//            String sessionData = responseEntity.getBody();
//            JSONObject jsonObj = JSON.parseObject(sessionData);
//            String openId = jsonObj.getString("openid");
//            httpSession.setAttribute("openId", openId);
//            httpSession.setAttribute("id", 123);
//            System.out.println(httpSession.getAttribute("openId"));
//            String sessionKey = jsonObj.getString("session_key");
//            if (iwxConfigService.getOpenIdCount(openId) == 0) {
//                System.out.println("该微信用户还没有再系统注册");
//                loginResult.put("isRegister", false);
//
////                iwxConfigService.insertOpenID(openId);
////                将openid写入数据库，并弹出用户信息填写表单
//            } else {
//                loginResult.put("isRegister", true);
//                System.out.println("已经注册" + openId);
////                查询用户基本信息
//            }
//            loginResult.put("openId", openId);
//            loginResult.put("sessionKey", sessionKey);
//        }
//        return loginResult;
//    }
//
//    @RequestMapping(value = "/getBannerPics.do")
//    @ResponseBody
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public JSONObject getBannerPics() {
//        JSONObject result = new JSONObject();
//        JSONArray bannerPicsArray = new JSONArray();
//        List<BannerPic> bannerList = iwxConfigService.getBannerPics();
//
//        if (bannerList.size() > 0) {
//            result.put("result", "success");
//        } else {
//            result.put("result", "failed");
//        }
//
//        for (BannerPic pic : bannerList) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("pic_url", pic.getUrl());
//            bannerPicsArray.add(jsonObject);
//        }
//        result.put("banner", bannerPicsArray);
//        return result;
//    }
//
//
    @RequestMapping(value = "/getIcon.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONArray getIcon(String page , String role) {
    	List<IconInfo> list = iwxConfigService.getIcon(page, role);
        return (JSONArray) JSONArray.toJSON(list);
    }


    @RequestMapping(value = "/getAllModel.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONArray getAllModel() {
        List<ArchiveModel> list = archiveModelService.getAllArchiveModel();
        return (JSONArray) JSONArray.toJSON(list);
    }


    @RequestMapping(value = "/getNotice.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONArray getNotice(String role) {
    	List<Notice> noticeList = iwxConfigService.getNotice(role);

        return (JSONArray) JSONArray.toJSON(noticeList);
    }
//
//    @RequestMapping(value = "/weixinTest.do")
//    @ResponseBody
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public String weixinTest(String code) {
//    	System.out.println("测试controller");
//
//        return code;
//    }
//
//    @RequestMapping(value = "/insertOpenIDAndUserId.do",method = RequestMethod.POST)
//    @ResponseBody
//    @CrossOrigin(origins = "*", maxAge = 3600)
//    public JSONObject insertOpenIDAndUserId(@RequestBody String userInfo) {
//        System.out.println(userInfo);
//        JSONObject insertResult = new JSONObject();
//        JSONObject userInfoJSON = JSONObject.parseObject(userInfo);
//        iwxConfigService.insertOpenIDAndUserId(userInfoJSON.getString("openID"),userInfoJSON.getString("userID"));
//        System.out.println(userInfo);
//        insertResult.put("result","success");
//        return insertResult;
//    }
//

}
