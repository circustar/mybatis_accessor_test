package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.dto.DeleteFlag;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.ProductOrderDetail;
import com.test.mybatis_accessor.service.IProductOrderDetailService;
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
@DtoEntityRelation(entityClass = ProductOrderDetail.class, service = IProductOrderDetailService.class)
public class ProductOrderDetailDto extends BaseDto implements Serializable {
    private Integer orderDetailId;

    private Integer orderId;

    private Integer productId;

    private String productName;

    private BigDecimal weight;

    private BigDecimal amount;

    private Integer deleted;
}
