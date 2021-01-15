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
    private BigDecimal[] totalFinalFreightReturns;//尾程运费退回
    private Float[] finalFreightReturnRates;//尾程运费退回占比
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
    private BigDecimal[] totalStorageFees;
    private Float[] storageFeeRates;
    private BigDecimal[] totalShippingLabelFees;
    /**
     * shippingLabelFeeRate totalPlaformServiceFee plaformServiceFeeRate totalPlatformRefundServiceFee
     *     platformRefundServiceFeeRate totalPlatformServiceAdjustmentFee platformServiceAdjustmentFeeRate
     */
    private Float[] shippingLabelFeeRates;
    private BigDecimal[] totalPlaformServiceFees;
    private Float[] plaformServiceFeeRates;
    private BigDecimal[] totalPlatformRefundServiceFees;
    private Float[] platformRefundServiceFeeRates;
    private BigDecimal[] totalPlatformServiceAdjustmentFees;
    private Float[] platformServiceAdjustmentFeeRates;

    /**
     * totalServiceProviderFee serviceProviderFeeRate totalOtherFee otherFeeRate
     *     totalAsinkingNum tatalYcDeliveryNum price
     */
    private BigDecimal[] totalServiceProviderFees;
    private Float[] serviceProviderFeeRates;
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
    private BigDecimal[] totalMixedVat2s;
    private Float[] mixedVat2Rates;
    private BigDecimal[] totalClearBrokerFees;
    private Float[] clearBrokerFeeRates;


}
