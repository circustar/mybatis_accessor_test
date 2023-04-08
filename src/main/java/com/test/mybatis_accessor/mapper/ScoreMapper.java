package com.test.mybatis_accessor.mapper;

import com.circustar.mybatis_accessor.mapper.CommonMapper;
import com.test.mybatis_accessor.entity.Score;
import org.springframework.context.annotation.DependsOn;

//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
@DependsOn("studentMapper")
public interface ScoreMapper extends CommonMapper<Score> {
}
