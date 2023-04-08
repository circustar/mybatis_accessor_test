package com.test.mybatis_accessor.dto;

import com.circustar.mybatis_accessor.annotation.scan.DtoEntityRelation;
import com.test.mybatis_accessor.entity.Score;
import com.test.mybatis_accessor.service.IScoreService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@DtoEntityRelation(entityClass = Score.class, service = IScoreService.class)
public class ScoreUpdateDto implements Serializable {
    private Integer scoreId;
    private BigDecimal score;
    private Integer version;
}
