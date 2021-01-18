package com.ruoyi.project.compdata.finance.domain;

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
 * 财务数据对象 cpd_finance
 *
 * @author Beignet
 * @date 2021-01-14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Finance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 月份 */
    @Excel(name = "年/月",dateFormat = "yyyy年MM月",dateType = "dayNum")
    private String month;

    /** 站点 */
    @Excel(name = "站点")
    private String site;

    /** 负责人 */
    @Excel(name = "负责人")
    private String principal;

    /** 型号 */
    @Excel(name = "型号")
    private String type;

    /** 销售收入(含自配送） */
    @Excel(name = "销售收入(含自配送）")
    private BigDecimal salesRevenue;

    /** 赔偿金 */
    @Excel(name = "赔偿金")
    private BigDecimal compensation;

    /** 尾程运费信用 */
    @Excel(name = "尾程运费信用")
    private BigDecimal finalFreight;

    /** 包装费 */
    @Excel(name = "包装费")
    private BigDecimal packagingFee;

    /** 促销费 */
    @Excel(name = "促销费")
    private BigDecimal promotionFee;

    /** 平台退款 */
    @Excel(name = "平台退款")
    private BigDecimal platformRefund;

    /** 尾程运费退回（N6+T6+AG6,占销售比例14.5%） */
    @Excel(name = "尾程运费退回（N6+T6+AG6,占销售比例14.5%）",defaultValue = "0")
    private BigDecimal finalFreightReturn;

    /** 自配送销售佣金 */
    @Excel(name = "自配送销售佣金",dateType = "dayNum",dateFormat = "yyyy年MM月")
    private BigDecimal selfdeliveryCommission;

    /** FBA销售佣金 */
    @Excel(name = "FBA销售佣金")
    private BigDecimal fbaSalesCommission;

    /** 其他交易费 */
    @Excel(name = "其他交易费")
    private BigDecimal otherTransactionFee;

    /** 仓储费 */
    @Excel(name = "仓储费（0.79%）")
    private BigDecimal storageFee;

    /** 运输标签费 */
    @Excel(name = "运输标签费")
    private BigDecimal shippingLabelFee;

    /** 平台服务费 */
    @Excel(name = "平台服务费")
    private BigDecimal platformServiceFee;

    /** 平台退款服务费 */
    @Excel(name = "平台退款服务费")
    private BigDecimal platformRefundServiceFee;

    /** 平台服务调整费 */
    @Excel(name = "平台服务调整费")
    private BigDecimal platformServiceAdjustmentFee;

    /** 广告费（3.32%） */
    @Excel(name = "广告费（3.32%）")
    private BigDecimal advertisingFee;

    /** 服务商费用 */
    @Excel(name = "服务商费用")
    private BigDecimal serviceProviderFee;

    /** 财务报表毛利 */
    @Excel(name = "财务报表毛利")
    private BigDecimal grossProfit;

    /** Asinking数量 */
    @Excel(name = "数量(Asinking产品表现查看销售数量（数量相差50套或者3%）")
    private Long asinkingNum;

    /** 易仓出货量 */
    @Excel(name = "易仓出货量")
    private Long ycDeliveryNum;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 17, 18 混合增值税 */
    @Excel(name = "17, 18 混合增值税")
    private BigDecimal mixedVat;

    /** 其他 */
    @Excel(name = "其他")
    private BigDecimal otherFee;

    /** 34 35 混合增值税 */
    @Excel(name = "34 35 混合增值税")
    private BigDecimal mixedVat2;

    /** 清算经纪费 */
    @Excel(name = "清算经纪费")
    private BigDecimal clearBrokerFee;

    /** 尾程运费退回（N6+T6+AG6,占销售比例14.5%） */
    @Excel(name = "尾程运费退回")
    private BigDecimal finalFreightReturn2;

    private Integer isdelete;
}