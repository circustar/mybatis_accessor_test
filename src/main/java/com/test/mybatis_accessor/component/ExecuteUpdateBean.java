package com.test.mybatis_accessor.component;

import com.test.mybatis_accessor.dto.ProductOrderDetail3Dto;
import org.springframework.stereotype.Component;

@Component(value = "executeUpdateBean")
public class ExecuteUpdateBean {
    private String productName;

    public void test1(Integer productId, String productName) {
        System.out.println("productId:" + productId + ", productName:" + productName);
        this.productName = productName;
    }
    public void test2(ProductOrderDetail3Dto pd) {
        this.productName = pd.getProductName();
        System.out.println("pId:" + pd.getProductId() + ", pName:" + pd.getProductName());
    }

    public String getProductName() {
        return productName;
    }
}
