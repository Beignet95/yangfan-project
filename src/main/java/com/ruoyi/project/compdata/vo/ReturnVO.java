package com.ruoyi.project.compdata.vo;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnVO {
    /**return-date 退货时间
     order-id 订单id
     sku  销售商品
     asin 销售商品（金蝶系统）
     fnsku
     product-name 商品名称
     quantity 数量
     fulfillment-center-id
     detailed-disposition
     reason 退货原因
     status 状态
     license-plate-number
     customer-comments 客户说明（备注）**/
    @Excel(name = "账号")
    private String store_code;
    @Excel(name = "return-date")
    private String returnDate;
    @Excel(name = "sku")
    private String sku;
    @Excel(name = "asin")
    private String asin;
    @Excel(name = "fnsku")
    private String fnsku;
    @Excel(name = "product-name")
    private String productName;
    @Excel(name = "quantity")
    private String quantity;
    @Excel(name = "fulfillment-center-id")
    private String fulfillmentCenterId;
    @Excel(name = "detailed-disposition")
    private String detailedDisposition;
    @Excel(name = "reason")
    private String reason;
    @Excel(name = "status")
    private String status;
    @Excel(name = "license-plate-number")
    private String licensePlateNumber;
    @Excel(name = "customer-comments")
    private String customerComments;

}
