package com.test.mybatis_accessor.dto;

import com.test.mybatis_accessor.entity.Role;
import com.test.mybatis_accessor.service.IRoleService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.circustar.mybatis_accessor.annotation.Connector;
import com.circustar.mybatis_accessor.annotation.dto.Selector;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = Role.class, service = IRoleService.class)
public class RoleDto {
    private Integer roleId;

    private String roleName;

    private Integer version;

    @Selector(connector = Connector.EXISTS
            , valueExpression = "select * from role_privilege rp where rp.privilege_id = privilege.privilege_id and rp.role_id = #{roleId}")
    @Selector(tableColumn = "version", connector = Connector.EQ, valueExpression = "1")
    private List<PrivilegeDto> privileges;

    @Selector(tableColumn = "role_id", valueExpression = "#{roleId}")
    private List<RolePrivilegeDto> rolePrivileges;
}
