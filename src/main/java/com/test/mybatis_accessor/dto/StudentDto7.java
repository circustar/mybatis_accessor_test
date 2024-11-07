package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.Connector;
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
public class StudentDto7 {
    private Integer studentId;

    @QueryWhere(connector = Connector.LIKE_RIGHT)
    private String name;

    private Integer grade;

    @DeleteFlag
    private Integer deleted;

    private Integer version;

    private String uselessColumnForTest;

    private Integer param0 = 0;

    @QueryJoin(subQueryExpression = "select max(t.score_id) score_id, t.student_id, max(t.course_id) course_id, sum(t.score) score\n" +
            ",'' name, 0 deleted, max(t.version) version from score t where t.deleted = #{param0} \n" +
            "group by student_id", tableAlias = "t", joinExpression = "t.student_id = student.student_id")
    private List<ScoreDto> scoreList;
}
