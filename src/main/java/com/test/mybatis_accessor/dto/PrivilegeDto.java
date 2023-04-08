package com.test.mybatis_accessor.dto;

import com.test.mybatis_accessor.entity.Privilege;
import com.test.mybatis_accessor.service.IPrivilegeService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = Privilege.class, service = IPrivilegeService.class)
public class PrivilegeDto {
    private Integer privilegeId;

    private String privilegeName;

    private Integer version;

}
