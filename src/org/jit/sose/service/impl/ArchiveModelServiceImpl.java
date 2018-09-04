package org.jit.sose.service.impl;

import org.jit.sose.entity.ArchiveModel;
import org.jit.sose.mapper.ArchiveModelMapper;
import org.jit.sose.service.ArchiveModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArchiveModelServiceImpl implements ArchiveModelService{

    @Autowired
    ArchiveModelMapper archiveModelMapper;

    @Override
    public List<ArchiveModel> getAllArchiveModel() {
        return archiveModelMapper.getAllArchiveModel();
    }
}
