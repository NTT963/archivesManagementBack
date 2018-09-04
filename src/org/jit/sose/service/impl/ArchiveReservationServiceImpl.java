package org.jit.sose.service.impl;

import org.jit.sose.entity.ArchiveReservation;
import org.jit.sose.mapper.ArchiveReservationMapper;
import org.jit.sose.service.ArchiveReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArchiveReservationServiceImpl implements ArchiveReservationService{

	@Autowired
	private ArchiveReservationMapper ArchiveReservationMapper;
	
	
	@Override
	public boolean insertReservation(ArchiveReservation archiveReservation) {

		if (ArchiveReservationMapper.insertSelective(archiveReservation)>0) {
			return true;
		}
		return false;
	}

}
