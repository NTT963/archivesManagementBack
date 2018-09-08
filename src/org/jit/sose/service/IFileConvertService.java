package org.jit.sose.service;

import java.util.Map;

/**
 * Created by sunwujun on 2018/9/5 11:56
 */
public interface IFileConvertService {
    Map<String, Object> convertOfficeToPDF(String officePath);
    String downloadArchive(String virtualURL);

}
