package com.test.other;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@RunWith(SpringRunner.class)
@Slf4j
public class TestOther {

    private final static Logger LOGGER = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    @Test
    public void Test1() throws UnsupportedEncodingException {
        String test = "你好你好1你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好你好";
        System.out.println(getSubString(test, 0, 25));
    }

    @Test
    public void TestStringFormat() {
        String str = "123%s456%s";
        String str1 = String.format(str, "abc", "%s");
        System.out.println(str1);
        System.out.println(String.format(str1, "def"));
    }

    public String getSubString(String str, int start, int len) throws UnsupportedEncodingException {
        if(str == null || str.length() < start) {
            return "";
        }
        char[] chars = str.substring(start).toCharArray();
        String result = "";
        int index = 0;
        for(char c :chars) {
            int nextSize = String.valueOf(c).getBytes("GBK").length;
            if(index + nextSize > len) {
                break;
            }
            result = result + c;
            index += nextSize;
        }
        return result;
    }

    @Test
    public void Test2() {
        String str1 = "1";
        Integer int1 = 1;
        System.out.println(str1.equals(int1));
    }

    @Test
    public void Test3() {
        List<String> st = Arrays.asList("1", "2");
        String[] strings = st.toArray(new String[0]);
        System.out.println(strings[0] + ":" + strings[1]);
    }

    @Test
    public void Test4() throws InterruptedException {
        Lock lock = new ReentrantLock();
        LOGGER.info("main thread id:" + Thread.currentThread().getId());
        Executor lockExecutor = Executors.newSingleThreadExecutor();
        for(int i = 0; i < 2; i ++) {
            int finalI = i;
            CompletableFuture.supplyAsync(() -> {
                    return "test";}, lockExecutor)
                    .thenApplyAsync(x -> {
                        LOGGER.info(finalI + "id:" + Thread.currentThread().getId());
                        lock.lock();
                        try {
                            TimeUnit.SECONDS.sleep(5);
                            LOGGER.info("lock released");
                        } catch(Exception ex) {
                            LOGGER.info("error");
                        } finally {
                            lock.unlock();
                        }
                        return x;
                    }, lockExecutor).thenApply(x -> {
                        LOGGER.info(finalI + "id:" + Thread.currentThread().getId());
                        return x;
                    }).thenAcceptAsync(x -> {
                     LOGGER.info(finalI + "id:" + Thread.currentThread().getId());
                    }).whenComplete((x, e) -> {
                      LOGGER.info(finalI + "id:" + Thread.currentThread().getId());
                    });
        }
        TimeUnit.SECONDS.sleep(10);
    }

}
