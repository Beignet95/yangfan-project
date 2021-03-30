package com.ruoyi.project.pms.advertisingFee.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;
import com.ruoyi.project.pms.advertisingFee.domain.AdvertisingFee;

/**
 * 广告费费用Service接口
 * 
 * @author Beignet
 * @date 2021-03-12
 */
public interface IAdvertisingFeeService 
{
    /**
     * 查询广告费费用
     * 
     * @param id 广告费费用ID
     * @return 广告费费用
     */
    public AdvertisingFee selectAdvertisingFeeById(Long id);

    /**
     * 查询广告费费用列表
     * 
     * @param advertisingFee 广告费费用
     * @return 广告费费用集合
     */
    public List<AdvertisingFee> selectAdvertisingFeeList(AdvertisingFee advertisingFee);

    /**
     * 新增广告费费用
     * 
     * @param advertisingFee 广告费费用
     * @return 结果
     */
    public int insertAdvertisingFee(AdvertisingFee advertisingFee);

    /**
     * 修改广告费费用
     * 
     * @param advertisingFee 广告费费用
     * @return 结果
     */
    public int updateAdvertisingFee(AdvertisingFee advertisingFee);

    /**
     * 批量删除广告费费用
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAdvertisingFeeByIds(String ids);

    /**
     * 删除广告费费用信息
     * 
     * @param id 广告费费用ID
     * @return 结果
     */
    public int deleteAdvertisingFeeById(Long id);

    /**
     * 导入广告费费用
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importAdvertisingFee(List<AdvertisingFee> advertisingFeeList, boolean isUpdateSupport, Date month,String site);


    public List<AdvertisingFee> selectSkuAdvertisingFeeList(AdvertisingFee advertisingFee);

    /**
     * 解锁并删除数据
     * @param month
     * @param site
     * @return
     */
    int unlockData(Date month, String site);

}
