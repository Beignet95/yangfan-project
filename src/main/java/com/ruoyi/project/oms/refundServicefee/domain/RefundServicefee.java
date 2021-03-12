package com.ruoyi.project.oms.refundServicefee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 平台退款服务费对象 oms_refund_servicefee
 * 
 * @author Beignet
 * @date 2021-03-10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundServicefee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** Order ID */
    @Excel(name = "Order ID")
    private String orderId;

    /** Payment Type */
    @Excel(name = "Payment Type")
    private String paymentType;

    /** Payment Detail */
    @Excel(name = "Payment Detail")
    private String paymentDetail;

    /** Amount */
    @Excel(name = "Amount")
    private BigDecimal amount;

    public String toString(){
        return orderId;
    }

}
