package com.test.mybatis_accessor;

import com.circustar.mybatis_accessor.common.MybatisAccessorException;
import com.circustar.mybatis_accessor.support.MybatisAccessorService;
import com.test.mybatis_accessor.dto.ProductOrderDetailDto;
import com.test.mybatis_accessor.dto.ProductOrderDto5;
import com.test.mybatis_accessor.dto.ProductOrderDto5;
import com.test.mybatis_accessor.entity.ProductOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
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
public class Test04_UpdateEventExecutor6 {
    private String strDateFormat = "yyyyMMdd";
    private SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
    private String TEST_NAME = "testCase6_";

    @Autowired
    private MybatisAccessorService mybatisAccessorService;

    private BigDecimal amount = BigDecimal.valueOf(123.45d);
    private BigDecimal weight1 = BigDecimal.valueOf(12);
    private BigDecimal weight2 = BigDecimal.valueOf(23);
    private BigDecimal weight3 = BigDecimal.valueOf(15);
    private BigDecimal weight4 = BigDecimal.valueOf(55);

    @Test
    @Rollback(false)
    public void test1() throws MybatisAccessorException {
        String testName = TEST_NAME + sdf.format(new Date());
        int createUser = 5920;
        ProductOrderDto5 queryDto = new ProductOrderDto5();
        queryDto.setOrderName(testName);
        List<ProductOrderDto5> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        if(dtoList != null && dtoList.size() > 0) {
            // 删除主项以及子项
            mybatisAccessorService.deleteByIds(ProductOrderDto5.class
                    , dtoList.stream().map(x -> x.getOrderId()).collect(Collectors.toSet())
                    , true
                    , null, false, null);

            // 验证主项被删除
            List<ProductOrderDto5> dtoListVerify = mybatisAccessorService.getDtoListByAnnotation(queryDto);
            assert(dtoListVerify == null || dtoListVerify.size() == 0);
        }

        ProductOrderDto5 orderDto = ProductOrderDto5.builder().orderName("a1").amount(amount)
                .orderName(testName).build();
        orderDto.setCreateUser(createUser);
        ProductOrderDetailDto productOrderDetailDto1 = ProductOrderDetailDto.builder().productId(1).productName("n1").weight(weight1).build();
        ProductOrderDetailDto productOrderDetailDto2 = ProductOrderDetailDto.builder().productId(1).productName("n2").weight(weight2).build();
        ProductOrderDetailDto productOrderDetailDto3 = ProductOrderDetailDto.builder().productId(1).productName("n3").weight(weight3).build();
        ProductOrderDetailDto productOrderDetailDto4 = ProductOrderDetailDto.builder().productId(1).productName("n4").weight(weight4).build();
        productOrderDetailDto1.setCreateUser(createUser);
        productOrderDetailDto2.setCreateUser(createUser);
        productOrderDetailDto3.setCreateUser(createUser);
        productOrderDetailDto4.setCreateUser(createUser);

        orderDto.setOrderDetails(Arrays.asList(productOrderDetailDto1, productOrderDetailDto2, productOrderDetailDto3, productOrderDetailDto4));
        ProductOrder productOrder = mybatisAccessorService.save(orderDto, true, null, false, null);

        ProductOrderDto5 po = mybatisAccessorService.getDtoById(ProductOrderDto5.class, productOrder.getOrderId(), true ,null);
        assert(po!= null);
        assert(po.getOrderDetails().size() == 4);
        assert(amount.compareTo(po.getAmount()) == 0);
        assert(po.getOrderDetails().stream().filter(x -> x.getProductName().equals("n1") &&x.getWeight().compareTo(weight1) == 0).count() == 1);
        assert(po.getOrderDetails().stream().filter(x -> x.getProductName().equals("n2") &&x.getWeight().compareTo(weight2) == 0).count() == 1);
        assert(po.getOrderDetails().stream().filter(x -> x.getProductName().equals("n3") &&x.getWeight().compareTo(weight3) == 0).count() == 1);
        assert(po.getOrderDetails().stream().filter(x -> x.getProductName().equals("n4") &&x.getWeight().compareTo(weight4) == 0).count() == 1);
        assert(po.getTotalCount() == orderDto.getOrderDetails().size());
        assert(po.getOrderDetails().stream().map(x -> x.getWeight()).min(BigDecimal::compareTo).get().compareTo(po.getTotalWeight()) == 0);
        assert(po.getOrderDetails().stream().map(x -> x.getAmount()).reduce((x, y) -> x.add(y)).get().compareTo(po.getAmount()) == 0);
        assert(po.getTotalCount() == orderDto.getOrderDetails().size());
    }

    @Test
    @Rollback(false)
    public void test2() throws MybatisAccessorException {
        String testName = TEST_NAME + sdf.format(new Date());
        int createUser = 5921;
        ProductOrderDto5 queryDto = new ProductOrderDto5();
        queryDto.setOrderName(testName);
        List<ProductOrderDto5> dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        dtoList = mybatisAccessorService.getDtoListByAnnotation(queryDto);
        assert(dtoList.size() > 0);

        ProductOrderDto5 orderDto = mybatisAccessorService.getDtoById(ProductOrderDto5.class, dtoList.get(0).getOrderId(), true ,null);;
        assert(orderDto.getOrderDetails().size() == 4);

        BigDecimal amount = BigDecimal.valueOf(234.56);
        orderDto.setAmount(amount);
        BigDecimal newWeight1 = BigDecimal.valueOf(10);

        orderDto.getOrderDetails().stream().filter(x -> x.getProductName().equals("n4")).forEach(x -> x.setDeleted(1));
        orderDto.getOrderDetails().stream().filter(x -> x.getProductName().equals("n1")).forEach(x -> x.setWeight(newWeight1));
        orderDto.setCreateUser(createUser);
        mybatisAccessorService.update(orderDto, true, null, false, null);

        ProductOrderDto5 po = mybatisAccessorService.getDtoById(ProductOrderDto5.class, orderDto.getOrderId(), true ,null);
        assert(po!= null);
        assert(po.getOrderDetails().size() == 3);
        assert(amount.compareTo(po.getAmount()) == 0);
        assert(po.getOrderDetails().stream().filter(x -> x.getProductName().equals("n1") &&x.getWeight().compareTo(newWeight1) == 0).count() == 1);
        assert(po.getOrderDetails().stream().filter(x -> x.getProductName().equals("n2") &&x.getWeight().compareTo(weight2) == 0).count() == 1);
        assert(po.getOrderDetails().stream().filter(x -> x.getProductName().equals("n3") &&x.getWeight().compareTo(weight3) == 0).count() == 1);
        assert(po.getTotalCount() == po.getOrderDetails().size());
        assert(po.getOrderDetails().stream().map(x -> x.getWeight()).min(BigDecimal::compareTo).get().compareTo(po.getTotalWeight()) == 0);
        assert(po.getOrderDetails().stream().map(x -> x.getAmount()).reduce((x, y) -> x.add(y)).get().compareTo(po.getAmount()) == 0);
    }
}
