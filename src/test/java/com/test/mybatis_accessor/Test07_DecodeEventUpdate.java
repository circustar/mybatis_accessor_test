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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebTestApplication.class})
@Slf4j
public class Test07_DecodeEventUpdate {
    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private String namePrefix = "testDecodeEventUpdate_";

    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

    @Autowired
    private ExecuteUpdateBean executeUpdateBean;

    @Test
    public void TestA1() throws MybatisAccessorException {
        String testName = namePrefix + "A1";
        int createUser = 12345;
        ProductOrderDto3 queryDto = new ProductOrderDto3();
        queryDto.setOrderName(testName);
        List<ProductOrderDto3> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(ProductOrderDto3.class
                    , dtoList.stream().map(x -> x.getOrderId()).collect(Collectors.toSet())
                    , true
                    , null, false, null);

            // 验证主项被删除
            List<ProductOrderDto3> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(queryDto);
            assert(dtoListVerify == null || dtoListVerify.size() == 0);
        }

        ProductOrderDto3 orderDto = ProductOrderDto3.builder().orderName("b1").amount(BigDecimal.ZERO)
                .orderName(testName).build();
        orderDto.setCreateUser(createUser);
        ProductOrderDetail3Dto productOrderDetailDto1 = ProductOrderDetail3Dto.builder().productId(1).productName("n1").weight(BigDecimal.ZERO).build();
        ProductOrderDetail3Dto productOrderDetailDto2 = ProductOrderDetail3Dto.builder().productId(2).productName("n2").weight(BigDecimal.ZERO).build();
        ProductOrderDetail3Dto productOrderDetailDto3 = ProductOrderDetail3Dto.builder().productId(2).productName("n3").weight(BigDecimal.ZERO).build();
        ProductOrderDetail3Dto productOrderDetailDto4 = ProductOrderDetail3Dto.builder().productId(3).productName("n4").weight(BigDecimal.ZERO).build();
        productOrderDetailDto1.setCreateUser(createUser);
        productOrderDetailDto2.setCreateUser(createUser);
        productOrderDetailDto3.setCreateUser(createUser);
        productOrderDetailDto4.setCreateUser(createUser);

        orderDto.setOrderDetails(Arrays.asList(productOrderDetailDto1, productOrderDetailDto2, productOrderDetailDto3, productOrderDetailDto4));
        ProductOrder productOrder = mybatisAccessorService.save(orderDto, true, null, false, null);

        ProductOrderDto po = mybatisAccessorService.getDtoById(ProductOrderDto.class, productOrder.getOrderId(), true ,null);
        assert(po!= null);
        assert(po.getOrderDetails().size() == 4);

        ProductDto productDto1 = mybatisAccessorService.getDtoById(ProductDto.class, 1, false, null);
        ProductDto productDto2 = mybatisAccessorService.getDtoById(ProductDto.class, 2, false, null);
        ProductDto productDto3 = mybatisAccessorService.getDtoById(ProductDto.class, 3, false, null);
        assert(productDto3.getProductName().equals(executeUpdateBean.getProductName()));

        ProductOrderDetail3Dto pd1 = mybatisAccessorService.getDtoById(ProductOrderDetail3Dto.class, productOrderDetailDto1.getOrderDetailId(), false, null);
        ProductOrderDetail3Dto pd2 = mybatisAccessorService.getDtoById(ProductOrderDetail3Dto.class, productOrderDetailDto2.getOrderDetailId(), false, null);
        ProductOrderDetail3Dto pd3 = mybatisAccessorService.getDtoById(ProductOrderDetail3Dto.class, productOrderDetailDto3.getOrderDetailId(), false, null);
        ProductOrderDetail3Dto pd4 = mybatisAccessorService.getDtoById(ProductOrderDetail3Dto.class, productOrderDetailDto4.getOrderDetailId(), false, null);

