package org.jit.sose.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
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

    @RequestMapping(value = "/testPOI.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public void testPOI(){
        java.io.File file = new File("/archive/JXB/JXB01/金陵科技学院课表编排规程.doc");
        String tempDir = System.getProperty("java.io.tmpdir");
        System.out.println("临时目录=========>"+tempDir);
        System.out.println("字符编码"+StringUtil.WIN_1252);
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            System.out.println(readDoc(fis));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readDoc (InputStream is) throws IOException {
        String text= "";
        is = FileMagic.prepareToCheckMagic(is);
        try {
            if (FileMagic.valueOf(is) == FileMagic.OLE2) {
                WordExtractor ex = new WordExtractor(is);
                text = ex.getText();
                ex.close();
            } else if(FileMagic.valueOf(is) == FileMagic.OOXML) {
                XWPFDocument doc = new XWPFDocument(is);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                text = extractor.getText();
                extractor.close();
            }
        } catch (Exception e) {
//			logger.error("for file " + filePath, e);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return text;
    }




    @RequestMapping(value = "/getApproveByUserId.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    List<Approve> getApproveByUserId(String userId) {
        List<Approve> list = iArchivesService.getApproveByUserId(userId);
        return list;
    }

    @RequestMapping(value = "/insertArchiveSave.do", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    void insertArchiveSave(@RequestBody String saveInfo) {
        ArchiveSave archiveSave = JSON.parseObject(saveInfo, new TypeReference<ArchiveSave>() {
        });
        iArchivesService.insertArchiveSave(archiveSave);

    }

    @RequestMapping(value = "/getArchiveSaves.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    List<ArchiveSave> getArchiveSaves(String userId) {
        List<ArchiveSave> list = iArchivesService.getArchiveSaves(userId);
        return list;
    }

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

        System.out.println("参数" + classifyId);
		/*JSONObject.toJSON(iArchivesService.getArchivesByClassifyId(classifyId));
		List<ArchivesInfo> archivesInfos = iArchivesService.getArchivesByClassifyId(classifyId);*/

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
    public JSONObject queryArchive(String keyWord) {
        System.out.println("档案查询" + keyWord);
        JSONObject queryResult = new JSONObject();
        List<ArchivesInfo> archivesInfoList = iArchivesService.queryArchives(keyWord);
        queryResult.put("result", "success");
        queryResult.put("resultCount", archivesInfoList.size());
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
