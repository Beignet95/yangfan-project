package com.ruoyi.project.oms.countries.domain;

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
 * 其余四国补发对象 oms_remaining_countries
 * 
 * @author Kwl
 * @date 2021-04-01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RemainingCountries extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 易仓SKU */
    @Excel(name = "易仓SKU")
    private String warehouseSku;

    /** 数量 */
    @Excel(name = "数量")
    private Integer numberSets;

    /** 跟踪号 */
    @Excel(name = "跟踪号")
    private String trackingNumber;

    /** 销售备注 */
    @Excel(name = "销售备注")
    private String customerRemarks;

    /** 登记日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date customerRegistrationDate;

    /** 站点 */
    @Excel(name = "站点")
    private String site;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWarehouseSku() {
        return warehouseSku;
    }

    public void setWarehouseSku(String warehouseSku) {
        this.warehouseSku = warehouseSku;
    }

    public Integer getNumberSets() {
        return numberSets;
    }

    public void setNumberSets(Integer numberSets) {
        this.numberSets = numberSets;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getCustomerRemarks() {
        return customerRemarks;
    }

    public void setCustomerRemarks(String customerRemarks) {
        this.customerRemarks = customerRemarks;
    }

    public Date getCustomerRegistrationDate() {
        return customerRegistrationDate;
    }

    public void setCustomerRegistrationDate(Date customerRegistrationDate) {
        this.customerRegistrationDate = customerRegistrationDate;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
