package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.Product;
import com.test.mybatis_accessor.service.IProductService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = Product.class, service = IProductService.class)
public class ProductDto extends BaseDto {
    private Integer productId;

    private String productName;

    private Integer deleted;
}
