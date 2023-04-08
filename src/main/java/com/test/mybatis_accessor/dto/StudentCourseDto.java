package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.dto.DeleteFlag;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.StudentCourse;
import com.test.mybatis_accessor.service.IStudentCourseService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = StudentCourse.class, service = IStudentCourseService.class)
public class StudentCourseDto {
    private Integer studentCourseId;

    private Integer studentId;

    private Integer courseId;

    private Date selectDate;

    @DeleteFlag
    private Integer deleted;

    private Integer version;
}
