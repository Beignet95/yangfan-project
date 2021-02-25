package com.ruoyi.project.compdata.stadvertising.vo;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KeywordAnalyVo extends BaseEntity {
    private int position;
    private String customerSearchTerm;
    private Integer totalOrder;
    private Float orderRate;
}
