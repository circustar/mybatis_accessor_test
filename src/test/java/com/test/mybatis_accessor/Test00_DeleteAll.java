package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test00_DeleteAll {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    List<String> sqlExpressions = new ArrayList<>();

    private void initSql() {
        sqlExpressions.add("truncate table CLASS_GROUP;");
        sqlExpressions.add("truncate table COURSE;");
        sqlExpressions.add("truncate table PERSON_INFO;");
        sqlExpressions.add("truncate table PRODUCT_ORDER;");
        sqlExpressions.add("truncate table PRODUCT_ORDER_DETAIL;");
        sqlExpressions.add("truncate table STUDENT;");
        sqlExpressions.add("truncate table STUDENT_COURSE;");
        sqlExpressions.add("truncate table STUDENT_GROUP;");
        sqlExpressions.add("truncate table SCORE;");
    }

    @Test
    public void deleteAll() throws SQLException {
        initSql();
        try(SqlSession sqlSession = sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
            Statement statement = connection.createStatement()) {
            for(String sql : sqlExpressions) {
                statement.execute(sql);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
