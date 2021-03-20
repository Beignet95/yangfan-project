package com.ruoyi.project.oms.orderRecord.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 订单记录对象 oms_order_record
 * 
 * @author Beignet
 * @date 2021-03-19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 参考单号 */
    @Excel(name = "参考单号")
    private String orderId;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String sku;



}
