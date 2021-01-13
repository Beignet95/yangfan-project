package com.ruoyi.project.compdata.vo;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 广告源数据实体类
 */
@Data
public class AdVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */
    private Date month;

    /** $column.columnComment */
    @Excel(name = "月份")
    private String monthStr;

    /** $column.columnComment */
    @Excel(name = "店铺")
    private String storeCode;

    /** $column.columnComment */
    @Excel(name = "国家")
    private String country;

    /** $column.columnComment */
    @Excel(name = "型号")
    private String type;

    /** $column.columnComment */
    @Excel(name = "ASIN")
    private String asin;

    /** $column.columnComment */
    @Excel(name = "SKU")
    private String sku;

    /** $column.columnComment */
    @Excel(name = "点击")
    private Long click;

    /** $column.columnComment */
    @Excel(name = "展示")
    private Long exposure;

    @Excel(name = "CTR",defaultValue = "0%")
    private String ctrStr;

    /** $column.columnComment */
    @Excel(name = "CVR",defaultValue = "0%")
    private String cvrStr;

    /** $column.columnComment */
    @Excel(name = "广告订单量")
    private Long advertisingOrder;

    /** $column.columnComment */
    @Excel(name = "广告花费")
    private BigDecimal advertisingSpend;

    /** $column.columnComment */
    @Excel(name = "ACOS",defaultValue = "0%")
    private String acosStr;

    /** $column.columnComment */
    @Excel(name = "CPC")
    private BigDecimal cpc;

    /** $column.columnComment */
    @Excel(name = "广告订单占比",defaultValue = "0%")
    private String advertisingOrderPercentageStr;

    /** $column.columnComment */
    @Excel(name = "ACoAS",defaultValue = "0%")
    private String acoasStr;

    /** $column.columnComment */
    @Excel(name = "退款率",defaultValue = "0%")
    private String refundRateStr;

    /** $column.columnComment */
    @Excel(name = "Sessions")
    private Integer sessions;

    /** $column.columnComment */
    @Excel(name = "广告销售额")
    private BigDecimal advertisingSales;

    /** $column.columnComment */
    @Excel(name = "销售额")
    private BigDecimal sales;

    /** $column.columnComment */
    @Excel(name = "退款量")
    private Long refund;

    /** $column.columnComment */
    @Excel(name = "销量")
    private Long salesNum;

    /** $column.columnComment */
    @Excel(name = "订单量")
    private Long orderNum;


}
