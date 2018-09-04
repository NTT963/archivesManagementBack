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
    void insertArchiveSave(ArchiveSave archiveSave);
    List<ArchiveSave> getArchiveSaves(String userId);
    List<Approve> getApproveByUserId(String userId);
}
