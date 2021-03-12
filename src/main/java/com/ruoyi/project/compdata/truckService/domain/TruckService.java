package com.ruoyi.project.compdata.truckService.domain;

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
 * 卡车费用对象 lms_truck_service
 *
 * @author Beignet
 * @date 2021-03-06
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TruckService extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 卡车记录id */
    @Excel(name = "卡车记录id")
    private Long truckRecordId;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 站点 */
    @Excel(name = "站点")
    private String site;

    /** 月份 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "月份", width = 30, dateFormat = "yyyy-MM-dd")
    private Date month;

    /** Merchant SKU */
    @Excel(name = "Merchant SKU")
    private String msku;

    /** ASIN */
    @Excel(name = "ASIN")
    private String asin;

    /** Shipped */
    @Excel(name = "Shipped")
    private Long shipped;



}