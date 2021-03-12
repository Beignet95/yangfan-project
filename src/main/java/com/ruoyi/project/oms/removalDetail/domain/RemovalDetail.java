package com.ruoyi.project.oms.removalDetail.domain;

import java.math.BigDecimal;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

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
public class RemovalDetail extends BaseEntity
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
    @CsvBindByPosition(position = 5)
    private String sku;

    /** removal-fee */
    @Excel(name = "removal-fee")
    @CsvBindByPosition(position = 13)
    private BigDecimal removalFee;

    /** currency */
    @Excel(name = "currency")
    @CsvBindByPosition(position = 14)
    private String currency;



}
