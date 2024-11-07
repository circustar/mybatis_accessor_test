package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.ScoreDto;
import com.test.mybatis_accessor.dto.StudentCourseDto;
import com.test.mybatis_accessor.dto.StudentDto;
import com.test.mybatis_accessor.entity.Score;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.entity.StudentCourse;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataSupport {
    private static String strDateFormat = "yyyyMMdd";
    private static SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    public static StudentDto createStudentData(MybatisAccessorService mybatisAccessorService
            , String studentNamePrefix, int scoreCount, int courseCount, String coursePrefix) throws MybatisAccessorException {
        StudentDto queryDto = new StudentDto();
        queryDto.setName(studentNamePrefix);
        removeStudentData(mybatisAccessorService, studentNamePrefix);

        Random random = new Random();
        Date dt = new Date();
        String name = studentNamePrefix + sdf.format(dt);
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        if(courseCount > 0) {
            List<StudentCourseDto> courseDtoList = IntStream.rangeClosed(1, courseCount).boxed()
                    .map(x -> StudentCourseDto.builder().courseId(x).selectDate(dt).build()).collect(Collectors.toList());
            studentDto.setCourseList(courseDtoList);
        }

        if(scoreCount > 0) {
            studentDto.setScoreList(new ArrayList<>());
            int scoreCount1 = scoreCount / 2 - 1;
            if(scoreCount1 > 0) {
                List<ScoreDto> scoreDtoList = IntStream.rangeClosed(1, scoreCount1).boxed().map(x -> ScoreDto.builder()
                        .courseId(1).name(coursePrefix + "1_" + x).score(BigDecimal.valueOf(random.nextInt(100)))
                        .build()).collect(Collectors.toList());
                studentDto.getScoreList().addAll(scoreDtoList);
            }
            int scoreCount2 = scoreCount - scoreCount1;
            if(scoreCount2 > 0) {
                List<ScoreDto> scoreDtoList = IntStream.rangeClosed(1, scoreCount2).boxed().map(x -> ScoreDto.builder()
                        .courseId(2).name(coursePrefix + "2_" + x).score(BigDecimal.valueOf(random.nextInt(100)))
                        .build()).collect(Collectors.toList());
                studentDto.getScoreList().addAll(scoreDtoList);
            }
        }

        // 插入数据
        Student updateResult = mybatisAccessorService.save(studentDto, false
                , Arrays.asList("scoreList","courseList"), false, null);
        assert(updateResult != null);

        StudentDto result = mybatisAccessorService.getDtoByAnnotation(queryDto, false
                , Arrays.asList("scoreList","courseList"));

        // 验证主项被插入
        assert(result != null);
        assert(result.getName().equals(name));

        // 验证子项被插入
        assert(result.getScoreList() != null);
        assert(result.getScoreList().size() == scoreCount);

        assert(result.getCourseList() != null);
        assert(result.getCourseList().size() == courseCount);

        return result;
    }

    public static void removeStudentData(MybatisAccessorService mybatisAccessorService
            , String studentNamePrefix) throws MybatisAccessorException {
        StudentDto queryDto = new StudentDto();
        queryDto.setName(studentNamePrefix);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(StudentDto.class
                    , dtoList.stream().map(x -> x.getStudentId()).collect(Collectors.toSet())
                    , false
                    , Arrays.asList("scoreList","courseList"), false, null);

            // 验证主项被删除
            List<StudentDto> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(queryDto);
            assert(dtoListVerify == null || dtoListVerify.size() == 0);

            // 验证子项被删除
            for(StudentDto dto : dtoList) {
                StudentCourseDto query1 = new StudentCourseDto();
                query1.setStudentId(dto.getStudentId());
                List<StudentCourse> studentCourseList = mybatisAccessorService.getEntityListByAnnotation(query1);
                assert(studentCourseList == null || studentCourseList.size() == 0);
                ScoreDto query2 = new ScoreDto();
                query2.setStudentId(dto.getStudentId());
                List<Score> scoreList = mybatisAccessorService.getEntityListByAnnotation(query2);
                assert(scoreList == null || scoreList.size() == 0);
            }
        }
    }
}
