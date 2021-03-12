package com.ruoyi.project.sms.storageRecord.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.project.sms.storageRecord.domain.StorageRecord;

/**
 * 仓储记录Service接口
 * 
 * @author Beignet
 * @date 2021-03-06
 */
public interface IStorageRecordService 
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
     * 批量删除仓储记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteStorageRecordByIds(String ids);

    /**
     * 删除仓储记录信息
     * 
     * @param id 仓储记录ID
     * @return 结果
     */
    public int deleteStorageRecordById(Long id);

    /**
     * 导入仓储记录
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importStorageRecord(List<StorageRecord> storageRecordList, boolean isUpdateSupport,String account);

    /**
     *获取一个Map<String, BigDecimal>
     *     String 标准SKU
     *     BigDecimal 对应标准SKU的总仓储费
     *     @param month 月份
     * @return
     */
    public Map<String, BigDecimal> getSkuStorageFeeMapByMonth(Date month);

}
