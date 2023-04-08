package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RolePrivilege implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer rolePrivilegeId;

    private Integer roleId;

    private Integer privilegeId;
}
