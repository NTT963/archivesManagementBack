package org.jit.sose.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jit.sose.entity.Admin;
import org.jit.sose.entity.Student;
import org.jit.sose.entity.Teacher;
import org.jit.sose.entity.WXUser;
import org.jit.sose.service.AdminService;
import org.jit.sose.service.StudentService;
import org.jit.sose.service.TeacherService;
import org.jit.sose.service.WXLoginService;
import org.jit.sose.service.WXUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
public class WXLoginController {


    @SuppressWarnings("unused")
    @Autowired
    private WXLoginService wxLoginService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private WXUserService WXUserService;


    @RequestMapping(value = "/weixinLogin.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONObject weixinLogin(String code) {
        JSONObject loginResult = new JSONObject();
        final String APPID = "wx62014884e4e1d695";
        final String SECRET = "1748f5271a09934bbe1143a63284718c";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        if (responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            String sessionData = responseEntity.getBody();
            JSONObject jsonObj = JSON.parseObject(sessionData);
            String openId = jsonObj.getString("openid");
            String sessionKey = jsonObj.getString("session_key");
            if (WXUserService.getOpenIdCount(openId) == 0) {
                System.out.println("该微信用户还没有再系统注册");
                loginResult.put("isRegister", false);
            } else {
                loginResult.put("isRegister", true);
                List<WXUser> wxUsers = WXUserService.getUserInfo(openId);

                loginResult.put("userId", wxUsers.get(0).getUserid());
                loginResult.put("userCard", wxUsers.get(0).getUsercard());


            }
            loginResult.put("openId", openId);
            loginResult.put("sessionKey", sessionKey);
        }
        return loginResult;
    }


    @RequestMapping(value = "/getUserInfo.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONObject getUserInfo(String role, String id) {
        JSONObject result = new JSONObject();
        result.put("result","success");
        System.out.println("获取到的信息为========》"+ role.equals("0")+ id);
        if (role.equals("0")){
            System.out.println("学生");
            Student student = studentService.selectBystuNum(id);
            result.put("role","0");
            result.put("userInfo",student);
        }else if (role.equals("1")){
            System.out.println("老师");
            Teacher teacher = teacherService.selectBystuNum(id);
            result.put("role","1");
            result.put("userInfo",teacher);
        }else if(role.equals("2")){
            System.out.println("管理员");
            result.put("role","2");
        }
        System.out.println(result.toJSONString());
        return result;


    }


    @RequestMapping(value = "/insertOpenIDAndUserId.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONObject insertOpenIDAndUserId(@RequestBody String userInfo, WXUser wxUser, HttpServletRequest request) {
        JSONObject insertResult = new JSONObject();
        JSONObject userInfoJSON = JSONObject.parseObject(userInfo);
        String userName = userInfoJSON.getString("userName");
        String userID = userInfoJSON.getString("userID");
        String openID = userInfoJSON.getString("openID");
        String cardId = userInfoJSON.getString("cardId");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("userId", userID);
        wxUser.setOpenid(openID);
        wxUser.setUserid(userID);
        boolean isbe = false;
        switch (cardId) {
            case "管理员":
                Admin admin = new Admin();
                admin.setAdminnum(userID);
                admin.setAdminname(userName);
                isbe = adminService.isbe(admin);
                wxUser.setUsercard("2");
                break;
            case "教师":
                Teacher teacher = new Teacher();
                teacher.setTchname(userName);
                teacher.setTchnum(userID);
                isbe = teacherService.isbe(teacher);
                wxUser.setUsercard("1");
                break;
            case "学生":
                Student student = new Student();
                student.setStuname(userName);
                student.setStunum(userID);
                isbe = studentService.isbe(student);
                wxUser.setUsercard("0");
                break;
        }
        System.out.println(isbe);
        boolean b = false;
        if (isbe) {
            b = WXUserService.insertOpenIDAndUserId(wxUser);
        }
        if (b) {
            insertResult.put("result", "success");
        } else {
            insertResult.put("result", "fail");
        }
        return insertResult;
    }


}
