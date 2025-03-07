package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.common.DBType;
import com.test.mybatis_accessor.common.DBTypeSupport;
import com.test.mybatis_accessor.dto.PersonInfo10Dto;
import com.test.mybatis_accessor.dto.PersonInfo9Dto;
import com.test.mybatis_accessor.entity.PersonInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
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
public class Test05_UpdatePartAssignEvent {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    @Autowired
    private DataSource dataSource;

    private String namePrefix = "testPartAssign_";

    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    @Test
    public void Test0() throws MybatisAccessorException {
        String testName = namePrefix + sdf.format(new Date()) + "_";

        QueryWrapper qw = new QueryWrapper<>();
        qw.likeRight("person_name",testName);

        List<PersonInfo9Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo9Dto.class, qw);
        if(dtoList.size() == 0) {
            return;
        }
        Set<Serializable> idList = dtoList.stream().map(x -> x.getPersonId()).collect(Collectors.toSet());
        mybatisAccessorService.deleteByIds(PersonInfo9Dto.class, idList, null, false, null);

        QueryWrapper qw2 = new QueryWrapper<>();
        qw2.likeRight("person_name",testName);
        dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo9Dto.class, qw);
        assert(dtoList.isEmpty());
    }

    @Test
    public void Test1() throws MybatisAccessorException {
        try {
        String testName = namePrefix + 1 + sdf.format(new Date()) + "_";
        BigDecimal sharePoll4_1 = BigDecimal.valueOf(72.2d);
        BigDecimal weight4_1 = BigDecimal.valueOf(98.1d);
        BigDecimal point4_1 = BigDecimal.valueOf(100.51d);
        PersonInfo9Dto personInfo4_1 = PersonInfo9Dto.builder().personName(testName + "1-1").sharePoll(sharePoll4_1).deleted(0)
                .weight(weight4_1).point(point4_1).build();
        personInfo4_1.setCreateUser(1);


        BigDecimal weight4_2 = BigDecimal.valueOf(56d);
        PersonInfo9Dto personInfo4_2 = PersonInfo9Dto.builder().personName(testName + "2-1").sharePoll(BigDecimal.ZERO).deleted(0)
                .weight(weight4_2).build();
        personInfo4_2.setCreateUser(2);

        BigDecimal weight3_1 = BigDecimal.valueOf(23.4d);
        PersonInfo9Dto personInfo4_3 = PersonInfo9Dto.builder().personName(testName + "2-2").sharePoll(BigDecimal.ZERO).deleted(0)
                .weight(weight3_1).build();
        personInfo4_3.setCreateUser(3);
        personInfo4_1.setPersonInfoList(Arrays.asList(personInfo4_3, personInfo4_2));

        PersonInfo mainPersonInfo = mybatisAccessorService.save(personInfo4_1, null, false, null);
        assert(mainPersonInfo != null);
        assert(mainPersonInfo.getPersonId() != null);
        assert(mainPersonInfo.getLeaderId() == null);

        QueryWrapper qw = new QueryWrapper<>();
        qw.likeRight("person_name",testName);

        List<PersonInfo9Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo9Dto.class, qw);
        assert(dtoList.size() == 3);

        List<PersonInfo9Dto> Person1_1 = dtoList.stream().filter(x -> (testName + "1-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person1_1.size() == 1);
        List<PersonInfo9Dto> Person2_1 = dtoList.stream().filter(x -> (testName + "2-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person2_1.size() == 1);
        List<PersonInfo9Dto> Person2_2 = dtoList.stream().filter(x -> (testName + "2-2").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person2_2.size() == 1);

        BigDecimal sharePoll = Person2_1.get(0).getSharePoll().add(Person2_2.get(0).getSharePoll());
        assert(Person1_1.get(0).getSharePoll().equals(sharePoll));

        BigDecimal point = Person2_1.get(0).getPoint().add(Person2_2.get(0).getPoint());
        assert(Person1_1.get(0).getPoint().equals(point));

        } catch (Exception ex) {
            if(ex instanceof org.springframework.jdbc.UncategorizedSQLException) {
                if(DBType.MYSQL.equals(DBTypeSupport.getDbType(dataSource))) {
                    return;
                }
            }
            throw ex;
        }
    }

    @Test
    public void Test2() throws MybatisAccessorException {
        try {
            String testName = namePrefix + 2 + sdf.format(new Date()) + "_";
            BigDecimal sharePoll4_1 = BigDecimal.valueOf(72.2d);
            BigDecimal weight4_1 = BigDecimal.valueOf(98.1d);
            BigDecimal point4_1 = BigDecimal.valueOf(100.51d);
            PersonInfo10Dto personInfo4_1 = PersonInfo10Dto.builder().personName(testName + "1-1").sharePoll(sharePoll4_1).deleted(0)
                    .weight(weight4_1).point(point4_1).build();
            personInfo4_1.setCreateUser(1);

            BigDecimal weight4_2 = BigDecimal.valueOf(56d);
            PersonInfo10Dto personInfo4_2 = PersonInfo10Dto.builder().personName(testName + "2-1").sharePoll(BigDecimal.ZERO).deleted(0)
                    .weight(weight4_2).build();
            personInfo4_2.setCreateUser(2);

            BigDecimal weight3_1 = BigDecimal.valueOf(23.4d);
            PersonInfo10Dto personInfo4_3 = PersonInfo10Dto.builder().personName(testName + "2-2").sharePoll(BigDecimal.ZERO).deleted(0)
                    .weight(weight3_1).build();
            personInfo4_3.setCreateUser(3);
            personInfo4_1.setPersonInfoList(Arrays.asList(personInfo4_3, personInfo4_2));

            PersonInfo mainPersonInfo = mybatisAccessorService.save(personInfo4_1, null, false, null);
            assert(mainPersonInfo != null);
            assert(mainPersonInfo.getPersonId() != null);
            assert(mainPersonInfo.getLeaderId() == null);

            QueryWrapper qw = new QueryWrapper<>();
            qw.likeRight("person_name",testName);

            List<PersonInfo10Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo10Dto.class, qw);
            assert(dtoList.size() == 3);

            List<PersonInfo10Dto> Person1_1 = dtoList.stream().filter(x -> (testName + "1-1").equals(x.getPersonName())).collect(Collectors.toList());
            assert(Person1_1.size() == 1);
            List<PersonInfo10Dto> Person2_1 = dtoList.stream().filter(x -> (testName + "2-1").equals(x.getPersonName())).collect(Collectors.toList());
            assert(Person2_1.size() == 1);
            List<PersonInfo10Dto> Person2_2 = dtoList.stream().filter(x -> (testName + "2-2").equals(x.getPersonName())).collect(Collectors.toList());
            assert(Person2_2.size() == 1);

            BigDecimal sharePoll = Person2_1.get(0).getSharePoll().add(Person2_2.get(0).getSharePoll());
            assert(!Person1_1.get(0).getSharePoll().equals(sharePoll));

            BigDecimal point = Person2_1.get(0).getPoint().add(Person2_2.get(0).getPoint());
            assert(!Person1_1.get(0).getPoint().equals(point));

            BigDecimal allWeight = Person2_1.get(0).getWeight().add(Person2_2.get(0).getWeight());
            assert(Person1_1.get(0).getSharePoll().multiply(allWeight).divide(Person1_1.get(0).getWeight(), 2, BigDecimal.ROUND_HALF_UP).equals(sharePoll));
            assert(Person1_1.get(0).getPoint().multiply(allWeight).divide(Person1_1.get(0).getWeight(), 2, BigDecimal.ROUND_HALF_UP).equals(point));

        } catch (Exception ex) {
            if(ex instanceof org.springframework.jdbc.UncategorizedSQLException) {
                if(DBType.MYSQL.equals(DBTypeSupport.getDbType(dataSource))) {
                    return;
                }
            }
            throw ex;
        }
    }

}
