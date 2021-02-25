package com.ruoyi.project.compdata.productPrincipal.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 产品负责人映射对象 cpd_product_principal
 * 
 * @author Beignet
 * @date 2021-02-23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductPrincipal extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String sku;

    /** 型号 */
    @Excel(name = "型号")
    private String type;

    /** 广告负责人 */
    @Excel(name = "广告负责人")
    private String principal;

    /** 类型 */
    @Excel(name = "类型",readConverterExp = "1=主推,2=清货")
    private Integer chargeType;



}
