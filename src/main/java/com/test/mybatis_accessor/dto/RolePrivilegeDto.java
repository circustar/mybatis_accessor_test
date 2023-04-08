package com.test.mybatis_accessor.dto;

import com.test.mybatis_accessor.entity.RolePrivilege;
import com.test.mybatis_accessor.service.IRolePrivilegeService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = RolePrivilege.class, service = IRolePrivilegeService.class)
public class RolePrivilegeDto {
    private Integer rolePrivilegeId;

    private Integer roleId;

    private Integer privilegeId;
}
