package com.ruoyi.project.compdata.videoadvertising.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 视频广告数据源对象 cpd_video_advertising
 * 
 * @author Beignet
 * @date 2021-01-29
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VideoAdvertising extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 账号简称 */
    @Excel(name = "账号简称")
    private String storeCode;

    /** ASIN */
    @Excel(name = "ASIN")
    private String asin;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String sku;

    /** 广告专员 */
    @Excel(name = "广告专员")
    private String commissioner;

    /** 产品类型 */
    @Excel(name = "产品类型")
    private String type;

    /** Listing链接 */
    @Excel(name = "Listing链接")
    private String link;

    /** 利润（不计算广告点数，只计算平台佣金，税费，不良率） */
    @Excel(name = "利润", readConverterExp = "不=计算广告点数，只计算平台佣金，税费，不良率")
    private BigDecimal profit;

    /** CPA（广告费用/订单总数） */
    @Excel(name = "CPA（广告费用/订单总数）")
    private BigDecimal cpa;

    /** CPA盈亏(利润值-CPA) */
    @Excel(name = "CPA盈亏(利润值-CPA)")
    private BigDecimal cpaProfit;

    /** CPA等级 */
    @Excel(name = "CPA等级")
    private Integer cpaProfitLevel;

    /** CTR等级 */
    @Excel(name = "CTR等级")
    private Integer ctrLevel;

    /** CVR等级 */
    @Excel(name = "CVR等级")
    private Integer cvrLevel;

    /** ACOS等级 */
    @Excel(name = "ACOS等级")
    private Integer acosLevel;

    /** 广告专员操作反馈 */
    @Excel(name = "广告专员操作反馈")
    private String operationFeedback;

    /** 识别码 */
    @Excel(name = "识别码")
    private String identificationCode;

    /** Start Date */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Start Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** End Date */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "End Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** Portfolio name */
    @Excel(name = "Portfolio name")
    private String portfolioName;

    /** Currency */
    @Excel(name = "Currency")
    private String currency;

    /** Campaign Name */
    @Excel(name = "Campaign Name")
    private String campaignName;

    /** Ad Group Name */
    @Excel(name = "Ad Group Name")
    private String adGroupName;

    /** Targeting */
    @Excel(name = "Targeting")
    private String targeting;

    /** Match Type */
    @Excel(name = "Match Type")
    private String matchType;

    /** Customer Search Term */
    @Excel(name = "Customer Search Term")
    private String customerSearchTerm;

    /** Impressions */
    @Excel(name = "Impressions")
    private Long impressions;

    /** Clicks */
    @Excel(name = "Clicks")
    private Long clicks;

    /** Click-Thru Rate (CTR) */
    @Excel(name = "Click-Thru Rate (CTR)")
    private Float ctr;

    /** Cost Per Click (CPC) */
    @Excel(name = "Cost Per Click (CPC)")
    private BigDecimal cpc;

    /** Spend */
    @Excel(name = "Spend")
    private BigDecimal spend;

    /** 14 Day Total Sales */
    @Excel(name = "14 Day Total Sales ")
    private BigDecimal totalSales;

    /** Total Advertising Cost of Sales (ACoS) */
    @Excel(name = "Total Advertising Cost of Sales (ACoS) ")
    private Float acos;

    /** Total Return on Advertising Spend (RoAS) */
    @Excel(name = "Total Return on Advertising Spend (RoAS)")
    private BigDecimal roas;

    /** 14 Day Total Orders (#) */
    @Excel(name = "14 Day Total Orders (#)")
    private Long totalOrders;

    /** 14 Day Total Units (#) */
    @Excel(name = "14 Day Total Units (#)")
    private Long totalUnits;

    /** 14 Day Conversion Rate */
    @Excel(name = "14 Day Conversion Rate")
    private Float cvr;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal costprice;



}
