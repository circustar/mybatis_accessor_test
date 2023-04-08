package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.*;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAvgAssignEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAvgEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateCountEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateSumEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
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
@UpdateEvent(onExpression = "", updateEventClass = UpdateCountEvent.class, updateParams = {"teamCount", "personInfoList"})
@UpdateEvent(onExpression = "", updateEventClass = UpdateSumEvent.class, updateParams = {"teamTotalPoint", "personInfoList", "point"})
@UpdateEvent(onExpression = "", updateEventClass = UpdateAvgEvent.class, updateParams = {"teamAveragePoint", "personInfoList", "point", "2"})
@UpdateEvent(onExpression = "", updateEventClass = UpdateAvgAssignEvent.class, updateParams = {"sharePoll", "personInfoList", "sharePoll", "2", "weight"})
public class PersonInfoDto extends BaseDto implements Serializable {
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

    private List<PersonInfoDto> personInfoList;
}
