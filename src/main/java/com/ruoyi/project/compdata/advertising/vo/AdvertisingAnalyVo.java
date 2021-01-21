package com.ruoyi.project.compdata.advertising.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingAnalyVo {
    private String month;
    //private String monthStr;
    private Long totalExposure;
    private Long totalClick;
    private Float methCtr;
    private Integer totalAdvertisingOrder;
    private Float  methCvr;
    private Integer maxSessions;
    private Float  maxCvr;
    private Integer totalAdvertisingSpend;
    private BigDecimal totalSales;
    private Float methAcos;
    private Float methAcoas;
    private Float methCpc;
    private Float methAdvertisingOrderPercentage;
    private Float methRefundRate;
    private BigDecimal totalAdvertisingSales;
}
