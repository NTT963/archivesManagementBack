package org.jit.sose.poi;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

/**
 * Created by sunwujun on 2018/9/3 4:22
 */
public class OfficeToHTMLUtil {
    public static boolean officeToHtml(String filePath) throws IOException, ParserConfigurationException {
        if (compareExtension(fromPathGetFileName(filePath), "doc")) {
            System.out.println("doc类型");
            docToHTML(filePath);
        } else if (compareExtension(fromPathGetFileName(filePath), "docx")) {
            System.out.println("docx类型");
        } else if (compareExtension(fromPathGetFileName(filePath), "ppt")) {
            System.out.println("ppt类型");
        } else if (compareExtension(fromPathGetFileName(filePath), "pptx")) {
            System.out.println("pptx类型");
        }
        return true;
    }

    /**
     * 将doc格式的word 转化为 HTML
     *
     * @param filePath word文件的绝对路径
     * @return 转化后的HTML的绝对路径
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static String docToHTML(String filePath) throws IOException, ParserConfigurationException {
        String path = getFilePath(filePath);
        String fileName = getFileNameWithoutExtension(fromPathGetFileName(filePath));
        String picturesPath = getFilePath(filePath) + "image";
        File picturesDir = new File(picturesPath);
        if (!picturesDir.isDirectory()) {
            picturesDir.mkdirs();
        }
        InputStream input = new FileInputStream(filePath);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName,
                                      float widthInches, float heightInches) {
                File file = new File(picturesPath + "\\" + fileName + suggestedName);
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(content);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return picturesPath + "/" + fileName + suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = null;
        try {
            serializer = tf.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        try {
            serializer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        outStream.close();
        String content = new String(outStream.toByteArray());
        content = content.replaceAll("</style>", ".b2{ margin: 1.0in 25% 0 25%;}</style>");
        FileUtils.writeStringToFile(new File(path, fileName + ".html"), content, "utf-8");
        System.out.println(path + fileName + ".html");
        return path + fileName + ".html";
    }

    //判断文件是否存在
    public static boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }



    public static String getFilePath(String filePath) {
        String temp1[] = filePath.split("\\\\");
//        String temp2[] = filePath.split("/");
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


}
