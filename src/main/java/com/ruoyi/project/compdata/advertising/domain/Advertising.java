package com.ruoyi.project.compdata.advertising.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 广告源数据对象 cpd_advertising
 * 
 * @author Beignet
 * @date 2021-01-18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Advertising extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 月份 */
    @Excel(name = "月份")
    private String month;

    /** 店铺 */
    @Excel(name = "店铺")
    private String storeCode;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 型号 */
    @Excel(name = "型号")
    private String type;

    /** asin */
    @Excel(name = "ASIN")
    private String asin;

    /** sku */
    @Excel(name = "SKU")
    private String sku;

    /** 点击量 */
    @Excel(name = "点击")
    private Long click;

    /** 曝光 */
    @Excel(name = "展示")
    private Long exposure;

    /** ctr */
    @Excel(name = "CTR",cellType=Excel.ColumnType.NUMERIC)
    private Float ctr;

    /** cvr */
    @Excel(name = "CVR",cellType=Excel.ColumnType.NUMERIC)
    private Float cvr;

    /** 广告订单量 */
    @Excel(name = "广告订单量")
    private Long advertisingOrder;

    /** 广告花费 */
    @Excel(name = "广告花费")
    private BigDecimal advertisingSpend;

    /** acos */
    @Excel(name = "ACOS",cellType=Excel.ColumnType.NUMERIC)
    private Float acos;

    /** cpc */
    @Excel(name = "CPC")
    private BigDecimal cpc;

    /** 广告订单占比 */
    @Excel(name = "广告订单占比",cellType=Excel.ColumnType.NUMERIC)
    private Float advertisingOrderPercentage;

    /** acoas */
    @Excel(name = "ACoAS",cellType=Excel.ColumnType.NUMERIC)
    private Float acoas;

    /** 退款率 */
    @Excel(name = "退款率",cellType=Excel.ColumnType.NUMERIC)
    private Float refundRate;

    /** sessions */
    @Excel(name = "Sessions")
    private Long sessions;

    /** 广告销售额 */
    @Excel(name = "广告销售额")
    private BigDecimal advertisingSales;

    /** 销售额 */
    @Excel(name = "销售额")
    private BigDecimal sales;

    /** 退款量 */
    @Excel(name = "退款量")
    private Long refund;

    /** 订单量 */
    @Excel(name = "订单量")
    private Long orderNum;

    /** 销量 */
    @Excel(name = "销量")
    private Long salesNum;

    /** 删除标记（1为删除,2为未删除） */
    private Integer isdelete;
}
