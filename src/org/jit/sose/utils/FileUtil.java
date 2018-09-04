package org.jit.sose.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jit.sose.poi.POIUtil;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件帮助类
 */
public class FileUtil {

    public static String dir;
    public static String preCode;


    public List<Map<String, Object>> fileUpLoad(CommonsMultipartFile file[], String filePath) {
        List<Map<String, Object>> fList = new ArrayList<>();
        try {
            //获取系统时间作为文件上传的时间
            Date day = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(df.format(day));
            String fileTime = df.format(day).toString();
            for (int i = 0; i < file.length; i++) {
                String fileName = file[i].getOriginalFilename();
                String newFileName = getNewName(fileName);
                File dir = new File(filePath, newFileName);
                if (!dir.exists()) {
                    System.out.println("文件不存在，准备创建！");
                    dir.mkdir();
                }
                try {
                    file[i].transferTo(dir);
                    Map<String, Object> map = new HashMap<>();
                    map.put("fileOriginalName", fileName);           //文件的原来名称
                    map.put("fileNewName", newFileName);    //文件上传到服务器新的名字
                    map.put("fileTime", fileTime);                   //文件上传的时间
                    map.put("fileUrl", filePath);                    //文件上传到服务器的地址
                    fList.add(map);
                } catch (IllegalStateException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            Map<String, Object> upFileMap = new HashMap<String, Object>();
            upFileMap.put("upFileFlag", true);
            fList.add(upFileMap);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> upFileMap = new HashMap<String, Object>();
            upFileMap.put("upFileFlag", false);
            fList.add(upFileMap);
        }

        return fList;
    }


    public List<Map<String, Object>> fileUpLoad(CommonsMultipartFile files[]) throws Exception {
        PropertiesUtil pUtil = new PropertiesUtil();
        List<Map<String, Object>> fList = new ArrayList<>();
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(day));
        String fileTime = df.format(day).toString();
        Properties properties = pUtil.readProperties("java.properties");
        String filePath = properties.getProperty("physicalURL");

        System.out.println("档案路径为====》" + this.dir);
        System.out.println(filePath);
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getOriginalFilename();
            String newFileName = getNewName(fileName);
            String path = filePath + this.dir + "/" + newFileName;
            System.out.println("PATH" + path);

            File file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                System.out.println("文件不存在，准备创建！");
                fileParent.mkdirs();
            }
            file.createNewFile();
            files[i].transferTo(file);

            File file1 = new File(path);
            String content = ParseText.parse(path);
            System.out.println("档案内容为====>"+content);
            if (content.length()>=20000){
                content = content.substring(0,20000);
//                System.out.println(content.substring(0,20000));
            }
//            System.out.println(content);

            Map<String, Object> map = new HashMap<>();
            map.put("fileOriginalName", fileName);           //文件的原来名称
            map.put("fileNewName", newFileName);    //文件上传到服务器新的名字
            map.put("fileTime", fileTime);                   //文件上传的时间
            map.put("fileUrl", filePath);                    //文件上传到服务器的地址
            map.put("content", content);
            fList.add(map);
        }
        Map<String, Object> upFileMap = new HashMap<String, Object>();
        upFileMap.put("upFileFlag", true);
        fList.add(upFileMap);

        return fList;
    }


    public String readDoc(InputStream is) throws IOException {
        String text = "";
        is = FileMagic.prepareToCheckMagic(is);
        try {
            if (FileMagic.valueOf(is) == FileMagic.OLE2) {
                WordExtractor ex = new WordExtractor(is);
                text = ex.getText();
                ex.close();
            } else if (FileMagic.valueOf(is) == FileMagic.OOXML) {
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

    /**
     * 文件下载功能
     *
     * @param fileName 文件在服务器上的新名称
     * @param response
     */
    public JSONObject fileDownLoad(String fileName, HttpServletResponse response) {
        JSONObject downJson = new JSONObject();
        PropertiesUtil pUtil = new PropertiesUtil();
        try {
            Properties properties = pUtil.readProperties("jdbc.properties");
            String path = properties.getProperty("filepath") + "/test";
            OutputStream oStream = response.getOutputStream();
            String filePath = path + File.separator + fileName;
            File file = new File(filePath);
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType("application/octet-stream; charset=utf-8");
            oStream.write(FileUtils.readFileToByteArray(file));
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            downJson.put("downResult", "下载失败");
        } catch (Exception e) {
            e.printStackTrace();
            downJson.put("downResult", "下载成功");
        }
        return downJson;
    }

    /**
     * 获得新名称
     *
     * @param name
     * @return
     */
    public String getNewName(String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newName = this.preCode + sdf.format(new Date()) + "_" + name;
        return newName;
    }

}



