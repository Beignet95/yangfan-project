package com.ruoyi.project.compdata.historyoperate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 历史操作记录对象 sys_history_operate
 * 
 * @author Beignet
 * @date 2021-02-26
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HistoryOperate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 重复编码 */
    @Excel(name = "重复编码")
    private String repeatCode;



}
