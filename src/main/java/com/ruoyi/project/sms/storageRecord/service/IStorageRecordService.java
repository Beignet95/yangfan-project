package com.ruoyi.project.sms.storageRecord.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.project.sms.storageRecord.domain.StorageRecord;
import com.ruoyi.project.sms.storageRecord.vo.StorageRecordImpTempVo;

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
    public String importStorageRecord(List<StorageRecordImpTempVo> storageRecordImpTempVos, boolean isUpdateSupport, String account,String site);

    /**
     *获取一个Map<String, BigDecimal>
     *     String 标准SKU
     *     BigDecimal 对应标准SKU的总仓储费
     *     @param month 月份
     * @return
     */
    public Map<String, BigDecimal> getSkuStorageFeeMapByMonth(Date month);

    /**
     * 获取仓储汇总列表
     *     String 标准SKU
     *     BigDecimal 对应标准SKU的总仓储费
     *     @param month 月份
     * @return
     */
    public List<StorageRecord> getStorageRecordGather(StorageRecord storageRecord);

    /**
     * 解锁并删除仓储记录
     * @param month
     * @param account
     * @return
     */
    int unlockData(Date month, String account,String site);

    /**
     * 获取对应标准sku对应的体积
     * @param storageRecord
     * @return
     */
    List<StorageRecord> selectAsinVolumnList(StorageRecord storageRecord);

    /**
     * 更新最新的产品信息于对应月份对应账号的仓促记录
     * @param month
     * @param site
     * @return
     */
    int updateProductinfo2Record(Date month, String account,String areaCode);
}
