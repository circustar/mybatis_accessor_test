package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Score implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer scoreId;
    private Integer studentId;
    private Integer courseId;
    private BigDecimal score;
    private String name;

    @TableLogic
    private Integer deleted;

    @Version
    private Integer version;

    @TableField(exist = false)
    private Student student;
}
