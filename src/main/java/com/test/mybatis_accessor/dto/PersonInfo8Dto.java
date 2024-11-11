package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.dto.QueryJoin;
import com.circustar.mybatis_accessor.annotation.event.PropertyChangeEvent;
import com.circustar.mybatis_accessor.annotation.event.UpdateEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAvgAssignEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateAvgEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateCountEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateSumEvent;
import com.test.mybatis_accessor.component.ExecuteUpdateBean2;
import com.test.mybatis_accessor.entity.PersonInfo2;
import com.test.mybatis_accessor.service.IPersonInfo2Service;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = PersonInfo2.class, service = IPersonInfo2Service.class)
@UpdateEvent(onExpression = "", updateEventClass = UpdateCountEvent.class, updateParams = {PersonInfo8Dto.Fields.teamCount, PersonInfo8Dto.Fields.personInfoList})
@PropertyChangeEvent(listenProperties = "teamCount", toExpression = "", updateEventClass = ExecuteUpdateBean2.class)
public class PersonInfo8Dto extends BaseDto implements Serializable {
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

    @QueryJoin(tableAlias = "t", joinExpression = "person_info.person_id = t.leader_id and t.deleted = 0")
    private List<PersonInfo8Dto> personInfoList;
}
