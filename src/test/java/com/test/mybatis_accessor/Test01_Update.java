package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.ScoreDto;
import com.test.mybatis_accessor.dto.StudentCourseDto;
import com.test.mybatis_accessor.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test01_Update {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;


    private Test01_Save test01Save = new Test01_Save();

    @Test
    @Transactional
    @Rollback(false)
    public void TestUpdateWithInsertAndDelete() throws MybatisAccessorException {
        String name = "TestUpdateWithInsertAndDelete";
        int score0 = 1234;
        test01Save.setMybatisAccessorService(mybatisAccessorService);
        test01Save.TestSaveWithChildren();

        String testName = "TestSaveWithChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);

        StudentDto studentDto = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto.getScoreList().size() == 2);
        assert(studentDto.getCourseList().size() == 3);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(10).selectDate(new Date()).build();
        courseDtoList.add(course1);
        studentDto.getCourseList().get(0).setCourseId(score0);
        courseDtoList.add(studentDto.getCourseList().get(0));
        studentDto.setCourseList(courseDtoList);

        studentDto.getScoreList().get(0).setDeleted(1);
        studentDto.getScoreList().get(1).setName(name);
        studentDto.setGrade(score0);
        mybatisAccessorService.update(studentDto, false, Arrays.asList("scoreList","courseList"), false, null);

        StudentDto studentDto1 = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto1.getGrade() == score0);
        assert(studentDto1.getCourseList().size() == 4);
        assert(studentDto1.getCourseList().stream().filter(x -> x.getCourseId().equals(score0)).findAny().isPresent());
        assert(studentDto1.getScoreList().size() == 1);
        assert(name.equals(studentDto1.getScoreList().get(0).getName()));
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestUpdateWithInsertAndDelete2() throws MybatisAccessorException {
        String name = "TestUpdateWithInsertAndDelete2";
        int score0 = 2234;
        test01Save.setMybatisAccessorService(mybatisAccessorService);
        test01Save.TestSaveWithChildren();

        String testName = "TestSaveWithChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);

        StudentDto studentDto = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto.getScoreList().size() == 2);
        assert(studentDto.getCourseList().size() == 3);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(10).selectDate(new Date()).build();
        courseDtoList.add(course1);
        studentDto.getCourseList().get(0).setCourseId(score0);
        courseDtoList.add(studentDto.getCourseList().get(0));
        studentDto.setCourseList(courseDtoList);

        studentDto.getScoreList().get(0).setDeleted(1);
        studentDto.getScoreList().get(1).setName(name);
        studentDto.setGrade(score0);
        mybatisAccessorService.update(studentDto, false, Arrays.asList("courseList"), false, null);

        StudentDto studentDto1 = mybatisAccessorService.getDtoByAnnotation( queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto1.getGrade() == score0);
        assert(studentDto1.getCourseList().size() == 4);
        assert(studentDto1.getCourseList().stream().filter(x -> x.getCourseId().equals(score0)).findAny().isPresent());
        assert(studentDto1.getScoreList().size() == 2);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestUpdateWithInsertAndDelete3() throws MybatisAccessorException {
        String name = "TestUpdateWithInsertAndDelete3";
        int score0 = 3234;
        test01Save.setMybatisAccessorService(mybatisAccessorService);
        test01Save.TestSaveWithChildren();

        String testName = "TestSaveWithChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);

        StudentDto studentDto = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto.getScoreList().size() == 2);
        assert(studentDto.getCourseList().size() == 3);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(10).selectDate(new Date()).build();
        courseDtoList.add(course1);
        studentDto.getCourseList().get(0).setCourseId(score0);
        courseDtoList.add(studentDto.getCourseList().get(0));
        studentDto.setCourseList(courseDtoList);

        studentDto.getScoreList().get(0).setDeleted(1);
        studentDto.getScoreList().get(1).setName(name);
        studentDto.setGrade(score0);
        mybatisAccessorService.update(studentDto, false, Arrays.asList("scoreList","courseList"), true, null);

        StudentDto studentDto1 = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto1.getGrade() != score0);
        assert(studentDto1.getCourseList().size() == 4);
        assert(studentDto1.getCourseList().stream().filter(x -> x.getCourseId().equals(score0)).findAny().isPresent());
        assert(studentDto1.getScoreList().size() == 1);
        assert(name.equals(studentDto1.getScoreList().get(0).getName()));
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestUpdateWithInsertAndDelete4() throws MybatisAccessorException {
        String name = "TestUpdateWithInsertAndDelete4";
        int score0 = 4234;
        test01Save.setMybatisAccessorService(mybatisAccessorService);
        test01Save.TestSaveWithChildren();

        String testName = "TestSaveWithChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);

        StudentDto studentDto = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto.getScoreList().size() == 2);
        assert(studentDto.getCourseList().size() == 3);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(score0).selectDate(new Date()).build();
        courseDtoList.add(course1);
        studentDto.setCourseList(courseDtoList);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(12).name( name).score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(23).name( name).score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        studentDto.setGrade(score0);
        mybatisAccessorService.update(studentDto, false, Arrays.asList("scoreList","courseList"), false, null);

        StudentDto studentDto1 = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto1.getGrade() == score0);
        assert(studentDto1.getCourseList().size() == 4);
        assert(studentDto1.getCourseList().stream().filter(x -> x.getCourseId().equals(score0)).findAny().isPresent());
        assert(studentDto1.getScoreList().size() == 4);
        assert(studentDto1.getScoreList().stream().filter(x -> x.getName().equals(name)).count() == 2);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestUpdateWithInsertAndDelete5() throws MybatisAccessorException {
        String name = "TestUpdateWithInsertAndDelete5";
        int score0 = 5234;
        test01Save.setMybatisAccessorService(mybatisAccessorService);
        test01Save.TestSaveWithChildren();

        String testName = "TestSaveWithChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);

        StudentDto studentDto = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto.getScoreList().size() == 2);
        assert(studentDto.getCourseList().size() == 3);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(score0).selectDate(new Date()).build();
        courseDtoList.add(course1);
        studentDto.setCourseList(courseDtoList);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(12).name( name).score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(23).name( name).score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        studentDto.setGrade(score0);
        mybatisAccessorService.update(studentDto, false, Arrays.asList("scoreList"), false, null);

        StudentDto studentDto1 = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto1.getGrade() == score0);
        assert(studentDto1.getCourseList().size() == 3);
        assert(!studentDto1.getCourseList().stream().filter(x -> x.getCourseId().equals(score0)).findAny().isPresent());
        assert(studentDto1.getScoreList().size() == 4);
        assert(studentDto1.getScoreList().stream().filter(x -> x.getName().equals(name)).count() == 2);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestUpdateWithInsertAndDelete6() throws MybatisAccessorException {
        String name = "TestUpdateWithInsertAndDelete6";
        int score0 = 6234;
        test01Save.setMybatisAccessorService(mybatisAccessorService);
        test01Save.TestSaveWithChildren();

        String testName = "TestSaveWithChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);

        StudentDto studentDto = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto.getScoreList().size() == 2);
        assert(studentDto.getCourseList().size() == 3);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(10).selectDate(new Date()).build();
        courseDtoList.add(course1);
        studentDto.getCourseList().get(0).setCourseId(score0);
        courseDtoList.add(studentDto.getCourseList().get(0));
        studentDto.setCourseList(courseDtoList);

        studentDto.getScoreList().get(0).setDeleted(1);
        studentDto.getScoreList().get(1).setName(name);
        studentDto.setGrade(score0);
        mybatisAccessorService.update(studentDto, true, null, false, null);

        StudentDto studentDto1 = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));

        assert(studentDto1.getGrade() == score0);
        assert(studentDto1.getCourseList().size() == 4);
        assert(studentDto1.getCourseList().stream().filter(x -> x.getCourseId().equals(score0)).findAny().isPresent());
        assert(studentDto1.getScoreList().size() == 1);
        assert(name.equals(studentDto1.getScoreList().get(0).getName()));
    }
