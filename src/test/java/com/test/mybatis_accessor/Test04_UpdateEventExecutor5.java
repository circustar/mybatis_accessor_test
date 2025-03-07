package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.provider.command.IUpdateCommand;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.component.ExecuteUpdateBean2;
import com.test.mybatis_accessor.dto.PersonInfo8Dto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test04_UpdateEventExecutor5 {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String namePrefix = "testCase5_";

    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    @Autowired
    private ExecuteUpdateBean2 executeUpdateBean2;

    @Test
    @Rollback(false)
    @Order(1)
    public void Test1() throws MybatisAccessorException {
        String testName = namePrefix + sdf.format(new Date()) + "_";

        BigDecimal point2_1 = BigDecimal.valueOf(47.87);
        BigDecimal weight2_1 = BigDecimal.valueOf(56.54);
        PersonInfo8Dto personInfo2_1 = PersonInfo8Dto.builder().personName(testName + "2-1").point(point2_1).deleted(0)
                .weight(weight2_1).build();
        personInfo2_1.setCreateUser(6);

        BigDecimal point2_2 = BigDecimal.valueOf(86.83);
        BigDecimal weight2_2 = BigDecimal.valueOf(67.71);
        PersonInfo8Dto personInfo2_2 = PersonInfo8Dto.builder().personName(testName + "2-2").point(point2_2).deleted(0)
                .weight(weight2_2).build();
        personInfo2_2.setCreateUser(7);

        BigDecimal point1_1 = BigDecimal.valueOf(87.82);
        BigDecimal weight1_1 = BigDecimal.valueOf(29.87);
        PersonInfo8Dto personInfo1_1 = PersonInfo8Dto.builder().personName(testName + "1-1").point(point1_1).deleted(0)
                .weight(weight1_1).sharePoll(BigDecimal.valueOf(100L)).build();
        personInfo1_1.setPersonInfoList(Arrays.asList(personInfo2_1, personInfo2_2));
        personInfo1_1.setCreateUser(8);

        mybatisAccessorService.save(personInfo1_1, null, false, null);
        assert((PersonInfo8Dto.class.getName() + "_" + IUpdateCommand.UpdateType.UPDATE.getName()).equals(executeUpdateBean2.getName()));

        mybatisAccessorService.deleteByIds(PersonInfo8Dto.class, Collections.singleton(personInfo1_1.getPersonId()),  null, false, null);
        assert((PersonInfo8Dto.class.getName() + "_" + IUpdateCommand.UpdateType.DELETE.getName()).equals(executeUpdateBean2.getName()));

    }

    @Test
    @Rollback(false)
    @Order(2)
    public void Test2() throws MybatisAccessorException {
        String testName = namePrefix + sdf.format(new Date()) + "_";

        BigDecimal point3_1 = BigDecimal.valueOf(1);
        BigDecimal weight3_1 = BigDecimal.valueOf(2);
        PersonInfo8Dto personInfo3_1 = PersonInfo8Dto.builder().personName(testName + "3-1")
                .point(point3_1).deleted(0)
                .weight(weight3_1).teamCount(12).build();
        personInfo3_1.setCreateUser(6);
        mybatisAccessorService.save(personInfo3_1, null, false, null);
        assert((PersonInfo8Dto.class.getName() + "_" + IUpdateCommand.UpdateType.INSERT.getName()).equals(executeUpdateBean2.getName()));

        mybatisAccessorService.deleteByIds(PersonInfo8Dto.class, Collections.singleton(personInfo3_1.getPersonId()),  null, false, null);
        assert((PersonInfo8Dto.class.getName() + "_" + IUpdateCommand.UpdateType.DELETE.getName()).equals(executeUpdateBean2.getName()));

    }
}
