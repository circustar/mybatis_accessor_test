package com.test.mybatis_accessor.mapper;

import com.test.mybatis_accessor.entity.User;
import com.circustar.mybatis_accessor.mapper.CommonMapper;

//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface UserMapper extends CommonMapper<User> {
}
