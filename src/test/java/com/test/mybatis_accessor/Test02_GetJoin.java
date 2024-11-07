package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.ScoreDto;
import com.test.mybatis_accessor.dto.StudentDto;
import com.test.mybatis_accessor.dto.StudentDto3;
import com.test.mybatis_accessor.dto.StudentDto7;
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
public class Test02_GetJoin {
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
    public void TestGetEntityListByAnnotation() throws MybatisAccessorException {
        StudentDto studentDto = new StudentDto();
        studentDto.setName(this.studentName);
        List<Student> list = mybatisAccessorService.getEntityListByAnnotation(studentDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 2);
        log.info(list.toString());
        Student studentS1 = list.stream().filter(x -> x.getName().startsWith(studentName1)).findAny().get();
        assert(studentS1.getScoreList() == null || studentS1.getScoreList().size() == 0);
        Student studentS2 = list.stream().filter(x -> x.getName().startsWith(studentName2)).findAny().get();
        assert(studentS2.getScoreList() == null || studentS2.getScoreList().size() == 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetEntityListByAnnotation2() throws MybatisAccessorException {
        StudentDto3 studentDto = new StudentDto3();
        studentDto.setName(this.studentName);
        List<Student> list = mybatisAccessorService.getEntityListByAnnotation(studentDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 2);
        log.info(list.toString());
        Student studentS1 = list.stream().filter(x -> x.getName().startsWith(studentName1)).findAny().get();
        assert(studentS1.getScoreList().size() == this.studentDto1.getScoreList().size());
        Student studentS2 = list.stream().filter(x -> x.getName().startsWith(studentName2)).findAny().get();
        assert(studentS2.getScoreList().size() == this.studentDto2.getScoreList().size());
        log.info("--- log end ---");
    }


    @Test
    public void TestGetEntityListByAnnotation3() throws MybatisAccessorException {
        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setStudentId(this.studentDto1.getStudentId());
        List<Score> list = mybatisAccessorService.getEntityListByAnnotation(scoreDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 5);
        Optional<Score> anyScore = list.stream().filter(x -> x.getStudent() == null).findAny();
        assert(!anyScore.isPresent());
        list.stream().forEach(x -> log.info(x.toString()));
        log.info("--- log end ---");
    }



    @Test
    public void TestGetDtoListByAnnotation() throws MybatisAccessorException {
        StudentDto studentDto = new StudentDto();
        studentDto.setName(this.studentName);
        List<StudentDto> list = mybatisAccessorService.getDtoListByAnnotation(studentDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 2);
        log.info(list.toString());
        StudentDto studentS1 = list.stream().filter(x -> x.getName().startsWith(studentName1)).findAny().get();
        assert(studentS1.getScoreList() == null || studentS1.getScoreList().size() == 0);
        StudentDto studentS2 = list.stream().filter(x -> x.getName().startsWith(studentName2)).findAny().get();
        assert(studentS2.getScoreList() == null || studentS2.getScoreList().size() == 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoListByAnnotation2() throws MybatisAccessorException {
        StudentDto3 studentDto = new StudentDto3();
        studentDto.setName(this.studentName);
        List<StudentDto3> list = mybatisAccessorService.getDtoListByAnnotation(studentDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 2);
        log.info(list.toString());
        StudentDto3 studentS1 = list.stream().filter(x -> x.getName().startsWith(studentName1)).findAny().get();
        assert(studentS1.getScoreList().size() == this.studentDto1.getScoreList().size());
        StudentDto3 studentS2 = list.stream().filter(x -> x.getName().startsWith(studentName2)).findAny().get();
        assert(studentS2.getScoreList().size() == this.studentDto2.getScoreList().size());
        log.info("--- log end ---");
    }


    @Test
    public void TestGetDtoListByAnnotation3() throws MybatisAccessorException {
        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setStudentId(this.studentDto1.getStudentId());
        List<ScoreDto> list = mybatisAccessorService.getDtoListByAnnotation(scoreDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 5);
        Optional<ScoreDto> anyScore = list.stream().filter(x -> x.getStudent() == null).findAny();
        assert(!anyScore.isPresent());
        list.stream().forEach(x -> log.info(x.toString()));
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoListByAnnotation4() throws MybatisAccessorException {
        StudentDto7 studentDto = new StudentDto7();
        studentDto.setName(this.studentName);
        studentDto.setDeleted(0);
        List<StudentDto7> list = mybatisAccessorService.getDtoListByAnnotation(studentDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 2);
        log.info(list.toString());
        assert(list.get(0).getScoreList() != null && list.get(0).getScoreList().size() == 1 );
        assert(list.get(1).getScoreList() != null && list.get(0).getScoreList().size() == 1 );
        log.info("--- log end ---");
    }

}
