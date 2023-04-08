package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.Connector;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.annotation.dto.QueryJoin;
import com.circustar.mybatis_accessor.annotation.dto.QueryWhere;
import com.test.mybatis_accessor.entity.ClassGroup;
import com.test.mybatis_accessor.service.IClassGroupService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = ClassGroup.class, service = IClassGroupService.class)
public class ClassGroupDto extends BaseDto {
    private Integer classGroupId;

    @QueryWhere(connector = Connector.LIKE_RIGHT)
    private String groupName;

    private Integer upperClassGroupId;

    private Integer deleted;

    @QueryJoin(tableAlias = "sg", joinExpression = "class_group.class_group_id = sg.upper_class_group_id")
    private List<ClassGroupDto> subClassGroups;

    @QueryJoin(tableAlias = "ug", joinExpression = "ug.class_group_id = class_group.upper_class_group_id")
    private ClassGroupDto upperClassGroup;
}
