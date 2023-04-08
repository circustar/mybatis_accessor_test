package com.test.mybatis_accessor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TeacherDto {
    private Integer teacherId;

//    @QueryField(connector = Connector.notExists, expression = "select * from student s where s.name like '#{name}'%")
    @NotBlank(message = "名称不能为空")
    @Size(max = 45, message = "名称不能超过45位")
    private String name;

    @Max(value=1, message = "性别不正确")
    @Min(value=0, message = "性别不正确")
    private Integer gender;

    private String mobile;

    private Integer version;

    @Valid
//    @EntityFilter(column="teacher_id", connector = Connector.eq, valueExpression = "teacherId")
//    @EntityFilter(column="grade", connector = Connector.between, valueExpression = {"1","25"})
//    @Join(group = "gp2", column="grade", connector = Connector.between, valueExpression = {"1","25"})
//    @Join(group = "gp2", column="teacher_id", connector = Connector.like, valueExpression = "teacherId")
    private List<StudentDto> students;
}
