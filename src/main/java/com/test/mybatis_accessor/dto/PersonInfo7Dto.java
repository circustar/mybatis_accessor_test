package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.UpdateEvent;
import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.circustar.mybatis_accessor.listener.event.update.UpdateExecuteSqlEvent;
import com.circustar.mybatis_accessor.provider.command.IUpdateCommand;
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
@UpdateEvent(onExpression = "", updateEventClass = UpdateExecuteSqlEvent.class
        , updateParams = {"update test.person_info set share_poll = 99 where person_id = #{personId}"}
        , updateType = {IUpdateCommand.UpdateType.INSERT, IUpdateCommand.UpdateType.UPDATE})
public class PersonInfo7Dto extends BaseDto implements Serializable {
    private Integer personId;

    private Integer leaderId;

    private String personName;

    private BigDecimal point;

    private Integer teamCount;

    private BigDecimal teamTotalPoint;

    private BigDecimal teamAveragePoint;

    private BigDecimal weight;

    private BigDecimal sharePoll;

    private BigDecimal remainPoll;

    private Integer deleted;

    private List<PersonInfo7Dto> personInfoList;
}
