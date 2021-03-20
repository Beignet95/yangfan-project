package com.ruoyi.project.oms.transactionRecord.vo;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

/**
 * 订单号与标准SKU关系实体
 */
@Data
public class OrderIdSku {
    @Excel(name = "参考单号")
    private String orderId;//订单号
    @Excel(name = "SKU1")
    private String sku;//标准SKU
}
