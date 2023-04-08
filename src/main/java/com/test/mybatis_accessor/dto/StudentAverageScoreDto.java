package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.annotation.dto.*;
import com.test.mybatis_accessor.entity.StudentStatistics;
import com.test.mybatis_accessor.service.IStudentAvgScoreService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = StudentStatistics.class, service = IStudentAvgScoreService.class)
public class StudentAverageScoreDto {
    @QueryGroupBy
    @QueryWhere
    private Integer studentId;

    @QuerySelect("max(student.name)")
    private String name;

    @QuerySelect("avg(score.score)")
    private BigDecimal averageScore;

    @QuerySelect("max(score.score)")
    private BigDecimal maxScore;

    @QuerySelect("min(score.score)")
    @QueryHaving(expression = "count(*) > 1")
    private BigDecimal minScore;

    @QueryJoin(tableAlias = "score",joinExpression = "score.student_id = student.student_id", order = 1)
    private ScoreDto scoreList;

}
