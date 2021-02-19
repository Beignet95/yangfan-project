package com.ruoyi.project.compdata.advertising.vo;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

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
public class AdvertisingTempVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;
//月份	店铺	国家	PARENT_ASIN	ASIN
    /** 月份 */
    @Excel(name = "月份")
    private String col1;

    /** 店铺 */
    @Excel(name = "店铺")
    private String col2;

    /** 国家 */
    @Excel(name = "国家")
    private String col3;

    /** PARENT_ASIN */
    @Excel(name = "PARENT_ASIN")
    private String col4;

    /** ASIN */
    @Excel(name = "ASIN")
    private String col41;


//	品名	SKU	分类	品牌	销量	订单量
    /** 品名 */
    @Excel(name = "品名")
    private String col5;

    /** SKU */
    @Excel(name = "SKU")
    private String col6;

    /** 分类 */
    @Excel(name = "分类")
    private String col7;

    /** 品牌 */
    @Excel(name = "品牌")
    private Long col8;

    /** 销量 */
    @Excel(name = "销量")
    private Long col9;

    /** 订单量 */
    @Excel(name = "订单量")
    private Float col10;
//销售额	退款量	退款率	FBA可售	待调仓	调仓中	待发货	FBA在途	不可售	FBA入库中	计划入库	总计	排名	评分	评论数	Sessions	PV	Buybox	CVR	展示	点击	CTR	广告花费	CPC	ACOS	ACoAS	ASoAS	广告订单量	广告订单占比	广告销售额	广告CVR
    /** 销售额 */
    @Excel(name = "销售额")
    private Float col11;

    /** 退款量 */
    @Excel(name = "退款量")
    private Long col12;

    /** 退款率 */
    @Excel(name = "退款率")
    private BigDecimal col13;

    /** FBA可售 */
    @Excel(name = "FBA可售")
    private Float col14;

    /** 待调仓 */
    @Excel(name = "待调仓")
    private BigDecimal col15;
//调仓中	待发货	FBA在途	不可售	FBA入库中	计划入库	总计	排名	评分	评论数	Sessions	PV	Buybox	CVR	展示	点击	CTR	广告花费	CPC	ACOS	ACoAS	ASoAS	广告订单量	广告订单占比	广告销售额	广告CVR
    /** 调仓中 */
    @Excel(name = "调仓中")
    private Float col16;

    /** 待发货 */
    @Excel(name = "待发货")
    private Float col17;

    /** FBA在途 */
    @Excel(name = "FBA在途")
    private Float col18;

    /** 不可售 */
    @Excel(name = "不可售")
    private Long col19;

    /** FBA入库中 */
    @Excel(name = "FBA入库中")
    private BigDecimal col20;
//计划入库	总计	排名	评分	评论数
   /** 计划入库 */
    @Excel(name = "计划入库")
    private BigDecimal col21;

    /** 总计 */
    @Excel(name = "总计")
    private Long col22;

    /** 排名 */
    @Excel(name = "排名")
    private Long col23;

    /** 评分 */
    @Excel(name = "评分")
    private Long col24;

    /** 评论数 */
    @Excel(name = "评论数")
    private Integer col25;
    //Sessions	PV	Buybox	CVR	展示
    /** Sessions */
    @Excel(name = "Sessions")
    private BigDecimal col26;

    /** PV */
    @Excel(name = "PV")
    private Long col27;

    /** Buybox */
    @Excel(name = "Buybox")
    private Long col28;

    /** CVR */
    @Excel(name = "CVR")
    private Long col29;

    /** 展示 */
    @Excel(name = "展示")
    private Integer col30;

    //点击	CTR	广告花费	CPC	ACOS
    /** 点击 */
    @Excel(name = "点击")
    private Integer col31;
    /** CTR */
    @Excel(name = "CTR")
    private Integer col32;
    /** 广告花费 */
    @Excel(name = "广告花费")
    private Integer col33;
    /** CPC */
    @Excel(name = "CPC")
    private Integer col34;
    /** ACOS */
    @Excel(name = "ACOS")
    private Integer col35;

    //ACoAS	ASoAS	广告订单量	广告订单占比	广告销售额	广告CVR
    /** ACoAS */
    @Excel(name = "ACoAS")
    private Integer col36;
    /** ASoAS */
    @Excel(name = "ASoAS")
    private Integer col37;
    /** 广告订单量 */
    @Excel(name = "广告订单量")
    private Integer col38;
    /** 广告订单占比 */
    @Excel(name = "广告订单占比")
    private Integer col39;
    /** 广告销售额 */
    @Excel(name = "广告销售额")
    private Integer col40;
    /** 广告CVR */
    @Excel(name = "广告CVR")
    private Integer col42;
}
