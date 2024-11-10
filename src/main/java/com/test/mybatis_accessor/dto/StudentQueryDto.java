package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.*;
import com.circustar.mybatis_accessor.annotation.dto.QueryColumn;
import com.circustar.mybatis_accessor.annotation.dto.QueryWhere;
import com.circustar.mybatis_accessor.annotation.dto.Selector;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.service.IStudentService;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class StudentQueryDto {

    private Integer studentId;

    @QueryWhere(connector = Connector.LIKE_RIGHT)
    private String name;

    @QueryColumn("(select max(score.name) from score where score.student_id = student.student_id)")
    private String score_name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @QueryWhere(tableColumn = "grade", connector = Connector.GE)
    private Integer grade_from;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @QueryWhere(tableColumn = "grade", connector = Connector.LT)
    private Integer grade_to;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer grade;

    @Selector(tableColumn = "student_id", connector = Connector.EQ
            , valueExpression = "#{studentId}")
    private List<ScoreDto> scoreList;

    private List<StudentCourseDto> courseList;
    //    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
////    @QueryField(connector = Connector.exists, expression = "'select * from teacher t where t.name like ''' + teacherName + '%'''")
//    private String teacherName;
}
