package com.ruoyi.project.oms.transactionRecord.domain;

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

    /** 账号 */
    //@Excel(name = "账号")
    private String account;

    /** 站点 */
    //@Excel(name = "站点")
    private String site;

    /** date/time */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "date/time", width = 30, dateFormat = "d MMM yyyy HH:mm:ss 'UTC'")
    @CsvBindByPosition(position = 0)
    private Date time;

    /** settlement id */
    @Excel(name = "settlement id")
    @CsvBindByPosition(position = 1)
    private String settlementId;

    /** type */
    @Excel(name = "type")
    @CsvBindByPosition(position = 2)
    private String type;

    /** order id */
    @Excel(name = "order id")
    @CsvBindByPosition(position = 3)
    private String orderId;

    /** sku */
    @Excel(name = "sku")
    @CsvBindByPosition(position = 4)
    private String sku;

    /** description */
    //@Excel(name = "description")
    private String description;

    /** quantity */
    @Excel(name = "quantity")
    @CsvBindByPosition(position = 6)
    private Long quantity;

    /** marketplace */
    @Excel(name = "marketplace")
    @CsvBindByPosition(position = 7)
    private String marketplace;

    /** fulfilment */
    @Excel(name = "fulfilment")
    @CsvBindByPosition(position = 8)
    private String fulfilment;

    /** order city */
    @Excel(name = "order city")
    @CsvBindByPosition(position = 9)
    private String orderCity;

    /** order state */
    @Excel(name = "order state")
    @CsvBindByPosition(position = 10)
    private String orderState;

    /** order postal */
    @Excel(name = "order postal")
    @CsvBindByPosition(position = 11)
    private String orderPostal;

    /** tax collection model */
    @Excel(name = "tax collection model")
    @CsvBindByPosition(position = 12)
    private String taxCollectionModel;

    /** product sales */
    @Excel(name = "product sales")
    @CsvBindByPosition(position = 13)
    private BigDecimal productSales;

    /** product sales tax */
    @Excel(name = "product sales tax")
    @CsvBindByPosition(position = 14)
    private BigDecimal productSalesTax;

    /** postage credits */
    @Excel(name = "postage credits")
    @CsvBindByPosition(position = 15)
    private BigDecimal postageCredits;

    /** shipping credits tax */
    @Excel(name = "shipping credits tax")
    @CsvBindByPosition(position = 16)
    private BigDecimal shippingCreditsTax;

    /** gift wrap credits */
    @Excel(name = "gift wrap credits")
    @CsvBindByPosition(position = 17)
    private BigDecimal giftWrapCredits;

    /** giftwrap credits tax */
    @Excel(name = "giftwrap credits tax")
    @CsvBindByPosition(position = 18)
    private BigDecimal giftwrapCreditsTax;

    /** promotional rebates */
    @Excel(name = "promotional rebates")
    @CsvBindByPosition(position = 19)
    private BigDecimal promotionalRebates;

    /** promotional rebates tax */
    @Excel(name = "promotional rebates tax")
    @CsvBindByPosition(position = 20)
    private BigDecimal promotionalRebatesTax;

    /** marketplace withheld tax */
    @Excel(name = "marketplace withheld tax")
    @CsvBindByPosition(position = 21)
    private BigDecimal marketplaceWithheldTax;

    /** selling fees */
    @Excel(name = "selling fees")
    @CsvBindByPosition(position = 22)
    private BigDecimal sellingFees;

    /** fba fees */
    @Excel(name = "fba fees")
    @CsvBindByPosition(position = 23)
    private BigDecimal fbaFees;

    /** other transaction fees */
    @Excel(name = "other transaction fees")
    @CsvBindByPosition(position = 24)
    private BigDecimal otherTransactionFees;

    /** other */
    @Excel(name = "other")
    @CsvBindByPosition(position = 25)
    private BigDecimal other;

    /** total */
    @Excel(name = "total")
    @CsvBindByPosition(position = 26)
    private BigDecimal total;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String standardSku;

    /** 型号 */
    @Excel(name = "型号")
    private String spu;

    /** 负责人 */
    @Excel(name = "负责人")
    private String principal;

    /** 类型 */
    @Excel(name = "类型")
    private Integer chargType;

    /** 备用字段 */
    //@Excel(name = "备用字段")
    private String spareField;



}
