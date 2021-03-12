package com.ruoyi.project.oms.transactionRecord.vo;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class SkuRemovalFee {
    private String sku;
    private BigDecimal removalFee;
}
