package org.jit.sose.service;

import org.jit.sose.entity.Approve;
import org.jit.sose.entity.ArchiveSave;
import org.jit.sose.entity.ArchivesInfo;

import java.util.List;

public interface IArchivesService {
    List<ArchivesInfo> getAllArchives();
    int insertArchive(ArchivesInfo archivesInfo);
    List<ArchivesInfo> getArchivesByClassifyId(String classifyId);
    List<ArchivesInfo> queryArchives(String keyWord);
}
