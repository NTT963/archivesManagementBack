package org.jit.sose.mapper;

import org.jit.sose.entity.Admin;
import org.jit.sose.entity.Approve;
import org.jit.sose.entity.ArchivesInfo;

import java.util.List;

public interface AdminMapper {

    int updateArchiveState(int archiveId);

    int insertApprove(Approve approve);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin selectByNumAndName(Admin admin);

    List<ArchivesInfo> getArchiveWaitMeApprove(String adminId);
}