//
//    @Test
//    public void TestUpdateAndInsertNewChild() {
//
//        StudentDto studentDto = new StudentDto();
//        studentDto.setStudentId(113);
//
//        StudentDto dto = mybatisAccessorService.getDtoByAnnotation(StudentDto.class, studentDto, "scoreList");
//        log.info(dto.toString());
//        dto.getScoreList().get(0).setName("testing");
//        dto.getScoreList().add(ScoreDto.builder().score(BigDecimal.valueOf(88)).name("ddd").courseId(123).build());
//
//        mybatisAccessorService.update(dto, "scoreList", true, false);
//
//    }
//
//    @Test
//    @Transactional
//    public void TestUpdate2() {
//        StudentDto studentDto = new StudentDto();
//        studentDto.setStudentId(113);
//
//        String name2 = "test" + (new Random()).nextInt(10000);
//        StudentDto dto = mybatisAccessorService.getDtoByAnnotation(StudentDto.class, studentDto, "scoreList");
//        dto.setName(name2);
//
//        log.info(dto.toString());
//        assert(dto.getScoreList() != null && dto.getScoreList().size() > 0);
//        dto.getScoreList().get(0).setName("dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddaaaaaaaaaaddddddddddd");
//        Exception e = null;
//        try {
//            mybatisAccessorService.update(dto, "scoreList", false, false);
//        } catch (Exception ex) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            e = ex;
//        }
//        assert(e != null);
//
//        StudentDto dto2 = mybatisAccessorService.getDtoByAnnotation(StudentDto.class, studentDto, "scoreList");
//        assert(!name2.equals(dto2.getName()));
//
//    }
//
//
//    @Test
//    public void TestUpdate3() {
//
//        StudentDto studentDto = new StudentDto();
//        studentDto.setStudentId(113);
//
//        String name2 = "test" + (new Random()).nextInt(10000);
//        StudentDto dto = mybatisAccessorService.getDtoByAnnotation(StudentDto.class, studentDto, "scoreList");
//        dto.setName(name2);
//
//        log.info(dto.toString());
//        assert(dto.getScoreList() != null && dto.getScoreList().size() > 0);
//        ScoreDto scoreDto = ScoreDto.builder()
//                .score(BigDecimal.TEN)
//                .name("123123")
//                .deleted(1)
//                .scoreId(999999)
//                .version(1)
//                .build();
//        dto.getScoreList().add(scoreDto);
//
//        Exception e = null;
//        try {
//            mybatisAccessorService.update(dto, "scoreList", false, false);
//        } catch (Exception ex) {
//            e = ex;
//        }
//        assert(e != null);
//
//    }
//
//
//    @Test
//    public void TestUpdate4() {
//
//        StudentDto studentDto = new StudentDto();
//        studentDto.setStudentId(113);
//
//        String name2 = "test" + (new Random()).nextInt(10000);
//        StudentDto dto = mybatisAccessorService.getDtoByAnnotation(StudentDto.class, studentDto, "scoreList");
//        dto.setName(name2);
//
//        log.info(dto.toString());
//        assert(dto.getScoreList() != null && dto.getScoreList().size() > 0);
//        Integer scoreSize = dto.getScoreList().size();
//        dto.getScoreList().get(0).setDeleted(1);
//
//        mybatisAccessorService.update(dto, "scoreList", false, false);
//
//        StudentDto dto2 = mybatisAccessorService.getDtoByAnnotation(StudentDto.class, studentDto, "scoreList");
//        assert(dto2.getName().equals(name2));
//        assert(dto2.getScoreList() == null || dto2.getScoreList().size() == scoreSize - 1);
//    }
}

