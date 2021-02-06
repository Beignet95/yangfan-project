package com.ruoyi.project.oms.orderRefundRepeat.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 退款(重复数据）对象 oms_order_refund_repeat
 * 
 * @author Beignet
 * @date 2021-02-02
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRefundRepeat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 重复id */
    private String repeatId;

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
