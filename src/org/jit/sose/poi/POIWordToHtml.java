package org.jit.sose.poi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

public class POIWordToHtml {
	private static final String ENCODING = "UTF-8";// UTF-8
	public static String wordToHtml(String sourcePath, String picturesPath,String targetPath){
		String ext = FileUtils.GetFileExt(sourcePath);
		File picturesDir = new File(picturesPath);
		if (!picturesDir.isDirectory()) {
			picturesDir.mkdirs();
		}
		String content = null;
		try {
			if (ext.equals("doc")) {
				HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(sourcePath));
				WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
						DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
				wordToHtmlConverter.setPicturesManager(new PicturesManager() {
					@Override
					public String savePicture(byte[] content, PictureType pictureType, String suggestedName,
							float widthInches, float heightInches) {
						File file = new File(picturesPath + "\\" + suggestedName);
						FileOutputStream fos = null;
						try {
							fos = new FileOutputStream(file);
							fos.write(content);
							fos.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return picturesPath + "/" + suggestedName;
					}
				});
				wordToHtmlConverter.processDocument(wordDocument);
				Document htmlDocument = wordToHtmlConverter.getDocument();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				DOMSource domSource = new DOMSource(htmlDocument);
				StreamResult streamResult = new StreamResult(out);

				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer serializer = tf.newTransformer();

				serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
				serializer.setOutputProperty(OutputKeys.INDENT, "yes");
				serializer.setOutputProperty(OutputKeys.METHOD, "html");
				serializer.transform(domSource, streamResult);
				out.close();
				FileUtils.writeFile(new String(out.toByteArray()), targetPath);
				content = out.toString();
			} else if (ext.equals("docx")) {
				InputStream in = new FileInputStream(new File(sourcePath));
				XWPFDocument document = new XWPFDocument(in);
				XHTMLOptions options = XHTMLOptions.create();
				options.setExtractor(new FileImageExtractor(picturesDir));
				options.URIResolver(new BasicURIResolver(picturesPath));

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				XHTMLConverter.getInstance().convert(document, baos, options);
				baos.close();
				content = baos.toString();
				FileUtils.writeFile(content, targetPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}
}
