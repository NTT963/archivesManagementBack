package org.jit.sose.controller;

import org.jit.sose.poi.OfficeToHTMLUtil;
import org.jit.sose.poi.POIPptToHtml;
import org.jit.sose.service.impl.FileConvertService;
import org.jit.sose.utils.OfficeConverterUtil;
import org.jit.sose.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * Created by sunwujun on 2018/9/3 8:09
 */
@Controller
@RequestMapping("fileHandle")
public class FileController {

    @Autowired
    FileConvertService fileConvertService;

    @RequestMapping(value = "/convertOfficeToPDF.do")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Map<String, Object> convertOfficeToPDF(String officePath) throws Exception {
        return fileConvertService.convertOfficeToPDF(officePath);
    }
}
