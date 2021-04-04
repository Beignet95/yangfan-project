package com.ruoyi.project.pms.registration.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.util.converter.LocalDateStringConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 补发登记对象 pms_reissue_registration
 * 
 * @author Kwl
 * @date 2021-04-01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReissueRegistration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNumber;

    /** 客服负责人 */
    @Excel(name = "客服负责人")
    private String customerManager;

    /** 账号 */
    @Excel(name = "账号")
    private String accountNumber;

    /** 站点 */
    @Excel(name = "站点")
    private String site;

    /** 易仓SKU */
    @Excel(name = "易仓SKU")
    private String warehouseSku;

    /** 套数 */
    @Excel(name = "套数")
    private Integer numberSets;

    /** 个数 */
    @Excel(name = "个数")
    private Integer number;

    /** 收件人姓名 */
    @Excel(name = "收件人姓名")
    private String recipientName;

    /** 收货地址 */
    @Excel(name = "收货地址")
    private String receiptAddress;

    /** 对应货物净重 */
    @Excel(name = "对应货物净重")
    private BigDecimal goodsNetweight;

    /** 对应货物毛重 */
    @Excel(name = "对应货物毛重")
    private BigDecimal goodsGrossweight;

    /** 跟踪号 */
    @Excel(name = "跟踪号")
    private String trackingNumber;

    /** 客服备注 */
    @Excel(name = "客服备注")
    private String customerRemarks;

    /** 客服登记日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "客服登记日期", width = 30, dateFormat = "yyyy-MM-dd" )
    private Date customerRegistrationDate;

    /** 仓库发货日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "仓库发货日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warehouseShipDate;

    /** 物流是否已操作 */
    @Excel(name = "物流是否已操作")
    private String logisticsOperating;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(String customerManager) {
        this.customerManager = customerManager;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getWarehouseSku() {
        return warehouseSku;
    }

    public void setWarehouseSku(String warehouseSku) {
        this.warehouseSku = warehouseSku;
    }

    public Integer getNumberSets() {
        return numberSets;
    }

    public void setNumberSets(Integer numberSets) {
        this.numberSets = numberSets;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    public BigDecimal getGoodsNetweight() {
        return goodsNetweight;
    }

    public void setGoodsNetweight(BigDecimal goodsNetweight) {
        this.goodsNetweight = goodsNetweight;
    }

    public BigDecimal getGoodsGrossweight() {
        return goodsGrossweight;
    }

    public void setGoodsGrossweight(BigDecimal goodsGrossweight) {
        this.goodsGrossweight = goodsGrossweight;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getCustomerRemarks() {
        return customerRemarks;
    }

    public void setCustomerRemarks(String customerRemarks) {
        this.customerRemarks = customerRemarks;
    }

    public Date getCustomerRegistrationDate() {
        return customerRegistrationDate;
    }

    public void setCustomerRegistrationDate(Date customerRegistrationDate) {
        this.customerRegistrationDate = customerRegistrationDate;
    }

    public Date getWarehouseShipDate() {
        return warehouseShipDate;
    }

    public void setWarehouseShipDate(Date warehouseShipDate) {
        this.warehouseShipDate = warehouseShipDate;
    }

    public String getLogisticsOperating() {
        return logisticsOperating;
    }

    public void setLogisticsOperating(String logisticsOperating) {
        this.logisticsOperating = logisticsOperating;
    }
}
