package com.ruoyi.project.compdata.advertisingactivity.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 广告活动映射对象 cpd_advertising_activity
 * 
 * @author Beignet
 * @date 2021-01-26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisingActivity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 广告活动 */
    @Excel(name = "广告活动")
    private String activity;

    /** ASIN */
    @Excel(name = "ASIN")
    private String asin;

    /** 标准SKU */
    @Excel(name = "标准SKU")
    private String sku;

    /** 店铺 */
    private String storeCode;

    /** 广告专员 */
    @Excel(name = "广告专员")
    private String commissioner;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("activity", getActivity())
            .append("asin", getAsin())
            .append("sku", getSku())
            .append("storeCode", getStoreCode())
            .append("commissioner", getCommissioner())
            .toString();
    }
}
