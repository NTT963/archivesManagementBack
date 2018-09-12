package org.jit.sose.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jit.sose.mapper.AdminMapper;
import org.jit.sose.mapper.IArchivesMapper;
import org.jit.sose.mapper.ITotalChart;
import org.jit.sose.service.TeacherService;
import org.jit.sose.service.impl.ArchivesService;
import org.jit.sose.service.impl.FileConvertService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Test {
    //	@Autowired
//	ArchiveModelService archiveModelService;
//	@Autowired
//	StudentServiceImpl studentService;
    @Autowired
    ArchivesService archivesService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    FileConvertService fileConvertService;

//	@Value("#{configProperties['virtualURL']}")
//	private String virtualURL;
//
//	@Value("#{configProperties['physicalURL']}")
//	private String physicalURL;


//	@Value("${virtualURL}")
//	private String virtualURL;

    @Autowired
    IArchivesMapper iArchivesMapper;

    @Autowired
    ITotalChart iTotalChart;


    @org.junit.Test
    public void insertArchive() {
//        Map<String,Object> resultMap = new HashMap<>();
//        List<Map<String, Object>> LineGroup = iTotalChart.getLineGroudByDate();
//        List<Map<String, Object>> weekExtraData = iTotalChart.getWeekEtraData();
//        List<String> titles = iTotalChart.getBarTitle();
//        String[] XData = new String[7];
//        long[] YTotal = new long[7];
//        int index = 0;
//        for (Map<String, Object> item : LineGroup) {
//            XData[index] = (String) item.get("data");
//            YTotal[index] = (Long) item.get("total");
//            index++;
//        }
//        boolean isFound = false;
//        Map<String,Object> weekData = new HashMap<>();
//        for (String title : titles) {
//            long[] counts = new long[7];
//            for (int i = 0; i < XData.length; i++) {
//                System.out.println(title + " : " + XData[i]);
//                for (Map<String, Object> item : weekExtraData) {
//                    if (title.equals(item.get("title")) && XData[i].equals(item.get("data"))) {
//                        isFound = true;
//                        counts[i] = (long) item.get("count");
//                        break;
//                    }
//                }
//                if (!isFound) {
//                    counts[i] = 0;
//                }
//            }
//            isFound = false;
//            weekData.put(title,counts);
//        }
//        resultMap.put("xData",XData);
//        resultMap.put("legend",titles);
//        resultMap.put("weekData",weekData);
//        resultMap.put("total",YTotal);
//        String json = JSONObject.toJSONString(resultMap);
//        System.out.println(json);
        System.out.println(iTotalChart.getArchiveCount());

    }

    @org.junit.Test
    public void test() {

        String path = "/archive/中山大学2017硕士录取名单（公示）.xls";

        // String path = "D:\\temp\\temp\\test.xlsx";
        File file = new File(path);
        InputStream is = null;
        Workbook workbook = null;
        try {
            is = new FileInputStream(file);
            if (path.endsWith(".xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (path.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
            if (workbook != null) {
                int sheetCount = workbook.getNumberOfSheets();
                if (sheetCount > 0) {
                    // 文本内容
                    StringBuilder content = new StringBuilder();
                    for (int i = 0; i < sheetCount; i++) {
                        Sheet sheet = workbook.getSheetAt(i);
                        content.append(sheet.getSheetName());
                        for (int rownum = sheet.getFirstRowNum(); rownum <= sheet.getLastRowNum(); rownum++) {
                            Row row = sheet.getRow(rownum);
                            if (row == null || row.getFirstCellNum() < 0) {
                                break;
                            }
                            for (int columnnum = row.getFirstCellNum(); columnnum <= row
                                    .getLastCellNum(); columnnum++) {
                                String cellValue = getCellValue(row.getCell(columnnum));
                                content.append(cellValue);
                                if (content.length() > 500) {// 没500字输出一次
                                    System.out.println(content.toString());
                                    content.delete(0, content.length());
                                }
                            }
                        }
                    }
                    if (content.length() > 0) {
                        System.out.println(content.toString());
                    }

                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
//		archivesService.insertApprove(new Approve("001","test","JCB"));
//
//		System.out.println(archivesService.getApproveByUserId("001").get(0).getArchiveID());
        System.out.println();

    }

    public String getCellValue(org.apache.poi.ss.usermodel.Cell cell) {
        if (cell == null) {
            return "";
        }
        // 都按文本格式读取
        ((org.apache.poi.ss.usermodel.Cell) cell).setCellType(CellType.STRING);
        return ((org.apache.poi.ss.usermodel.Cell) cell).getStringCellValue();
    }

//	private String getCellValue(Cell cell) {
//		if (cell == null) {
//			return "";
//		}
//		// 都按文本格式读取
//		((org.apache.poi.ss.usermodel.Cell) cell).setCellType(CellType.STRING);
//		return ((org.apache.poi.ss.usermodel.Cell) cell).getStringCellValue();
//	}

    @org.junit.Test
    public void addArchive() {
        System.out.println(StringUtil.WIN_1252);

//		String filePath = "/archive//JXM/JXM03/unix-实验报告.doc";
//		File file = new File(filePath);
//		String str = "";
//		try {
//			FileInputStream fis = new FileInputStream(file);
//			HWPFDocument doc = new HWPFDocument(fis);
//			String doc1 = doc.getDocumentText();
//			System.out.println(doc1);
//			StringBuilder doc2 = doc.getText();
//			System.out.println(doc2);
//			Range rang = doc.getRange();
//			String doc3 = rang.text();
//			System.out.println(doc3);
//			fis.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

    }
//
//
//	File file = new File(chatFilePath + "/" + user_phone + ".txt");
//		if (!file.exists()) {//如果文件不存在
//		try {
//			file.createNewFile();
//			logger.info("聊天记录文件不存在,创建一个:" + user_phone + ".txt");
//		} catch (IOException e) {
//			e.printStackTrace();

    @org.junit.Test
    public void uploadFile() {
        String strPath = "/archive/aaa.txt";
        File file = new File(strPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @org.junit.Test
    public void testPOI() {
        File file = new File("/archive/JXM/JXM03/金陵科技学院课表编排规程.doc");
        String str = "";
        try {
            FileInputStream fis = new FileInputStream(file);
//			System.out.println(readDoc(fis));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> readXls(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        // HSSFWorkbook 标识整个excel
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<List<String>> result = new ArrayList<List<String>>();
        int size = hssfWorkbook.getNumberOfSheets();
        // 循环每一页，并处理当前循环页
        for (int numSheet = 0; numSheet < size; numSheet++) {
            // HSSFSheet 标识某一页
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                // HSSFRow表示行
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                int minColIx = hssfRow.getFirstCellNum();
                int maxColIx = hssfRow.getLastCellNum();
                List<String> rowList = new ArrayList<String>();
                // 遍历改行，获取处理每个cell元素
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    // HSSFCell 表示单元格
                    HSSFCell cell = hssfRow.getCell(colIx);
                    if (cell == null) {
                        continue;
                    }
                    rowList.add(cell.toString());
                }
                result.add(rowList);
            }
        }
        return result;
    }

    /**
     * @Title: readXlsx @Description: 处理Xlsx文件 @param @param
     * path @param @return @param @throws Exception 设定文件 @return List<List<String>>
     * 返回类型 @throws
     */
    public List<List<String>> readXlsx(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<List<String>> result = new ArrayList<List<String>>();
        // 循环每一页，并处理当前循环页
        for (Sheet xssfSheet : xssfWorkbook) {
            if (xssfSheet == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = (XSSFRow) xssfSheet.getRow(rowNum);
                int minColIx = xssfRow.getFirstCellNum();
                int maxColIx = xssfRow.getLastCellNum();
                List<String> rowList = new ArrayList<String>();
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    XSSFCell cell = xssfRow.getCell(colIx);
                    if (cell == null) {
                        continue;
                    }
                    rowList.add(cell.toString());
                }
                result.add(rowList);
            }
        }
        return result;
    }

    @org.junit.Test
    public void readDoc() throws IOException, ParserConfigurationException {
//		D:archive/JXB/JXB02/软件工程专业卓越工程师教育培养方案文字说明.docx
        final String path = "D:\\archive\\JXB\\JXB02\\";
        final String file = "软件工程专业卓越工程师教育培养方案文字说明.docx";
        InputStream input = new FileInputStream(path + file);
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType,
                                      String suggestedName, float widthInches, float heightInches) {
                return suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        List pics = wordDocument.getPicturesTable().getAllPictures();
        if (pics != null) {
            for (int i = 0; i < pics.size(); i++) {
                Picture pic = (Picture) pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(path
                            + pic.suggestFullFileName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
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
        System.out.println(content);
        FileUtils.writeStringToFile(new File(path, file + ".html"), content, "utf-8");
    }

    @org.junit.Test
    public void fileExists() {
        String path = "D:/archive/JXP/JXP03/JXP0302/金陵科技学院监考守则.doc";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
