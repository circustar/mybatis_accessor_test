package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.UpdateEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAssignEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateCountEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateLogEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateSumEvent;
import com.circustar.mybatis_accessor.provider.command.IUpdateCommand;
import com.test.mybatis_accessor.entity.ProductOrder;
import com.test.mybatis_accessor.service.IProductOrderService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = ProductOrder.class, service = IProductOrderService.class)
@UpdateEvent(onExpression = "", updateEventClass = UpdateLogEvent.class, updateType = {IUpdateCommand.UpdateType.DELETE, IUpdateCommand.UpdateType.INSERT, IUpdateCommand.UpdateType.UPDATE})
public class ProductOrderDto4 extends BaseDto implements Serializable {
    private Integer orderId;

    private String orderName;

    private Integer totalCount;

    private BigDecimal totalWeight;

    private BigDecimal amount;

    private Integer deleted;

    private List<ProductOrderDetail4Dto> orderDetails;
}
