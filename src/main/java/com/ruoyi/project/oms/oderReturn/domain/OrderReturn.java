package com.ruoyi.project.oms.oderReturn.domain;

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
 * 退货对象 oms_order_return
 * 
 * @author Beignet
 * @date 2021-02-03
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderReturn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 账号-站点 */
    @Excel(name = "账号-站点")
    private String storeCode;

    /** return-date */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "return-date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /** order-id */
    @Excel(name = "order-id")
    private String orderId;

    /** sku */
    @Excel(name = "sku")
    private String sku;

    /** asin */
    @Excel(name = "asin")
    private String asin;

    /** fnsku */
    @Excel(name = "fnsku")
    private String fnsku;

    /** product-name */
    @Excel(name = "product-name")
    private String productName;

    /** quantity */
    @Excel(name = "quantity")
    private Long quantity;

    /** fulfillment-center-id */
    @Excel(name = "fulfillment-center-id")
    private String fulfillmentCenterId;

    /** detailed-disposition */
    @Excel(name = "detailed-disposition")
    private String detailedDisposition;

    /** reason */
    @Excel(name = "reason")
    private String reason;

    /** status */
    @Excel(name = "status")
    private String status;

    /** license-plate-number */
    @Excel(name = "license-plate-number")
    private String licensePlateNumber;

    /** customer-comments */
    @Excel(name = "customer-comments")
    private String customerComments;

    /** detailed-disposition翻译 */
    @Excel(name = "detailed-disposition翻译")
    private String detailedDispositionForcn;

    /** reason翻译 */
    @Excel(name = "reason翻译")
    private String reasonForcn;



}
