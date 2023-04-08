package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.Privilege;
import com.test.mybatis_accessor.mapper.PrivilegeMapper;
import com.test.mybatis_accessor.service.IPrivilegeService;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl extends ServiceImpl<PrivilegeMapper, Privilege> implements IPrivilegeService {
}
