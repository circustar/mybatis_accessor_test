package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.circustar.mybatis_accessor.annotation.entity.IdReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@TableName("person_info")
public class PersonInfo2 extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer personId;

    @IdReference
    private Integer leaderId;

    private String personName;

    private BigDecimal point;

    private Integer teamCount;

    private BigDecimal teamTotalPoint;

    private BigDecimal teamAveragePoint;

    private BigDecimal weight;

    private BigDecimal sharePoll;

    @TableLogic
    private Integer deleted;

    @TableField(exist = false)
    private List<PersonInfo2> personInfoList;

}
