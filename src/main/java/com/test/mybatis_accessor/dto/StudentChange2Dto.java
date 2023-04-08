package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.Connector;
import com.circustar.mybatis_accessor.annotation.dto.DeleteFlag;
import com.circustar.mybatis_accessor.annotation.dto.QueryWhere;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.service.IStudentService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = Student.class, service = IStudentService.class)
//@PropertyChangeEvent(changeProperties = {"name", "grade"}, triggerOnAnyChanged = false
//        , onChangeExecutor = StudentChangeEvent2.class, updateParams = {"2"}, executeTiming = ExecuteTiming.BEFORE_UPDATE)
public class StudentChange2Dto implements Serializable {
    private Integer studentId;

    @QueryWhere(connector = Connector.LIKE_RIGHT)
    private String name;

    private Integer grade;

    @DeleteFlag
    private Integer deleted;

    private Integer version;

    private String propertyChangedTest;
}
