package com.test.mybatis_accessor.mapper;

import com.circustar.mybatis_accessor.mapper.CommonMapper;
import com.test.mybatis_accessor.entity.PersonInfo;
import com.test.mybatis_accessor.entity.ProductOrder;

//@CacheNamespace(implementation= MybatisRedisCache.class,eviction=MybatisRedisCache.class)
public interface PersonInfoMapper extends CommonMapper<PersonInfo> {
}
