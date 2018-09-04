package org.jit.sose.service;

import org.jit.sose.entity.Student;
import org.jit.sose.entity.Teacher;

public interface TeacherService {

	boolean isbe(Teacher teacher);
	Teacher selectBystuNum(String tchNum);

}
