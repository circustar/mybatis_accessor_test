package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.dto.DeleteFlag;
import com.circustar.mybatis_accessor.annotation.dto.QueryJoin;
import com.circustar.mybatis_accessor.annotation.dto.UpdateCascade;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.Score;
import com.test.mybatis_accessor.service.IScoreService;
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
@DtoEntityRelation(entityClass = Score.class, service = IScoreService.class)
public class ScoreDto implements Serializable {
    private Integer scoreId;
    private Integer studentId;
    private Integer courseId;
    private BigDecimal score;
    private Integer version;
    private String name;

    @DeleteFlag
    private Integer deleted;

    @UpdateCascade(false)
    @QueryJoin(tableAlias = "student", joinExpression = "student.student_id = score.student_id and score.deleted = 0" , order = 1)
    private StudentDto student;
}
