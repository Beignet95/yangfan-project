package com.ruoyi.project.compdata.finance.vo;

import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 型号利润分析 实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TypeProfitAnalyVo extends BaseEntity {
    private String curMonth;//本月月份
    private String preMonth;//上月月份
    private String type;//型号
    private BigDecimal curMonthProfit;//本月毛利
    private BigDecimal preMonthProfit;//上月毛利
    private BigDecimal profitGross;//毛利增长
    private Float decliningProfitpercentage;//下滑利润占比
    private Float profitGrowthRatio;//毛利增长比例
}
