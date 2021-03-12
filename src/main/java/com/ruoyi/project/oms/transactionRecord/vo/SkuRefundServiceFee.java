package com.ruoyi.project.oms.transactionRecord.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuRefundServiceFee {
    private String sku;
    private BigDecimal refundServiceFee;
}
