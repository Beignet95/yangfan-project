package com.ruoyi.project.compdata.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingEchartsVo {
    private String[] monthStrs;
    private Long[] totalExposures;
    private Long[] totalClicks;
    private Float[] methCtrs;
    private Integer[] totalAdvertisingOrders;
    private Float[]  methCvrs;
    private Integer[] maxSessionses;
    private Float[]  maxCvrs;
    private Integer[] totalAdvertisingSpends;
    private BigDecimal[] totalAdvertisingSaleses;
    private BigDecimal[] totalSaleses;
    private Float[] methAcoses;
    private Float[] methAcoases;
    private Float[] methCpcs;
    private Float[] methAdvertisingOrderPercentages;
    private Float[] methRefundRates;
}
