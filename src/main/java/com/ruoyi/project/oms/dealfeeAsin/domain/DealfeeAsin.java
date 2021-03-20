package com.ruoyi.project.oms.dealfeeAsin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * Deal Fee与ASIN映射对象 oms_dealfee_asin
 * 
 * @author Beignet
 * @date 2021-03-16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DealfeeAsin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 记录id */
    @Excel(name = "记录id")
    private Long recordId;

    /** description */
    @Excel(name = "description")
    private String description;

    /** ASIN */
    @Excel(name = "ASIN")
    private String asin;



}
