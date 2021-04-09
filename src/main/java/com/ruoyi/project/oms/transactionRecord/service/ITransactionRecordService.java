package com.ruoyi.project.oms.transactionRecord.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecordImpTempVo;
import com.ruoyi.project.oms.transactionRecord.vo.BLDTransactionRecordVo;
import com.ruoyi.project.oms.transactionRecord.vo.OrderIdSku;

/**
 * 交易数据Service接口
 * 
 * @author Beignet
 * @date 2021-02-23
 */
public interface ITransactionRecordService 
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
     * 批量删除交易数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTransactionRecordByIds(String ids);

    /**
     * 删除交易数据信息
     * 
     * @param id 交易数据ID
     * @return 结果
     */
    public int deleteTransactionRecordById(Long id);

    /**
     * 导入交易数据
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importTransactionRecord(List<TransactionRecordImpTempVo> impTempVos,
                                          boolean isUpdateSupport,Date month, String account, String site,String spare);

    /**
     * 查询交易总数据汇列表
     *
     * @param transactionRecord 交易汇总数据
     * @return 交易总数据集合
     */
    public Map<String,Object> selectTransactionAnaly(TransactionRecord transactionRecord) throws ParseException, IllegalAccessException;

    /**
     * @param removalType 移除类型
     * @return Map<String, BigDecimal>
     *     key 标准SKU
     *     value 移除费
     */
    public Map<String, TransactionRecord> getSkuRemovalFeeMap(TransactionRecord transactionRecord, String removalType);

    /**
     * @param
     * @return Map<String, BigDecimal>
     *     key 标准SKU
     *     value 退款服务费
     */
    public Map<String, TransactionRecord> getSkuRefundServiceFeeMap(TransactionRecord transactionRecord);

    List<BLDTransactionRecordVo> selectBLDTransactionReocordVoList(BLDTransactionRecordVo vo);

    int BLDRecordrelateASIN(Long recordId, String asin);

    String exportOrderIdStrs(TransactionRecord transactionRecord);

    String importOrderSku4FeeAdjustmentOrder(List<OrderIdSku> impTempVos, boolean updateSupport);

    /**
     * 解锁并删除数据
     * @param month
     * @param site
     * @return
     */
    int unlockData(Date month, String site,String spareField);

    /**
     * 更新财务模块所有关联产品信息的记录（主要更新产品型号，负责人）
     */
    int updateProductinfo4AllRecord(Date month, String account, String site, String spareField);
}
