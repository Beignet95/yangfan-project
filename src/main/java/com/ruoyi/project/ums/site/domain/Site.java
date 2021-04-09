package com.ruoyi.project.ums.site.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 站点对象 ums_site
 * 
 * @author Beignet
 * @date 2021-03-20
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Site extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 账号id */
    @Excel(name = "账号id")
    private Long accountId;

    /** 站点 */
    @Excel(name = "站点")
    private String site;

    /** 语言 */
    @Excel(name = "语言")
    private String language;

    /** 时间格式 */
    @Excel(name = "时间格式")
    private String timeFormat;

    /** 描述 */
    @Excel(name = "描述")
    private String descript;


    /** 负责人 */
    private String principal;



}
