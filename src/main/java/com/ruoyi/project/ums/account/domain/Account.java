package com.ruoyi.project.ums.account.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 账号对象 ums_account
 * 
 * @author Beignet
 * @date 2021-03-20
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Account extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 账号 */
    @Excel(name = "账号")
    private String account;

    /** 描述 */
    @Excel(name = "描述")
    private String description;



}
