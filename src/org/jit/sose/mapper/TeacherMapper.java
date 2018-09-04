package org.jit.sose.mapper;

import org.jit.sose.entity.Teacher;

public interface TeacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

	Teacher selectByNumAndName(Teacher teacher);

    Teacher selectBystuNum(String tchNum);
}