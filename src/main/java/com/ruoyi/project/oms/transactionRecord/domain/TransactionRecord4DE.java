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
public class TransactionRecord4DE extends BaseEntity
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
    @Excel(name = "Datum/Uhrzeit", width = 30, dateFormat = "d MMM yyyy HH:mm:ss 'UTC'")
    private Date time;

    /** settlement id */
    @Excel(name = "Abrechnungsnummer")
    @CsvBindByName(column = "settlement id")
    private String settlementId;

    /** type */
    @Excel(name = "Typ")
    private String type;

    /** order id */
    @Excel(name = "Bestellnummer")
    private String orderId;

    /** sku */
    @Excel(name = "SKU")
    private String sku;

    /** description */
    //@Excel(name = "description")
    private String description;

    /** quantity */
    @Excel(name = "Menge")
    private Long quantity;

    /** marketplace */
    @Excel(name = "Marketplace")
    private String marketplace;

    /** fulfilment */
    @Excel(name = "Versand")
    @CsvBindByName(column = "fulfilment")
    private String fulfilment;

    /** order city */
    @Excel(name = "Ort der Bestellung")
    @CsvBindByName(column = "order city")
    private String orderCity;

    /** order state */
    @Excel(name = "Bundesland")
    @CsvBindByName(column = "order state")
    private String orderState;

    /** order postal */
    @Excel(name = "Postleitzahl")
    private String orderPostal;

    /** tax collection model */
    @Excel(name = "Steuererhebungsmodell")
    private String taxCollectionModel;

    /** product sales */
    @Excel(name = "Umsätze")
    @CsvBindByName(column = "product sales")
    private BigDecimal productSales;

    /** product sales tax */
    @Excel(name = "Produktumsatzsteuer")
    @CsvBindByName(column = "product sales tax")
    private BigDecimal productSalesTax;

    /** postage credits */
    @Excel(name = "Gutschrift für Versandkosten")
    @CsvBindByName(column = "postage credits")
    private BigDecimal postageCredits;

    /** shipping credits tax */
    @Excel(name = "Steuer auf Versandgutschrift")
    @CsvBindByName(column = "shipping credits tax")
    private BigDecimal shippingCreditsTax;

    /** gift wrap credits */
    @Excel(name = "Gutschrift für Geschenkverpackung")
    @CsvBindByName(column = "gift wrap credits")
    private BigDecimal giftWrapCredits;

    /** giftwrap credits tax */
    @Excel(name = "Steuer auf Geschenkverpackungsgutschriften")
    @CsvBindByName(column = "giftwrap credits tax")
    private BigDecimal giftwrapCreditsTax;

    /** promotional rebates */
    @Excel(name = "Rabatte aus Werbeaktionen")
    @CsvBindByName(column = "promotional rebates")
    private BigDecimal promotionalRebates;
//"","","","Gebühren zu Versand durch Amazon","Andere Transaktionsgebühren","Andere","Gesamt"
    /** promotional rebates tax */
    @Excel(name = "Steuer auf Aktionsrabatte")
    @CsvBindByName(column = "promotional rebates tax")
    private BigDecimal promotionalRebatesTax;

    /** marketplace withheld tax */
    @Excel(name = "Einbehaltene Steuer auf Marketplace")
    @CsvBindByName(column = "marketplace withheld tax")
    private BigDecimal marketplaceWithheldTax;

    /** selling fees */
    @Excel(name = "Verkaufsgebühren")
    @CsvBindByName(column = "selling fees")
    private BigDecimal sellingFees;

    /** fba fees */
    @Excel(name = "Gebühren zu Versand durch Amazon")
    @CsvBindByName(column = "fba fees")
    private BigDecimal fbaFees;

    /** other transaction fees */
    @Excel(name = "Andere Transaktionsgebühren")
    @CsvBindByName(column = "other transaction fees")
    private BigDecimal otherTransactionFees;

    /** other */
    @Excel(name = "Andere")
    @CsvBindByName(column = "other")
    private BigDecimal other;

    /** total */
    @Excel(name = "Gesamt")
    @CsvBindByName(column = "total")
    private BigDecimal total;



}
