package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.common.DBType;
import com.test.mybatis_accessor.common.DBTypeSupport;
import com.test.mybatis_accessor.dto.PersonInfo4Dto;
import com.test.mybatis_accessor.entity.PersonInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
public class Test05_UpdateEventExecutorSql2 {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    @Autowired
    private DataSource dataSource;

    private String namePrefix = "testCaseSql2_";

    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);


    @Test
    public void Test0() throws MybatisAccessorException {
        String testName = namePrefix + sdf.format(new Date()) + "_";

        QueryWrapper qw = new QueryWrapper<>();
        qw.likeRight("person_name",testName);
        qw.likeLeft("person_name","1-1");

        List<PersonInfo4Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo4Dto.class, qw);
        if(dtoList.size() == 0) {
            return;
        }
        Set<Serializable> idList = dtoList.stream().map(x -> x.getPersonId()).collect(Collectors.toSet());
        mybatisAccessorService.deleteByIds(PersonInfo4Dto.class, idList, true, null, false, null);

        QueryWrapper qw2 = new QueryWrapper<>();
        qw2.likeRight("person_name",testName);
        dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo4Dto.class, qw);
        assert(dtoList.isEmpty());
    }

    @Test
    @Order(1)
    public void Test1() throws MybatisAccessorException {
        try {
        String testName = namePrefix + sdf.format(new Date()) + "_";
        BigDecimal point4_1 = BigDecimal.valueOf(75.56d);
        BigDecimal weight4_1 = BigDecimal.valueOf(23.12d);
        PersonInfo4Dto personInfo4_1 = PersonInfo4Dto.builder().personName(testName + "4-1").point(point4_1).deleted(0)
                .weight(weight4_1).build();
        personInfo4_1.setCreateUser(1);

        BigDecimal point4_2 = BigDecimal.valueOf(45.15d);
        BigDecimal weight4_2 = BigDecimal.valueOf(33.16d);
        PersonInfo4Dto personInfo4_2 = PersonInfo4Dto.builder().personName(testName + "4-2").point(point4_2).deleted(0)
                .weight(weight4_2).build();
        personInfo4_2.setCreateUser(2);

        BigDecimal point3_1 = BigDecimal.valueOf(35.5d);
        BigDecimal weight3_1 = BigDecimal.valueOf(46.98d);
        PersonInfo4Dto personInfo3_1 = PersonInfo4Dto.builder().personName(testName + "3-1").point(point3_1).deleted(0)
                .weight(weight3_1).build();
        personInfo3_1.setPersonInfoList(Arrays.asList(personInfo4_1, personInfo4_2));
        personInfo3_1.setCreateUser(3);

        BigDecimal point3_2 = BigDecimal.valueOf(18.88d);
        BigDecimal weight3_2 = BigDecimal.valueOf(29.48d);
        PersonInfo4Dto personInfo3_2 = PersonInfo4Dto.builder().personName(testName + "3-2").point(point3_2).deleted(0)
                .weight(weight3_2).build();
        personInfo3_2.setCreateUser(4);

        BigDecimal point3_3 = BigDecimal.valueOf(89.23);
        BigDecimal weight3_3 = BigDecimal.valueOf(66.75);
        PersonInfo4Dto personInfo3_3 = PersonInfo4Dto.builder().personName(testName + "3-3").point(point3_3).deleted(0)
                .weight(weight3_3).build();
        personInfo3_3.setCreateUser(5);

        BigDecimal point3_4 = BigDecimal.valueOf(49.82);
        BigDecimal weight3_4 = BigDecimal.valueOf(28.02);
        PersonInfo4Dto personInfo3_4 = PersonInfo4Dto.builder().personName(testName + "3-4").point(point3_4).deleted(0)
                .weight(weight3_4).build();
        personInfo3_4.setCreateUser(5);

        BigDecimal point2_1 = BigDecimal.valueOf(47.87);
        BigDecimal weight2_1 = BigDecimal.valueOf(56.54);
        PersonInfo4Dto personInfo2_1 = PersonInfo4Dto.builder().personName(testName + "2-1").point(point2_1).deleted(0)
                .weight(weight2_1).build();
        personInfo2_1.setPersonInfoList(Arrays.asList(personInfo3_1, personInfo3_2, personInfo3_3));
        personInfo2_1.setCreateUser(6);

        BigDecimal point2_2 = BigDecimal.valueOf(86.83);
        BigDecimal weight2_2 = BigDecimal.valueOf(67.71);
        PersonInfo4Dto personInfo2_2 = PersonInfo4Dto.builder().personName(testName + "2-2").point(point2_2).deleted(0)
                .weight(weight2_2).build();
        personInfo2_2.setPersonInfoList(Arrays.asList(personInfo3_4));
        personInfo2_2.setCreateUser(7);

        BigDecimal point1_1 = BigDecimal.valueOf(87.82);
        BigDecimal weight1_1 = BigDecimal.valueOf(29.87);
        PersonInfo4Dto personInfo1_1 = PersonInfo4Dto.builder().personName(testName + "1-1").point(point1_1).deleted(0)
                .weight(weight1_1).sharePoll(BigDecimal.valueOf(100L)).build();
        personInfo1_1.setPersonInfoList(Arrays.asList(personInfo2_1, personInfo2_2));
        personInfo1_1.setCreateUser(8);

        PersonInfo mainPersonInfo = mybatisAccessorService.save(personInfo1_1, true, null, false, null);
        assert(mainPersonInfo != null);
        assert(mainPersonInfo.getPersonId() != null);
        assert(mainPersonInfo.getLeaderId() == null);

        QueryWrapper qw = new QueryWrapper<>();
        qw.likeRight("person_name",testName);

        BigDecimal maxPoint = BigDecimal.valueOf(0.001f);

        List<PersonInfo4Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo4Dto.class, qw);
        assert(dtoList.size() == 9);

        List<PersonInfo4Dto> Person1_1 = dtoList.stream().filter(x -> (testName + "1-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person1_1.size() == 1);
        List<PersonInfo4Dto> Person2_1 = dtoList.stream().filter(x -> (testName + "2-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person2_1.size() == 1);
        List<PersonInfo4Dto> Person2_2 = dtoList.stream().filter(x -> (testName + "2-2").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person2_2.size() == 1);
        BigDecimal totalPoint = Person2_1.get(0).getPoint().add(Person2_2.get(0).getPoint());
        BigDecimal sharePoll = Person2_1.get(0).getSharePoll().add(Person2_2.get(0).getSharePoll());
        int count = 2;
        assert(Person1_1.get(0).getTeamTotalPoint().equals(totalPoint));
        assert(Person1_1.get(0).getTeamAveragePoint().subtract(totalPoint.divide(BigDecimal.valueOf(count))).compareTo(maxPoint) < 0);
        assert(Person1_1.get(0).getSharePoll().equals(sharePoll));
        assert(Person1_1.get(0).getTeamCount() == count);

        List<PersonInfo4Dto> Person3_1 = dtoList.stream().filter(x -> (testName + "3-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person3_1.size() == 1);
        List<PersonInfo4Dto> Person3_2 = dtoList.stream().filter(x -> (testName + "3-2").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person3_2.size() == 1);
        List<PersonInfo4Dto> Person3_3 = dtoList.stream().filter(x -> (testName + "3-3").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person3_3.size() == 1);
        List<PersonInfo4Dto> Person3_4 = dtoList.stream().filter(x -> (testName + "3-4").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person3_4.size() == 1);

        totalPoint = Person3_1.get(0).getPoint().add(Person3_2.get(0).getPoint()).add(Person3_3.get(0).getPoint());
        sharePoll = Person3_1.get(0).getSharePoll().add(Person3_2.get(0).getSharePoll()).add(Person3_3.get(0).getSharePoll());
        count = 3;
        assert(Person2_1.get(0).getTeamTotalPoint().equals(totalPoint));
        assert(Person2_1.get(0).getTeamAveragePoint().subtract(totalPoint.divide(BigDecimal.valueOf(count))).compareTo(maxPoint) < 0);
        assert(Person2_1.get(0).getSharePoll().equals(sharePoll));
        assert(Person2_1.get(0).getTeamCount() == count);

        totalPoint = Person3_4.get(0).getPoint();
        sharePoll = Person3_4.get(0).getSharePoll();
        count = 1;
        assert(Person2_2.get(0).getTeamTotalPoint().equals(totalPoint));
        assert(Person2_2.get(0).getTeamAveragePoint().subtract(totalPoint.divide(BigDecimal.valueOf(count))).compareTo(maxPoint) < 0);
        assert(Person2_2.get(0).getSharePoll().equals(sharePoll));
        assert(Person2_2.get(0).getTeamCount() == count);

        List<PersonInfo4Dto> Person4_1 = dtoList.stream().filter(x -> (testName + "4-1").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person4_1.size() == 1);
        List<PersonInfo4Dto> Person4_2 = dtoList.stream().filter(x -> (testName + "4-2").equals(x.getPersonName())).collect(Collectors.toList());
        assert(Person4_2.size() == 1);
        totalPoint = Person4_1.get(0).getPoint().add(Person4_2.get(0).getPoint());
        sharePoll = Person4_1.get(0).getSharePoll().add(Person4_2.get(0).getSharePoll());
        count = 2;
        assert(Person3_1.get(0).getTeamTotalPoint().equals(totalPoint));
        assert(Person3_1.get(0).getTeamAveragePoint().subtract(totalPoint.divide(BigDecimal.valueOf(count))).compareTo(maxPoint) < 0);
        assert(Person3_1.get(0).getSharePoll().equals(sharePoll));
        assert(Person3_1.get(0).getTeamCount() == count);

        assert(Person4_1.get(0).getLeaderId().equals(Person3_1.get(0).getPersonId()));
        assert(Person4_2.get(0).getLeaderId().equals(Person3_1.get(0).getPersonId()));

        assert(Person3_1.get(0).getLeaderId().equals(Person2_1.get(0).getPersonId()));
        assert(Person3_2.get(0).getLeaderId().equals(Person2_1.get(0).getPersonId()));
        assert(Person3_3.get(0).getLeaderId().equals(Person2_1.get(0).getPersonId()));
        assert(Person3_4.get(0).getLeaderId().equals(Person2_2.get(0).getPersonId()));

        assert(Person2_1.get(0).getLeaderId().equals(Person1_1.get(0).getPersonId()));
        assert(Person2_2.get(0).getLeaderId().equals(Person1_1.get(0).getPersonId()));

        PersonInfo4Dto dto1_1 = mybatisAccessorService.getDtoById(PersonInfo4Dto.class, Person1_1.get(0).getPersonId(), true, null);
        assert(dto1_1.getPersonInfoList().size() == 2);
        assert(dto1_1.getPersonInfoList().stream().filter(x -> x.getPersonId().equals(Person2_1.get(0).getPersonId())
                || x.getPersonId().equals(Person2_2.get(0).getPersonId())).count() == 2);
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
    @Order(2)
    public void Test2() throws MybatisAccessorException {
        try {
            String testName = namePrefix + sdf.format(new Date()) + "_";

            QueryWrapper qw = new QueryWrapper<>();
            qw.eq("person_name", testName + "1-1");

            List<PersonInfo4Dto> dtoList = mybatisAccessorService.getDtoListByQueryWrapper(PersonInfo4Dto.class, qw);
            PersonInfo4Dto dto1_1 = mybatisAccessorService.getDtoById(PersonInfo4Dto.class, dtoList.get(0).getPersonId(), true, null);

            dto1_1.getPersonInfoList().stream().filter(x -> x.getPersonName().equals(testName + "2-1")).forEach(x -> x.setDeleted(1));
            PersonInfo4Dto dto2_2 = dto1_1.getPersonInfoList().stream().filter(x -> x.getPersonName().equals(testName + "2-2")).findAny().get();

            BigDecimal point3_5 = BigDecimal.valueOf(12.34);
            BigDecimal weight3_5 = BigDecimal.valueOf(90.41);
            PersonInfo4Dto personInfo3_5 = PersonInfo4Dto.builder().personName(testName + "3-5").point(point3_5).deleted(0)
                    .weight(weight3_5).build();
            personInfo3_5.setCreateUser(6);
            dto2_2.setPersonInfoList(Arrays.asList(personInfo3_5));

            BigDecimal point2_3 = BigDecimal.valueOf(25.1);
            BigDecimal weight2_3 = BigDecimal.valueOf(81.29);
            PersonInfo4Dto personInfo2_3 = PersonInfo4Dto.builder().personName(testName + "2-3").point(point2_3).deleted(0)
                    .weight(weight2_3).build();
            personInfo2_3.setCreateUser(6);

            dto1_1.getPersonInfoList().add(personInfo2_3);

            PersonInfo mainPersonInfo = mybatisAccessorService.saveOrUpdate(dto1_1, true, null, false, null);
            assert (mainPersonInfo != null);

            dto1_1 = mybatisAccessorService.getDtoById(PersonInfo4Dto.class, dto1_1.getPersonId(), true, null);
            BigDecimal maxDiffPoint = BigDecimal.valueOf(0.001f);

            BigDecimal totalPoint = dto1_1.getPersonInfoList().stream().map(x -> x.getPoint()).reduce((x, y) -> x.add(y)).get();
            BigDecimal totalSharePoll = dto1_1.getPersonInfoList().stream().map(x -> x.getSharePoll()).reduce((x, y) -> x.add(y)).get();
            int count = dto1_1.getPersonInfoList().size();
            assert (dto1_1.getTeamCount().equals(count));
            assert (dto1_1.getTeamTotalPoint().equals(totalPoint));
            assert (dto1_1.getSharePoll().equals(totalSharePoll));
            assert (dto1_1.getTeamAveragePoint().subtract(totalPoint.divide(BigDecimal.valueOf(count))).compareTo(maxDiffPoint) < 0);

            dto2_2 = mybatisAccessorService.getDtoById(PersonInfo4Dto.class, dto2_2.getPersonId(), true, null);
            totalPoint = dto2_2.getPersonInfoList().stream().map(x -> x.getPoint()).reduce((x, y) -> x.add(y)).get();
            totalSharePoll = dto2_2.getPersonInfoList().stream().map(x -> x.getSharePoll()).reduce((x, y) -> x.add(y)).get();
            count = dto2_2.getPersonInfoList().size();
            assert (dto2_2.getTeamCount().equals(count));
            assert (dto2_2.getTeamTotalPoint().equals(totalPoint));
            assert (dto2_2.getSharePoll().equals(totalSharePoll));
            assert (dto2_2.getTeamAveragePoint().subtract(totalPoint.divide(BigDecimal.valueOf(count))).compareTo(maxDiffPoint) < 0);
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
