package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.*;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAssignSqlEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAvgSqlEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateCountSqlEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateSumSqlEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.PersonInfo2;
import com.test.mybatis_accessor.service.IPersonInfoService;
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
@DtoEntityRelation(entityClass = PersonInfo2.class, service = IPersonInfoService.class)
@UpdateEvent(onExpression = "", updateEventClass = UpdateCountSqlEvent.class, updateParams = {"teamCount", "personInfoList"})
@UpdateEvent(onExpression = "", updateEventClass = UpdateSumSqlEvent.class, updateParams = {"teamTotalPoint", "personInfoList", "point"})
@UpdateEvent(onExpression = "", updateEventClass = UpdateAvgSqlEvent.class, updateParams = {"teamAveragePoint", "personInfoList", "point", "2"})
@UpdateEvent(onExpression = "", updateEventClass = UpdateAssignSqlEvent.class, updateParams = {"sharePoll", "personInfoList", "sharePoll", "2", "weight"})
public class PersonInfo4Dto extends BaseDto implements Serializable {
    private Integer personId;

    private Integer leaderId;

    private String personName;

    private BigDecimal point;

    private Integer teamCount;

    private BigDecimal teamTotalPoint;

    private BigDecimal teamAveragePoint;

    private BigDecimal weight;

    private BigDecimal sharePoll;

    private Integer deleted;

    private List<PersonInfo4Dto> personInfoList;
}
