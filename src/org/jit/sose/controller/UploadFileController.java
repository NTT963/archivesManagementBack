package org.jit.sose.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.jit.sose.entity.Approve;
import org.jit.sose.entity.ArchivesInfo;
import org.jit.sose.service.IArchivesService;
import org.jit.sose.service.impl.FileConvertService;
import org.jit.sose.utils.FileUtil;
import org.jit.sose.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("uploadFile")
public class UploadFileController {

    @Autowired
    IArchivesService iArchivesService;
    
    @Autowired
	FileConvertService fileConvertService;

    @ResponseBody
    @RequestMapping(value = "/uploadArchive.do")
    public void uploadArchive(@RequestParam(value = "file", required = true) CommonsMultipartFile file[],
                              HttpServletRequest request) throws Exception {
        String path = request.getParameter("path").trim();
        String userId = request.getParameter("userId");
        System.out.println("=====>用户编号为" + userId);

        JSONObject resJson = new JSONObject();
//        boolean b = false, c = false;
        PropertiesUtil pUtil = new PropertiesUtil();
        Properties properties = null;
        try {
            properties = pUtil.readProperties("java.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = properties.getProperty("url");
        System.out.println(url);
        // 构造文件上传的帮助类，实现文件上传功能
        FileUtil fileUtil = new FileUtil();

        FileUtil.dir = "/" + request.getParameter("path").replaceAll(",", "/");
        FileUtil.preCode = path.substring(path.lastIndexOf(",") + 1, path.length()) + userId;
        System.out.println("=======>" + FileUtil.dir);
        List<Map<String, Object>> fileList = fileUtil.fileUpLoad(file);


        if ((boolean) fileList.get(fileList.size() - 1).get("upFileFlag")) { // 文件上传成功的状态
            for (int i = 0; i < fileList.size() - 1; i++) { // 集合中最后一个内容是文件上传标志，不是文件本身信息，所以要减1
                String classifyID = path.substring(path.lastIndexOf(",") + 1, path.length());
                String archivesId = fileList.get(i).get("fileNewName").toString();
                String archivesName = fileList.get(i).get("fileOriginalName").toString();
                String content = fileList.get(i).get("content").toString();
                String archiveURL = FileUtil.dir;
                String state = "0";
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String uploadTime = df.format(new Date());
                String uploadUserId = request.getParameter("userId");
                String userImagePath = url + '/' + fileList.get(i).get("fileNewName");
                System.out.println(userImagePath);
                System.out.println("POI文件内容为=====》" + fileList.get(i).get("content"));
                System.out.println(archivesId);

                ArchivesInfo archivesInfo = new ArchivesInfo(classifyID, archivesId, archivesName, content, archiveURL, state, uploadTime, uploadUserId);

                int archiveId = iArchivesService.insertArchive(archivesInfo);
                if(archiveId != 0) {
                	fileConvertService.convertOfficeToPDF(archiveURL+"/"+archivesId);
                }
                	
                System.out.println("插入主键ID===》" + archivesInfo.getID());
//                iArchivesService.insertApprove(new Approve(userId, archivesId, classifyID));
//                String classifyId, String archivesId, String archivesName, String content, String url, String state
            }
            resJson.put("success", true);
//            return "redirect:/archivesManagementBack/fileHandle/convertOfficeToPDF.do?officePath="+;
        } else { // 文件上传失败的状态
            resJson.put("success", false);
        }
    }

}
