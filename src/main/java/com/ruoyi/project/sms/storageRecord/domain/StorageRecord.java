package com.ruoyi.project.sms.storageRecord.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 仓储记录对象 sms_storage_record
 * 
 * @author Beignet
 * @date 2021-03-06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StorageRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** month-of-charge */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "month-of-charge", width = 30, dateFormat = "yyyy-MM-dd")
    @CsvBindByPosition(position = 17)
    private Date month;

    /** account */
    @Excel(name = "account")
    private String account;

    /** country-code */
    @Excel(name = "country-code")
    @CsvBindByPosition(position = 4)
    private String countryCode;

    /** asin */
    @Excel(name = "asin")
    @CsvBindByPosition(position = 0)
    private String asin;

    /** item-volume */
    @Excel(name = "item-volume")
    @CsvBindByPosition(position = 11)
    private BigDecimal itemVolume;

    /** currency */
    @Excel(name = "currency")
    @CsvBindByPosition(position = 19)
    private String currency;

    /** estimated-monthly-storage-fee */
    @Excel(name = "estimated-monthly-storage-fee")
    @CsvBindByPosition(position = 20)
    private BigDecimal storageFee;

    /** 标准SKU */
    //@Excel(name = "标准SKU")
    private String standardSku;

    /** 型号 */
    //@Excel(name = "型号")
    private String spu;

    /** 负责人 */
    //@Excel(name = "负责人")
    private String principal;

}
