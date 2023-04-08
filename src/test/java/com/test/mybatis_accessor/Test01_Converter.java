package com.test.mybatis_accessor;

import com.circustar.common_utils.reflection.FieldUtils;
import com.circustar.mybatis_accessor.converter.DefaultConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RunWith(SpringRunner.class)
@Slf4j
public class Test01_Converter {
    public class TestSource {
        int param0;
        String param1;
        Integer param2;
        Test01_Converter param3;

        public int getParam0() {
            return param0;
        }

        public void setParam0(int param0) {
            this.param0 = param0;
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public Integer getParam2() {
            return param2;
        }

        public void setParam2(Integer param2) {
            this.param2 = param2;
        }

        public Test01_Converter getParam3() {
            return param3;
        }

        public void setParam3(Test01_Converter param3) {
            this.param3 = param3;
        }
    }
    public class TestClass0 {
        int param0;
        String param1;
        Integer param2;
        Test01_Converter param3;

        public int getParam0() {
            return param0;
        }

        public void setParam0(int param0) {
            this.param0 = param0;
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public Integer getParam2() {
            return param2;
        }

        public void setParam2(Integer param2) {
            this.param2 = param2;
        }

        public Test01_Converter getParam3() {
            return param3;
        }

        public void setParam3(Test01_Converter param3) {
            this.param3 = param3;
        }
    }
    public class TestClass1 {
        String param1;
        Integer param2;
        Test01_Converter param3;
        public TestClass1(String param1, Integer param2, Test01_Converter param3) {
            this.param1 = param1;
            this.param2 = param2;
            this.param3 = param3;
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public Integer getParam2() {
            return param2;
        }

        public void setParam2(Integer param2) {
            this.param2 = param2;
        }

        public Test01_Converter getParam3() {
            return param3;
        }

        public void setParam3(Test01_Converter param3) {
            this.param3 = param3;
        }
    }
    public class TestClass2 {
        int param0;
        String param1;
        Integer param2;

        public TestClass2(int param0, String param1, Integer param2) {
            this.param1 = param1;
            this.param2 = param2;
            this.param0 = param0;
        }

        public int getParam0() {
            return param0;
        }

        public void setParam0(int param0) {
            this.param0 = param0;
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public Integer getParam2() {
            return param2;
        }

        public void setParam2(Integer param2) {
            this.param2 = param2;
        }
    }
    private DefaultConverter defaultConverter = new DefaultConverter<>();

    private TestSource testSource = null;

    @Before
    public void initTestSource() {
        if(testSource == null) {
            testSource = new TestSource();
            testSource.setParam0(1021);
            testSource.setParam1("testing");
            testSource.setParam2(2021);
            testSource.setParam3(this);
        }
    }

    @Test
    public void Test0() {
        TestClass0 target = (TestClass0)defaultConverter.convert(TestClass0.class, this.testSource);
        assert(testSource.getParam0() == target.getParam0());
        assert(testSource.getParam1().equals(target.getParam1()));
        assert(testSource.getParam2().equals(target.getParam2()));
        assert(testSource.getParam3().equals(target.getParam3()));
    }

    @Test
    public void Test1() {
        TestClass1 target = (TestClass1)defaultConverter.convert(TestClass1.class, this.testSource);
        assert(testSource.getParam1().equals(target.getParam1()));
        assert(testSource.getParam2().equals(target.getParam2()));
        assert(testSource.getParam3().equals(target.getParam3()));
    }

    @Test
    public void Test2() {
        TestClass2 target = (TestClass2)defaultConverter.convert(TestClass2.class, this.testSource);
        assert(testSource.getParam0() == target.getParam0());
        assert(testSource.getParam1().equals(target.getParam1()));
        assert(testSource.getParam2().equals(target.getParam2()));
    }

    @Test
    public void Test3() {
        Object o = (int)3;
        assert(Integer.class.isAssignableFrom(o.getClass()));
        assert(!int.class.equals(Integer.class));

        double d1 = 1d;
        Double d2 = (Double) d1;
        assert(d2.equals(d1));

    }

    @Test
    public void Test4() {
        List<Comparable> var0 = new ArrayList<>();
        var0.add(null);
        var0.add(1);

        Comparable var1 = (Comparable) var0.stream()
                .filter(x -> x!= null)
                .min(Comparator.comparing(x -> (Comparable) x)).orElse(null);

        assert(var1 != null);
    }
}
