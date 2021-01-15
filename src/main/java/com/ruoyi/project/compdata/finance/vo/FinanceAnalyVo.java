package com.ruoyi.project.compdata.finance.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 财务数据分析内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinanceAnalyVo {
    private String month;//增长率
    private BigDecimal totalGrossProfit;//财务报表毛利
    private Float advertisingFeeRate;
    private BigDecimal totalAdvertisingFee;
    /**
     *  totalSalesRevenue totalCompensation compensationRate totalFinalFreight finalFreightRate
     *             totalPackagingFee packagingRage totalPromotionFee promotionFeeRate totalPlatformRefund
     */
    private BigDecimal totalSalesRevenue;
    private BigDecimal totalCompensation;
    private Float compensationRate;
    private BigDecimal totalFinalFreight;
    private Float finalFreightRate;
    private BigDecimal totalPackagingFee;
    private Float packagingRage;
    private BigDecimal totalPromotionFee;
    private Float promotionFeeRate;
    private BigDecimal totalPlatformRefund;
    /**
     * platformRefundRate totalFinalFreightReturn finalFreightReturn totalOtherTransactionFee
     *     otherTransactionFeeRate totalSelfdeliveryCommission selfdeliveryCommissionRate
     */
    private Float platformRefundRate;
    private BigDecimal totalFinalFreightReturn;//
    private BigDecimal finalFreightReturn;
    private BigDecimal totalOtherTransactionFee;
    private Float otherTransactionFeeRate;
    private BigDecimal totalSelfdeliveryCommission;
    private Float selfdeliveryCommissionRate;
    /**
     * totalFbaSalesCommission fbaSalesCommissionRate
     *             finalFreightReturnRate totalStorageFee storageFeeRate totalShippingLabelFee
     */
    private BigDecimal totalFbaSalesCommission;
    private Float fbaSalesCommissionRate;
    private Float finalFreightReturnRate;
    private BigDecimal totalStorageFee;
    private Float storageFeeRate;
    private BigDecimal totalShippingLabelFee;
    /**
     * shippingLabelFeeRate totalPlaformServiceFee plaformServiceFeeRate totalPlatformRefundServiceFee
     *     platformRefundServiceFeeRate totalPlatformServiceAdjustmentFee platformServiceAdjustmentFeeRate
     */
    private Float shippingLabelFeeRate;
    private BigDecimal totalPlaformServiceFee;
    private Float plaformServiceFeeRate;
    private BigDecimal totalPlatformRefundServiceFee;
    private Float platformRefundServiceFeeRate;
    private BigDecimal totalPlatformServiceAdjustmentFee;
    private Float platformServiceAdjustmentFeeRate;

    /**
     * totalServiceProviderFee serviceProviderFeeRate totalOtherFee otherFeeRate
     *     totalAsinkingNum tatalYcDeliveryNum price
     */
    private BigDecimal totalServiceProviderFee;
    private Float serviceProviderFeeRate;
    private BigDecimal totalOtherFee;
    private Float otherFeeRate;
    private Integer totalAsinkingNum;
    private Integer tatalYcDeliveryNum;
    private BigDecimal price;
    /**
     * mixedVat mixedVat2 clearBrokerFee
     */
    private BigDecimal totalMixedVat;
    private Float mixedVatRate;
    private BigDecimal totalMixedVat2;
    private Float mixedVat2Rate;
    private BigDecimal totalClearBrokerFee;
    private Float clearBrokerFeeRate;

}
