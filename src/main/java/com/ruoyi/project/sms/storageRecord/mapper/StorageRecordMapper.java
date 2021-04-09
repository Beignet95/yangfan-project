package com.ruoyi.project.sms.storageRecord.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.project.sms.storageRecord.domain.StorageRecord;
import com.ruoyi.project.sms.storageRecord.vo.SkuStorageFee;

/**
 * 仓储记录Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-06
 */
public interface StorageRecordMapper 
{
    /**
     * 查询仓储记录
     * 
     * @param id 仓储记录ID
     * @return 仓储记录
     */
    public StorageRecord selectStorageRecordById(Long id);

    /**
     * 查询仓储记录列表
     * 
     * @param storageRecord 仓储记录
     * @return 仓储记录集合
     */
    public List<StorageRecord> selectStorageRecordList(StorageRecord storageRecord);

    /**
     * 新增仓储记录
     * 
     * @param storageRecord 仓储记录
     * @return 结果
     */
    public int insertStorageRecord(StorageRecord storageRecord);

    /**
     * 修改仓储记录
     * 
     * @param storageRecord 仓储记录
     * @return 结果
     */
    public int updateStorageRecord(StorageRecord storageRecord);

    /**
     * 删除仓储记录
     * 
     * @param id 仓储记录ID
     * @return 结果
     */
    public int deleteStorageRecordById(Long id);

    /**
     * 批量删除仓储记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStorageRecordByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param StorageRecord ${subTable.functionName}
     * @return 结果
     */
    public int updateStorageRecordByOnlyCondition(StorageRecord storageRecord);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param StorageRecord ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public StorageRecord selectStorageRecordByOnlyCondition(StorageRecord storageRecord);

    /**
     * 查询标准SKU对应的仓储费
     * @return
     */
    public List<SkuStorageFee> selectSkuStorageFeeByMonth(Date time);

    /**
     * 获取仓储汇总数据
     * @param storageRecord
     * @return
     */
    List<StorageRecord> selectStorageRecordGather(StorageRecord storageRecord);

    /**
     * @param month
     * @param Site
     * @return
     */
    int deleteStorageRecordLockUnit(Date month,String account, String countryCode);

    /**
     * 获取standartSku与volumn的List
     * @param storageRecord
     * @return
     */
    List<StorageRecord> selectAsinVolumnList(StorageRecord storageRecord);

    int updateProductinfo2Record(Date month, String account,String areaCode);
}
