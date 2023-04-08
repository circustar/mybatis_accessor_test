package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
public class ProductOrderDetail extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer orderDetailId;

    private Integer orderId;

    private Integer productId;

    private String productName;

    private BigDecimal weight;

    private BigDecimal amount;

    @TableLogic
    private Integer deleted;
}
