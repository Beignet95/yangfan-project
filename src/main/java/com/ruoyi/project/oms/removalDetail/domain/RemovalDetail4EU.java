package com.ruoyi.project.oms.removalDetail.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByPosition;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 移除明细对象 oms_removal_detail
 * 
 * @author Beignet
 * @date 2021-03-08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RemovalDetail4EU extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** order-id */
    @Excel(name = "order-id")
    @CsvBindByPosition(position = 1)
    private String orderId;

    /** order-type */
    @Excel(name = "order-type")
    @CsvBindByPosition(position = 2)
    private String orderType;

    /** sku */
    @Excel(name = "sku")
    @CsvBindByPosition(position = 6)
    private String sku;

    /** removal-fee */
    @Excel(name = "removal-fee")
    @CsvBindByPosition(position = 14)
    private BigDecimal removalFee;

    /** currency */
    @Excel(name = "currency")
    @CsvBindByPosition(position = 15)
    private String currency;

    /** 月份 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "月份", width = 30, dateFormat = "yyyy-MM-dd")
    private Date month;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

}
