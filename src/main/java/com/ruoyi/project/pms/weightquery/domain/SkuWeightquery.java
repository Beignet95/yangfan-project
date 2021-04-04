package com.ruoyi.project.pms.weightquery.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 补发登记SKU对象 pms_sku_weightquery
 * 
 * @author Beignet
 * @date 2021-04-01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SkuWeightquery extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 产品SKU */
    @Excel(name = "产品SKU")
    private String productSku;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 品类 */
    @Excel(name = "品类")
    private String category;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 包装尺寸-长(cm) */
    @Excel(name = "包装尺寸-长(cm)")
    private BigDecimal packageSizeLong;

    /** 包装尺寸-宽(cm) */
    @Excel(name = "包装尺寸-宽(cm)")
    private BigDecimal packageSizeWidth;

    /** 包装尺寸-高(cm) */
    @Excel(name = "包装尺寸-高(cm)")
    private BigDecimal packageSizeHigh;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPackageSizeLong() {
        return packageSizeLong;
    }

    public void setPackageSizeLong(BigDecimal packageSizeLong) {
        this.packageSizeLong = packageSizeLong;
    }

    public BigDecimal getPackageSizeWidth() {
        return packageSizeWidth;
    }

    public void setPackageSizeWidth(BigDecimal packageSizeWidth) {
        this.packageSizeWidth = packageSizeWidth;
    }

    public BigDecimal getPackageSizeHigh() {
        return packageSizeHigh;
    }

    public void setPackageSizeHigh(BigDecimal packageSizeHigh) {
        this.packageSizeHigh = packageSizeHigh;
    }
}
