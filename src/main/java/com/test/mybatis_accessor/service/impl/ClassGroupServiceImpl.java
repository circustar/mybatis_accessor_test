package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.ClassGroup;
import com.test.mybatis_accessor.entity.Course;
import com.test.mybatis_accessor.mapper.ClassGroupMapper;
import com.test.mybatis_accessor.mapper.CourseMapper;
import com.test.mybatis_accessor.service.IClassGroupService;
import com.test.mybatis_accessor.service.ICourseService;
import org.springframework.stereotype.Service;

@Service
public class ClassGroupServiceImpl extends ServiceImpl<ClassGroupMapper, ClassGroup> implements IClassGroupService {
}
