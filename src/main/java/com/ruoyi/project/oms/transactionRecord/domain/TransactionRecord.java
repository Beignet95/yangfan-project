package com.ruoyi.project.oms.transactionRecord.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 交易数据对象 oms_transaction_record
 * 
 * @author Beignet
 * @date 2021-02-23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** date/time */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "date/time", width = 30, dateFormat = "d MMM yyyy HH:mm:ss 'UTC'")
    @CsvBindByName(column = "date/time")
    private Date time;

    /** settlement id */
    @Excel(name = "settlement id")
    @CsvBindByName(column = "settlement id")
    private String settlementId;

    /** type */
    @Excel(name = "type")
    @CsvBindByName(column = "type")
    private String type;

    /** order id */
    @Excel(name = "order id")
    @CsvBindByName(column = "order id")
    private String orderId;

    /** sku */
    @Excel(name = "sku")
    @CsvBindByName(column = "sku")
    private String sku;

    /** description */
    //@Excel(name = "description")
    private String description;

    /** quantity */
    @Excel(name = "quantity")
    @CsvBindByName(column = "quantity")
    private Long quantity;

    /** marketplace */
    @Excel(name = "marketplace")
    @CsvBindByName(column = "marketplace")
    private String marketplace;

    /** fulfilment */
    @Excel(name = "fulfilment")
    @CsvBindByName(column = "fulfilment")
    private String fulfilment;

    /** order city */
    @Excel(name = "order city")
    @CsvBindByName(column = "order city")
    private String orderCity;

    /** order state */
    @Excel(name = "order state")
    @CsvBindByName(column = "order state")
    private String orderState;

    /** order postal */
    @Excel(name = "order postal")
    @CsvBindByName(column = "order postal")
    private String orderPostal;

    /** tax collection model */
    @Excel(name = "tax collection model")
    @CsvBindByName(column = "tax collection model")
    private String taxCollectionModel;

    /** product sales */
    @Excel(name = "product sales")
    @CsvBindByName(column = "product sales")
    private BigDecimal productSales;

    /** product sales tax */
    @Excel(name = "product sales tax")
    @CsvBindByName(column = "product sales tax")
    private BigDecimal productSalesTax;

    /** postage credits */
    @Excel(name = "postage credits")
    @CsvBindByName(column = "postage credits")
    private BigDecimal postageCredits;

    /** shipping credits tax */
    @Excel(name = "shipping credits tax")
    @CsvBindByName(column = "shipping credits tax")
    private BigDecimal shippingCreditsTax;

    /** gift wrap credits */
    @Excel(name = "gift wrap credits")
    @CsvBindByName(column = "gift wrap credits")
    private BigDecimal giftWrapCredits;

    /** giftwrap credits tax */
    @Excel(name = "giftwrap credits tax")
    @CsvBindByName(column = "giftwrap credits tax")
    private BigDecimal giftwrapCreditsTax;

    /** promotional rebates */
    @Excel(name = "promotional rebates")
    @CsvBindByName(column = "promotional rebates")
    private BigDecimal promotionalRebates;

    /** promotional rebates tax */
    @Excel(name = "promotional rebates tax")
    @CsvBindByName(column = "promotional rebates tax")
    private BigDecimal promotionalRebatesTax;

    /** marketplace withheld tax */
    @Excel(name = "marketplace withheld tax")
    @CsvBindByName(column = "marketplace withheld tax")
    private BigDecimal marketplaceWithheldTax;

    /** selling fees */
    @Excel(name = "selling fees")
    @CsvBindByName(column = "selling fees")
    private BigDecimal sellingFees;

    /** fba fees */
    @Excel(name = "fba fees")
    @CsvBindByName(column = "fba fees")
    private BigDecimal fbaFees;

    /** other transaction fees */
    @Excel(name = "other transaction fees")
    @CsvBindByName(column = "other transaction fees")
    private BigDecimal otherTransactionFees;

    /** other */
    @Excel(name = "other")
    @CsvBindByName(column = "other")
    private BigDecimal other;

    /** total */
    @Excel(name = "total")
    @CsvBindByName(column = "total")
    private BigDecimal total;



}
