package com.test.mybatis_accessor;

import org.mybatis.spring.annotation.MapperScan;
import com.circustar.mybatis_accessor.annotation.scan.EnableMybatisAccessor;
import com.circustar.mybatis_accessor.annotation.scan.RelationScanPackages;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableTransactionManagement
@SpringBootApplication
@EnableOpenApi
@MapperScan("com.test.mybatis_accessor.mapper")
@EnableMybatisAccessor(
        relationScan = @RelationScanPackages({"com.test.mybatis_accessor.entity", "com.test.mybatis_accessor.dto"})
)
public class WebTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebTestApplication.class, args);
    }
}
