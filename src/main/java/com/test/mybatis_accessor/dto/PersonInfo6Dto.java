package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.event.UpdateEvent;
import com.circustar.mybatis_accessor.listener.event.update.UpdateFillEvent;
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
@UpdateEvent(onExpression = "", updateEventClass = UpdateFillEvent.class, updateParams = {"sharePoll", "remainPoll", "personInfoList", "sharePoll", "fillValue", "personName"})
public class PersonInfo6Dto extends BaseDto implements Serializable {
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

    private BigDecimal fillValue = new BigDecimal(20);

    private List<PersonInfo6Dto> personInfoList;
}
