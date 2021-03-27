package com.ruoyi.project.oms.transactionRecord.vo;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;

/**
 * 交易汇总实体
 *
 * @author Beignet
 * @date 2021-01-14
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FinanceVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 月份 */
    //@Excel(name = "年/月",dateFormat = "yyyy年MM月",dateType = "dayNum")
    private String month;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 站点 */
    @Excel(name = "站点")
    private String site;

    /** 负责人 */
    @Excel(name = "负责人")
    private String principal;

    /** 型号 */
    @Excel(name = "型号")
    private String type;

    /** 易仓系统标准SKU */
    @Excel(name = "易仓系统标准SKU")
    private String sku;

    /** 数量 */
    @Excel(name = "数量",cellType = Excel.ColumnType.NUMERIC)
    private Long quantity;


    /** 销售收入(含自配送） */
    @Excel(name = "销售收入(含自配送）",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal salesRevenue;

    //1.自配送销售
    /** 自配送销售 */
    @Excel(name = "自配送销售",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal productSalesOfSeller;

    //2.自配送销售退款
    /** 自配送销售退款 */
    @Excel(name = "自配送销售退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal productSaleRefundsOfSeller;

    /** FBA销售 */
    @Excel(name = "FBA销售",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fbaProductSales;

    /** FBA销售退款 */
    @Excel(name = "FBA销售退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fbaProductSalesRefunds;

    /** 赔偿金 */
    @Excel(name = "赔偿金",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal compensation;

    //5.FBA inventory credit
    /** FBA库存信贷 */
    @Excel(name = "FBA库存信贷",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fbaInventoryCredit;

    //6.FBA liquidation proceeds
    //7. FBA Liquidations proceeds adjustments
    // FBA清算继续进行 6和7合并在一起
    /** FBA清算继续进行 */
    @Excel(name = "FBA清算继续进行",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fbaLpAndFbaLpa;

    /** 尾程运费信用 */
    @Excel(name = "尾程运费信用",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal finalFreight;

    //8. Shipping credits
    /** 尾程运费信用 */
    @Excel(name = "尾程运费信用",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal shippingCredits;

    //9. Shipping credit refunds
    /** 尾程运费信用退款 */
    @Excel(name = "尾程运费信用退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal shippingCreditRefunds;

    /** 包装费 */
    @Excel(name = "包装费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal packagingFee;

    //10. Gift wrap credits
    /** 包装费 */
    @Excel(name = "包装费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal giftWrapCredits;

    //11. Gift wrap credit refunds
    /** 包装费退款 */
    @Excel(name = "包装费退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal giftWrapCreditRefunds;

    /** 促销费 */
    @Excel(name = "促销费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal promotionFee;

    //12. Promotional rebates
    /** 促销费 */
    @Excel(name = "促销费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal promotionalRebates;

    //13. Promotional rebate refunds
    /** 促销费退款 */
    @Excel(name = "促销费退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal promotionalRebateRefunds;

    /** 平台退款 */
    @Excel(name = "平台退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal platformRefund;

    //14. A-to-z Guarantee claims
    /** A-to-z保证索赔 */
    @Excel(name = "A-to-z保证索赔",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal guaranteeClaims;

    //15. Chargebacks
    /** 退款 */
    @Excel(name = "退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal chargebacks;

    //16. Amazon Shipping Reimbursement
    /** 尾程运费退回 */
    @Excel(name = "尾程运费退回",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal shippingReimbursement;

    //17. SAFE-T reimbursement
    /** 其他 */
    @Excel(name = "其他",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal reimbursement;

    /** 混合增值税 */
    @Excel(name = "混合增值税",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal mixedVat2;

    /** 销售佣金(含自配送） */
    @Excel(name = "销售佣金(含自配送）",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal selfdeliveryCommission;

    //18. Seller fulfilled selling fees
    /** 自配送销售佣金 */
    @Excel(name = "自配送销售佣金",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal sellerFulfilledSellingFees;

    //自配送销售退款佣金
    /** 自配送销售退款佣金） */
    @Excel(name = "自配送销售退款佣金",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal sellerFulfilledSellingFeeRefunds;


    //19. FBA selling fees
    /** FBA销售佣金） */
    @Excel(name = "FBA销售佣金",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fbaSellingFees;

    //20. Selling fee refunds
    /** FBA销售退款佣金） */
    @Excel(name = "FBA销售退款佣金",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal sellingFeeRefunds;

    /** 尾程运费退回（N6+T6+AG6,占销售比例14.5%） */
    @Excel(name = "尾程运费退回（N6+T6+AG6,占销售比例14.5%）",defaultValue = "0",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal finalFreightReturn;

    //21. FBA transaction fees
    /** 尾程运费 */
    @Excel(name = "尾程运费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fbaTransactionFees;

    //22. FBA transaction fee refunds
    /** 尾程运费退回 */
    @Excel(name = "尾程运费退回",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fbaTransactionFeeRefunds;

    /** 其他交易费 */
    @Excel(name = "其他交易费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal otherTransactionFee;

    //23. Other transaction fees
    /** 其他交易费 */
    @Excel(name = "其他交易费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal othertransactionFees;

    //24. Other transaction fee refunds
    /** 其他交易费退回 */
    @Excel(name = "其他交易费退回",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal otherTransactionFeeRefunds;

    //25. FBA inventory and inbound services fees
    /** 仓储费（0.79%） */
    @Excel(name = "仓储费（0.79%）",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal fbaInventoryAndInboundServicesFees;

    /** 卡车服务费 */
    @Excel(name = "卡车服务费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal truckServiceFee;

    /** 仓储费 */
    @Excel(name = "仓储费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal storageFee;

    /** 长期仓储费 */
    @Excel(name = "长期仓储费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal longtimeStorageFee;

    /** 退货移除费 */
    @Excel(name = "退货移除费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal returnRemovalFee;

    /** 破损库存销毁手续费 */
    @Excel(name = "破损库存销毁手续费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal disposalRemovalFee;

    /** 运输标签费 */
    @Excel(name = "运输标签费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal shippingLabelFee;

    //26. Shipping label purchases
    /** 运输标签费 */
    @Excel(name = "运输标签费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal ShippingLabelPurchases;

    //27. Shipping label refunds
    /** 运输标签费退款 */
    @Excel(name = "运输标签费退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal ShippingLabelRefunds;

    //28. Carrier shipping label adjustments
    /** 承运人运输标签调整 */
    @Excel(name = "承运人运输标签调整",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal carrierShippingLabelAdjustments;

    //29. Service fees
    /** 平台服务费 */
    @Excel(name = "平台服务费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal serviceFees;

    //店租-(Service Fee-Subscription 按照表格中标准sku数目摊分）
    /** 店租 */
    @Excel(name = "店租",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal shopRent;

    /** BD */
    @Excel(name = "BD",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal bdDealFee;

    /** LD */
    @Excel(name = "LD",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal ldDealFee;

    /** 早期 */
    @Excel(name = "早期",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal earlyFee;

    /** 手续费 */
    @Excel(name = "手续费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal couponFee;

    /** 其他服务费 */
    @Excel(name = "其他服务费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal otherServiceFee;



    //30. Refund administration fees
    /** 平台退款服务费 */
    @Excel(name = "平台退款服务费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal refundAdministrationFees;

    //31. Adjustments
    /** 平台服务调整费 */
    @Excel(name = "平台服务调整费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal Adjustments;

    /** 广告费（3.32%） */
    @Excel(name = "广告费（3.32%）",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal advertisingFee;

    //32. Cost of Advertising
    /** 广告费 */
    @Excel(name = "广告费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal costOfAdvertising;

    //33. Refund for Advertiser
    /** 广告费退款 */
    @Excel(name = "广告费退款",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal refundForAdvertiser;

    /** 混合增值税 */
    @Excel(name = "混合增值税",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal mixedVat;

    //34. Liquidations brokerage fee
    /** 清算经纪费 */
    @Excel(name = "清算经纪费",cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal liquidationsBrokerageFee;

    public FinanceVo(TransactionRecord record) {
        super();
        this.account = record.getAccount();
        this.site = record.getSite();
        this.principal = record.getPrincipal();
        this.type = record.getSpu();
        this.sku = record.getStandardSku();
    }

//    /** FBA销售佣金 */
//    @Excel(name = "FBA销售佣金")
//    private BigDecimal fbaSalesCommission;
//
//    /** 仓储费 */
//    @Excel(name = "仓储费（0.79%）")
//    private BigDecimal storageFee;
//
//    /** 平台服务费 */
//    @Excel(name = "平台服务费")
//    private BigDecimal platformServiceFee;
//
//    /** 平台退款服务费 */
//    @Excel(name = "平台退款服务费")
//    private BigDecimal platformRefundServiceFee;
//
//    /** 平台服务调整费 */
//    @Excel(name = "平台服务调整费")
//    private BigDecimal platformServiceAdjustmentFee;
//
//    /** 服务商费用 */
//    @Excel(name = "服务商费用")
//    private BigDecimal serviceProviderFee;
//
//    /** 财务报表毛利 */
//    @Excel(name = "财务报表毛利")
//    private BigDecimal grossProfit;
//
//    /** Asinking数量 */
//    @Excel(name = "数量(Asinking产品表现查看销售数量（数量相差50套或者3%）")
//    private Long asinkingNum;
//
//    /** 易仓出货量 */
//    @Excel(name = "易仓出货量")
//    private Long ycDeliveryNum;
//
//    /** 单价 */
//    @Excel(name = "单价")
//    private BigDecimal price;
//
//    /** 17, 18 混合增值税 */
//    @Excel(name = "17, 18 混合增值税")
//    private BigDecimal mixedVat;
//
//    /** 其他 */
//    @Excel(name = "其他")
//    private BigDecimal otherFee;
//
//    /** 34 35 混合增值税 */
//    @Excel(name = "34 35 混合增值税")
//    private BigDecimal mixedVat2;
//
//    /** 清算经纪费 */
//    @Excel(name = "清算经纪费")
//    private BigDecimal clearBrokerFee;
//
//    /** 尾程运费退回（N6+T6+AG6,占销售比例14.5%） */
//    @Excel(name = "尾程运费退回")
//    private BigDecimal finalFreightReturn2;
//
//    private Integer isdelete;

}