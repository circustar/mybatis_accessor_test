package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.*;
import com.test.mybatis_accessor.entity.ClassGroup;
import com.test.mybatis_accessor.entity.Score;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.entity.StudentCourse;
import com.test.mybatis_accessor.mapper.StudentCourseMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test01_Save {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    public void setMybatisAccessorService(MybatisAccessorService service) {
        this.mybatisAccessorService = service;
    }

    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    @Test
    @Transactional
    @Rollback(false)
    @Order(1)
    public void testDeleteByPhysic() throws MybatisAccessorException {
        String testName = "testDeleteByPhysic";

        String name = testName + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        Student student = mybatisAccessorService.save(studentDto, true, null, false, null);

        mybatisAccessorService.deleteByIds(StudentDto3.class, Collections.singleton(student.getStudentId()), false, null, false, null);

        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        assert(dtoList == null || dtoList.size() == 0);

        // 看下数据是否被物理删除了！
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveWithChildren() throws MybatisAccessorException {
        String testName = "TestSaveWithChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(StudentDto.class
                    , dtoList.stream().map(x -> x.getStudentId()).collect(Collectors.toSet())
                    , false
                    , Arrays.asList(StudentDto.Fields.scoreList,StudentDto.Fields.courseList), false, null);

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

        String name = testName + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("c_" + name).score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(2).name("c_" + name).score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto.setCourseList(courseDtoList);

        // 插入数据
        Student updateResult = mybatisAccessorService.save(studentDto, false
                , Arrays.asList("scoreList","courseList"), false, null);
        assert(updateResult != null);
        log.info(updateResult.toString());

        StudentDto result = mybatisAccessorService.getDtoByAnnotation(queryDto, false
                , Arrays.asList("scoreList","courseList"));
        log.info(result.toString());

        // 验证主项被插入
        assert(result != null);
        assert(result.getName().equals(name));

        // 验证子项被插入
        assert(result.getScoreList() != null);
        assert(result.getScoreList().size() == 2);
        assert(result.getScoreList().stream().filter(x -> !("c_" + name).equals(x.getName())).count() == 0);

        assert(result.getCourseList() != null);
        assert(result.getCourseList().size() == 3);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveWithoutChildren() throws MybatisAccessorException {

        String testName = "TestSaveWithoutChildren";
        StudentDto query = new StudentDto();
        query.setName(testName);

        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(query);
        if(dtoList != null && dtoList.size() > 0) {
            mybatisAccessorService.deleteByIds(StudentDto.class.getSimpleName()
                    , dtoList.stream().map(x -> x.getStudentId()).collect(Collectors.toSet())
                    , false
                    , Arrays.asList("scoreList","courseList"), false, null);

            // 验证主项被删除
            List<StudentDto> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(query);
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

        String name = testName + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("cur1").score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(1).name("cur2").score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        Student updateResult = mybatisAccessorService.save(studentDto, false, null, false, null);
        assert(updateResult != null);
        log.info(updateResult.toString());

        StudentDto result = mybatisAccessorService.getDtoByAnnotation(query, false, Arrays.asList("scoreList"));
        log.info(result.toString());
        assert(result != null);
        assert(result.getName().equals(name));
        assert(result.getScoreList() == null || result.getScoreList().size() == 0);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveIncludeAllChildren() throws MybatisAccessorException {

        String testName = "TestSaveIncludeAllChildren";
        StudentDto query = new StudentDto();
        query.setName(testName);

        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(query);
        if(dtoList != null && dtoList.size() > 0) {
            mybatisAccessorService.deleteByIds(StudentDto.class.getSimpleName()
                    , dtoList.stream().map(x -> x.getStudentId()).collect(Collectors.toSet())
                    , false
                    , Arrays.asList("scoreList","courseList"), false, null);

            // 验证主项被删除
            List<StudentDto> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(query);
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

        String name = testName + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("cur3-1").score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(1).name("cur3-2").score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto.setCourseList(courseDtoList);

        Student updateResult = mybatisAccessorService.save(studentDto, true, Arrays.asList("scoreList"), false, null);
        assert(updateResult != null);
        log.info(updateResult.toString());

        StudentDto queryDto = new StudentDto();
        queryDto.setName(name);

        StudentDto result = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));
        log.info(result.toString());
        assert(result != null);
        assert(result.getName().equals(name));
        assert(result.getScoreList() != null && result.getScoreList().size() == 2);
        assert(result.getCourseList() != null && result.getCourseList().size() == 3);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveChildrenOnly() throws MybatisAccessorException {
        String testName = "TestSaveChildrenOnly";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(StudentDto.class.getSimpleName()
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

        String name = testName + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        Student updateResult0 = mybatisAccessorService.save(studentDto, false, null, false, null);
        assert(updateResult0 != null);
        studentDto.setStudentId(updateResult0.getStudentId());

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("c_" + name).score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(2).name("c_" + name).score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto.setCourseList(courseDtoList);

        // 插入数据
        Student updateResult = mybatisAccessorService.save(studentDto, false, Arrays.asList("scoreList","courseList"), true, null);
        assert(updateResult != null);
        log.info(updateResult.toString());

        StudentDto result = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));
        log.info(result.toString());

        // 验证主项被插入
        assert(result != null);
        assert(result.getName().equals(name));

        // 验证子项被插入
        assert(result.getScoreList() != null);
        assert(result.getScoreList().size() == 2);
        assert(result.getScoreList().stream().filter(x -> !("c_" + name).equals(x.getName())).count() == 0);

        assert(result.getCourseList() != null);
        assert(result.getCourseList().size() == 3);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestDeleteCertainChildren() throws MybatisAccessorException {

        String testName = "TestDeleteCertainChildren";
        StudentDto query = new StudentDto();
        query.setName(testName);

        String name = testName + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("cur3-1").score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(1).name("cur3-2").score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto.setCourseList(courseDtoList);

        Student updateResult = mybatisAccessorService.save(studentDto, true, Arrays.asList("scoreList"), false, null);
        assert(updateResult != null);
        log.info(updateResult.toString());

        StudentDto queryDto = new StudentDto();
        queryDto.setName(name);

        StudentDto result = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));
        log.info(result.toString());
        assert(result != null);
        assert(result.getName().equals(name));
        assert(result.getScoreList() != null && result.getScoreList().size() == 2);
        assert(result.getCourseList() != null && result.getCourseList().size() == 3);

        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(query);
        if(dtoList != null && dtoList.size() > 0) {
            mybatisAccessorService.deleteByIds(StudentDto.class.getName()
                    , dtoList.stream().map(x -> x.getStudentId()).collect(Collectors.toSet())
                    , false
                    , Arrays.asList("scoreList"), false, null);

            // 验证主项被删除
            List<StudentDto> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(query);
            assert(dtoListVerify == null || dtoListVerify.size() == 0);

            for(StudentDto dto : dtoList) {
                // 验证子项未被删除
                StudentCourseDto query1 = new StudentCourseDto();
                query1.setStudentId(dto.getStudentId());
                List<StudentCourse> studentCourseList = mybatisAccessorService.getEntityListByAnnotation(query1);
                assert(studentCourseList != null && studentCourseList.size() > 0);

                // 验证子项被删除
                ScoreDto query2 = new ScoreDto();
                query2.setStudentId(dto.getStudentId());
                List<Score> scoreList = mybatisAccessorService.getEntityListByAnnotation(query2);
                assert(scoreList == null || scoreList.size() == 0);
            }
        }
        StudentDto result2 = mybatisAccessorService.getDtoByAnnotation(queryDto, false, Arrays.asList("scoreList","courseList"));
        assert(result2 == null);
        for(StudentCourseDto studentCourseDto : result.getCourseList()) {
            StudentCourse studentCourse = mybatisAccessorService.getEntityById(StudentCourseDto.class, studentCourseDto.getStudentCourseId());
            assert(studentCourse != null);
            assert(studentCourse.getDeleted() != 1);
            studentCourseMapper.deleteById(studentCourse.getStudentCourseId());
        }
    }

    @Test
    @Transactional
    public void TestRollBack() throws MybatisAccessorException {
        String testName = "TestRollBack";
        StudentDto query = new StudentDto();
        query.setName(testName);

        String name = testName + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("cur3-1").score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(1).name("cur3-2").score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto.setCourseList(courseDtoList);

        Student updateResult = mybatisAccessorService.save(studentDto, true, Arrays.asList("scoreList","courseList"), false, null);
        assert(updateResult != null);
        log.info(updateResult.toString());

    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveListWithChildren() throws MybatisAccessorException {
        String testName = "TestSaveListWithChild";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(StudentDto.class.getSimpleName()
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

        String name = testName + "1" + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("c_" + name).score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(2).name("c_" + name).score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto.setCourseList(courseDtoList);

        String name2 = testName + "2" + sdf.format(new Date());
        StudentDto studentDto2 = new StudentDto();
        studentDto2.setName(name2);
        studentDto2.setGrade(2);

        // 插入数据
        List<Student> updateResult = mybatisAccessorService.saveList(Arrays.asList(studentDto2, studentDto)
                , false, Arrays.asList("scoreList","courseList"), false, null);
        assert(updateResult != null && updateResult.size() == 2 && updateResult.get(0).getStudentId() != null);
        log.info(updateResult.toString());

        StudentDto queryDto1 = new StudentDto();
        queryDto1.setName(testName + "1");
        StudentDto result = mybatisAccessorService.getDtoByAnnotation(queryDto1, false, Arrays.asList("scoreList","courseList"));
        log.info(result.toString());

        // 验证主项被插入
        assert(result != null);

        // 验证子项被插入
        assert(result.getScoreList() != null);
        assert(result.getScoreList().size() == 2);
        assert(result.getScoreList().stream().filter(x -> !("c_" + name).equals(x.getName())).count() == 0);

        assert(result.getCourseList() != null);
        assert(result.getCourseList().size() == 3);

        StudentDto queryDto2 = new StudentDto();
        queryDto2.setName(testName + "2");
        StudentDto result2 = mybatisAccessorService.getDtoByAnnotation(queryDto2, false, Arrays.asList("scoreList","courseList"));
        log.info(result2.toString());

        assert(result2 != null);
        assert(result2.getScoreList() != null || result2.getScoreList().size() == 0);
        assert(result2.getCourseList() != null || result2.getCourseList().size() == 0);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveListNoChildren() throws MybatisAccessorException {
        String testName = "TestSaveListNoChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(StudentDto.class.getSimpleName()
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

        String name = testName + "1" + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("c_" + name).score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(2).name("c_" + name).score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto.setCourseList(courseDtoList);

        String name2 = testName + "2" + sdf.format(new Date());
        StudentDto studentDto2 = new StudentDto();
        studentDto2.setName(name2);
        studentDto2.setGrade(2);

        // 插入数据
        List<Student> updateResult = mybatisAccessorService.saveList(Arrays.asList(studentDto2, studentDto)
                , false, null, false, null);
        assert(updateResult != null && updateResult.size() == 2 && updateResult.get(0).getStudentId() != null);
        log.info(updateResult.toString());

        StudentDto queryDto1 = new StudentDto();
        queryDto1.setName(testName + "1");
        StudentDto result = mybatisAccessorService.getDtoByAnnotation(queryDto1, false, Arrays.asList("scoreList","courseList"));
        log.info(result.toString());

        // 验证主项被插入
        assert(result != null);

        // 验证子项被插入
        assert(result.getScoreList() == null || result.getScoreList().size() == 0);
        assert(result.getCourseList() == null || result.getCourseList().size() == 0);

        StudentDto queryDto2 = new StudentDto();
        queryDto2.setName(testName + "2");
        StudentDto result2 = mybatisAccessorService.getDtoByAnnotation(queryDto2, false, Arrays.asList("scoreList","courseList"));
        log.info(result2.toString());

        assert(result2 != null);
        assert(result2.getScoreList() != null || result2.getScoreList().size() == 0);
        assert(result2.getCourseList() != null || result2.getCourseList().size() == 0);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveListAllChildren() throws MybatisAccessorException {
        String testName = "TestSaveListAllChildren";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(StudentDto.class.getSimpleName()
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

        String name = testName + "1" + sdf.format(new Date());
        StudentDto studentDto = new StudentDto();
        studentDto.setName(name);
        studentDto.setGrade(1);

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("c_" + name).score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(2).name("c_" + name).score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto.setCourseList(courseDtoList);

        String name2 = testName + "2" + sdf.format(new Date());
        StudentDto studentDto2 = new StudentDto();
        studentDto2.setName(name2);
        studentDto2.setGrade(2);

        // 插入数据
        List<Student> updateResult = mybatisAccessorService.saveList(Arrays.asList(studentDto2, studentDto)
                , true, Arrays.asList("scoreList"), false, null);
        assert(updateResult != null && updateResult.size() == 2 && updateResult.get(0).getStudentId() != null);
        log.info(updateResult.toString());

        StudentDto queryDto1 = new StudentDto();
        queryDto1.setName(testName + "1");
        StudentDto result = mybatisAccessorService.getDtoByAnnotation(queryDto1, false, Arrays.asList("scoreList","courseList"));
        log.info(result.toString());

        // 验证主项被插入
        assert(result != null);

        // 验证子项被插入
        assert(result.getScoreList() != null);
        assert(result.getScoreList().size() == 2);
        assert(result.getScoreList().stream().filter(x -> !("c_" + name).equals(x.getName())).count() == 0);

        assert(result.getCourseList() != null);
        assert(result.getCourseList().size() == 3);

        StudentDto queryDto2 = new StudentDto();
        queryDto2.setName(testName + "2");
        StudentDto result2 = mybatisAccessorService.getDtoByAnnotation(queryDto2, false, Arrays.asList("scoreList","courseList"));
        log.info(result2.toString());

        assert(result2 != null);
        assert(result2.getScoreList() != null || result2.getScoreList().size() == 0);
        assert(result2.getCourseList() != null || result2.getCourseList().size() == 0);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveListChildrenOnly() throws MybatisAccessorException {
        String testName = "TestSaveListChildrenOnly";
        StudentDto queryDto = new StudentDto();
        queryDto.setName(testName);
        List<StudentDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(StudentDto.class.getSimpleName()
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

        String name1 = testName + "1" + sdf.format(new Date());
        StudentDto studentDto1 = new StudentDto();
        studentDto1.setName(name1);
        studentDto1.setGrade(1);

        String name2 = testName + "2" + sdf.format(new Date());
        StudentDto studentDto2 = new StudentDto();
        studentDto2.setName(name2);
        studentDto2.setGrade(2);

        List<Student> updateResult0 = mybatisAccessorService.saveList(Arrays.asList(studentDto1, studentDto2)
                , false, null, false, null);
        assert(updateResult0 != null);
        studentDto1.setStudentId(updateResult0.get(0).getStudentId());
        studentDto2.setStudentId(updateResult0.get(1).getStudentId());

        List<ScoreDto> scoreDtoList = new ArrayList<>();
        ScoreDto score1 = ScoreDto.builder().courseId(1).name("c_" + name1).score(BigDecimal.valueOf(120)).build();
        scoreDtoList.add(score1);
        ScoreDto score2 = ScoreDto.builder().courseId(2).name("c_" + name1).score(BigDecimal.valueOf(121)).build();
        scoreDtoList.add(score2);
        studentDto1.setScoreList(scoreDtoList);

        List<StudentCourseDto> courseDtoList = new ArrayList<>();
        StudentCourseDto course1 = StudentCourseDto.builder().courseId(1).selectDate(new Date()).build();
        courseDtoList.add(course1);
        StudentCourseDto course2 = StudentCourseDto.builder().courseId(2).selectDate(new Date()).build();
        courseDtoList.add(course2);
        StudentCourseDto course3 = StudentCourseDto.builder().courseId(3).selectDate(new Date()).build();
        courseDtoList.add(course3);
        studentDto1.setCourseList(courseDtoList);

        ScoreDto scoreV21 = ScoreDto.builder().courseId(1).name("c_" + name2).score(BigDecimal.valueOf(333)).build();
        ScoreDto scoreV22 = ScoreDto.builder().courseId(2).name("c_" + name2).score(BigDecimal.valueOf(291)).build();
        studentDto2.setScoreList(Arrays.asList(scoreV21, scoreV22));

        // 插入数据
        List<Student> updateResult = mybatisAccessorService.saveList(Arrays.asList(studentDto1, studentDto2)
                , false, Arrays.asList("scoreList","courseList"), true, null);
        assert(updateResult != null && updateResult.size() == 2);
        log.info(updateResult.toString());

        StudentDto queryDto1 = new StudentDto();
        queryDto1.setName(name1);
        StudentDto result1 = mybatisAccessorService.getDtoByAnnotation(queryDto1, false, Arrays.asList("scoreList","courseList"));
        log.info(result1.toString());

        // 验证主项被插入
        assert(result1 != null);
        assert(result1.getName().equals(name1));

        // 验证子项被插入
        assert(result1.getScoreList() != null);
        assert(result1.getScoreList().size() == 2);
        assert(result1.getScoreList().stream().filter(x -> !("c_" + name1).equals(x.getName())).count() == 0);

        assert(result1.getCourseList() != null);
        assert(result1.getCourseList().size() == 3);

        StudentDto queryDto2 = new StudentDto();
        queryDto2.setName(name2);
        StudentDto result2 = mybatisAccessorService.getDtoByAnnotation(queryDto2, false, Arrays.asList("scoreList","courseList"));
        log.info(result2.toString());

        // 验证主项被插入
        assert(result2 != null);
        assert(result2.getName().equals(name2));

        // 验证子项被插入
        assert(result2.getScoreList() != null);
        assert(result2.getScoreList().size() == 2);
        assert(result2.getScoreList().stream().filter(x -> !("c_" + name2).equals(x.getName())).count() == 0);

        assert(result2.getCourseList() == null || result2.getCourseList().size() == 0);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveCascade() throws MybatisAccessorException {
        Integer i = 0;
        String name = "testCascade";

        ClassGroupDto query = ClassGroupDto.builder().groupName(name).build();
        List<ClassGroup> classGroupList = mybatisAccessorService.getEntityListByAnnotation(query);
        if(classGroupList != null && classGroupList.size() > 0) {
            mybatisAccessorService.deleteByIds(ClassGroupDto.class.getSimpleName()
                    , classGroupList.stream().map(x -> x.getClassGroupId()).collect(Collectors.toSet())
                    , false
                    , null, false, null);
            // 验证主项被删除
            List<StudentDto> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(query);
            assert(dtoListVerify == null || dtoListVerify.size() == 0);
        }

        ClassGroupDto top = ClassGroupDto.builder()
                .groupName(name + "0").build();

        top.setCreateUser(i++);
        top.setCreateUser(i++);

        ClassGroupDto generation1 = ClassGroupDto.builder()
                .groupName(name + "1").build();

        generation1.setCreateUser(i++);
        generation1.setCreateUser(i++);

        ClassGroupDto generation11 = ClassGroupDto.builder()
                .groupName(name + "1-1").build();

        generation11.setCreateUser(i++);
        generation11.setCreateUser(i++);

        ClassGroupDto generation12 = ClassGroupDto.builder()
                .groupName(name + "1-2").build();

        generation12.setCreateUser(i++);
        generation12.setCreateUser(i++);

        ClassGroupDto generation2 = ClassGroupDto.builder()
                .groupName(name + "2").build();

        generation2.setCreateUser(i++);
        generation2.setCreateUser(i++);

        ClassGroupDto generation21 = ClassGroupDto.builder()
                .groupName(name + "2-1").build();

        generation21.setCreateUser(i++);
        generation21.setCreateUser(i++);

        ClassGroupDto generation22 = ClassGroupDto.builder()
                .groupName(name + "2-2").build();

        generation22.setCreateUser(i++);
        generation22.setCreateUser(i++);

        ClassGroupDto generation221 = ClassGroupDto.builder()
                .groupName(name + "2-2-1").build();

        generation221.setCreateUser(i++);
        generation221.setCreateUser(i++);

        ClassGroupDto generation222 = ClassGroupDto.builder()
                .groupName(name + "2-2-2").build();

        generation222.setCreateUser(i++);
        generation222.setCreateUser(i++);

        ClassGroupDto generation23 = ClassGroupDto.builder()
                .groupName(name +"2-3").build();

        generation23.setCreateUser(i++);
        generation23.setCreateUser(i++);

        generation22.setSubClassGroups(Arrays.asList(generation221, generation222));
        generation1.setSubClassGroups(Arrays.asList(generation11, generation12));
        generation2.setSubClassGroups(Arrays.asList(generation21, generation22, generation23));

        top.setSubClassGroups(Arrays.asList(generation1, generation2));

        mybatisAccessorService.save(top, true, null, false, null);

        List<ClassGroup> classGroupList2 = mybatisAccessorService.getEntityListByAnnotation(query);
        assert(classGroupList2 != null && classGroupList2.size() > 0);
        ClassGroup classGroup0 = classGroupList2.stream().filter(x -> (name + "0").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup1 = classGroupList2.stream().filter(x -> (name + "1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup2 = classGroupList2.stream().filter(x -> (name + "2").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup11 = classGroupList2.stream().filter(x -> (name + "1-1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup12 = classGroupList2.stream().filter(x -> (name + "1-2").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup21 = classGroupList2.stream().filter(x -> (name + "2-1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup22 = classGroupList2.stream().filter(x -> (name + "2-2").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup23 = classGroupList2.stream().filter(x -> (name + "2-3").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup221 = classGroupList2.stream().filter(x -> (name + "2-2-1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup222 = classGroupList2.stream().filter(x -> (name + "2-2-2").equals(x.getGroupName())).findAny().get();
        assert(classGroup222.getUpperClassGroupId().equals(classGroup22.getClassGroupId()));
        assert(classGroup221.getUpperClassGroupId().equals(classGroup22.getClassGroupId()));
        assert(classGroup21.getUpperClassGroupId().equals(classGroup2.getClassGroupId()));
        assert(classGroup22.getUpperClassGroupId().equals(classGroup2.getClassGroupId()));
        assert(classGroup23.getUpperClassGroupId().equals(classGroup2.getClassGroupId()));

        assert(classGroup11.getUpperClassGroupId().equals(classGroup1.getClassGroupId()));
        assert(classGroup12.getUpperClassGroupId().equals(classGroup1.getClassGroupId()));

        assert(classGroup1.getUpperClassGroupId().equals(classGroup0.getClassGroupId()));
        assert(classGroup2.getUpperClassGroupId().equals(classGroup0.getClassGroupId()));

        ClassGroupDto query2 = ClassGroupDto.builder().groupName(name + "2-2").build();
        List<ClassGroup> classGroupList22 = mybatisAccessorService.getEntityListByAnnotation(query2);
        List<ClassGroup> collect = classGroupList22.stream().filter(x -> (name + "2-2").equals(x.getGroupName())).collect(Collectors.toList());
        assert(collect != null && collect.size() > 0);
        assert(collect.get(0).getSubClassGroupList().size() == 2);
        assert(collect.get(0).getUpperClassGroup() != null);
        assert(collect.size() == 1);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void TestSaveCascadeList() throws MybatisAccessorException {
        Integer i = 0;
        String name = "testCascadeList";

        ClassGroupDto query = ClassGroupDto.builder().groupName(name).build();
        List<ClassGroup> classGroupList = mybatisAccessorService.getEntityListByAnnotation(query);
        if(classGroupList != null && classGroupList.size() > 0) {
            mybatisAccessorService.deleteByIds(ClassGroupDto.class.getSimpleName()
                    , classGroupList.stream().map(x -> x.getClassGroupId()).collect(Collectors.toSet())
                    , false
                    , null, false, null);
            // 验证主项被删除
            List<StudentDto> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(query);
            assert(dtoListVerify == null || dtoListVerify.size() == 0);
        }

        ClassGroupDto top0 = ClassGroupDto.builder()
                .groupName(name + "0").build();

        top0.setCreateUser(i++);
        top0.setCreateUser(i++);

        ClassGroupDto generation1 = ClassGroupDto.builder()
                .groupName(name + "1").build();

        generation1.setCreateUser(i++);
        generation1.setCreateUser(i++);

        ClassGroupDto generation11 = ClassGroupDto.builder()
                .groupName(name + "1-1").build();

        generation11.setCreateUser(i++);
        generation11.setCreateUser(i++);

        ClassGroupDto generation12 = ClassGroupDto.builder()
                .groupName(name + "1-2").build();

        generation12.setCreateUser(i++);
        generation12.setCreateUser(i++);

        ClassGroupDto generation2 = ClassGroupDto.builder()
                .groupName(name + "2").build();

        generation2.setCreateUser(i++);
        generation2.setCreateUser(i++);

        ClassGroupDto generation21 = ClassGroupDto.builder()
                .groupName(name + "2-1").build();

        generation21.setCreateUser(i++);
        generation21.setCreateUser(i++);

        ClassGroupDto generation22 = ClassGroupDto.builder()
                .groupName(name + "2-2").build();

        generation22.setCreateUser(i++);
        generation22.setCreateUser(i++);

        ClassGroupDto generation221 = ClassGroupDto.builder()
                .groupName(name + "2-2-1").build();

        generation221.setCreateUser(i++);
        generation221.setCreateUser(i++);

        ClassGroupDto generation222 = ClassGroupDto.builder()
                .groupName(name + "2-2-2").build();

        generation222.setCreateUser(i++);
        generation222.setCreateUser(i++);

        ClassGroupDto generation23 = ClassGroupDto.builder()
                .groupName(name +"2-3").build();

        generation23.setCreateUser(i++);
        generation23.setCreateUser(i++);

        generation22.setSubClassGroups(Arrays.asList(generation221, generation222));
        generation1.setSubClassGroups(Arrays.asList(generation11, generation12));
        generation2.setSubClassGroups(Arrays.asList(generation21, generation22, generation23));

        top0.setSubClassGroups(Arrays.asList(generation1, generation2));


        ClassGroupDto top1 = ClassGroupDto.builder()
                .groupName(name + "A").build();

        top1.setCreateUser(i++);
        top1.setCreateUser(i++);

        ClassGroupDto generationA = ClassGroupDto.builder()
                .groupName(name + "A-1").build();

        generationA.setCreateUser(i++);
        generationA.setCreateUser(i++);

        ClassGroupDto generationA1 = ClassGroupDto.builder()
                .groupName(name + "A-1-1").build();

        generationA1.setCreateUser(i++);
        generationA1.setCreateUser(i++);

        generationA.setSubClassGroups(Arrays.asList(generationA1));
        top1.setSubClassGroups(Arrays.asList(generationA));

        mybatisAccessorService.saveList(Arrays.asList(top0, top1), true, null, false, null);

        List<ClassGroup> classGroupList2 = mybatisAccessorService.getEntityListByAnnotation(query);
        assert(classGroupList2 != null && classGroupList2.size() > 0);
        ClassGroup classGroup0 = classGroupList2.stream().filter(x -> (name + "0").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup1 = classGroupList2.stream().filter(x -> (name + "1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup2 = classGroupList2.stream().filter(x -> (name + "2").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup11 = classGroupList2.stream().filter(x -> (name + "1-1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup12 = classGroupList2.stream().filter(x -> (name + "1-2").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup21 = classGroupList2.stream().filter(x -> (name + "2-1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup22 = classGroupList2.stream().filter(x -> (name + "2-2").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup23 = classGroupList2.stream().filter(x -> (name + "2-3").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup221 = classGroupList2.stream().filter(x -> (name + "2-2-1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroup222 = classGroupList2.stream().filter(x -> (name + "2-2-2").equals(x.getGroupName())).findAny().get();
        assert(classGroup222.getUpperClassGroupId().equals(classGroup22.getClassGroupId()));
        assert(classGroup221.getUpperClassGroupId().equals(classGroup22.getClassGroupId()));
        assert(classGroup21.getUpperClassGroupId().equals(classGroup2.getClassGroupId()));
        assert(classGroup22.getUpperClassGroupId().equals(classGroup2.getClassGroupId()));
        assert(classGroup23.getUpperClassGroupId().equals(classGroup2.getClassGroupId()));

        assert(classGroup11.getUpperClassGroupId().equals(classGroup1.getClassGroupId()));
        assert(classGroup12.getUpperClassGroupId().equals(classGroup1.getClassGroupId()));

        assert(classGroup1.getUpperClassGroupId().equals(classGroup0.getClassGroupId()));
        assert(classGroup2.getUpperClassGroupId().equals(classGroup0.getClassGroupId()));

        ClassGroup classGroupA = classGroupList2.stream().filter(x -> (name + "A").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroupA1 = classGroupList2.stream().filter(x -> (name + "A-1").equals(x.getGroupName())).findAny().get();
        ClassGroup classGroupA11 = classGroupList2.stream().filter(x -> (name + "A-1-1").equals(x.getGroupName())).findAny().get();

        assert(classGroupA1.getUpperClassGroupId().equals(classGroupA.getClassGroupId()));
        assert(classGroupA11.getUpperClassGroupId().equals(classGroupA1.getClassGroupId()));
    }
}

