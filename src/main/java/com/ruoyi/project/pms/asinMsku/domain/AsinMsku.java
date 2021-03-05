package com.ruoyi.project.pms.asinMsku.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * ASIN与亚马逊产品SKU关系对象 pms_asin_msku
 * 
 * @author Beignet
 * @date 2021-03-05
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AsinMsku extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** ASIN */
    @Excel(name = "ASIN")
    private String asin;

    /** MSKU */
    @Excel(name = "MSKU")
    private String msku;



}
