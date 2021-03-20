package com.ruoyi.project.oms.transactionRecord.vo;

import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;
import lombok.Data;

/**
 * TransactionRecord与ASIN关联的Vo实体（用于展示TransactionRecord与ASIN的关联，BD与LD列表页面）
 */
@Data
public class BLDTransactionRecordVo extends TransactionRecord {
    private String asin;
}
