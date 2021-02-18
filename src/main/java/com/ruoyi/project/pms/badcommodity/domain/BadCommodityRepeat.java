package com.ruoyi.project.pms.badcommodity.domain;

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
 * 不良记录重复数据对象 pms_bad_commodity_repeat
 * 
 * @author Beignet
 * @date 2021-02-18
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BadCommodityRepeat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 重复id */
    @Excel(name = "重复id")
    private String repeatId;

    /** 账户站点 */
    @Excel(name = "账户站点")
    private String storeCode;

    /** 销售负责人 */
    @Excel(name = "销售负责人")
    private String principal;

    /** 订单日期 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "订单日期", width = 30, dateFormat = "yyyy/MM/dd")
    private Date orderDate;

    /** 处理日期 */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "处理日期", width = 30, dateFormat = "yyyy/MM/dd")
    private Date dealDate;

    /** 订单号或无订单号 */
    @Excel(name = "订单号或无订单号")
    private String orderId;

    /** 型号 */
    @Excel(name = "型号")
    private String type;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String sku;

    /** 不良总数            （个数） */
    @Excel(name = "不良总数            ", readConverterExp = "个=数")
    private Long badNum;

    /** 补发数量            （个数） */
    @Excel(name = "补发数量            ", readConverterExp = "个=数")
    private Long reissueNum;

    /** 退款数量            （个数） */
    @Excel(name = "退款数量            ", readConverterExp = "个=数")
    private Long refundNum;

    /** 追溯码 */
    @Excel(name = "追溯码")
    private String tracebackCode;

    /** 不良原因 */
    @Excel(name = "不良原因")
    private String badReason;

    /** 备注（具体客诉原因） */
    @Excel(name = "备注", readConverterExp = "具=体客诉原因")
    private String remarks;

    /** 产品不良解决方式 */
    @Excel(name = "产品不良解决方式")
    private String solution;

    /** 不良来源 */
    @Excel(name = "不良来源")
    private String badSource;

    /** FBA SKU */
    @Excel(name = "FBA SKU")
    private String fbaSku;

    /** return-date */
    @Excel(name = "return-date")
    private String returnDate;

    /** 型号2 */
    @Excel(name = "型号2")
    private String type2;

    /** 每套个数 */
    @Excel(name = "每套个数")
    private Long eachsetNum;

    /** 不良总个数 */
    @Excel(name = "不良总个数")
    private Long badNum2;

    /** 补发或退款            易仓SKU */
    @Excel(name = "补发或退款            易仓SKU")
    private String ycSku;

    /** 补发数量            （个数） */
    @Excel(name = "补发数量            ", readConverterExp = "个=数")
    private Long reissue2;

    /** 补发跟踪号 */
    @Excel(name = "补发跟踪号")
    private Long reissueTrackingId;

    /** 补发是否送达 */
    @Excel(name = "补发是否送达")
    private Integer isdelivered;

    /** 退款数量            （个数） */
    @Excel(name = "退款数量            ", readConverterExp = "个=数")
    private Long refundNum2;

    /** 退款金额            （默认原币） */
    @Excel(name = "退款金额            ", readConverterExp = "默=认原币")
    private BigDecimal refundAmount;

    /** 邮件主动            跟进次数 */
    @Excel(name = "邮件主动            跟进次数")
    private Long mailFollowTimes;

    /** 最后跟进时间            时间格式：yyyy/MM/dd */
    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "最后跟进时间            时间格式：yyyy/MM/dd", width = 30, dateFormat = "yyyy/MM/dd")
    private Date lastFollowTime;

    /** 是否完成 */
    @Excel(name = "是否完成")
    private String isdone;

    /** Feedback            Case ID */
    @Excel(name = "Feedback            Case ID")
    private String feedbackCaseId;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks2;

    /** 不良图片一 */
    @Excel(name = "不良图片一")
    private String picUrl1;

    /** 不良图片二 */
    @Excel(name = "不良图片二")
    private String picUrl2;

    /** 不良图片三 */
    @Excel(name = "不良图片三")
    private String picUrl3;



}
