package org.jit.sose.utils;
import java.io.File;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jit.sose.poi.POIPptToHtml;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.sun.org.apache.regexp.internal.recompile;

public class OfficeConverterUtil {
	/**
	 * log
	 */
	private static Logger logger = Logger.getLogger(OfficeConverterUtil.class);
	private static final int WDFO_RMATPDF = 17;
	private static final int XLTYPE_PDF = 0;
	private static final int PPT_SAVEAS_PDF = 32;
	public static final int WORD_HTML = 8;
	public static final int WORD_TXT = 7;
	public static final int EXCEL_HTML = 44;
	public static final int PPT_SAVEAS_JPG = 17;
	// private static final int msoTrue = -1;
	// private static final int msofalse = 0;
 
	/**
	 * @param argInputFilePath
	 * @param argPdfPath
	 * @return
	 */
	public static boolean officeFileConverterToPdf(String argInputFilePath, String argPdfPath) {
		if (argInputFilePath.isEmpty() || argPdfPath.isEmpty() || getFileSufix(argInputFilePath).isEmpty()) {
			logger.debug("输入或输出文件路徑有誤！");
			return false;
		}
 
		String suffix = getFileSufix(argInputFilePath);
 
		File file = new File(argInputFilePath);
		if (!file.exists()) {
			logger.debug("文件不存在！");
			return false;
		}
 
		// PDF如果不存在则创建文件夹
		file = new File(getFilePath(argPdfPath));
		if (!file.exists()) {
			file.mkdir();
		}
 
		// 如果输入的路径为PDF 则生成失败
		if (suffix.equals("pdf")) {
			System.out.println("PDF not need to convert!");
			return false;
		}
 
		if (suffix.equals("doc") || suffix.equals("docx") || suffix.equals("txt")) {
			return wordToPDF(argInputFilePath, argPdfPath);
		} else if (suffix.equals("xls") || suffix.equals("xlsx")) {
			return excelToPdf(argInputFilePath, argPdfPath);
		} else if (suffix.equals("ppt") || suffix.equals("pptx")) {
//			return pptToPdf(argInputFilePath, argPdfPath);
//			 return PPT2PDF(argInputFilePath, argPdfPath);
			 return POIPptToHtml.pptToHtml(argInputFilePath, argPdfPath);
		}
 
		return false;
	}
 
	/**
	 * converter word to pdf
	 * 
	 * @param wordPath
	 * @param pdfPath
	 * @return
	 */
	public static boolean wordToPDF(String wordPath, String pdfPath) {
		ActiveXComponent msWordApp = new ActiveXComponent("Word.Application");
		msWordApp.setProperty("Visible", new Variant(false));
 
		Dispatch docs = Dispatch.get(msWordApp, "Documents").toDispatch();
		// long pdfStart = System.currentTimeMillis();
		Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] { wordPath, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
 
		deletePdf(pdfPath);
 
		Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { pdfPath, new Variant(WDFO_RMATPDF) }, new int[1]);
		// long pdfEnd = System.currentTimeMillis();
		logger.debug(wordPath + ",pdf转换完成..");
		if (null != doc) {
			Dispatch.call(doc, "Close", false);
		}
		return true;
	}
 
	/**
	 * excel to pdf
	 * 
	 * @param inputFile
	 * @param pdfFile
	 * @return
	 */
	public static boolean excelToPdf(String inputFile, String pdfFile) {
		ActiveXComponent activeXComponent = new ActiveXComponent("Excel.Application");
		activeXComponent.setProperty("Visible", false);
 
		deletePdf(pdfFile);
 
		Dispatch excels = activeXComponent.getProperty("Workbooks").toDispatch();
		Dispatch excel = Dispatch.call(excels, "Open", inputFile, false, true).toDispatch();
		Dispatch.call(excel, "ExportAsFixedFormat", XLTYPE_PDF, pdfFile);
		Dispatch.call(excel, "Close", false);
		activeXComponent.invoke("Quit");
		return true;
	}
	
	
	 private static boolean PPT2PDF(String inputFile, String pdfFile) {
		         try {
		              ComThread.InitSTA(true);
		              ActiveXComponent app = new ActiveXComponent("KWPP.Application");
		  //            app.setProperty("Visible", false);
		              System.out.println("开始转化PPT为PDF...");
		             long date = new Date().getTime();
		              Dispatch ppts = app.getProperty("Presentations").toDispatch();
		              Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true, // ReadOnly
		                  //    false, // Untitled指定文件是否有标题
		                      false// WithWindow指定文件是否可见
		              ).toDispatch();
		              Dispatch.invoke(ppt, "SaveAs", Dispatch.Method, new Object[]{
		                     pdfFile,new Variant(PPT_SAVEAS_PDF)},new int[1]);
		              System.out.println("PPT");
		              Dispatch.call(ppt, "Close");
		              long date2 = new Date().getTime();
		              int time = (int) ((date2 - date) / 1000);
		              app.invoke("Quit");
		              return true;
		          } catch (Exception e) {
		              // TODO: handle exception
		              return false;
		          }
		      }
 
	/**
	 * ppt to pdf
	 * 
	 * @param inputFile
	 * @param pdfFile
	 * @return
	 */
	public static boolean pptToPdf(String inputFile, String pdfFile) {
//		ComThread.InitSTA();
		ActiveXComponent activeXComponent = new ActiveXComponent("PowerPoint.Application");
//		activeXComponent.setProperty("Visible", new Variant(false));
		Dispatch ppts = activeXComponent.getProperty("Presentations").toDispatch();
 
		deletePdf(pdfFile);
 
		Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, false, // ReadOnly
				true, // Untitled指定文件是否有标题
				true// WithWindow指定文件是否可见
		).toDispatch();
 
