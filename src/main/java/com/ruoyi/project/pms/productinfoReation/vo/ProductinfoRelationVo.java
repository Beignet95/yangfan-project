package com.ruoyi.project.pms.productinfoReation.vo;

import lombok.Data;

@Data
public class ProductinfoRelationVo {
    /** 负责人 */
    private String principal;

    /** 型号 */
    private String type;

    /** 标准SKU */
    private String sku;

    /** general field */
    private String generalField;
}
