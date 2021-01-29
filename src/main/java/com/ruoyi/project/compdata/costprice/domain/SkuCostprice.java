package com.ruoyi.project.compdata.costprice.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 成本对象 cpd_sku_costprice
 * 
 * @author Beignet
 * @date 2021-01-26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuCostprice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String sku;

    /** 成本价(不含平台费，广告，不良等费用） */
    @Excel(name = "成本价(不含平台费，广告，不良等费用）")
    private BigDecimal costprice;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sku", getSku())
            .append("costprice", getCostprice())
            .toString();
    }
}
