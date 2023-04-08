package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.response.PageInfo;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.StudentDto;
import com.test.mybatis_accessor.dto.StudentQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test02_GetPage {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String studentName = "testGetList";
    private String courseNamePrefix = "courseList";
    private StudentDto studentDto = null;

    private StudentDto studentDto1 = null;
    private StudentDto studentDto2 = null;
    private StudentDto studentDto3 = null;
    private StudentDto studentDto4 = null;
    private StudentDto studentDto5 = null;

    @Before
    public void createTestData() throws MybatisAccessorException {
        if(this.studentDto1 == null || this.studentDto2 == null || this.studentDto3 == null
                || this.studentDto4== null || this.studentDto5 == null) {
            DataSupport.removeStudentData(mybatisAccessorService, studentName);
            this.studentDto1 = DataSupport.createStudentData(mybatisAccessorService, studentName + "1", 0, 0, courseNamePrefix);
            this.studentDto2 = DataSupport.createStudentData(mybatisAccessorService, studentName + "2", 0, 0, courseNamePrefix);
            this.studentDto3 = DataSupport.createStudentData(mybatisAccessorService, studentName + "3", 0, 0, courseNamePrefix);
            this.studentDto4 = DataSupport.createStudentData(mybatisAccessorService, studentName + "4", 0, 0, courseNamePrefix);
            this.studentDto5 = DataSupport.createStudentData(mybatisAccessorService, studentName + "5", 0, 0, courseNamePrefix);
        }
    }


    @Test
    public void TestGetEntityPageByQueryWrapper() throws MybatisAccessorException {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", studentName);
        PageInfo<Object> pageInfo = mybatisAccessorService.getEntityPageByQueryWrapper(new StudentDto(), queryWrapper,1,4);
        log.info("--- log start ---");
        assert(pageInfo != null);
        assert(pageInfo.getCurrent() == 1);
        assert(pageInfo.getRecords().size() == 4);

        PageInfo<Object> pageInfo2 = mybatisAccessorService.getEntityPageByQueryWrapper(new StudentDto(), queryWrapper,2,3);
        assert(pageInfo2 != null);
        assert(pageInfo2.getCurrent() == 2);
        assert(pageInfo2.getRecords().size() == 2);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoPageByQueryWrapper() throws MybatisAccessorException {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", studentName);
        PageInfo<Object> pageInfo = mybatisAccessorService.getDtoPageByQueryWrapper(new StudentDto(), queryWrapper,1,4);
        log.info("--- log start ---");
        assert(pageInfo.getCurrent() == 1);
        assert(pageInfo.getRecords().size() == 4);

        PageInfo<Object> pageInfo2 = mybatisAccessorService.getDtoPageByQueryWrapper(new StudentDto(), queryWrapper,3,2);
        assert(pageInfo2.getCurrent() == 3);
        assert(pageInfo2.getRecords().size() == 1);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetEntityPageByAnnotation() throws MybatisAccessorException {
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        studentQueryDto.setName(studentName);
        studentQueryDto.setGrade_from(0);
        studentQueryDto.setGrade_to(100);
        PageInfo<Object> pageInfo = mybatisAccessorService.getEntityPageByAnnotation(studentQueryDto,1,4);
        log.info("--- log start ---");
        assert(pageInfo.getCurrent() == 1);
        assert(pageInfo.getRecords().size() == 4);

        PageInfo<Object> pageInfo2 = mybatisAccessorService.getEntityPageByAnnotation(studentQueryDto,2,3);
        assert(pageInfo2.getCurrent() == 2);
        assert(pageInfo2.getRecords().size() == 2);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoPageByAnnotation() throws MybatisAccessorException {
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        studentQueryDto.setName(studentName);
        studentQueryDto.setGrade_from(0);
        studentQueryDto.setGrade_to(100);
        PageInfo<Object> pageInfo = mybatisAccessorService.getDtoPageByAnnotation(studentQueryDto, 1, 4);
        log.info("--- log start ---");
        assert(pageInfo.getCurrent() == 1);
        assert(pageInfo.getRecords().size() == 4);

        PageInfo<Object> pageInfo2 = mybatisAccessorService.getDtoPageByAnnotation(studentQueryDto, 3, 2);
        assert(pageInfo2.getCurrent() == 3);
        assert(pageInfo2.getRecords().size() == 1);
        log.info("--- log end ---");
    }
}
