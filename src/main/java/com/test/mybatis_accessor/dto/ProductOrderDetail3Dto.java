package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.DecodeEvent;
import com.circustar.mybatis_accessor.annotation.event.UpdateEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.listener.event.update.UpdateExecuteBeanMethodEvent;
import com.circustar.mybatis_accessor.provider.command.IUpdateCommand;
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
@DecodeEvent(onExpression = "", targetProperties = "productName", matchProperties = "productId"
        , sourceDtoClass = ProductDto.class)
@UpdateEvent(onExpression = "", updateEventClass = UpdateExecuteBeanMethodEvent.class, updateParams = {"executeUpdateBean", "test2"}, updateType = {IUpdateCommand.UpdateType.INSERT, IUpdateCommand.UpdateType.UPDATE})
public class ProductOrderDetail3Dto extends BaseDto implements Serializable {
    private Integer orderDetailId;

    private Integer orderId;

    private Integer productId;

    private String productName;

    private BigDecimal weight;

    private BigDecimal amount;

    private Integer deleted;
}
