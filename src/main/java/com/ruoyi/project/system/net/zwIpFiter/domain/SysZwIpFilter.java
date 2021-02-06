package com.ruoyi.project.system.net.zwIpFiter.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * ZwIpFilter对象 sys_zw_ip_filter
 * 
 * @author Beignet
 * @date 2021-01-30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SysZwIpFilter extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String ip;

    /** 模块 */
    @Excel(name = "模块")
    private String moudle;

    /** 标记 */
    @Excel(name = "标记")
    private Integer mark;



}
