package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.Course;
import com.test.mybatis_accessor.service.ICourseService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = Course.class, service = ICourseService.class)
public class CourseDto implements Serializable {
    private Integer courseId;
    private String courseName;
    private Date startDate;
    private Date endDate;
    private Integer version;

    private List<ScoreDto> scoreList;
}
