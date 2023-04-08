package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
//@TableName(autoResultMap = true)
public class Student implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer studentId;
    private String name;
    private Integer grade;

    @TableField(exist = false)
    private String score_name;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private List<Score> scoreList;

}
