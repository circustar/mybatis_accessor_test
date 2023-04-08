package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.PersonInfo7Dto;
import com.test.mybatis_accessor.entity.PersonInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test05_UpdateEventExecutorSql3 {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String namePrefix = "testCaseSql3_";

    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    @Test
    public void Test00() throws MybatisAccessorException {
        String testName = namePrefix + sdf.format(new Date()) + "_";

        QueryWrapper qw = new QueryWrapper<>();
        qw.likeRight("person_name",testName);
        qw.likeLeft("person_name","1-1");

        List<PersonInfo7Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo7Dto.class, qw);
        if(dtoList.size() == 0) {
            return;
        }
        Set<Serializable> idList = dtoList.stream().map(x -> x.getPersonId()).collect(Collectors.toSet());
        mybatisAccessorService.deleteByIds(PersonInfo7Dto.class, idList, true, null, false, null);

        QueryWrapper qw2 = new QueryWrapper<>();
        qw2.likeRight("person_name",testName);
        dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo7Dto.class, qw);
        assert(dtoList.isEmpty());
    }

    @Test
    public void Test01() throws MybatisAccessorException {
        String testName = namePrefix + sdf.format(new Date()) + "_";
        BigDecimal point4_1 = BigDecimal.valueOf(75.56d);
        BigDecimal weight4_1 = BigDecimal.valueOf(23.12d);
        PersonInfo7Dto personInfo4_1 = PersonInfo7Dto.builder().personName(testName + "4-1").point(point4_1).deleted(0)
                .weight(weight4_1).build();
        personInfo4_1.setCreateUser(1);

        BigDecimal point4_2 = BigDecimal.valueOf(45.15d);
        BigDecimal weight4_2 = BigDecimal.valueOf(33.16d);
        PersonInfo7Dto personInfo4_2 = PersonInfo7Dto.builder().personName(testName + "4-2").point(point4_2).deleted(0)
                .weight(weight4_2).build();
        personInfo4_2.setCreateUser(2);

        BigDecimal point3_1 = BigDecimal.valueOf(35.5d);
        BigDecimal weight3_1 = BigDecimal.valueOf(46.98d);
        PersonInfo7Dto personInfo3_1 = PersonInfo7Dto.builder().personName(testName + "3-1").point(point3_1).deleted(0)
                .weight(weight3_1).build();
        personInfo3_1.setPersonInfoList(Arrays.asList(personInfo4_1, personInfo4_2));
        personInfo3_1.setCreateUser(3);

        BigDecimal point3_2 = BigDecimal.valueOf(18.88d);
        BigDecimal weight3_2 = BigDecimal.valueOf(29.48d);
        PersonInfo7Dto personInfo3_2 = PersonInfo7Dto.builder().personName(testName + "3-2").point(point3_2).deleted(0)
                .weight(weight3_2).build();
        personInfo3_2.setCreateUser(4);

        BigDecimal point3_3 = BigDecimal.valueOf(89.23);
        BigDecimal weight3_3 = BigDecimal.valueOf(66.75);
        PersonInfo7Dto personInfo3_3 = PersonInfo7Dto.builder().personName(testName + "3-3").point(point3_3).deleted(0)
                .weight(weight3_3).build();
        personInfo3_3.setCreateUser(5);

        BigDecimal point3_4 = BigDecimal.valueOf(49.82);
        BigDecimal weight3_4 = BigDecimal.valueOf(28.02);
        PersonInfo7Dto personInfo3_4 = PersonInfo7Dto.builder().personName(testName + "3-4").point(point3_4).deleted(0)
                .weight(weight3_4).build();
        personInfo3_4.setCreateUser(5);

        BigDecimal point2_1 = BigDecimal.valueOf(47.87);
        BigDecimal weight2_1 = BigDecimal.valueOf(56.54);
        PersonInfo7Dto personInfo2_1 = PersonInfo7Dto.builder().personName(testName + "2-1").point(point2_1).deleted(0)
                .weight(weight2_1).build();
        personInfo2_1.setPersonInfoList(Arrays.asList(personInfo3_1, personInfo3_2, personInfo3_3));
        personInfo2_1.setCreateUser(6);

        BigDecimal point2_2 = BigDecimal.valueOf(86.83);
        BigDecimal weight2_2 = BigDecimal.valueOf(67.71);
        PersonInfo7Dto personInfo2_2 = PersonInfo7Dto.builder().personName(testName + "2-2").point(point2_2).deleted(0)
                .weight(weight2_2).build();
        personInfo2_2.setPersonInfoList(Arrays.asList(personInfo3_4));
        personInfo2_2.setCreateUser(7);

        BigDecimal point1_1 = BigDecimal.valueOf(87.82);
        BigDecimal weight1_1 = BigDecimal.valueOf(29.87);
        PersonInfo7Dto personInfo1_1 = PersonInfo7Dto.builder().personName(testName + "1-1").point(point1_1).deleted(0)
                .weight(weight1_1).sharePoll(BigDecimal.valueOf(100L)).build();
        personInfo1_1.setPersonInfoList(Arrays.asList(personInfo2_1, personInfo2_2));
        personInfo1_1.setCreateUser(8);

        PersonInfo mainPersonInfo = mybatisAccessorService.save(personInfo1_1, true, null, false, null);
        assert(mainPersonInfo != null);
        assert(mainPersonInfo.getPersonId() != null);
        assert(mainPersonInfo.getLeaderId() == null);

        QueryWrapper qw = new QueryWrapper<>();
        qw.likeRight("person_name",testName);

        List<PersonInfo7Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo7Dto.class, qw);
        assert(dtoList.size() == 9);
        assert(dtoList.stream().filter(x -> BigDecimal.valueOf(99).compareTo(x.getSharePoll()) == 0).count() == 9);
    }
}
