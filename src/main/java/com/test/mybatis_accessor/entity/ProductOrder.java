package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductOrder extends BaseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer orderId;

    private String orderName;

    private Integer totalCount;

    private BigDecimal totalWeight;

    private BigDecimal amount;

    @TableLogic
    private Integer deleted;

}
