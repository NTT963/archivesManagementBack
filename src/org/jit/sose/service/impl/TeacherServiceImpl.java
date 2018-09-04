package org.jit.sose.service.impl;

import org.jit.sose.entity.Teacher;
import org.jit.sose.mapper.TeacherMapper;
import org.jit.sose.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TeacherServiceImpl implements TeacherService{
	
	@Autowired
	private TeacherMapper TeacherMapper;

	@Override
	public boolean isbe(Teacher teacher) {
		Teacher teacher2 = TeacherMapper.selectByNumAndName(teacher);
		System.out.println(teacher2);
		if (teacher2.getId()>0) {
			return true;
		}
		return false;
	}

	@Override
	public Teacher selectBystuNum(String tchNum) {
		return TeacherMapper.selectBystuNum(tchNum);
	}
}
