package com.test.mybatis_accessor.dto;

import com.test.mybatis_accessor.entity.User;
import com.test.mybatis_accessor.service.IUserService;
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
@DtoEntityRelation(entityClass = User.class, service = IUserService.class)
public class UserDto {
    private Integer userId;

    private String name;

    private String password;

    private String email;

    private String tel;

    private Integer version;

    @Selector(tableColumn = "user_id", connector = Connector.EXISTS
            , valueExpression = "select * from user_role ur where ur.role_id = role.role_id and ur.user_id = #{userId} ")
    private List<RoleDto> roles;

    @Selector(connector = Connector.EXISTS
            , valueExpression = "select * from user_role ur inner join role_privilege rp on rp.role_id = ur.role_id where rp.privilege_id = privilege.privilege_id and ur.user_id = #{userId} ")
    private List<PrivilegeDto> privileges;
}
