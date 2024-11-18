package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.UpdateEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAssignSqlEvent;
import com.test.mybatis_accessor.entity.PersonInfo;
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
@DtoEntityRelation(entityClass = PersonInfo.class, service = IPersonInfoService.class)
@UpdateEvent(onExpression = "", updateEventClass = UpdateAssignSqlEvent.class, updateParams = {"sharePoll", "personInfoList", "sharePoll", "2", "weight", "weight"})
@UpdateEvent(onExpression = "", updateEventClass = UpdateAssignSqlEvent.class, updateParams = {"point", "personInfoList", "point", "2", "weight", "weight"})
public class PersonInfo10Dto extends BaseDto implements Serializable {
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

    private List<PersonInfo10Dto> personInfoList;
}
