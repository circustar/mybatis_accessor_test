package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.ScoreDto;
import com.test.mybatis_accessor.dto.StudentCourseDto;
import com.test.mybatis_accessor.dto.StudentDto;
import com.test.mybatis_accessor.dto.StudentDto6;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test03_UpdateWithDeleteAndInsert {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String studentName = "testDeleteAndInsert";

    private String courseNamePrefix = "testDeleteAndInsertC";

    private StudentDto studentDto = null;
    @Before
    public void createTestData() throws MybatisAccessorException {
        StudentDto6 studentDto6 = new StudentDto6();
        studentDto6.setName(studentName);
        List<StudentDto6> dtoList = mybatisAccessorService.getDtoListByAnnotation(studentDto6);
        mybatisAccessorService.deleteByIds(StudentDto6.class
                , dtoList.stream().map(x -> x.getStudentId()).collect(Collectors.toSet())
                , true, null, false, null);

        this.studentDto = DataSupport.createStudentData(mybatisAccessorService, studentName + "A1", 4, 3, courseNamePrefix);
    }

    @Test
    @Rollback(false)
    public void TestDelete1() throws MybatisAccessorException {
        StudentDto6 studentDto1 = mybatisAccessorService.getDtoById(StudentDto6.class, this.studentDto.getStudentId(), true, null);
        assert(studentDto1 != null);
        assert(studentDto1.getCourseList().size() == 3);
        assert(studentDto1.getScoreList().size() == 4);

        studentDto1.setScoreList(null);
        studentDto1.setCourseList(null);
        studentDto1.setName(studentName + "test");
        mybatisAccessorService.saveOrUpdate(studentDto1, true, null, true, null);

        StudentDto6 studentDto2 = mybatisAccessorService.getDtoById(StudentDto6.class, this.studentDto.getStudentId(), true, null);
        assert(studentDto2 != null);
        assert(!(studentName + "test").equals(studentDto2.getName()));
        assert(studentDto2.getCourseList().size() == 0);
        assert(studentDto2.getScoreList().size() == 4);
    }

    @Test
    @Rollback(false)
    public void TestDelete2() throws MybatisAccessorException {
        StudentDto6 studentDto1 = mybatisAccessorService.getDtoById(StudentDto6.class, this.studentDto.getStudentId(), true, null);
        assert(studentDto1 != null);
        assert(studentDto1.getCourseList().size() == 3);
        assert(studentDto1.getScoreList().size() == 4);

        List<ScoreDto> updateScoreList = studentDto1.getScoreList().stream().limit(1).collect(Collectors.toList());
        List<StudentCourseDto> updateCourseList = studentDto1.getCourseList().stream().limit(1).collect(Collectors.toList());

        studentDto1.setScoreList(updateScoreList);
        studentDto1.setCourseList(updateCourseList);
        studentDto1.setName(studentName + "test");
        mybatisAccessorService.saveOrUpdate(studentDto1, true, null, false, null);

        StudentDto6 studentDto2 = mybatisAccessorService.getDtoById(StudentDto6.class, this.studentDto.getStudentId(), true, null);
        assert(studentDto2 != null);
        assert((studentName + "test").equals(studentDto2.getName()));
        assert(studentDto2.getCourseList().size() == 1);
        assert(studentDto2.getScoreList().size() == 1);
        assert(studentDto2.getCourseList().get(0).getStudentCourseId().equals(updateCourseList.get(0).getStudentCourseId()));
        assert(studentDto2.getScoreList().get(0).getScoreId().equals(updateScoreList.get(0).getScoreId()));
    }
}

