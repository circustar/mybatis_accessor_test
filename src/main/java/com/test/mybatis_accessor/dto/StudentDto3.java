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
public class StudentDto3 {
    private Integer studentId;

    @QueryWhere(connector = Connector.LIKE_RIGHT)
    private String name;

    private Integer grade;

    @DeleteFlag
    private Integer deleted;

    private Integer version;

    @QueryJoin()
    private List<ScoreDto> scoreList;

    private String uselessColumnForTest;
}
