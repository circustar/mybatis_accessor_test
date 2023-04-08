package com.test.mybatis_accessor.mapper;

import com.circustar.mybatis_accessor.mapper.CommonMapper;
import com.test.mybatis_accessor.entity.StudentStatistics;
import org.springframework.context.annotation.DependsOn;

//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
@DependsOn("studentMapper")
public interface StudentAvgScoreMapper extends CommonMapper<StudentStatistics> {
}
