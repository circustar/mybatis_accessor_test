package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.test.mybatis_accessor.dto.TeacherDto;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.service.ITeacherService;
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
@DtoEntityRelation(dtoClass = TeacherDto.class, service = ITeacherService.class)
public class Teacher implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer teacherId;
    private String name;
    private Integer gender;
    private String mobile;

    @TableLogic
    private Integer deleted;
    @Version
    private Integer version;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
