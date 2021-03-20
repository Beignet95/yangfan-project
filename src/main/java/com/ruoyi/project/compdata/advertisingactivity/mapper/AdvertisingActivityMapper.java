package com.ruoyi.project.compdata.advertisingactivity.mapper;

import java.util.List;
import com.ruoyi.project.compdata.advertisingactivity.domain.AdvertisingActivity;
import com.ruoyi.project.pms.advertisingFee.vo.CampaignProductinfoRelation;

/**
 * 广告活动映射Mapper接口
 * 
 * @author Beignet
 * @date 2021-01-26
 */
public interface AdvertisingActivityMapper 
{
    /**
     * 查询广告活动映射
     * 
     * @param id 广告活动映射ID
     * @return 广告活动映射
     */
    public AdvertisingActivity selectAdvertisingActivityById(Long id);

    /**
     * 查询广告活动映射列表
     * 
     * @param advertisingActivity 广告活动映射
     * @return 广告活动映射集合
     */
    public List<AdvertisingActivity> selectAdvertisingActivityList(AdvertisingActivity advertisingActivity);

    /**
     * 新增广告活动映射
     * 
     * @param advertisingActivity 广告活动映射
     * @return 结果
     */
    public int insertAdvertisingActivity(AdvertisingActivity advertisingActivity);

    /**
     * 修改广告活动映射
     * 
     * @param advertisingActivity 广告活动映射
     * @return 结果
     */
    public int updateAdvertisingActivity(AdvertisingActivity advertisingActivity);

    /**
     * 删除广告活动映射
     * 
     * @param id 广告活动映射ID
     * @return 结果
     */
    public int deleteAdvertisingActivityById(Long id);

    /**
     * 批量删除广告活动映射
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAdvertisingActivityByIds(String[] ids);

    /**
     * OnlyCondition为activity
     * @param advertisingActivity
     * @return
     */
    AdvertisingActivity selectAdvertisingActivityByOnlyCondition(AdvertisingActivity advertisingActivity);

    /**
     * OnlyCondition为activity
     * @param advertisingActivity
     * @return
     */
    int updateAdvertisingActivityByOnlyCondition(AdvertisingActivity advertisingActivity);

    /**
     * 获取广告词对应的Productinfo关系
     * @param skuAdvertisingFee
     * @return
     */

    List<CampaignProductinfoRelation> selectCampaignProductinfoRelationList(CampaignProductinfoRelation campaignProductinfoRelation);
}
