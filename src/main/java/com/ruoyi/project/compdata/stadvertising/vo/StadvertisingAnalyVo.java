package com.ruoyi.project.compdata.stadvertising.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import com.ruoyi.project.compdata.advertisingactivity.domain.AdvertisingActivity;
import com.ruoyi.project.compdata.stadvertising.domain.Stadvertising;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StadvertisingAnalyVo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** cpd_stadvertisin表的主键 */
    private Long id;
    //其他属性
    /** 产品类型 **/
    private String type;
    /**Listing链接**/
    private String link;
    /**利润（不计算广告点数，只计算平台佣金，税费，不良率）**/
    private BigDecimal profit;
    /**CPA（广告费用/订单总数）**/
    private BigDecimal cpa;
    /**CPA盈亏(利润值-CPA) 也是另外一列的CPA**/
    private BigDecimal cpaProfit;
    /**CPA盈亏(利润值-CPA)**/
    private String cpaProfitLevel;
    /**CTR**/
    //private String ctr;
    /**CTR等级**/
    private String ctrLevel;
    /**CVR**/
    private String cvr;
    /**CVR等级**/
    private String cvrLevel;
    /**ACOS**/
    //private String acos;
    /**ACOS等级**/
    private String acosLevel;
    /**识别码**/
    private String identificationCode;


    //额外

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

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal costprice;

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
    private Long ctr;

    /** Cost Per Click (CPC) */
    @Excel(name = "Cost Per Click (CPC)")
    private BigDecimal cpc;

    /** Spend */
    @Excel(name = "Spend")
    private BigDecimal spend;

    /** 7 Day Total Sales  */
    @Excel(name = "7 Day Total Sales ")
    private BigDecimal totalSales;

    /** Total Advertising Cost of Sales (ACoS)  */
    @Excel(name = "Total Advertising Cost of Sales (ACoS) ")
    private BigDecimal acos;

    /** Total Return on Advertising Spend (RoAS) */
    @Excel(name = "Total Return on Advertising Spend (RoAS)")
    private BigDecimal roas;

    /** 7 Day Total Orders (#) */
    @Excel(name = "7 Day Total Orders (#)")
    private Long totalOrders;

    /** 7 Day Total Units (#) */
    @Excel(name = "7 Day Total Units (#)")
    private Long totalUnits;

    /** 7 Day Conversion Rate */
    @Excel(name = "7 Day Conversion Rate")
    private Long conversionRate;

    /** 7 Day Advertised SKU Units (#) */
    @Excel(name = "7 Day Advertised SKU Units (#)")
    private Long advertisedSkuUnits;

    /** 7 Day Other SKU Units (#) */
    @Excel(name = "7 Day Other SKU Units (#)")
    private Long otherSkuUnits;

    /** 7 Day Advertised SKU Sales  */
    @Excel(name = "7 Day Advertised SKU Sales ")
    private BigDecimal advertisedSkuSales;

    /** 7 Day Other SKU Sales  */
    @Excel(name = "7 Day Other SKU Sales ")
    private BigDecimal otherSkuSales;

    /** 广告专员操作反馈 */
    @Excel(name = "广告专员操作反馈")
    private String operationFeedback;

}
