package org.jit.sose.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.jit.sose.utils.OfficeConverterUtil;
import org.jit.sose.utils.ParseText;
import org.junit.Test;

/**
 * Created by sunwujun on 2018/8/19 1:34
 */
public class UtilTest {
    @Test
    public void testGetFile() throws IOException, ParserConfigurationException {
    	
    	String a = "<img src=\"D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理-1.png\"/><br><img src=\"D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理-2.png\"/><br><img src=\"D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理-3.png\"/><br><img src=\"D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理-4.png\"/><br><img src=\"D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理-5.png\"/><br><img src=\"D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理-6.png\"/><br><img src=\"D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理-7.png\"/><br>";
    	System.out.println(a.replaceAll("[a-zA-Z]:", ""));
//		String[] wordAndExcel = {"doc", "docx", "xls","xlsx"};
//		String iLikeEat = "docx1";
//		boolean isContains = Arrays.asList(wordAndExcel).contains(iLikeEat);
//		if(isContains){
//		      // 这里有你要吃的水果
//			System.out.println("存在");
//		}else{
//			System.out.println("不存在");
//		     // 你到别出在看看把
//		}
//    	String string = "D:\\temp\\temp\\test.xlsx";
//		System.out.println(string.substring(string.lastIndexOf(".")+1, string.length()));
//    	FileReader fileReader = new FileReader("D:/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.docx");
//        BufferedReader bufferedReader = new BufferedReader(fileReader);
//        List<String> lines = new ArrayList<String>();
//        String line = null;
//        while ((line = bufferedReader.readLine()) != null) {
//            lines.add(line);
//        }
//        bufferedReader.close();
//		System.out.println(ParseText.parse("D:/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.docx"));
//		System.out.println(ParseText.parse("D:/archive/JXB/JXB02/test/宫晓东档案分类编号与入库管理.ppt"));
//    	System.out.println(ParseText.parse(getBytes("D:/archive/JXB/JXB02/test/附表1课程设置与安排表(201646.pdf"), "pdf"));

//        OfficeConverterUtil.officeFileConverterToPdf("D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理.ppt","D:/archive/JXB/JXB02/JXB02171221302320180903133029_宫晓东档案分类编号与入库管理.pdf");
//        OfficeConverterUtil.officeFileConverterToPdf("D:/archive/JXB/JXB02/test/附表1课程设置与安排表(201646.xls","D:/archive/JXB/JXB02/test/附表1课程设置与安排表(201646.pdf");
//        System.out.println(POIUtil.fromPathGetFileName("D:\\archive\\JXB\\JXB02\\软件工程专业卓越工程师教育培养方案文字说明.docx"));
//        System.out.println(POIUtil.fromPathGetFileName("c:/win/good/aaa.txt"));
//        System.out.println(POIUtil.getFileNameWithoutExtension(POIUtil.fromPathGetFileName("D:\\archive\\JXB\\JXB02\\软件工程专业卓越工程师教育培养方案文字说明.docx")));
//        System.out.println(POIUtil.getFileExtension("aaa.doc"));
//        System.out.println(POIUtil.getFilePath("c:/win/good/aaa.txt"));
//        System.out.println(POIUtil.getFilePath("D:\\archive\\JXB\\JXB02\\软件工程专业卓越工程师教育培养方案文字说明.docx"));
//        POIWordToHtml.wordToHtml("D:/archive/JXB/JXB02/test/JXB02171221302320180903024431_2017校精品、双语课程评审结果公文.doc", "D:/archive/JXB/JXB02/test/image", "D:/archive/JXB/JXB02/test/JXB02171221302320180903024431_2017校精品、双语课程评审结果公文.html");

//        OfficeToHTMLUtil.officeToHtml("D:/archive/JXB/JXB02/test/JXB02171221302320180903024431_2017校精品、双语课程评审结果公文.doc");
//        POIUtil.docxToHTML("D:/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.docx");
//        POIUtil.Word2007ToHtml();
//        POIUtil.Word2007ToHtml("D:/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.docx");
//        POIWordToHtml.wordToHtml("D:/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.docx", "D:/archive/JXB/JXB02/test/image", "D:/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.html");
//        OfficeToHTMLUtil.officeToHtml("D:/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.docx");
//        OfficeToHTMLUtil.officeToHtml("D:/archive/JXB/JXB02/test/档案管理系统创业计划书.pdf");
//        OfficeToHTMLUtil.officeToHtml("D:/archive/JXB/JXB02/test/宫晓东档案分类编号与入库管理.ppt");
//        OfficeToHTMLUtil.officeToHtml("D:/archive/JXB/JXB02/test/41472x班级名单.xlsx");
//        OfficeToHTMLUtil.officeToHtml("D:/archive/JXB/JXB02/test/附表1课程设置与安排表(201646.xls");

//        try {
//            POIUtil.docxToHTML("D:\\archive\\JXB\\JXB02\\软件工程专业卓越工程师教育培养方案文字说明.docx");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
    
    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return buffer;  
    } 
}
