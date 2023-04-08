package com.test.mybatis_accessor.mapper;

import com.circustar.mybatis_accessor.mapper.CommonMapper;
import com.test.mybatis_accessor.entity.Course;

//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface CourseMapper extends CommonMapper<Course> {
}