        assert(pd1.getProductName().equals(productDto1.getProductName()));
        assert(pd2.getProductName().equals(productDto2.getProductName()));
        assert(pd3.getProductName().equals(productDto2.getProductName()));
        assert(pd4.getProductName().equals(productDto3.getProductName()));
    }

    @Test
    public void TestA2() throws MybatisAccessorException {
        String testName = namePrefix + "A2";
        int createUser = 12345;
        ProductOrderDto4 queryDto = new ProductOrderDto4();
        queryDto.setOrderName(testName);
        List<ProductOrderDto4> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(ProductOrderDto4.class
                    , dtoList.stream().map(x -> x.getOrderId()).collect(Collectors.toSet())
                    , true
                    , null, false, null);

            // 验证主项被删除
            List<ProductOrderDto4> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(queryDto);
            assert(dtoListVerify == null || dtoListVerify.size() == 0);
        }

        ProductOrderDto4 orderDto = ProductOrderDto4.builder().orderName("b1").amount(BigDecimal.ZERO)
                .orderName(testName).build();
        orderDto.setCreateUser(createUser);
        ProductOrderDetail4Dto productOrderDetailDto1 = ProductOrderDetail4Dto.builder().productId(1).productName("n1").weight(BigDecimal.ZERO).build();
        ProductOrderDetail4Dto productOrderDetailDto2 = ProductOrderDetail4Dto.builder().productId(2).productName("n2").weight(BigDecimal.ZERO).build();
        ProductOrderDetail4Dto productOrderDetailDto3 = ProductOrderDetail4Dto.builder().productId(2).productName("n3").weight(BigDecimal.ZERO).build();
        ProductOrderDetail4Dto productOrderDetailDto4 = ProductOrderDetail4Dto.builder().productId(3).productName("n4").weight(BigDecimal.ZERO).build();
        productOrderDetailDto1.setCreateUser(createUser);
        productOrderDetailDto2.setCreateUser(createUser);
        productOrderDetailDto3.setCreateUser(createUser);
        productOrderDetailDto4.setCreateUser(createUser);

        orderDto.setOrderDetails(Arrays.asList(productOrderDetailDto1, productOrderDetailDto2, productOrderDetailDto3, productOrderDetailDto4));
        ProductOrder productOrder = mybatisAccessorService.save(orderDto, true, null, false, null);

        ProductOrderDto po = mybatisAccessorService.getDtoById(ProductOrderDto.class, productOrder.getOrderId(), true ,null);
        assert(po!= null);
        assert(po.getOrderDetails().size() == 4);

        ProductDto productDto1 = mybatisAccessorService.getDtoById(ProductDto.class, 1, false, null);
        ProductDto productDto2 = mybatisAccessorService.getDtoById(ProductDto.class, 2, false, null);
        ProductDto productDto3 = mybatisAccessorService.getDtoById(ProductDto.class, 3, false, null);
        assert(productDto3.getProductName().equals(executeUpdateBean.getProductName()));

        ProductOrderDetail4Dto pd1 = mybatisAccessorService.getDtoById(ProductOrderDetail4Dto.class, productOrderDetailDto1.getOrderDetailId(), false, null);
        ProductOrderDetail4Dto pd2 = mybatisAccessorService.getDtoById(ProductOrderDetail4Dto.class, productOrderDetailDto2.getOrderDetailId(), false, null);
        ProductOrderDetail4Dto pd3 = mybatisAccessorService.getDtoById(ProductOrderDetail4Dto.class, productOrderDetailDto3.getOrderDetailId(), false, null);
        ProductOrderDetail4Dto pd4 = mybatisAccessorService.getDtoById(ProductOrderDetail4Dto.class, productOrderDetailDto4.getOrderDetailId(), false, null);

        assert(pd1.getProductName().equals(productDto1.getProductName()));
        assert(pd2.getProductName().equals(productDto2.getProductName()));
        assert(pd3.getProductName().equals(productDto2.getProductName()));
        assert(pd4.getProductName().equals(productDto3.getProductName()));
    }
}
