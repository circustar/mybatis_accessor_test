package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.*;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAssignEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateCountEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateSumEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
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
@UpdateEvent(onExpression = "#{orderDetails?.size() > 0}", updateEventClass = UpdateCountEvent.class, updateParams = {"totalCount", "orderDetails"})
@UpdateEvent(onExpression = "#{orderDetails?.size() > 0}", updateEventClass = UpdateSumEvent.class, updateParams = {"totalWeight", "orderDetails", "weight"})
//@AfterUpdate(onExpression = "#{orderDetails?.size() > 0}", afterUpdateExecutor = AfterUpdateAvgSqlExecutor.class, updateParams = {"totalWeight", "orderDetails", "weight", "2"})
@UpdateEvent(onExpression = "#{amount!=null}", updateEventClass = UpdateAssignEvent.class, updateParams = {"amount", "orderDetails", "amount", "2", "weight"})
public class ProductOrderDto2 extends BaseDto implements Serializable {
    private Integer orderId;

    private String orderName;

    private Integer totalCount;

    private BigDecimal totalWeight;

    private BigDecimal amount;

    private Integer deleted;

    private List<ProductOrderDetailDto> orderDetails;
}
