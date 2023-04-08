package com.test.mybatis_accessor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.mybatis_accessor.entity.RolePrivilege;
import com.test.mybatis_accessor.mapper.RolePrivilegeMapper;
import com.test.mybatis_accessor.service.IRolePrivilegeService;
import org.springframework.stereotype.Service;

@Service
public class RolePrivilegeServiceImpl extends ServiceImpl<RolePrivilegeMapper, RolePrivilege> implements IRolePrivilegeService {
}
