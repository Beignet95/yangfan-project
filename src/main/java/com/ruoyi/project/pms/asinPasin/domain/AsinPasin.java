package com.ruoyi.project.pms.asinPasin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * ASIN与父ASIN关系对象 pms_asin_pasin
 * 
 * @author Beignet
 * @date 2021-03-08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AsinPasin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** ASIN /
     Product ID */
    @Excel(name = "ASIN / \n" +
            " Product ID")
    private String asin;

    /** 捆绑ASIN */
    @Excel(name = "捆绑ASIN")
    private String parentAsin;



}
