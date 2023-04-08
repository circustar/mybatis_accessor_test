package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.circustar.mybatis_accessor.support.MybatisAccessorUpdateManager;
import com.test.mybatis_accessor.dto.ScoreDto;
import com.test.mybatis_accessor.dto.StudentCourseDto;
import com.test.mybatis_accessor.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test03_UpdateManager {
    private static String strDateFormat = "yyyyMMdd";
    private static SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    private static String studentNamePrefix = "testUpdateManager";

    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    @Autowired
    private MybatisAccessorUpdateManager updateManager;

    @Test
    public void testUpdateManager() throws MybatisAccessorException {

        QueryWrapper queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.likeRight("name", studentNamePrefix);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(new StudentDto(), queryWrapper2);
        if(!dtoList.isEmpty()) {
            dtoList.stream().forEach(x ->x.setDeleted(1));
            updateManager.putDto(dtoList);
            updateManager.submit();
        }

        Random random = new Random();
        Date dt = new Date();
        String name = studentNamePrefix + sdf.format(dt);
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<StudentCourseDto> courseDtoList = IntStream.rangeClosed(1, 2).boxed()
                .map(x -> StudentCourseDto.builder().courseId(x).selectDate(new Date()).build()).collect(Collectors.toList());
        studentDto.setCourseList(courseDtoList);

        List<ScoreDto> scoreDtoList = IntStream.rangeClosed(1, 3).boxed().map(x -> ScoreDto.builder()
                .courseId(1).name("nnn" + "_" + x).score(BigDecimal.valueOf(random.nextInt(100)))
                .build()).collect(Collectors.toList());
        studentDto.setScoreList(scoreDtoList);

        updateManager.putDto(studentDto);
        updateManager.submit();

        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("name", name);
        List<StudentDto> dtoListByQueryWrapper = mybatisAccessorService.getDtoListByQueryWrapper(new StudentDto(), queryWrapper);
        assert(dtoListByQueryWrapper.size() == 1);
        studentDto.setStudentId(dtoListByQueryWrapper.get(0).getStudentId());
        StudentDto dto = mybatisAccessorService.getDtoById(StudentDto.class, studentDto.getStudentId()
                , false, Arrays.asList("scoreList", "courseList"));
        assert(dto!= null);
        assert(dto.getName().equals(name));
        assert(dto.getScoreList().size() == 3);
        assert(dto.getCourseList().size() == 2);

        dto.setName(studentNamePrefix + "ABC");
        int i = 0;
        for(ScoreDto scoreDto : dto.getScoreList()) {
            scoreDto.setName("testABC" + i ++ );
        }
        for(StudentCourseDto courseDto : dto.getCourseList()) {
            courseDto.setDeleted(1);
        }
        updateManager.putDto(dto);
        updateManager.submit();

        dto = mybatisAccessorService.getDtoById(StudentDto.class, studentDto.getStudentId()
                , false, Arrays.asList("scoreList", "courseList"));
        assert(dto != null);
        assert(dto.getName().equals(studentNamePrefix + "ABC"));
        assert(dto.getScoreList().size() == 3);
        assert(dto.getScoreList().stream().filter(x -> !x.getName().startsWith("testABC")).count() == 0);
        assert(dto.getCourseList().size() == 0);
    }
}
