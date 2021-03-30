package com.ruoyi.project.pms.advertisingFee.mapper;

import java.util.Date;
import java.util.List;

import com.ruoyi.project.oms.transactionRecord.vo.SkuFee;
import com.ruoyi.project.pms.advertisingFee.domain.AdvertisingFee;

/**
 * 广告费费用Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-12
 */
public interface AdvertisingFeeMapper 
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
     * 删除广告费费用
     * 
     * @param id 广告费费用ID
     * @return 结果
     */
    public int deleteAdvertisingFeeById(Long id);

    /**
     * 批量删除广告费费用
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAdvertisingFeeByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param AdvertisingFee ${subTable.functionName}
     * @return 结果
     */
    public int updateAdvertisingFeeByOnlyCondition(AdvertisingFee advertisingFee);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param AdvertisingFee ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public AdvertisingFee selectAdvertisingFeeByOnlyCondition(AdvertisingFee advertisingFee);

    public List<AdvertisingFee> selectSkuAdvertisingFeeList(AdvertisingFee AdvertisingFee);

    /**
     * 更具月份和站点删除数据
     * @param month
     * @param site
     * @return
     */
    int deleteAdvertisingFeeByTypeAndSite(Date month, String site);
}
