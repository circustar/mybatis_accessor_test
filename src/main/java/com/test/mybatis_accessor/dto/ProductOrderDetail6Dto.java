package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.listener.ExecuteTiming;
import com.circustar.mybatis_accessor.annotation.event.UpdateEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.listener.event.update.UpdateExecuteMethodEvent;
import com.circustar.mybatis_accessor.provider.command.IUpdateCommand;
import com.test.mybatis_accessor.entity.ProductOrderDetail;
import com.test.mybatis_accessor.service.IProductOrderDetailService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldNameConstants
@DtoEntityRelation(entityClass = ProductOrderDetail.class, service = IProductOrderDetailService.class)
@UpdateEvent(onExpression = "", updateEventClass = UpdateExecuteMethodEvent.class, updateParams = {"createNewOrder", "'testMethodEvent_'", "1", "100.0"}, updateType = {IUpdateCommand.UpdateType.INSERT}, executeTiming = ExecuteTiming.BEFORE_ENTITY_UPDATE)
public class ProductOrderDetail6Dto extends BaseDto implements Serializable {
    private Integer orderDetailId;

    private Integer orderId;

    private Integer productId;

    private String productName;

    private BigDecimal weight;

    private BigDecimal amount;

    private Integer deleted;

    private ProductOrderDto6 productOrder6Dto;

    public void createNewOrder(String param, Integer val1, BigDecimal val2) {
        System.out.println("Test FOR UpdateExecuteMethodEvent：" + param + ", val1:" + val1 + ", val2:" + val2);
        this.productOrder6Dto.setOrderId( this.getOrderId());
        this.productOrder6Dto.setOrderName(param);
        this.productOrder6Dto.setTotalCount(1);
        this.productOrder6Dto.setTotalWeight(this.weight);
        this.productOrder6Dto.setAmount(this.amount);
    }

}
