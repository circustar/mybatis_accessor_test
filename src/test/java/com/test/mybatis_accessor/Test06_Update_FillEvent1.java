package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.*;
import com.test.mybatis_accessor.entity.PersonInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test06_Update_FillEvent1 {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String namePrefix = "testFillEvent_";

    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    private boolean assertSharePoll1(PersonInfo5Dto main, List<PersonInfo5Dto> subList) {
        for(PersonInfo5Dto sub : subList) {
            assert(sub.getWeight().compareTo(sub.getSharePoll()) >= 0);
        }
        BigDecimal sumWeight = subList.stream().map(x -> x.getSharePoll()).reduce((x, y) -> x.add(y)).get();
        BigDecimal sumSharePoll = subList.stream().map(x -> x.getSharePoll()).reduce((x, y) -> x.add(y)).get();
        if(sumWeight.compareTo(main.getSharePoll()) >= 0) {
            assert(main.getSharePoll().compareTo(sumSharePoll) == 0);
        } else {
            assert(sumWeight.compareTo(sumSharePoll) == 0);
        }
        return true;
    }

    private void testInner(String namePrefix, BigDecimal totalSharePoll) throws MybatisAccessorException {
        //String testName = namePrefix + sdf.format(new Date()) + "_";
        String testName = namePrefix;
        BigDecimal point4_1 = BigDecimal.valueOf(75.56d);
        BigDecimal weight4_1 = BigDecimal.valueOf(23.12d);
        PersonInfo5Dto personInfo5_1 = PersonInfo5Dto.builder().personName(testName + "4-1").point(point4_1).deleted(0)
                .weight(weight4_1).sharePoll(BigDecimal.ZERO).build();
        personInfo5_1.setCreateUser(1);

        BigDecimal point4_2 = BigDecimal.valueOf(45.15d);
        BigDecimal weight4_2 = BigDecimal.valueOf(33.16d);
        PersonInfo5Dto personInfo5_2 = PersonInfo5Dto.builder().personName(testName + "4-2").point(point4_2).deleted(0)
                .weight(weight4_2).sharePoll(BigDecimal.ZERO).build();
        personInfo5_2.setCreateUser(2);

        BigDecimal point3_1 = BigDecimal.valueOf(35.5d);
        BigDecimal weight3_1 = BigDecimal.valueOf(46.98d);
        PersonInfo5Dto personInfo3_1 = PersonInfo5Dto.builder().personName(testName + "3-1").point(point3_1).deleted(0)
                .weight(weight3_1).sharePoll(BigDecimal.ZERO).build();
        personInfo3_1.setPersonInfoList(Arrays.asList(personInfo5_1, personInfo5_2));
        personInfo3_1.setCreateUser(3);

        BigDecimal point3_2 = BigDecimal.valueOf(18.88d);
        BigDecimal weight3_2 = BigDecimal.valueOf(29.48d);
        PersonInfo5Dto personInfo3_2 = PersonInfo5Dto.builder().personName(testName + "3-2").point(point3_2).deleted(0)
                .weight(weight3_2).sharePoll(BigDecimal.ZERO).build();
        personInfo3_2.setCreateUser(4);

        BigDecimal point3_3 = BigDecimal.valueOf(89.23);
        BigDecimal weight3_3 = BigDecimal.valueOf(66.75);
        PersonInfo5Dto personInfo3_3 = PersonInfo5Dto.builder().personName(testName + "3-3").point(point3_3).deleted(0)
                .weight(weight3_3).sharePoll(BigDecimal.ZERO).build();
        personInfo3_3.setCreateUser(5);

        BigDecimal point3_4 = BigDecimal.valueOf(49.82);
        BigDecimal weight3_4 = BigDecimal.valueOf(28.02);
        PersonInfo5Dto personInfo3_4 = PersonInfo5Dto.builder().personName(testName + "3-4").point(point3_4).deleted(0)
                .weight(weight3_4).sharePoll(BigDecimal.ZERO).build();
        personInfo3_4.setCreateUser(5);

        BigDecimal point2_1 = BigDecimal.valueOf(47.87);
        BigDecimal weight2_1 = BigDecimal.valueOf(56.54);
        PersonInfo5Dto personInfo2_1 = PersonInfo5Dto.builder().personName(testName + "2-1").point(point2_1).deleted(0)
                .weight(weight2_1).sharePoll(BigDecimal.ZERO).build();
        personInfo2_1.setPersonInfoList(Arrays.asList(personInfo3_1, personInfo3_2, personInfo3_3));
        personInfo2_1.setCreateUser(6);

        BigDecimal point2_2 = BigDecimal.valueOf(86.83);
        BigDecimal weight2_2 = BigDecimal.valueOf(67.71);
        PersonInfo5Dto personInfo2_2 = PersonInfo5Dto.builder().personName(testName + "2-2").point(point2_2).deleted(0)
                .weight(weight2_2).sharePoll(BigDecimal.ZERO).build();
        personInfo2_2.setPersonInfoList(Arrays.asList(personInfo3_4));
        personInfo2_2.setCreateUser(7);

        BigDecimal point1_1 = BigDecimal.valueOf(87.82);
        BigDecimal weight1_1 = BigDecimal.valueOf(29.87);
        PersonInfo5Dto personInfo1_1 = PersonInfo5Dto.builder().personName(testName + "1-1").point(point1_1).deleted(0)
                .weight(weight1_1).sharePoll(totalSharePoll).build();
        personInfo1_1.setPersonInfoList(Arrays.asList(personInfo2_1, personInfo2_2));
        personInfo1_1.setCreateUser(8);

        PersonInfo mainPersonInfo = mybatisAccessorService.save(personInfo1_1, true, null, false, null);
        assert(mainPersonInfo != null);
        assert(mainPersonInfo.getPersonId() != null);
        assert(mainPersonInfo.getLeaderId() == null);
        QueryWrapper qw = new QueryWrapper<>();
        qw.likeRight("person_info.person_name",testName);

        List<PersonInfo5Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo5Dto.class, qw);
        assert(dtoList.size() == 9);

        List<PersonInfo5Dto> Person1_1 = dtoList.stream().filter(x -> (testName + "1-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person1_1.size() == 1);
        List<PersonInfo5Dto> Person2_1 = dtoList.stream().filter(x -> (testName + "2-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person2_1.size() == 1);
        List<PersonInfo5Dto> Person2_2 = dtoList.stream().filter(x -> (testName + "2-2").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person2_2.size() == 1);

        List<PersonInfo5Dto> Person3_1 = dtoList.stream().filter(x -> (testName + "3-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person3_1.size() == 1);
        List<PersonInfo5Dto> Person3_2 = dtoList.stream().filter(x -> (testName + "3-2").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person3_2.size() == 1);
        List<PersonInfo5Dto> Person3_3 = dtoList.stream().filter(x -> (testName + "3-3").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person3_3.size() == 1);
        List<PersonInfo5Dto> Person3_4 = dtoList.stream().filter(x -> (testName + "3-4").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person3_4.size() == 1);

        List<PersonInfo5Dto> Person4_1 = dtoList.stream().filter(x -> (testName + "4-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person4_1.size() == 1);
        List<PersonInfo5Dto> Person4_2 = dtoList.stream().filter(x -> (testName + "4-2").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person4_2.size() == 1);

        assert(Person4_1.get(0).getLeaderId().equals(Person3_1.get(0).getPersonId()));
        assert(Person4_2.get(0).getLeaderId().equals(Person3_1.get(0).getPersonId()));

        assert(Person3_1.get(0).getLeaderId().equals(Person2_1.get(0).getPersonId()));
        assert(Person3_2.get(0).getLeaderId().equals(Person2_1.get(0).getPersonId()));
        assert(Person3_3.get(0).getLeaderId().equals(Person2_1.get(0).getPersonId()));
        assert(Person3_4.get(0).getLeaderId().equals(Person2_2.get(0).getPersonId()));

        assert(Person2_1.get(0).getLeaderId().equals(Person1_1.get(0).getPersonId()));
        assert(Person2_2.get(0).getLeaderId().equals(Person1_1.get(0).getPersonId()));

        assert(personInfo1_1.getSharePoll().subtract(personInfo1_1.getRemainPoll()).compareTo(Person2_1.get(0).getSharePoll().add(Person2_2.get(0).getSharePoll())) == 0);

        assertSharePoll1(Person1_1.get(0), Arrays.asList(Person2_1.get(0), Person2_2.get(0)));
        assertSharePoll1(Person2_1.get(0), Arrays.asList(Person3_1.get(0), Person3_2.get(0), Person3_3.get(0)));
        assertSharePoll1(Person2_2.get(0), Arrays.asList(Person3_4.get(0)));
        assertSharePoll1(Person3_1.get(0), Arrays.asList(Person4_1.get(0), Person4_2.get(0)));
    }

    @Test
    @Rollback(false)
    public void Test0() throws MybatisAccessorException {
        String testName = namePrefix ;

        QueryWrapper qw = new QueryWrapper<>();
        qw.likeRight("person_name",testName);

        List<PersonInfoDto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfoDto.class, qw);
        Set<Serializable> idList = dtoList.stream().filter(x -> x.getLeaderId() == null)
                .map(x -> x.getPersonId()).collect(Collectors.toSet());
        mybatisAccessorService.deleteByIds(PersonInfoDto.class, idList, true, null, false, null);

        QueryWrapper qw2 = new QueryWrapper<>();
        qw2.likeRight("person_name",testName);
        dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfoDto.class, qw);
        assert(dtoList.isEmpty());
    }

    @Test
    @Rollback(false)
    @Order(1)
    public void Test1() throws MybatisAccessorException {
        testInner(namePrefix + "_1_", BigDecimal.valueOf(100));
    }

    @Test
    @Rollback(false)
    @Order(1)
    public void Test2() throws MybatisAccessorException {
        testInner(namePrefix + "_2_", BigDecimal.valueOf(180));
    }

    @Test
    @Rollback(false)
    @Order(1)
    public void Test3() throws MybatisAccessorException {
        testInner(namePrefix + "_3_", BigDecimal.valueOf(30));
    }

}
