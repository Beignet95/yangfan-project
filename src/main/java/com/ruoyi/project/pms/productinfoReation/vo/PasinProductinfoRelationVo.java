package com.ruoyi.project.pms.productinfoReation.vo;

import lombok.Data;

@Data
public class PasinProductinfoRelationVo {
    /** 负责人 */
    private String principal;

    /** 型号 */
    private String type;

    /** 标准SKU */
    private String sku;

    /** 父ASIN */
    private String parentAsin;
}
