package com.ruoyi.project.oms.transactionRecord.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByPosition;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 交易数据对象 oms_transaction_record
 * 
 * @author Beignet
 * @date 2021-02-23
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionRecord3 extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** settlement id */
    @Excel(name = "settlement id")
    @CsvBindByPosition(position = 2)
    private String settlementId;


}
