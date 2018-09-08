package org.jit.sose.service.impl;

import org.jit.sose.entity.Approve;
import org.jit.sose.entity.ArchiveSave;
import org.jit.sose.entity.ArchivesInfo;
import org.jit.sose.mapper.IArchivesMapper;
import org.jit.sose.service.IArchivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchivesService implements IArchivesService{
    @Autowired
    IArchivesMapper iArchivesMapper;

    public List<ArchivesInfo> getAllArchives() {
        return iArchivesMapper.getAllArchives();
    }

    public int insertArchive(ArchivesInfo archivesInfo) {
        return iArchivesMapper.insertArchive(archivesInfo);
    }

    public List<ArchivesInfo> getArchivesByClassifyId(String classifyId) {
        return iArchivesMapper.getArchivesByClassifyId(classifyId);
    }

    @Override
    public List<ArchivesInfo> queryArchives(String keyWord) {
        return iArchivesMapper.queryArchives(keyWord);
    }

}
