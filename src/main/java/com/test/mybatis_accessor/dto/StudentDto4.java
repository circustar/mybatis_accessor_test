package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.*;
import com.circustar.mybatis_accessor.annotation.dto.DeleteFlag;
import com.circustar.mybatis_accessor.annotation.dto.QueryJoin;
import com.circustar.mybatis_accessor.annotation.dto.QueryWhere;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.service.IStudentService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = Student.class, service = IStudentService.class)
public class StudentDto4 {
    private Integer studentId;

    @QueryWhere(connector = Connector.LIKE_RIGHT)
    private String name;

    private Integer grade;

    @DeleteFlag
    private Integer deleted;

    private Integer version;

    @QueryJoin(tableAlias = "t1", joinType = QueryJoin.JoinType.LEFT
            , joinExpression = "t1.student_id = student.student_id and t1.deleted = 0")
    @QueryWhere(expression = "select * from student_group g where g.student_id = student.student_id"
            , connector = Connector.EXISTS)
    private List<ScoreDto> scoreList;

    @QueryWhere(tableColumn = "t1.score", connector = Connector.GT)
    private Integer minScore;
}
