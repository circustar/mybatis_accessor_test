package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.PersonInfo;
import com.test.mybatis_accessor.entity.PersonInfo2;
import com.test.mybatis_accessor.mapper.PersonInfo2Mapper;
import com.test.mybatis_accessor.mapper.PersonInfoMapper;
import com.test.mybatis_accessor.service.IPersonInfo2Service;
import com.test.mybatis_accessor.service.IPersonInfoService;
import org.springframework.stereotype.Service;

@Service
public class PersonInfo2ServiceImpl extends ServiceImpl<PersonInfo2Mapper, PersonInfo2> implements IPersonInfo2Service {
}
