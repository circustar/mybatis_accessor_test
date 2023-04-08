package com.test.other;

import com.test.mybatis_accessor.WebTestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//import org.apache.velocity.Template;
//import org.apache.velocity.VelocityContext;
//import org.apache.velocity.app.VelocityEngine;
//import org.apache.velocity.runtime.RuntimeConstants;
//import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class TestVelocity {

    private static final String VM_PATH = "template/velocity/helloworld.vm";
    @Test
    public void Test1() {
//        // 初始化模板引擎
//        VelocityEngine velocityEngine = new VelocityEngine();
//        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
//        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
//        velocityEngine.init();
//
//        // 获取模板文件
//        Template template = velocityEngine.getTemplate(VM_PATH);
//
//        // 设置变量，velocityContext是一个类似map的结构
//        VelocityContext velocityContext = new VelocityContext();
//        velocityContext.put("name", "world");
//        List<String> list = new ArrayList<String>();
//        list.add("jack");
//        list.add("kitty");
//        velocityContext.put("list", list);
//
//        // 输出渲染后的结果
//        StringWriter stringWriter = new StringWriter();
//        template.merge(velocityContext, stringWriter);
//        System.out.println(stringWriter.toString());
    }
}
