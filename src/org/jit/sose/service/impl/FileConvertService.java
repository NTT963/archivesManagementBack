package org.jit.sose.service.impl;

import org.jit.sose.poi.OfficeToHTMLUtil;
import org.jit.sose.poi.POIPptToHtml;
import org.jit.sose.service.IFileConvertService;
import org.jit.sose.utils.OfficeConverterUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunwujun on 2018/9/5 11:56
 */
@Service
public class FileConvertService implements IFileConvertService {

    //    @Value("#{configProperties['virtualURL']}")
//    private String virtualURL;
//
//    @Value("#{configProperties['physicalURL']}")
//    private String physicalURL;
    @Value("${virtualURL}")
    private String virtualURL;

    @Value("${physicalURL}")
    private String physicalURL;

    @Override
    public Map<String, Object> convertOfficeToPDF(String officePath) {
        System.out.println("源文件路径" + officePath);
        Map<String, Object> resultMap = new HashMap<>();
        //获取文件的扩展名
        String ext = officePath.substring(officePath.lastIndexOf(".") + 1, officePath.length());
        String[] wordAndExcel = {"doc", "docx", "xls", "xlsx"};
        String[] ppt = {"ppt", "pptx"};

        //根据扩展名生成对应的预览文件的路径
        String previewPath = "";
        String officePhysicalPath = physicalURL + officePath;

        if (Arrays.asList(wordAndExcel).contains(ext)) {
            System.out.println("word和excel");
            previewPath = officePath.substring(0, officePath.lastIndexOf(".")) + ".pdf";
            //判断预览文件是否存在,
            String previewPhysicalPath = physicalURL + previewPath;
            if (OfficeToHTMLUtil.fileIsExists(previewPhysicalPath)) {
                //若已存在则直接返回预览文件的虚拟路径
                resultMap.put("result", "success");
                resultMap.put("previewURL", virtualURL + previewPath);
                return resultMap;
            } else {
                //不存在则生成预览文件
                if (OfficeConverterUtil.officeFileConverterToPdf(officePhysicalPath, previewPhysicalPath)) {
                    //预览文件生成成功
                    resultMap.put("result", "success");
                    resultMap.put("previewURL", virtualURL + previewPath);
                } else {
                    //预览文件生成失败
                    resultMap.put("result", "fail");
                }

            }
        } else if (Arrays.asList(ppt).contains(ext)) {
            System.out.println("ppt");
            previewPath = officePath.substring(0, officePath.lastIndexOf(".")) + ".html";
            //判断预览文件是否存在,若已存在则直接返回预览文件的虚拟路径
            String previewPhysicalPath = physicalURL + previewPath;
            if (OfficeToHTMLUtil.fileIsExists(previewPhysicalPath)) {
                resultMap.put("result", "success");
                resultMap.put("previewURL", virtualURL + previewPath);
                return resultMap;
            } else {
                //不存在则生成预览文件

                String targetDir = officePhysicalPath.substring(0, officePhysicalPath.lastIndexOf("/"));
                if (POIPptToHtml.pptToHtml(officePhysicalPath, targetDir)) {
                    //预览文件生成成功
                    resultMap.put("result", "success");
                    resultMap.put("previewURL", virtualURL + previewPath);
                } else {
                    //预览文件生成失败
                    resultMap.put("result", "fail");
                }

            }
        } else {
            System.out.println("不存在");
        }


        return resultMap;
    }

    @Override
    public String downloadArchive(String url) {

        String downloadURL = virtualURL + url;
        String filePhysicalURL = physicalURL + url;
        System.out.println(filePhysicalURL);
        if (OfficeToHTMLUtil.fileIsExists(filePhysicalURL)) {
            System.out.println("文件存在");
            return downloadURL;
        }else {
            return "error";
        }
    }
}
