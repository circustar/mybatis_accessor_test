package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.entity.StudentCourse;
import com.test.mybatis_accessor.mapper.StudentCourseMapper;
import com.test.mybatis_accessor.mapper.StudentMapper;
import com.test.mybatis_accessor.service.IStudentCourseService;
import com.test.mybatis_accessor.service.IStudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements IStudentCourseService {
}
