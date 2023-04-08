package com.test.mybatis_accessor.dto;

import com.test.mybatis_accessor.entity.UserRole;
import com.test.mybatis_accessor.service.IUserRoleService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = UserRole.class, service = IUserRoleService.class)
public class UserRoleDto {
    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

}
