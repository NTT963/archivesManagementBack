package org.jit.sose.service;

import org.jit.sose.entity.Student;

public interface StudentService {

	boolean isbe(Student student);

	Student selectBystuNum(String stunum);

}
