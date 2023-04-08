package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.ScoreDto;
import com.test.mybatis_accessor.dto.StudentDto;
import com.test.mybatis_accessor.dto.StudentDto3;
import com.test.mybatis_accessor.dto.StudentDto4;
import com.test.mybatis_accessor.entity.Score;
import com.test.mybatis_accessor.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test02_SelectCountWithJoin {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String studentName = "testJoin";

    private String studentName1 = "testJoin1";
    private String courseNamePrefix1 = "courseJoin1";
    private StudentDto studentDto1 = null;

    private String studentName2 = "testJoin2";
    private String courseNamePrefix2 = "courseJoin2";
    private StudentDto studentDto2 = null;

    @Before
    public void createTestData() throws MybatisAccessorException {
        if(this.studentDto1 == null) {
            this.studentDto1 = DataSupport.createStudentData(mybatisAccessorService, studentName1, 5, 6, courseNamePrefix1);
            log.info(this.studentDto1.toString());
        }

        if(this.studentDto2 == null) {
            this.studentDto2 = DataSupport.createStudentData(mybatisAccessorService, studentName2, 4, 3, courseNamePrefix2);
            log.info(this.studentDto2.toString());
        }
    }

    @Test
    public void TestGetCountByAnnotation() throws MybatisAccessorException {
        StudentDto4 studentDto = new StudentDto4();
        studentDto.setName(this.studentName);
        studentDto.setMinScore(50);
        Long result = mybatisAccessorService.getCountByAnnotation(studentDto);
        log.info("--- log start ---");
        log.info("件数：" + result);
        log.info("--- log end ---");
    }


}
