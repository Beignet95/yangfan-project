package com.ruoyi.project.oms.removalDetail.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
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
public class RemovalDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** order-id */
    @Excel(name = "order-id")
    @CsvBindByPosition(position = 1)
    @CsvBindByName( column = "order-id")
    private String orderId;

    /** order-type */
    @Excel(name = "order-type")
    @CsvBindByPosition(position = 2)
    @CsvBindByName( column = "order-type")
    private String orderType;

    /** sku */
    @Excel(name = "sku")
    @CsvBindByPosition(position = 5)
    @CsvBindByName( column = "sku")
    private String sku;

    /** removal-fee */
    @Excel(name = "removal-fee")
    @CsvBindByPosition(position = 13)
    @CsvBindByName( column = "removal-fee")
    private BigDecimal removalFee;

    /** currency */
    @Excel(name = "currency")
    @CsvBindByPosition(position = 14)
    @CsvBindByName( column = "currency")
    private String currency;

    /** 月份 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "月份", width = 30, dateFormat = "yyyy-MM-dd")
    private Date month;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 负责人 */
    @Excel(name = "负责人")
    private String principal;

    /** 型号 */
    @Excel(name = "型号")
    private String spu;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String standardSku;

    @Override
    public String toString(){
        return "订单号："+orderId;
    }

}
