package com.ruoyi.project.compdata.finance.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanceEchartsVo {
    private String[] months;
    private BigDecimal[] totalGrossProfits;//财务报表毛利
    private Float[] advertisingFeeRates;
    private BigDecimal[] totalAdvertisingFees;
    /**
     *  totalSalesRevenue totalCompensation compensationRate totalFinalFreight finalFreightRate
     *             totalPackagingFee packagingRage totalPromotionFee promotionFeeRate totalPlatformRefund
     */
    private BigDecimal[] totalSalesRevenues;
    private BigDecimal[] totalCompensations;//赔偿金
    private Float[] compensationRates;//赔偿金占比
    private BigDecimal[] totalFinalFreights;//尾程运费信用
    private Float[] finalFreightRates;//尾程运费占比
    private BigDecimal[] totalPackagingFees;//包装费
    private Float[] packagingRages;//包装费占比
    private BigDecimal[] totalPromotionFees;//促销费
    private Float[] promotionFeeRates;//促销费占比
    private BigDecimal[] totalPlatformRefunds;//平台退款
    /**
     * platformRefundRate totalFinalFreightReturn finalFreightReturn totalOtherTransactionFee
     *     otherTransactionFeeRate totalSelfdeliveryCommission selfdeliveryCommissionRate
     */
    private Float[] platformRefundRates;//平台退款占比
    private BigDecimal[] totalFinalFreightReturns;//尾程运费退回（N6+T6+AG6,占销售比例14.5%）
    private Float[] finalFreightReturnRates;//尾程运费退回（N6+T6+AG6,占销售比例14.5%）占比
    private BigDecimal[] totalOtherTransactionFees;//其他交易费
    private Float[] otherTransactionFeeRates;//其他交易费占比
    private BigDecimal[] totalSelfdeliveryCommissions;//自配送销售佣金
    private Float[] selfdeliveryCommissionRates;//自配送销售佣金占比
    /**
     * totalFbaSalesCommission fbaSalesCommissionRate
     *             finalFreightReturnRate totalStorageFee storageFeeRate totalShippingLabelFee
     */
    private BigDecimal[] totalFbaSalesCommissions;//FBA销售佣金
    private Float[] fbaSalesCommissionRates;//FBA销售佣金占比
    private BigDecimal[] totalStorageFees;//仓储费
    private Float[] storageFeeRates;//仓储费占比
    private BigDecimal[] totalShippingLabelFees;//运输标签费
    /**
     * shippingLabelFeeRate totalPlaformServiceFee plaformServiceFeeRate totalPlatformRefundServiceFee
     *     platformRefundServiceFeeRate totalPlatformServiceAdjustmentFee platformServiceAdjustmentFeeRate
     */
    private Float[] shippingLabelFeeRates;//运输标签费占比
    private BigDecimal[] totalPlaformServiceFees;//平台服务费
    private Float[] plaformServiceFeeRates;//平台服务费占比
    private BigDecimal[] totalPlatformRefundServiceFees;//平台退款服务费
    private Float[] platformRefundServiceFeeRates;//平台退款服务费占比
    private BigDecimal[] totalPlatformServiceAdjustmentFees;//平台服务调整费
    private Float[] platformServiceAdjustmentFeeRates;//平台服务调整费占比

    /**
     * totalServiceProviderFee serviceProviderFeeRate totalOtherFee otherFeeRate
     *     totalAsinkingNum tatalYcDeliveryNum price
     */
    private BigDecimal[] totalServiceProviderFees;//服务商费用
    private Float[] serviceProviderFeeRates;//服务商费用占比
    private BigDecimal[] totalOtherFees;//其他
    private Float[] otherFeeRates;//其他占比
    private Integer[] totalAsinkingNums;
    private Integer[] tatalYcDeliveryNums;
    private BigDecimal[] prices;//单价
    /**
     * totalMixedVats mixedVatRates totalMixedVat2s mixedVat2Rates totalClearBrokerFees  clearBrokerFeeRates
     */
    private BigDecimal[] totalMixedVats;//17, 18 混合增值税
    private Float[] mixedVatRates;//17, 18 混合增值税占比
    private BigDecimal[] totalMixedVat2s;//34 35 混合增值税
    private Float[] mixedVat2Rates;//34 35 混合增值税占比
    private BigDecimal[] totalClearBrokerFees;//清算经纪费
    private Float[] clearBrokerFeeRates;//清算经纪费占比

    private BigDecimal[] totalFinalFreightReturn2s;//尾程运费退回
    private Float[] finalFreightReturn2Rates;//尾程运费退回占比


}
