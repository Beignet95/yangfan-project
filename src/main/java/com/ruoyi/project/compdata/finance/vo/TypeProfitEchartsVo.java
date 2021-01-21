package com.ruoyi.project.compdata.finance.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TypeProfitEchartsVo {
    private String curMonth;//本月月份
    private String preMonth;//上月月份
    private String[] types;//型号
    private BigDecimal[] curMonthProfits;//本月毛利
    private BigDecimal[] preMonthProfits;//上月毛利
    private BigDecimal[] profitGrosses;//毛利增长
    private Float[] decliningProfitpercentages;//下滑利润占比
    private Float[] profitGrowthRatios;//毛利增长比例
}
