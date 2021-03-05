package com.ruoyi.project.pms.productinfoReation.vo;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 标准SKU与MSKU关系实体类
 */
@Data
public class MskuProductinfoRelationVo {
    /** 负责人 */
    private String principal;

    /** 型号 */
    private String type;

    /** 标准SKU */
    private String sku;

    /** 标准SKU */
    private String msku;
}
