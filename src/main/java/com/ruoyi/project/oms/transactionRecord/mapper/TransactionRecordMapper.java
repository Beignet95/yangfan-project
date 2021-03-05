package com.ruoyi.project.oms.transactionRecord.mapper;

import java.util.List;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;

/**
 * 交易数据Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-23
 */
public interface TransactionRecordMapper 
{
    /**
     * 查询交易数据
     * 
     * @param id 交易数据ID
     * @return 交易数据
     */
    public TransactionRecord selectTransactionRecordById(Long id);

    /**
     * 查询交易数据列表
     * 
     * @param transactionRecord 交易数据
     * @return 交易数据集合
     */
    public List<TransactionRecord> selectTransactionRecordList(TransactionRecord transactionRecord);

    /**
     * 新增交易数据
     * 
     * @param transactionRecord 交易数据
     * @return 结果
     */
    public int insertTransactionRecord(TransactionRecord transactionRecord);

    /**
     * 修改交易数据
     * 
     * @param transactionRecord 交易数据
     * @return 结果
     */
    public int updateTransactionRecord(TransactionRecord transactionRecord);

    /**
     * 删除交易数据
     * 
     * @param id 交易数据ID
     * @return 结果
     */
    public int deleteTransactionRecordById(Long id);

    /**
     * 批量删除交易数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTransactionRecordByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param TransactionRecord ${subTable.functionName}
     * @return 结果
     */
    public int updateTransactionRecordByOnlyCondition(TransactionRecord transactionRecord);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param TransactionRecord ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public TransactionRecord selectTransactionRecordByOnlyCondition(TransactionRecord transactionRecord);

    public List<TransactionRecord> selectTransactionAnaly(TransactionRecord transactionRecord);
}
