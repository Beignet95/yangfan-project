package com.ruoyi.project.pms.advertisingFee.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 广告费费用对象 pms_advertising_fee
 * 
 * @author Beignet
 * @date 2021-03-12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingFee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 月份 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "月份", width = 30, dateFormat = "yyyy-MM-dd",type = Excel.Type.EXPORT)
    private Date month;


    /** 站点 */
    @Excel(name = "站点",type = Excel.Type.EXPORT)
    private String site;

    /** Campaign */
    @Excel(name = "Campaign")
    private String campaign;

    /** Charge */
    @Excel(name = "Charge")
    private BigDecimal charge;

    /** 标准SKU */
    //@Excel(name = "标准SKU")
    private String standardSku;

    /** 型号 */
    //@Excel(name = "型号")
    private String spu;

    /** 负责人 */
    //@Excel(name = "负责人")
    private String principal;

    public String toString(){
        return campaign;
    }



}
