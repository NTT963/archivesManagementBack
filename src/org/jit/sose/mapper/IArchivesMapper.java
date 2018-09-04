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

    void insertArchiveSave(ArchiveSave archiveSave);
    List<ArchiveSave> getArchiveSaves(String userId);
    List<Approve> getApproveByUserId(String userId);

}
