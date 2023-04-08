package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.StudentAverageScoreDto;
import com.test.mybatis_accessor.dto.StudentDto;
import com.test.mybatis_accessor.dto.StudentQueryDto;
import com.test.mybatis_accessor.entity.StudentStatistics;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test02_GetGroup {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String studentName = "testGroup";
    private String courseNamePrefix = "courseGroup";
    private StudentDto studentDto = null;

    @Before
    public void createTestData() throws MybatisAccessorException {
        if(this.studentDto != null) {
            return;
        }
        this.studentDto = DataSupport.createStudentData(mybatisAccessorService, studentName, 5, 6, courseNamePrefix);
        log.info(this.studentDto.toString());
    }

    @Test
    public void TestGetEntityListByAnnotation() throws MybatisAccessorException {
        StudentAverageScoreDto studentQueryDto = new StudentAverageScoreDto();
        studentQueryDto.setStudentId(this.studentDto.getStudentId());
        List<StudentStatistics> list = mybatisAccessorService.getEntityListByAnnotation(studentQueryDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 1);
        StudentStatistics studentStatistics = list.get(0);
        BigDecimal minValue = this.studentDto.getScoreList().stream().map(x -> x.getScore()).min(BigDecimal::compareTo).get();
        BigDecimal maxValue = this.studentDto.getScoreList().stream().map(x -> x.getScore()).max(BigDecimal::compareTo).get();
        BigDecimal avgValue = this.studentDto.getScoreList().stream().map(x -> x.getScore()).reduce(BigDecimal.ZERO,BigDecimal::add)
                .divide(BigDecimal.valueOf(this.studentDto.getScoreList().size()));
        assert(minValue.compareTo(studentStatistics.getMinScore()) == 0);
        assert(maxValue.compareTo(studentStatistics.getMaxScore()) == 0);
        assert(avgValue.compareTo(studentStatistics.getAverageScore()) == 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoListByAnnotation() throws MybatisAccessorException {
        StudentAverageScoreDto studentQueryDto = new StudentAverageScoreDto();
        studentQueryDto.setStudentId(this.studentDto.getStudentId());
        List<StudentAverageScoreDto> list = mybatisAccessorService.getDtoListByAnnotation(studentQueryDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 1);
        StudentAverageScoreDto studentStatistics = list.get(0);
        BigDecimal minValue = this.studentDto.getScoreList().stream().map(x -> x.getScore()).min(BigDecimal::compareTo).get();
        BigDecimal maxValue = this.studentDto.getScoreList().stream().map(x -> x.getScore()).max(BigDecimal::compareTo).get();
        BigDecimal avgValue = this.studentDto.getScoreList().stream().map(x -> x.getScore()).reduce(BigDecimal.ZERO,BigDecimal::add)
                .divide(BigDecimal.valueOf(this.studentDto.getScoreList().size()));
        assert(minValue.compareTo(studentStatistics.getMinScore()) == 0);
        assert(maxValue.compareTo(studentStatistics.getMaxScore()) == 0);
        assert(avgValue.compareTo(studentStatistics.getAverageScore()) == 0);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoListByAnnotation2() throws MybatisAccessorException {
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        studentQueryDto.setStudentId(this.studentDto.getStudentId());
        List<Object> list = mybatisAccessorService.getDtoListByAnnotation(studentQueryDto);
        log.info("--- log start ---");
        assert(list != null);
        assert(list.size() == 1);
        list.stream().forEach(x -> log.info(x.toString()));
        log.info("--- log end ---");
    }
}
