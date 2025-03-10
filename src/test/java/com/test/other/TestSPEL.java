package com.test.other;

import com.circustar.common_utils.collection.StringUtils;
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
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.*;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.ReflectiveMethodExecutor;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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


    @Test
    public void Test5() throws NoSuchMethodException {
        ExpressionParser parser =new SpelExpressionParser();
        ProductOrderDto orderdto = ProductOrderDto.builder().amount(BigDecimal.valueOf(123.45d))
                .orderName("test").build();
        StandardEvaluationContext context = new StandardEvaluationContext(orderdto);
        Method method = com.circustar.common_utils.collection.StringUtils.class.getDeclaredMethod("c2l", new Class[]{String.class});

        context.addMethodResolver(new MethodResolver() {
            @Override
            public MethodExecutor resolve(EvaluationContext evaluationContext, Object o, String s, List<TypeDescriptor> list) throws AccessException {
                if("c2l".equals(s)) {
                    return new ReflectiveMethodExecutor(method);
                }
                return null;
            }
        });
//        Method method = com.circustar.common_utils.collection.StringUtils.class.getDeclaredMethod("camelToUnderLine", new Class[]{String.class});
        //context.registerFunction("camelToUnderLine", method);
//        context.setVariable("camelToUnderLine", method);
//        context.registerFunction("camelToUnderLine", method);
//        final Expression o = parser.parseExpression("name is #{T(com.circustar.common_utils.collection.StringUtils).camelToUnderLine('hello.testIgnored')}", new TemplateParserContext());
        final Expression o = parser.parseExpression("name is #{c2l('testPrefix.testSuffix')}", new TemplateParserContext());

        System.out.println(o.getValue(context).toString());
        System.out.println(o);
    }

    @Test
    public void Test6() throws NoSuchMethodException {
        ProductOrderDto orderdto = ProductOrderDto.builder().amount(BigDecimal.valueOf(123.45d))
                .orderName("order_name:aaa").build();
        System.out.println(SPELParser.parseStringExpression(orderdto, "name is #{orderName}1: #{c2l('testPrefix1.testSuffixMethod1Test')}"));
        System.out.println(SPELParser.parseStringExpression(orderdto, "name is #{orderName}2: #{c2l('testSuffixMethod2Test')}"));
        System.out.println(SPELParser.parseStringExpression(orderdto, "name is #{orderName}3: #{c2l('testPrefix1.testSuffixMethod1Test.test3Adc')}"));
    }

    @Test
    public void Test7() throws NoSuchMethodException {
        ExpressionParser parser =new SpelExpressionParser();
        ProductOrderDto orderdto = ProductOrderDto.builder().amount(BigDecimal.valueOf(123.45d))
                .orderName("test").build();
        StandardEvaluationContext context = new StandardEvaluationContext(orderdto);

    }
}
