package com.test.other;

import com.circustar.common_utils.parser.SPELParser;
import com.test.mybatis_accessor.WebTestApplication;
import com.test.mybatis_accessor.dto.ProductOrderDetailDto;
import com.test.mybatis_accessor.dto.ProductOrderDto;
import com.test.mybatis_accessor.dto.ScoreDto;
import com.test.mybatis_accessor.dto.StudentDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@RunWith(SpringRunner.class)
@Slf4j
public class TestSPEL {

    @Value("name is ${spring.datasource.type}")
    private String test123;


    @Test
    public void Test1()  {
        ExpressionParser parser =new SpelExpressionParser();
        StudentDto studentDto = StudentDto.builder().grade(1).name("xiaoming").studentId(2).build();
        StandardEvaluationContext context =new StandardEvaluationContext(studentDto);
        String value =parser.parseExpression("'name is ${name}'").getValue(context, String.class);
//        System.out.println(parser.parseExpression("T(System).getProperty('user.dir')").getValue());

        System.out.println(value);
    }

    @Test
    public void Test2() {
        ExpressionParser parser = new SpelExpressionParser();
        ParserContext parserContext = new TemplateParserContext();
        String template = "name: #{name}, score: #{scoreList[0].score}";
        Expression expression = parser.parseExpression(template, parserContext);

        StudentDto studentDto = StudentDto.builder().grade(1).name("xiaoming").studentId(2).build();
        ScoreDto scoreDto = ScoreDto.builder().score(BigDecimal.TEN).build();
        studentDto.setScoreList(Collections.singletonList(scoreDto));
        StandardEvaluationContext context =new StandardEvaluationContext(studentDto);
        System.out.println(expression.getValue(context));
    }

    @Test
    public void Test3()  {
        ExpressionParser parser =new SpelExpressionParser();
        Boolean b = SPELParser.calcExpression("1 > 2", Boolean.class);
//        System.out.println(parser.parseExpression("T(System).getProperty('user.dir')").getValue());

        System.out.println(b);
    }

    @Test
    public void Test4()  {
        String testName = "ts";
        Integer createUser = 1;

        ProductOrderDto orderdto = ProductOrderDto.builder().amount(BigDecimal.valueOf(123.45d))
                .orderName(testName).build();
        orderdto.setCreateUser(createUser);
        ProductOrderDetailDto productOrderDetailDto1 = ProductOrderDetailDto.builder().productId(1).productName("n1").weight(BigDecimal.valueOf(12)).build();
        ProductOrderDetailDto productOrderDetailDto2 = ProductOrderDetailDto.builder().productId(1).productName("n2").weight(BigDecimal.valueOf(23)).build();
        ProductOrderDetailDto productOrderDetailDto3 = ProductOrderDetailDto.builder().productId(1).productName("n3").weight(BigDecimal.valueOf(15)).build();
        ProductOrderDetailDto productOrderDetailDto4 = ProductOrderDetailDto.builder().productId(1).productName("n4").weight(BigDecimal.valueOf(55)).build();
        productOrderDetailDto1.setCreateUser(createUser);
        productOrderDetailDto2.setCreateUser(createUser);
        productOrderDetailDto3.setCreateUser(createUser);
        productOrderDetailDto4.setCreateUser(createUser);
        orderdto.setOrderDetails(Arrays.asList(productOrderDetailDto1, productOrderDetailDto2, productOrderDetailDto3, productOrderDetailDto4));

        System.out.println(SPELParser.parseStringExpression(orderdto, "#{orderDetails?.size() > 0}"));
    }
}
