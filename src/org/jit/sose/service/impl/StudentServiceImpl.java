package org.jit.sose.service.impl;

import org.jit.sose.entity.Student;
import org.jit.sose.mapper.StudentMapper;
import org.jit.sose.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentMapper StudentMapper;

	@Override
	public boolean isbe(Student student) {
		Student student2 = StudentMapper.selectByNumAndName(student);
		if (student2.getId()>0) {
			return true;
		}
		return false;
	}

	@Override
	public Student selectBystuNum(String stunum) {
		return StudentMapper.selectBystuNum(stunum);
	}

}
