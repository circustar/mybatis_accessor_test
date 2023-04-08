package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.PersonInfo;
import com.test.mybatis_accessor.entity.ProductOrder;
import com.test.mybatis_accessor.mapper.PersonInfoMapper;
import com.test.mybatis_accessor.mapper.ProductOrderMapper;
import com.test.mybatis_accessor.service.IPersonInfoService;
import com.test.mybatis_accessor.service.IProductOrderService;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoServiceImpl extends ServiceImpl<PersonInfoMapper, PersonInfo> implements IPersonInfoService {
}
