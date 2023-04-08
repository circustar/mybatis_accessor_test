package com.test.mybatis_accessor.mapper;

import com.circustar.mybatis_accessor.mapper.CommonMapper;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.entity.StudentCourse;

//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface StudentCourseMapper extends CommonMapper<StudentCourse> {
}
