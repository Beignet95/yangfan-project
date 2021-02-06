package com.ruoyi.project.oms.orderRefund.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 退款对象 oms_oder_refund
 * 
 * @author Beignet
 * @date 2021-02-02
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRefund extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 账号-站点 */
    @Excel(name = "账号-站点")
    private String storeCode;

    /** Date */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date;

    /** Order ID */
    @Excel(name = "Order ID")
    private String orderId;

    /** SKU */
    @Excel(name = "SKU")
    private String sku;

    /** Transaction type */
    @Excel(name = "Transaction type")
    private String transactionType;

    /** Payment Type */
    @Excel(name = "Payment Type")
    private String paymentType;

    /** Payment Detail */
    @Excel(name = "Payment Detail")
    private String paymentDetail;

    /** Amount */
    @Excel(name = "Amount")
    private String amount;

    /** Quantity */
    @Excel(name = "Quantity")
    private Long quantity;

    /** Product Title */
    @Excel(name = "Product Title")
    private String productTitle;



}
