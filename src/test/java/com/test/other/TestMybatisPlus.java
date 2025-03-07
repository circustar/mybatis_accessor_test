package com.test.other;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.circustar.mybatis_accessor.class_info.DtoClassInfoHelper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.WebTestApplication;
import com.test.mybatis_accessor.dto.StudentDto;
import com.test.mybatis_accessor.entity.Score;
import com.test.mybatis_accessor.entity.Student;
import com.test.mybatis_accessor.service.IStudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class TestMybatisPlus {

    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String studentName = "testPlusStudent";
    private String courseNamePrefix = "testPlusCourse";

    private StudentDto studentDto1 = null;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private DtoClassInfoHelper dtoClassInfoHelper;


//    @Before
//    public void createTestData() {
//        if(this.studentDto1 == null) {
//            DataSupport.removeStudentData(mybatisAccessorService, studentName);
//            this.studentDto1 = DataSupport.createStudentData(mybatisAccessorService, studentName + "A1", 3, 0, courseNamePrefix);
//        }
//    }

    @Test
    public void testUpdateByQueryWrapper() {
        QueryWrapper<Score> qw = new QueryWrapper<>();
        qw.select("max(t.score) as score");

        UpdateWrapper<Student> uw = new UpdateWrapper<>();
        uw.eq("student_id",120);
        uw.setSql("score = (select sum(t2.score) from score t2 where t2.student_id = student.student_id)");
        log.info(uw.getSqlSet());
        log.info(uw.getSqlSegment());
        log.info(uw.getTargetSql());

//        studentService.update(qw);
    }

    @Test
    public void testSelectByQueryWrapper() {
        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.eq("name", "me");

        studentService.list(qw);

//        studentService.update(qw);
    }


    @Test
    public void testUpdateExpressionByQueryWrapper() {
        UpdateWrapper<Student> uw = new UpdateWrapper<>();
        uw.eq("student_id",120);
        uw.setSql("score = (select sum(t2.score) from score t2 where t2.student_id = student.student_id)");
        log.info(uw.getSqlSet());
        log.info(uw.getSqlSegment());
        log.info(uw.getTargetSql());

//        studentService.update(qw);
    }

    @Test
    public void testUpdateExpressionByQueryWrapper2() {
        UpdateWrapper<Student> uw = new UpdateWrapper<>();
        uw.eq("student_id",120);
        uw.set("grade", 123);
        uw.set("version", null);
        uw.setSql(" name = 'hell' ");
        uw.setSql("score = (select sum(t2.score) from score t2 where t2.student_id = student.student_id)");
        log.info(uw.getSqlSet());
        log.info(uw.getSqlSegment());
        log.info(uw.getTargetSql());

        //studentService.update(uw);

//        studentService.update(qw);
    }

    @Test
    public void testUpdateExpressionByQueryWrapper3() {
        BigDecimal zero = BigDecimal.ZERO;
        log.info(zero.toString());
        Float f = 10.23f;
        log.info(f.toString());
        Double d = 10.23d;
        log.info(d.toString());
        Long l = 12L;
        log.info(l.toString());
    }

    @Test
    public void testUpdateNothing() {

        UpdateWrapper<Student> uw = new UpdateWrapper<>();
        uw.eq("student_id",120);
        log.info(uw.getSqlSet());
        log.info(uw.getSqlSegment());
        log.info(uw.getTargetSql());
        studentService.update(uw);
    }

    @Test
    public void testUpdateNothing2() {

        Student s = new Student();
        s.setStudentId(120);
        studentService.updateById(s);
    }

    @Test
    public void testInsertBatch() throws MybatisAccessorException {
        StudentDto s1 = StudentDto.builder().name("1").grade(1).build();
        StudentDto s2 = StudentDto.builder().name("1").grade(1).build();
        List<StudentDto> a1 = new ArrayList<>();
        a1.add(s1);
        a1.add(s2);
        mybatisAccessorService.updateList(a1,  Collections.emptyList(), false, null);
    }
}