//		Dispatch ppt = Dispatch.invoke(ppts, "Open", Dispatch.Method, new Object[] { inputFile, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
 
//		Dispatch.call(ppt, "SaveAs", pdfFile, PPT_SAVEAS_PDF);
//		Dispatch.call(ppt, "SaveAs", pdfFile, new Variant(PPT_SAVEAS_PDF));
//		Dispatch.call(ppt, "SaveAs", pdfFile, new Variant(PPT_SAVEAS_PDF));
//		Dispatch.invoke(ppt, "SaveAs", Dispatch.Method, new Object[] { pdfFile, PPT_SAVEAS_PDF }, new int[1]);
//		Dispatch.invoke(ppt, "SaveAs", Dispatch.Method, new Object[] { new Variant(PPT_SAVEAS_PDF) }, new int[1]);
		Dispatch.callN(ppt, "SaveAs",  new Variant(pdfFile));
		
		Dispatch.call(ppt, "Close");
 
		activeXComponent.invoke("Quit");
//		ComThread.Release();
		return true;
	}
 
	/**
	 * ppt to img
	 * 
	 * @param inputFile
	 * @param imgFile
	 * @return
	 */
	public static boolean pptToImg(String inputFile, String imgFile) {
		// 打开word应用程序
		ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
		// 设置word不可见，office可能有限制
		// app.setProperty("Visible", false);
		// 获取word中国所打开的文档，返回Documents对象
		Dispatch files = app.getProperty("Presentations").toDispatch();
		// 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
		Dispatch file = Dispatch.call(files, "open", inputFile, true, true, false).toDispatch();
		// 调用Document对象的SaveAs方法，将文档保存为pdf格式
		// Dispatch.call(doc, "ExportAsFixedFormat", outputFile,
		// PPT_TO_PDF);
		Dispatch.call(file, "SaveAs", imgFile, PPT_SAVEAS_JPG);
		// 关闭文档
		// Dispatch.call(file, "Close", false);
		Dispatch.call(file, "Close");
		// 关闭word应用程序
		// app.invoke("Quit", 0);
		app.invoke("Quit");
		return true;
	}
 
	/**
	 * get file extension
	 * 
	 * @param argFilePath
	 * @return
	 */
	public static String getFileSufix(String argFilePath) {
		int splitIndex = argFilePath.lastIndexOf(".");
		return argFilePath.substring(splitIndex + 1);
	}
 
	/**
	 * subString file path
	 * 
	 * @param argFilePath
	 *            file path
	 * @return filePaths
	 */
	public static String getFilePath(String filePath) {
		System.out.println(filePath);
		String temp1[] = filePath.split("\\\\");
        String temp2[] = filePath.split("/");
        if (temp1.length == 1) {
            return filePath.substring(0, filePath.lastIndexOf("/") + 1);
        } else {
            return filePath.substring(0, filePath.lastIndexOf("\\") + 1);

        }
//		int pathIndex = filePath.lastIndexOf("/");
//		return filePath.substring(0, pathIndex);
	}
 
	/**
	 * 如果PDF存在则删除PDF
	 * 
	 * @param pdfPath
	 */
	private static void deletePdf(String pdfPath) {
		File pdfFile = new File(pdfPath);
		if (pdfFile.exists()) {
			pdfFile.delete();
		}
	}

}
