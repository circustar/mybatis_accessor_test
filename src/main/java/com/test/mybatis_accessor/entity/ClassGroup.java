package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.circustar.mybatis_accessor.annotation.entity.IdReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ClassGroup extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer classGroupId;

    private String groupName;

    @IdReference
    private Integer upperClassGroupId;

    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private ClassGroup upperClassGroup;

    @TableField(exist = false)
    private List<ClassGroup> subClassGroupList;
}
