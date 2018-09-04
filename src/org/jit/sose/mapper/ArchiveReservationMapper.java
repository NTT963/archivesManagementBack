package org.jit.sose.mapper;

import org.jit.sose.entity.ArchiveReservation;

public interface ArchiveReservationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArchiveReservation record);

    int insertSelective(ArchiveReservation record);

    ArchiveReservation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArchiveReservation record);

    int updateByPrimaryKey(ArchiveReservation record);
}