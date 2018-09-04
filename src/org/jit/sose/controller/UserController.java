package org.jit.sose.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import org.jit.sose.entity.Staff;
import org.jit.sose.service.StaffService;
import org.jit.sose.utils.CaptchaUtil;
import org.jit.sose.utils.MD5Util;
import org.jit.sose.utils.QcloudSmsUtil;
import org.jit.sose.utils.SendCodeUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff")
public class UserController {

    @Autowired
    private StaffService staffService;

    public void setStaffService(StaffService staffService) {
        this.staffService = staffService;
    }

    // 生成验证码，利用验证码工具类
    @RequestMapping("/findVerifyCode.do")
    public void findVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 通知浏览器不要缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "No-cache");
        CaptchaUtil util = CaptchaUtil.Instance();
        // 将验证码输入到session中，用来验证
        String code = util.getString();
        request.getSession().setAttribute("code", code);
        // 输出打web页面
        ImageIO.write(util.getImage(), "jpg", response.getOutputStream());
    }


    @RequestMapping(value = "/test.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public void test(@RequestBody String params) {
        System.out.println(params);

    }

    // 验证图形验证码
    @ResponseBody
    @RequestMapping(value = "/compareYzm.do", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Map<String, Object> compareYzm(@RequestBody String c, HttpServletRequest request, HttpSession session)
            throws Exception {
        System.out.println("参数位" + c);
        java.util.Map<String, Object> map = new HashMap<>();
        try {
            JSONObject strj = new JSONObject(c);
            String code = strj.getString("picCode");
            System.out.println("用户输入验证码为:" + code);
            // session中的验证码
            String a = (String) session.getAttribute("code");
            System.out.println("系统生成验证码为:" + a);
            // 登录前先验证验证码输入是否正确,正确进行登录验证,不正确则重新输入,不进行登录验证
            if (code.equalsIgnoreCase(a)) {
                System.out.println("验证码验证正确！");
                map.put("validate", "success");
            } else {
                System.out.println("验证码验证错误！");
                map.put("validate", "fail");
            }
        } catch (Exception e) {
            System.out.println("验证码异常:" + e.getMessage());
            map.put("error", "异常");
        }
        return map;
    }

    // 用户登录
    @ResponseBody
    @RequestMapping(value = "/login.do", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Map<String, Object> login(@RequestBody String s, HttpServletRequest request, HttpSession session)
            throws ParseException {
        System.out.println("用户信息" + s);
        Map<String, Object> map = new HashMap<>();
        MD5Util md5Util = new MD5Util();
        try {
            JSONObject strj = new JSONObject(s);
            Staff loginStaff = new Staff();
            // 获取用户输入的账户名和密码
            loginStaff.setLoginName(strj.getString("userName"));
            loginStaff.setPassword(md5Util.getMD5(strj.getString("password")));
            Staff staff = staffService.getByLoginName(loginStaff.getLoginName());
            if (staff != null) {
                if (staff.getIsLocked().equals("Y")) {
                    // 账户已经锁定
                    map.put("loginStatus", "fail");
                    map.put("message", "账户已经锁定，请向管理员提交申请进行解锁");
                } else {
                    if (staff.getPassword().equals(loginStaff.getPassword())) {
                        // 用户名密码匹配，登陆成功,同时重置数据库中用户登录失败次数
                        staffService.resetLoginFailedTimes(loginStaff.getLoginName());
                        // 用户信息放入拦截器，权限管理
                        session.setAttribute("STAFF_SESSION", staff);
                        System.out.println(staff.getStaffName());
                        Staff testS = (Staff) session.getAttribute("STAFF_SESSION");
                        System.out.println("session中的用户信息为：" + testS.getLoginName());
                        map.put("loginStatus", "success");
                        map.put("userName",staff.getStaffName());
                        map.put("message", "登陆成功");
                    } else {
                        if (staff.getLoginFailedTimes() < 4) {
                            // 登录失败次数，达到五次即进行锁定，需要管理员进行解锁
                            staff.setLoginFailedTimes(staff.getLoginFailedTimes() + 1);
                            staffService.setLoginFailedTimes(staff);
                            map.put("loginStatus", "fail");
                            map.put("message", "登录失败，请重新登录");
                            System.out.println("用户名密码错误，提示用户重新登录");
                        } else {
                            // 锁定，无需传值，状态值只有两种
                            staffService.setLocked(loginStaff.getLoginName());
                            map.put("loginStatus", "fail");
                            map.put("message", "失败次数已达上限，账户锁定");
                            System.out.println("失败次数已达上限，账户即将锁定");
                        }
                    }
                }
            } else {
                map.put("loginStatus", "fail");
                map.put("message", "用户名不存在");
                System.out.println("用户名不存在");
            }
        } catch (Exception e) {
            map.put("error", e.getMessage());
        }
        return map;
    }

    // 查找用户名是否存在
    @ResponseBody
    @RequestMapping(value = "/findLoginName.do", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Map<String, Object> findLoginName(@RequestBody String s, HttpServletRequest request, HttpSession session)
            throws ParseException {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject strj = new JSONObject(s);
            Staff loginStaff = new Staff();
            // 获取用户输入的账户名和密码
            loginStaff.setLoginName(strj.getString("loginName"));
            System.out.println(loginStaff.getLoginName());
            Staff staff = staffService.getByLoginName(loginStaff.getLoginName());
            if (staff != null) {
                map.put("findStatus", "success");
            } else {
                map.put("findStatus", "fail");
                map.put("message", "用户名不存在");
                System.out.println("用户名不存在");
            }
        } catch (Exception e) {
            map.put("error", e.getMessage());
        }
        return map;
    }

    // 用户重置密码
    @ResponseBody
    @RequestMapping(value = "/resetPassword.do", method = RequestMethod.POST, consumes = "application/json;charset=UTF-8")
    public Map<String, Object> resetPassword(@RequestBody String s, HttpServletRequest request, HttpSession session)
            throws ParseException {
        Map<String, Object> map = new HashMap<>();
        MD5Util md5Util = new MD5Util();
        try {
            JSONObject strj = new JSONObject(s);
            Staff loginStaff = new Staff();
            // 获取用户输入的账户名和密码
            loginStaff.setLoginName(strj.getString("loginName"));
            loginStaff.setPassword(md5Util.getMD5(strj.getString("password")));
            loginStaff.setPhone(strj.getString("phone"));
            int a = staffService.resetPassword(loginStaff);
            if (a > 0) {
                map.put("status", "Y");
                map.put("message", "重置密码成功，跳转到登录页面");
            } else {
                map.put("status", "N");
                map.put("message", "手机号不匹配，请重新重置密码");
            }
        } catch (Exception e) {
            map.put("error", e.getMessage());
        }
        return map;
    }

    // 注册
    @ResponseBody
    @RequestMapping(value = "/register.do", method = RequestMethod.POST, consumes = "application/json")
    public Map<String, Object> register(@RequestBody String str, Staff staff, HttpSession session)
            throws ParseException {
        Map<String, Object> map = new HashMap<>();
        MD5Util md5Util = new MD5Util();
        try {
            JSONObject strj = new JSONObject(str);
            System.out.println(strj);
            staff.setLoginName(strj.getString("loginName"));
            // 使用MD5给密码加密
            String md5Password = md5Util.getMD5(strj.getString("password"));
            System.out.println(md5Password);
            staff.setPassword(md5Password);
            staff.setPhone(strj.getString("phone"));
            // 向数据库插入值
            int result = staffService.insertStaff(staff);
            if (result > 0) {
                System.out.println("用户注册成功！");
                map.put("info", "success");
                return map;
            } else {
                System.out.println("用户注册失败！");
                map.put("info", "fail");
                return map;
            }
        } catch (Exception e) {
            System.out.println("注册异常:" + e.getMessage());
            map.put("error", e.getMessage());
        }
        return map;
    }

    // 获取手机验证码
    @ResponseBody
    @RequestMapping(value = "/sendMessage.do", method = RequestMethod.POST, consumes = "application/json")
    public Map<String, Object> sendMessage(@RequestBody String str, HttpServletRequest request) throws ParseException {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject strj = new JSONObject(str);
            System.out.println(strj);
            // 获取生成验证码的时间
            long createMsgCodeTime = new Date().getTime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("获取验证码时间为:" + df.format(new Date()));
            // 将当前时间放到session中，与注册时获取的时间相比较
            request.getSession().setAttribute("createMsgCodeTime", createMsgCodeTime);
            // 短信工具类
            // SendCodeUtil sendCodeUtil = new SendCodeUtil();
            QcloudSmsUtil qSmsUtil = new QcloudSmsUtil();
            // 获取手机号码
            // String WJphone=strj.getString("phone");
            String phone = strj.getString("phone");
            String[] QcloudPhone = new String[]{phone};
            // 通过template的值来分类使用模板
            String template = strj.getString("template");
            int templateId = 0;
            String qMsgFailureTime = "0";
            // 获取短信模板，有效时间
            if (template.equals("registerTemplate")) {
                templateId = 118951;
                qMsgFailureTime = "10";
                System.out.println("调用注册模板，有效时间为 " + qMsgFailureTime + "分钟！");
            } else if (template.equals("resetTemplate")) {
                templateId = 122443;
                qMsgFailureTime = "5";
                System.out.println("调用重置密码模板，有效时间为 " + qMsgFailureTime + "分钟！");
            } else {
                System.out.println("短信模板错误！");
            }
            // 发送验证码
            // int result=sendCodeUtil.sendSmsText(WJphone, request);
            int result = qSmsUtil.sendQcloudSms(QcloudPhone, qMsgFailureTime, templateId, request);
            map.put("msgCode", request.getSession().getAttribute("createMsgCode"));
            // 在前端判断验证码发送状态 中国网建 >0 成功 腾讯云 =0 成功
            map.put("sendMessageInfo", result);
        } catch (Exception e) {
            System.out.println("手机验证码获取异常:" + e.getMessage());
            map.put("error", e.getMessage());
        }
        return map;
    }

    // 验证手机验证码
    @ResponseBody
    @RequestMapping(value = "/compareSmsCode.do", method = RequestMethod.POST, consumes = "application/json")
    public Map<String, Object> compareSmsCode(@RequestBody String str, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        try {
            JSONObject strj = new JSONObject(str);
            // 获取注册时的时间 getTime()将当前时间变为毫秒 1秒=1000毫秒
            long registerTime = new Date().getTime();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("验证验证码时间为:" + df.format(new Date()));
            // long
            // createMsgCodeTime=Long.parseLong(session.getAttribute("createMsgCodeTime").toString());
            long createMsgCodeTime = (long) session.getAttribute("createMsgCodeTime");
            // 设置验证码有效时间
            // long msgFailureTime = 5 * 60 * 1000;
            String stringTime = (String) session.getAttribute("qMsgFailureTime");
            long qMsgFailureTime = Long.parseLong(stringTime) * 60 * 1000;

            // 获取从页面输入的手机验证码
            String msgCode = strj.getString("msgCode");
            // 从session中获取工具类生成的验证码
            String createMsgCode = (String) session.getAttribute("createMsgCode");

            // 比较验证码时间是否超时
            if (createMsgCodeTime + qMsgFailureTime < registerTime) {
                System.out.println("短信验证码填写超时!");
                map.put("info", "timeOut");
                return map;
            } else {
                System.out.println("短信验证码填写未超时!");
                // 比较验证码是否相同
                if (msgCode.equals(createMsgCode)) {
                    System.out.println("短信验证码验证成功！");
                    map.put("info", "success");
                } else {
                    System.out.println("短信验证码验证失败！");
                    map.put("info", "msgCodeErr");
                    return map;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("验证手机验证码异常:" + e.getMessage());
        }
        return map;
    }

}