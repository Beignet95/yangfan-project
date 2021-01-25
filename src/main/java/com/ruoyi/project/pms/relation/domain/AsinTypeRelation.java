package com.ruoyi.project.pms.relation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * ASIN与型号关联对象 pms_asin_type_relation
 * 
 * @author Beignet
 * @date 2021-01-22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AsinTypeRelation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 型号 */
    @Excel(name = "型号")
    private String type;

    /** ASIN */
    @Excel(name = "ASIN")
    private String asin;

    /** 删除标记（1为删除,2为未删除） */
    @Excel(name = "删除标记", readConverterExp = "1=为删除,2为未删除")
    private Integer isdelete;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("asin", getAsin())
            .append("isdelete", getIsdelete())
            .toString();
    }
}
