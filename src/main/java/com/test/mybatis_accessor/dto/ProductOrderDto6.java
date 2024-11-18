package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.ProductOrder;
import com.test.mybatis_accessor.service.IProductOrderService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = ProductOrder.class, service = IProductOrderService.class)
public class ProductOrderDto6 extends BaseDto implements Serializable {
    private Integer orderId;

    private String orderName;

    private Integer totalCount;

    private BigDecimal totalWeight;

    private BigDecimal amount;

    private Integer deleted;
}
