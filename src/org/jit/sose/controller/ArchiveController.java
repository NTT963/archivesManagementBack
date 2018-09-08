package org.jit.sose.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jit.sose.entity.*;
import org.jit.sose.service.IArchivesService;
import org.jit.sose.service.UserArchiveService;
import org.jit.sose.utils.FileUtil;
import org.jit.sose.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ArchiveController {
    @Autowired
    IArchivesService iArchivesService;

    
  
    @RequestMapping(value = "/getAllArchives.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public List getAllArchives() {
        List<ArchivesInfo> archivesInfos = iArchivesService.getAllArchives();
        return archivesInfos;
    }

    @RequestMapping(value = "getArchivesByClassifyId.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONArray getArchivesByClassifyId(String classifyId) {
        return (JSONArray) JSONArray.toJSON(iArchivesService.getArchivesByClassifyId(classifyId));
    }

    @RequestMapping(value = "addArchive.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public void addArchive(@RequestBody String archive) {
        ArchivesInfo archivesInfo = JSON.parseObject(archive, new TypeReference<ArchivesInfo>() {
        });
        System.out.println("新增档案" + archivesInfo.getArchivesName());
        iArchivesService.insertArchive(archivesInfo);
    }

    @RequestMapping(value = "queryArchive.do", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public JSONObject queryArchive(String keyWord, @RequestParam(required = true, defaultValue = "1") Integer page) {
        System.out.println("档案查询" + keyWord + "页码" + page);
        JSONObject queryResult = new JSONObject();

        PageHelper.startPage(page, 5);
        List<ArchivesInfo> archivesInfoList = iArchivesService.queryArchives(keyWord);
        PageInfo<ArchivesInfo> p = new PageInfo<ArchivesInfo>(archivesInfoList);


        queryResult.put("result", "success");
        queryResult.put("resultCount", p.getTotal());
        queryResult.put("archiveList", JSONArray.toJSON(archivesInfoList));
        return queryResult;
    }


    @ResponseBody
    @RequestMapping(value = "/putArchiveUrl.do")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public void PutArchiveUrl(@RequestBody String str, HttpServletRequest request) {
        org.json.JSONObject jsonObject = new org.json.JSONObject(str);
        org.json.JSONArray jsonArray = (org.json.JSONArray) jsonObject.get("url");
        String userId = jsonObject.getString("userId");
        HttpSession hSession = request.getSession();
        hSession.setAttribute("Path", jsonArray.toString());
        hSession.setAttribute("userId", userId);
        System.out.println(hSession.getAttribute("userId"));
    }


    @ResponseBody
    @RequestMapping(value = "/uploadArchive1.do")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public void UploadArchive1(@RequestParam(value = "file", required = true) CommonsMultipartFile file[],
                               HttpServletRequest request) throws Exception {
        HttpSession httpSession = request.getSession();

        String path = request.getParameter("path").trim();
        String openId = (String) httpSession.getAttribute("openId");
        Integer id = (Integer) httpSession.getAttribute("id");
        System.out.println(id);
        JSONObject resJson = new JSONObject();
        boolean b = false, c = false;
        PropertiesUtil pUtil = new PropertiesUtil();
        Properties properties = null;
        try {
            properties = pUtil.readProperties("java.properties");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String url = properties.getProperty("url");
        System.out.println(url);
        // 构造文件上传的帮助类，实现文件上传功能
        FileUtil fileUtil = new FileUtil();
//        request.getParameter("path").replaceAll(",","//")
//        fileUtil.setDir("testTTTTTTTTTTTTTTTT");


        FileUtil.dir = request.getParameter("path").replaceAll(",", "/");
        List<Map<String, Object>> fileList = fileUtil.fileUpLoad(file);
        if ((boolean) fileList.get(fileList.size() - 1).get("upFileFlag")) { // 文件上传成功的状态
            for (int i = 0; i < fileList.size() - 1; i++) { // 集合中最后一个内容是文件上传标志，不是文件本身信息，所以要减1
                String classifyID = path.substring(path.lastIndexOf(",") + 1, path.length());
                String archivesId = fileList.get(i).get("fileNewName").toString();
                String archivesName = fileList.get(i).get("fileOriginalName").toString();
                String content = fileList.get(i).get("content").toString();
                String archiveURL = "http://test.com";
                String state = "0";
                String userImagePath = url + '/' + fileList.get(i).get("fileNewName");
                System.out.println(userImagePath);
                System.out.println("POI文件内容为=====》" + fileList.get(i).get("content"));
                System.out.println(archivesId);

//                iArchivesService.insertArchive(new ArchivesInfo(classifyID, archivesId, archivesName, content, archiveURL, state));
//                String classifyId, String archivesId, String archivesName, String content, String url, String state
            }
            if (b == true && c == true) {
                resJson.put("success", true);
            }
        } else { // 文件上传失败的状态
            resJson.put("success", false);
        }
    }


}
