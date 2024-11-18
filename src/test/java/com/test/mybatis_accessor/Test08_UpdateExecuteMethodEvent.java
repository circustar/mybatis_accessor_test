package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.component.ExecuteUpdateBean;
import com.test.mybatis_accessor.dto.*;
import com.test.mybatis_accessor.entity.ProductOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test08_UpdateExecuteMethodEvent {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String TEST_NAME = "testMethodEvent_";

    @Test
    public void TestA1() throws MybatisAccessorException {
        String testName = TEST_NAME;
        ProductOrderDto queryDto = new ProductOrderDto();
        queryDto.setOrderName(testName);
        List<ProductOrderDto> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(ProductOrderDto.class
                    , dtoList.stream().map(x -> x.getOrderId()).collect(Collectors.toSet())
                    , true
                    , null, false, null);

            // 验证主项被删除
            List<ProductOrderDto> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(queryDto);
            assert(dtoListVerify == null || dtoListVerify.size() == 0);
        }
        Integer orderID = new Random().nextInt(50000) + 100000;
        ProductOrderDetail6Dto orderDetail6Dto = ProductOrderDetail6Dto.builder()
                .orderId(orderID).productName("testing").productId(1).weight(BigDecimal.TEN)
                .amount(BigDecimal.TEN)
                .productOrder6Dto(new ProductOrderDto6())
                .build();
        mybatisAccessorService.save(orderDetail6Dto, true, null, false, null);

        ProductOrderDto productOrderDto =  mybatisAccessorService.getDtoById(ProductOrderDto.class, orderID, true, null);
        assert(productOrderDto != null);
        assert(testName.equals(productOrderDto.getOrderName()));
        assert(productOrderDto.getOrderDetails() != null);
        assert(productOrderDto.getOrderDetails().size() == 1);
    }

}
