package com.ruoyi.project.pms.productinfoReation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 产品信息映射对象 pms_productinfo_relation
 * 
 * @author Beignet
 * @date 2021-03-05
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductinfoRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** ASIN */
    @NotNull
    @Excel(name = "ASIN")
    private String asin;

    /** 负责人 */
    @NotNull
    @Excel(name = "负责人")
    private String principal;

    /** 型号 */
    @NotNull
    @Excel(name = "型号")
    private String type;

    /** 标准SKU */
    @NotNull
    @Excel(name = "标准SKU")
    private String sku;
}
