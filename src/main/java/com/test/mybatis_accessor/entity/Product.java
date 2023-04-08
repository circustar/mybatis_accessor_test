package com.test.mybatis_accessor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Product  extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer productId;

    private String productName;

    @TableLogic
    private Integer deleted;
}
