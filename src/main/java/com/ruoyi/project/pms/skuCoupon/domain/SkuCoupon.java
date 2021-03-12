package com.ruoyi.project.pms.skuCoupon.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * sku与手续费关系对象 pms_sku_coupon
 * 
 * @author Beignet
 * @date 2021-03-10
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuCoupon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** Coupon Title */
    @Excel(name = "Coupon Title")
    private String couponTitle;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String sku;

    @Override
    public String toString(){
        return couponTitle;
    }



}
