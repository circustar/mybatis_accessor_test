package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.*;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAssignSqlEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateCountSqlEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateSumSqlEvent;
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
@UpdateEvent(onExpression = "#{orderDetails?.size() > 0}", updateEventClass = UpdateCountSqlEvent.class, updateParams = {"totalCount", "orderDetails"})
@UpdateEvent(onExpression = "#{orderDetails?.size() > 0}", updateEventClass = UpdateSumSqlEvent.class, updateParams = {"totalWeight", "orderDetails", "weight"})
//@AfterUpdate(onExpression = "#{orderDetails?.size() > 0}", afterUpdateExecutor = AfterUpdateAvgSqlExecutor.class, updateParams = {"totalWeight", "orderDetails", "weight", "2"})
@UpdateEvent(onExpression = "#{amount!=null}", updateEventClass = UpdateAssignSqlEvent.class, updateParams = {"amount", "orderDetails", "amount", "2", "weight"})
public class ProductOrderDto extends BaseDto implements Serializable {
    private Integer orderId;

    private String orderName;

    private Integer totalCount;

    private BigDecimal totalWeight;

    private BigDecimal amount;

    private Integer deleted;

    private List<ProductOrderDetailDto> orderDetails;
}
