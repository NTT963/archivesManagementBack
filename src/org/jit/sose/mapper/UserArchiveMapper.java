package org.jit.sose.mapper;

import org.jit.sose.entity.UserArchive;

public interface UserArchiveMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserArchive record);

    int insertSelective(UserArchive record);

    UserArchive selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserArchive record);

    int updateByPrimaryKey(UserArchive record);
}