package org.jit.sose.mapper;

import org.jit.sose.entity.Approve;
import org.jit.sose.entity.ArchiveSave;
import org.jit.sose.entity.ArchivesInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IArchivesMapper {
    List<ArchivesInfo> getAllArchives();
    int insertArchive(ArchivesInfo archivesInfo);
    List<ArchivesInfo> getArchivesByClassifyId(String classifyId);

    List<ArchivesInfo> queryArchives(String keyWord);
    List<ArchivesInfo> getMyUpload(String userId);
    int insertArchiveSave(ArchiveSave archiveSave);
    int cancleSaveArchive(ArchiveSave archiveSave);
    List<ArchivesInfo> getArchiveSaves(String userId);
    List<Approve> getApproveByUserId(String userId);

}
