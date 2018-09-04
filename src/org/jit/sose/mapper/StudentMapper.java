package org.jit.sose.mapper;

import org.jit.sose.entity.Student;

public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    Student selectBystuNum(String stunum);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

	Student selectByNumAndName(Student student);
}