package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.annotation.dto.*;
import com.test.mybatis_accessor.entity.StudentStatistics;
import com.test.mybatis_accessor.service.IStudentAvgScoreService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;

@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = StudentStatistics.class, service = IStudentAvgScoreService.class)
public class StudentAverageScoreDto {
    @QueryGroupBy
    @QueryWhere
    private Integer studentId;

    @QueryColumn("max(student.name)")
    private String name;

    @QueryColumn("avg(score.score)")
    private BigDecimal averageScore;

    @QueryColumn("max(score.score)")
    private BigDecimal maxScore;

    @QueryColumn("min(score.score)")
    @QueryHaving(expression = "count(*) > 1")
    private BigDecimal minScore;

    @QueryJoin(tableAlias = "score",joinExpression = "score.student_id = student.student_id", order = 1)
    private ScoreDto scoreList;

}
