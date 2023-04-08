package com.test.mybatis_accessor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.RoleDto;
import com.test.mybatis_accessor.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test02_GetSelector {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    @Test
    public void TestGetDtoById() throws MybatisAccessorException {

        log.info("--- log start ---");
        RoleDto entity = (RoleDto) mybatisAccessorService.getDtoById(RoleDto.class, 1, false, Arrays.asList("privileges"));
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getPrivileges().size() > 0);

        entity = (RoleDto) mybatisAccessorService.getDtoById(RoleDto.class, 1, false, Arrays.asList("rolePrivileges"));
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getRolePrivileges().size() > 0);

        entity = (RoleDto) mybatisAccessorService.getDtoById(RoleDto.class, 1, false, Arrays.asList("privileges", "rolePrivileges"));
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getPrivileges().size() > 0);
        assert(entity.getRolePrivileges().size() > 0);
        log.info("--- log end ---");

        UserDto userDto = mybatisAccessorService.getDtoById(UserDto.class, 1, false, Arrays.asList("roles","privileges") );
        log.info(userDto.toString());
        assert(userDto != null);
        assert(userDto.getPrivileges().size() > 0);
        assert(userDto.getRoles().size() > 0);
        log.info("--- log end ---");
    }


    @Test
    public void TestGetDtoByQueryWrapper() throws MybatisAccessorException {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.likeLeft("role_name", "1");
        RoleDto entity = (RoleDto) mybatisAccessorService.getDtoByQueryWrapper(new RoleDto(), queryWrapper
                , false, Arrays.asList("privileges"));
        log.info("--- log start ---");
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getPrivileges().size() > 0);

        entity = (RoleDto) mybatisAccessorService.getDtoByQueryWrapper(new RoleDto(), queryWrapper, false
                , Arrays.asList("rolePrivileges"));
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getRolePrivileges().size() > 0);

        entity = (RoleDto) mybatisAccessorService.getDtoByQueryWrapper(new RoleDto(), queryWrapper, false
                , Arrays.asList("privileges","rolePrivileges"));
        log.info(entity.toString());
        assert(entity != null);
        assert(entity.getPrivileges().size() > 0);
        assert(entity.getRolePrivileges().size() > 0);

        log.info("--- log end ---");
    }
}
