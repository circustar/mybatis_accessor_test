package com.test.mybatis_accessor.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ProjectConfig {
    @Bean
    public MetaObjectHandler getMetaObjectHandler() {
        return new MetaObjectHandler(){
            @Override
            public void insertFill(MetaObject metaObject) {
                Date dt = new Date();
                this.strictInsertFill(metaObject, "updateTime", Date.class, dt);
                this.strictInsertFill(metaObject, "createTime", Date.class, dt);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
            }
        };
    }
    @Bean
    public MybatisPlusInterceptor getMybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //分页配置
        interceptor.addInnerInterceptor((new PaginationInnerInterceptor()));

        return interceptor;
    }

}
