package com.test.other;

import com.test.mybatis_accessor.WebTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class TestThymeleaf {
    private final static TemplateEngine engine=new TemplateEngine();

    public static String render(String template, Map<String,Object> params){
        Context context = new Context();
        context.setVariables(params);
        engine.processThrottled("", context);
        return engine.process(template,context);
    }


    @Test
    public void Test1() {
        String template = "<p th:text='${title}'></p>";
        HashMap<String, Object> map = new HashMap<>();
        map.put("title","hello world");
        String render = render(template, map);
        System.out.println("渲染之后的字符串是:"+render);
    }
}
