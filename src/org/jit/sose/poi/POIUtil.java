package org.jit.sose.poi;

import java.io.*;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.*;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Created by sunwujun on 2018/8/18 4:48
 */
public class POIUtil {

    public static int Word2007ToHtml(String path) {
        int rv = 0;
        try {
//            String path =  presentationDto.getWordPath();
            //word路径
            String wordPath = path;
            System.out.println(wordPath);
            //word文件名
            String wordName = path.substring(path.lastIndexOf(File.separator)+1,path.lastIndexOf("."));
            //后缀
            String suffix = path.substring(path.lastIndexOf("."));
            //生成html路径
            String htmlPath = "D:\\archive\\JXB\\JXB02\\test\\" + File.separator + System.currentTimeMillis() + "_show" + File.separator;
            //生成html文件名
            String htmlName = System.currentTimeMillis() + ".html";
            //图片路径
            String imagePath = htmlPath + "image" + File.separator;

            //判断html文件是否存在
            File htmlFile = new File(htmlPath + htmlName);

            //word文件
            File wordFile = new File(path);

            // 1) 加载word文档生成 XWPFDocument对象
            InputStream in = new FileInputStream(wordFile);
            XWPFDocument document = new XWPFDocument(in);

            // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
            File imgFolder = new File(imagePath);
            XHTMLOptions options = XHTMLOptions.create();
            options.setExtractor(new FileImageExtractor(imgFolder));
            //html中图片的路径 相对路径
            options.URIResolver(new BasicURIResolver("image"));
            options.setIgnoreStylesIfUnused(false);
            options.setFragment(true);

            // 3) 将 XWPFDocument转换成XHTML
            //生成html文件上级文件夹
            File folder = new File(htmlPath);
            if(!folder.exists()){
                folder.mkdirs();
            }
//            OutputStream out = new FileOutputStream(htmlFile);
//            XHTMLConverter.getInstance().convert(document, out, options);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, baos, options);
            String content = baos.toString();
            System.out.println(content);
            baos.close();

            // 4) 转换为项目访问路径
            String absolutePath = htmlFile.getAbsolutePath();
//            htmlPath = tempContextUrl + absolutePath.substring(absolutePath.indexOf("upload"));
//            presentationDto.setHtmlPath(htmlPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return rv;
        } catch (XWPFConverterException e) {
            e.printStackTrace();
            return rv;
        } catch (IOException e) {
            e.printStackTrace();
            return rv;
        }
        rv = 1;
        return rv;
    }

    public static void Word2007ToHtml() throws IOException {
//        D:/archive/JXB/JXB02/test/软件工程专业卓越工程师教育培养方案文字说明.docx
        String filepath = "D:/archive/JXB/JXB02/test/";
        String fileName = "软件工程专业卓越工程师教育培养方案文字说明.docx";
        String htmlName = "软件工程专业卓越工程师教育培养方案文字说明.html";
        final String file = filepath + fileName;
        File f = new File(file);
        if (!f.exists()) {
            System.out.println("Sorry File does not Exists!");
        } else {
            if (f.getName().endsWith(".docx") || f.getName().endsWith(".DOCX")) {

                //1) 加载word文档生成 XWPFDocument对象
                InputStream in = new FileInputStream(f);
                XWPFDocument document = new XWPFDocument(in);
                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
                File imageFolderFile = new File(filepath+"/images");
                XHTMLOptions options = XHTMLOptions.create().URIResolver(new BasicURIResolver("images"));
                options.setExtractor(new FileImageExtractor(imageFolderFile));
                options.setIgnoreStylesIfUnused(false);
                options.setFragment(true);
                // 3) 将 XWPFDocument转换成XHTML
//                OutputStream out = new FileOutputStream(new File(filepath + htmlName));
//                XHTMLConverter.getInstance().convert(document, out, options);


//                也可以使用字符数组流获取解析的内容
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                XHTMLConverter.getInstance().convert(document, baos, options);
                                String content = baos.toString();
                                System.out.println(content);
                                 baos.close();
            } else {
                System.out.println("Enter only MS Office 2007+ files");
            }
        }
    }

    public static String docxToHTML(String filePath) throws IOException {
        String path = getFilePath(filePath);
        String fileName = getFileNameWithoutExtension(fromPathGetFileName(filePath));
        String picturesPath = getFilePath(filePath) + "image";

        try {
            File f = new File(filePath);
            if (!f.exists()) {
                System.out.println("Sorry File does not Exists!");
            } else {
                if (f.getName().endsWith(".docx")
                        || f.getName().endsWith(".DOCX")) {

                    // 1) Load DOCX into XWPFDocument
                    InputStream in = new FileInputStream(f);
                    XWPFDocument document = new XWPFDocument(in);

                    // 2) Prepare XHTML options (here we set the IURIResolver to
                    // load images from a "word/media" folder)
                    File imageFolderFile = new File(picturesPath);
                    XHTMLOptions options = XHTMLOptions.create().URIResolver(
                            new FileURIResolver(imageFolderFile));
                    options.setExtractor(new FileImageExtractor(imageFolderFile));
                    options.setIgnoreStylesIfUnused(false);
                    options.setFragment(true);

                    // 3) Convert XWPFDocument to XHTML
                    String HTMLFilePath = path + fileName + ".html";
                    File file = new File(HTMLFilePath);
                    OutputStream out = new FileOutputStream(file);
                    XHTMLConverter.getInstance()
                            .convert(document, out, options);

                    // 3) 将 XWPFDocument转换成XHTML
//                    OutputStream out = new FileOutputStream(new File(filepath + htmlName));
//                    XHTMLConverter.getInstance().convert(document, out, options);
                    BufferedReader in1 = new BufferedReader(new FileReader(HTMLFilePath));
                    String str;
                    while ((str = in1.readLine()) != null) {
                        System.out.println(str);
                    }
                    in1.close();
                    // file.delete();
                } else {
                    System.out.println("Enter only MS Office 2007+ files");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 判断文件夹是否存在，不存在则新建
     *
     * @param path
     */
    private static void fileExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 从文件路径中获得文件名
     *
     * @param path 文件路径
     * @return 文件名
     */
    public static String fromPathGetFileName(String path) {
        String temp1[] = path.split("\\\\");
        String temp2[] = path.split("/");
        if (temp1.length == 1) {
            return temp2[temp2.length - 1];
        } else {
            return temp1[temp1.length - 1];
        }
    }


    public static String getFilePath(String filePath) {
        String temp1[] = filePath.split("\\\\");
        String temp2[] = filePath.split("/");
        if (temp1.length == 1) {
            return filePath.substring(0, filePath.lastIndexOf("/") + 1);
        } else {
            return filePath.substring(0, filePath.lastIndexOf("\\") + 1);

        }
    }

    /**
     * 获取文件名称（不带扩展名）
     *
     * @param fileName 文件名带扩展名
     * @return 文件名，不带扩展名
     */
    public static String getFileNameWithoutExtension(String fileName) {
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf("."));
        return fileNameWithoutExtension;
    }

    /**
     * 获取文件名的扩展名
     *
     * @param fileName 文件名
     * @return 扩展名
     */
    public static String getFileExtension(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension;
    }

    /**
     * 比较文件的扩展名
     *
     * @param fileName
     * @param extension
     * @return
     */
    public static boolean compareExtension(String fileName, String extension) {
        if (getFileExtension(fileName).equals(extension))
            return true;
        else
            return false;
    }

}
