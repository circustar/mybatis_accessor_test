package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.circustar.common_utils.reflection.FieldUtils;
import com.circustar.mybatis_accessor.class_info.DtoClassInfo;
import com.circustar.mybatis_accessor.class_info.DtoClassInfoHelper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.StudentCourseDto;
import com.test.mybatis_accessor.dto.StudentDto;
import com.test.mybatis_accessor.dto.StudentQueryDto;
import com.test.mybatis_accessor.entity.StudentCourse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test02_GetList {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DtoClassInfoHelper dtoClassInfoHelper;

    private String studentName = "testGetList";
    private String courseNamePrefix = "courseList";

    private StudentDto studentDto1 = null;
    private StudentDto studentDto2 = null;
    private StudentDto studentDto3 = null;

    @Before
    public void createTestData() throws MybatisAccessorException {
        if(this.studentDto1 == null || this.studentDto2 == null || this.studentDto3 == null) {
            DataSupport.removeStudentData(mybatisAccessorService, studentName);
            this.studentDto1 = DataSupport.createStudentData(mybatisAccessorService, studentName + "A1", 0, 2, courseNamePrefix);
            this.studentDto2 = DataSupport.createStudentData(mybatisAccessorService, studentName + "A2", 0, 0, courseNamePrefix);
            this.studentDto3 = DataSupport.createStudentData(mybatisAccessorService, studentName + "A3", 0, 0, courseNamePrefix);
        }
    }

    @Test
    public void TestGetEntityListByQueryWrapper() throws MybatisAccessorException {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", studentName);
        List<Object> entityListByQueryWrapper = mybatisAccessorService.getEntityListByQueryWrapper(new StudentDto(), queryWrapper);
        log.info("--- log start ---");
        assert(entityListByQueryWrapper != null);
        assert(entityListByQueryWrapper.size() == 3);
        entityListByQueryWrapper.stream().forEach(x -> log.info(x.toString()));
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoListByQueryWrapper() throws MybatisAccessorException {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", studentName);
        List<Object> entityListByQueryWrapper = mybatisAccessorService.getDtoListByQueryWrapper(StudentDto.class, queryWrapper);
        log.info("--- log start ---");
        assert(entityListByQueryWrapper != null);
        assert(entityListByQueryWrapper.size() == 3);
        entityListByQueryWrapper.stream().forEach(x -> log.info(x.toString()));
        log.info("--- log end ---");
    }

    @Test
    public void TestGetEntityListByAnnotation() throws MybatisAccessorException {
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        studentQueryDto.setName(studentName);
        studentQueryDto.setGrade_from(0);
        studentQueryDto.setGrade_to(100);
        List<Object> list = mybatisAccessorService.getEntityListByAnnotation(studentQueryDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 3);
        list.stream().forEach(x -> log.info(x.toString()));
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoListByAnnotation() throws MybatisAccessorException {
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        studentQueryDto.setName(studentName);
        studentQueryDto.setGrade_from(0);
        studentQueryDto.setGrade_to(100);
        List<Object> list = mybatisAccessorService.getDtoListByAnnotation(studentQueryDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 3);
        list.stream().forEach(x -> log.info(x.toString()));
        log.info("--- log end ---");
    }

    @Test
    public void TestEntityQueryWrapper() {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("student_id", this.studentDto1.getStudentId());

        DtoClassInfo subDtoClassInfo = dtoClassInfoHelper.getDtoClassInfo(StudentCourseDto.class);
        IService subEntityService = subDtoClassInfo.getEntityDtoServiceRelation().getServiceBean(applicationContext);
        List<StudentCourse> deletedSubEntities = subEntityService.list(qw);
        assert(deletedSubEntities.size() == 2);
        assert(deletedSubEntities.stream().filter(x -> x.getDeleted() == 1).count() == 0);

        List subIds = deletedSubEntities.stream().map(x -> FieldUtils.getFieldValue(x
                , subDtoClassInfo.getEntityClassInfo().getKeyField().getPropertyDescriptor().getReadMethod()))
                .collect(Collectors.toList());
        log.info(subIds.toString());
    }
}
