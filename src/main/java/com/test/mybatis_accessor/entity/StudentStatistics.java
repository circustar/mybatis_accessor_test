package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("student")
public class StudentStatistics implements Serializable {
    private Integer studentId;

    private String name;

    private BigDecimal maxScore;

    private BigDecimal minScore;

    private BigDecimal averageScore;
}
