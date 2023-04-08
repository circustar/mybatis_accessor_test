package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.*;
import com.test.mybatis_accessor.entity.Score;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.entity.StudentCourse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test02_GetOne {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private StudentDto studentDto = null;

    @Before
    public void getTestData() throws MybatisAccessorException {
        if(this.studentDto != null) {
            return;
        }
        this.studentDto = null;
        List<StudentDto> studentList = mybatisAccessorService.getDtoListByAnnotation(new StudentDto());
        ScoreDto scoreDto = new ScoreDto();
        StudentCourseDto studentCourseDto = new StudentCourseDto();
        for(StudentDto s : studentList) {
            scoreDto.setStudentId(s.getStudentId());
            studentCourseDto.setStudentId(s.getStudentId());
            List<Score> scoreList = mybatisAccessorService.getEntityListByAnnotation(scoreDto);
            List<StudentCourse> studentCourseList = mybatisAccessorService.getEntityListByAnnotation(studentCourseDto);
            if(scoreList != null && scoreList.size() > 0
                && studentCourseList != null && studentCourseList.size() > 0) {
                this.studentDto = s;
                break;
            }
        }
        assert(this.studentDto != null);
    }

    @Test
    public void TestGetEntityById() throws MybatisAccessorException {
        Object entity = mybatisAccessorService.getEntityById(StudentDto.class, studentDto.getStudentId());
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoById() throws MybatisAccessorException {
        StudentDto entity = (StudentDto) mybatisAccessorService.getDtoById(StudentDto.class, studentDto.getStudentId()
                , false, Arrays.asList("scoreList"));
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList().size() > 0);
        assert(entity.getCourseList() == null || entity.getCourseList().size() == 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoById2() throws MybatisAccessorException {
        StudentDto2 entity = (StudentDto2) mybatisAccessorService.getDtoById(StudentDto2.class, studentDto.getStudentId()
                , false, Arrays.asList("scoreList"));
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetEntityById3() throws MybatisAccessorException {
        Student entity = mybatisAccessorService.getEntityById(StudentDto3.class, studentDto.getStudentId());
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList() != null && entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoById3() throws MybatisAccessorException {
        StudentDto3 entity = (StudentDto3) mybatisAccessorService.getDtoById(StudentDto3.class, studentDto.getStudentId()
                , false, null);
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoById4() throws MybatisAccessorException {
        StudentDto3 entity = (StudentDto3) mybatisAccessorService.getDtoById(StudentDto3.class
                , studentDto.getStudentId(), true, null);
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetEntityByQueryWrapper() throws MybatisAccessorException {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", this.studentDto.getStudentId());
        Object entity = mybatisAccessorService.getEntityByQueryWrapper(new StudentDto(), queryWrapper);
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetEntityByAnnotation() throws MybatisAccessorException {
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        studentQueryDto.setStudentId(this.studentDto.getStudentId());
        studentQueryDto.setGrade_from(0);
        studentQueryDto.setGrade_to(100);
        Object entity = mybatisAccessorService.getEntityByAnnotation(studentQueryDto);
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoByQueryWrapper() throws MybatisAccessorException {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentDto.getStudentId());
        StudentDto entity = (StudentDto) mybatisAccessorService.getDtoByQueryWrapper(new StudentDto(), queryWrapper
                , false, Arrays.asList("scoreList"));
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }


    @Test
    public void TestGetDtoByQueryWrapper2() throws MybatisAccessorException {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", studentDto.getStudentId());
        StudentDto2 entity = (StudentDto2) mybatisAccessorService.getDtoByQueryWrapper(new StudentDto2(), queryWrapper
                ,false, Arrays.asList("scoreList"));
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoByAnnotation() throws MybatisAccessorException {
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        studentQueryDto.setStudentId(this.studentDto.getStudentId());
        studentQueryDto.setGrade_from(0);
        studentQueryDto.setGrade_to(100);
        StudentQueryDto entity = (StudentQueryDto) mybatisAccessorService.getDtoByAnnotation(studentQueryDto
                , false, Arrays.asList("scoreList"));
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoWithAllChildren() throws MybatisAccessorException {
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        studentQueryDto.setStudentId(this.studentDto.getStudentId());
        studentQueryDto.setGrade_from(0);
        studentQueryDto.setGrade_to(100);
        StudentQueryDto entity = (StudentQueryDto) mybatisAccessorService.getDtoByAnnotation(studentQueryDto
                , true, null);
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getScoreList().size() > 0);
        assert(entity.getCourseList().size() > 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoWithAllChildren2() throws MybatisAccessorException {
        StudentDto3 studentDto = new StudentDto3();
        studentDto.setName(this.studentDto.getName());
        StudentDto3 entity =  mybatisAccessorService.getDtoByAnnotation(studentDto
                , true, null);
        log.info("--- log start ---");
        assert(entity != null);
        assert(entity.getScoreList() != null && entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetEntityWithAllChildren() throws MybatisAccessorException {
        StudentDto3 studentDto = new StudentDto3();
        studentDto.setName(this.studentDto.getName());
        Student entity =  mybatisAccessorService.getEntityByAnnotation(studentDto);
        log.info("--- log start ---");
        assert(entity != null);
        assert(entity.getScoreList() != null && entity.getScoreList().size() > 0);
        log.info("--- log end ---");
    }

}
