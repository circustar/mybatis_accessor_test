package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.ClassGroupDto;
import com.test.mybatis_accessor.entity.ClassGroup;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test03_InheritClass {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String name = "testSaveCascade";

    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    private ClassGroupDto classGroupDto;

    @Before
    public void createTestData() throws MybatisAccessorException {
        if(this.classGroupDto != null) {
            return;
        }
        ClassGroupDto query = new ClassGroupDto();
        query.setGroupName(name);

        List<ClassGroup> classGroups = mybatisAccessorService.getEntityListByAnnotation(query);
        if(classGroups != null && classGroups.size() > 0) {
            Set<Serializable> collect = classGroups.stream().map(x -> (Serializable) x.getClassGroupId()).collect(Collectors.toSet());
            mybatisAccessorService.deleteByIds(ClassGroupDto.class, collect, false, null, false, null);
        }
        ClassGroupDto classGroupDto = new ClassGroupDto();
        classGroupDto.setGroupName(name);
        classGroupDto.setCreateUser(1);
        classGroupDto.setUpdateUser(2);
        mybatisAccessorService.save(classGroupDto, false, null, false, null);
        this.classGroupDto = classGroupDto;
    }

    @Test
    public void TestGetEntityByAnnotation() throws MybatisAccessorException {
        ClassGroupDto classGroupDto = new ClassGroupDto();
        classGroupDto.setGroupName(name);
;
        ClassGroup entity = (ClassGroup) mybatisAccessorService.getEntityByAnnotation(classGroupDto);
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getCreateTime() != null);
        assert(entity.getUpdateTime() != null);
        assert(entity.getCreateUser() != null);
        assert(entity.getUpdateUser() != null);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetDtoByAnnotation() throws MybatisAccessorException {
        ClassGroupDto classGroupDto = new ClassGroupDto();
        classGroupDto.setGroupName(name);
        ;
        ClassGroupDto entity = (ClassGroupDto) mybatisAccessorService.getDtoByAnnotation(classGroupDto
                , false, null);
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getCreateTime() != null);
        assert(entity.getUpdateTime() != null);
        assert(entity.getCreateUser() != null);
        assert(entity.getUpdateUser() != null);
        log.info("--- log end ---");
    }

    @Test
    public void TestGetSaveDto() throws MybatisAccessorException {
        String name = this.name + sdf.format(new Date());
        ClassGroupDto classGroupDto = ClassGroupDto.builder().groupName(name).build();
        classGroupDto.setCreateUser(123);
        classGroupDto.setCreateTime(new Date());
        classGroupDto.setUpdateUser(456);
        classGroupDto.setUpdateTime(new Date());

        ClassGroupDto query = ClassGroupDto.builder().groupName(name).build();
        ClassGroupDto entity = mybatisAccessorService.getDtoByAnnotation(query
                , false, null);
        if(entity != null) {
            mybatisAccessorService.deleteByIds(ClassGroupDto.class
                    , Collections.singleton(entity.getClassGroupId()), false, null, false, null);
        }
        mybatisAccessorService.save(classGroupDto, false, null, false, null);
        entity = mybatisAccessorService.getDtoByAnnotation(query
                , false, null);
        log.info("--- log start ---");
        log.info(entity.toString());
        log.info(entity.getCreateTime().toString());
        log.info(entity.getCreateUser().toString());
        log.info(entity.getUpdateTime().toString());
        log.info(entity.getUpdateUser().toString());
        assert(entity != null);
        assert(entity.getCreateTime() != null);
        assert(entity.getUpdateTime() != null);
        assert(entity.getCreateUser() != null);
        assert(entity.getUpdateUser() != null);
        assert(name.equals(entity.getGroupName()));
        log.info("--- log end ---");
    }
}
