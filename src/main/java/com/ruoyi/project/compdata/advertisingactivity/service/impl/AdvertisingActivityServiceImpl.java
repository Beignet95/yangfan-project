package com.ruoyi.project.compdata.advertisingactivity.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.pms.advertisingFee.vo.CampaignProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import com.ruoyi.project.pms.skuCoupon.domain.SkuCoupon;
import com.ruoyi.project.system.user.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.compdata.advertisingactivity.mapper.AdvertisingActivityMapper;
import com.ruoyi.project.compdata.advertisingactivity.domain.AdvertisingActivity;
import com.ruoyi.project.compdata.advertisingactivity.service.IAdvertisingActivityService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 广告活动映射Service业务层处理
 * 
 * @author Beignet
 * @date 2021-01-26
 */
@Service
public class AdvertisingActivityServiceImpl implements IAdvertisingActivityService 
{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AdvertisingActivityMapper advertisingActivityMapper;

    /**
     * 查询广告活动映射
     * 
     * @param id 广告活动映射ID
     * @return 广告活动映射
     */
    @Override
    public AdvertisingActivity selectAdvertisingActivityById(Long id)
    {
        return advertisingActivityMapper.selectAdvertisingActivityById(id);
    }

    /**
     * 查询广告活动映射列表
     * 
     * @param advertisingActivity 广告活动映射
     * @return 广告活动映射
     */
    @Override
    public List<AdvertisingActivity> selectAdvertisingActivityList(AdvertisingActivity advertisingActivity)
    {
        return advertisingActivityMapper.selectAdvertisingActivityList(advertisingActivity);
    }

    /**
     * 新增广告活动映射
     * 
     * @param advertisingActivity 广告活动映射
     * @return 结果
     */
    @Override
    public int insertAdvertisingActivity(AdvertisingActivity advertisingActivity)
    {
        ProductinfoRelation pr = new ProductinfoRelation(null,null,null,null,advertisingActivity.getSku());
        if(!productinfoRelationService.checkProductinfoRelation(pr))
            throw new BusinessException("产品信息中没有标准SKU为 "+advertisingActivity.getSku()+" 的数据！请完善产品信息！");
        return advertisingActivityMapper.insertAdvertisingActivity(advertisingActivity);
    }

    /**
     * 修改广告活动映射
     * 
     * @param advertisingActivity 广告活动映射
     * @return 结果
     */
    @Override
    public int updateAdvertisingActivity(AdvertisingActivity advertisingActivity)
    {
        ProductinfoRelation pr = new ProductinfoRelation(null,null,null,null,advertisingActivity.getSku());
        if(!productinfoRelationService.checkProductinfoRelation(pr))
            throw new BusinessException(" 产品信息中没有标准SKU为"+advertisingActivity.getSku()+" 的数据！请完善产品信息！");
        return advertisingActivityMapper.updateAdvertisingActivity(advertisingActivity);
    }

    /**
     * 删除广告活动映射对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAdvertisingActivityByIds(String ids)
    {
        return advertisingActivityMapper.deleteAdvertisingActivityByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除广告活动映射信息
     * 
     * @param id 广告活动映射ID
     * @return 结果
     */
    @Override
    public int deleteAdvertisingActivityById(Long id)
    {
        return advertisingActivityMapper.deleteAdvertisingActivityById(id);
    }

    @Override
    public String importAdvertisingActivity(List<AdvertisingActivity> advertisingActivityList, boolean updateSupport) {
        if (StringUtils.isNull(advertisingActivityList) || advertisingActivityList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        checkProductinfoRelation(advertisingActivityList);

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (AdvertisingActivity advertisingActivity : advertisingActivityList)
        {
            try
            {

                /**关键列为null时直接跳过**/
                if(StringUtils.isEmpty(advertisingActivity.getActivity())|| StringUtils.isEmpty(advertisingActivity.getSku())) continue;
                /** ASIN字段超过10的为异常字段**/
                if(advertisingActivity.getAsin()!=null&&advertisingActivity.getAsin().length()>11){
                    failureMsg.append("<br/>" + successNum + "、"+advertisingActivity.getActivity()+" 的ASIN数据过长！");
                    failureNum++;
                    continue;
                }
                // 验证是否存在这个数据
                AdvertisingActivity a = advertisingActivityMapper.selectAdvertisingActivityByOnlyCondition(advertisingActivity);
                if (a==null||StringUtils.isEmpty(advertisingActivity.getActivity()))
                {
                    advertisingActivity.setCreateBy(operName);
                    advertisingActivity.setCreateTime(new Date());
                    this.insertAdvertisingActivity(advertisingActivity);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ advertisingActivity.getActivity()+"的广告活动"+advertisingActivity.getActivity()+" 的数据导入成功");
                }
                else if (updateSupport)
                {
                    advertisingActivity.setUpdateBy(operName);
                    advertisingActivity.setUpdateTime(new Date());
                    advertisingActivityMapper.updateAdvertisingActivityByOnlyCondition(advertisingActivity);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + advertisingActivity.getActivity()+"的广告活动"+advertisingActivity.getActivity()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + advertisingActivity.getActivity()+"的广告活动"+advertisingActivity.getActivity()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + advertisingActivity.getActivity()+"的广告活动"+advertisingActivity.getActivity()+ " 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Autowired
    IProductinfoRelationService productinfoRelationService;
    private void checkProductinfoRelation(List<AdvertisingActivity> advertisingActivityList) {
        if(advertisingActivityList.get(0)==null) throw new BusinessException("报表格式异常！请下载导入模板进行对照！");

        List<ProductinfoRelation> prList = productinfoRelationService.selectProductinfoRelationList(null);
        Map<String, ProductinfoRelation> skuPrMap = prList.stream()
                .collect(Collectors.toMap(ProductinfoRelation::getSku, Function.identity(), (key1, key2) -> key2));
        Set<String> skuCot = new LinkedHashSet();
        for(AdvertisingActivity advertisingActivity:advertisingActivityList){
            String sku = advertisingActivity.getSku();
            if(StringUtils.isNotEmpty(sku)&&!skuPrMap.containsKey(sku)) skuCot.add(sku);
        }
        if(skuCot.size()>0){
            StringBuilder warnMsg = new StringBuilder();
            warnMsg.append("以下SKU缺少产品信息关系！");
            for(String sku:skuCot){
                warnMsg.append("<br/>"+sku);
            }
            throw new BusinessException(warnMsg.toString());
        }
    }

    @Override
    public AdvertisingActivity selectAdvertisingActivityByOnlyCondition(AdvertisingActivity advertisingActivity) {
        return advertisingActivityMapper.selectAdvertisingActivityByOnlyCondition(advertisingActivity);
    }

    @Override
    public List<CampaignProductinfoRelation> selectCampaignProductinfoRelationList(CampaignProductinfoRelation CampaignProductinfoRelation) {
        return advertisingActivityMapper.selectCampaignProductinfoRelationList(CampaignProductinfoRelation);
    }
}
