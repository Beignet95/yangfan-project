package com.ruoyi.project.compdata.advertisingactivity.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.compdata.finance.domain.Finance;
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
                if(StringUtils.isEmpty(advertisingActivity.getActivity())&&StringUtils.isEmpty(advertisingActivity.getAsin())&&
                        StringUtils.isEmpty(advertisingActivity.getSku())&&StringUtils.isEmpty(advertisingActivity.getCommissioner())){
                    continue;
                }
                // 验证是否存在这个用户
                AdvertisingActivity a = advertisingActivityMapper.selectAdvertisingActivityByOnlyCondition(advertisingActivity);
                if (a==null||StringUtils.isEmpty(advertisingActivity.getActivity()))
                {
                    advertisingActivity.setCreateBy(operName);
                    advertisingActivity.setCreateTime(new Date());
                    this.insertAdvertisingActivity(advertisingActivity);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ advertisingActivity.getCommissioner()+"的广告活动"+advertisingActivity.getActivity()+" 的数据导入成功");
                }
                else if (updateSupport)
                {
                    advertisingActivity.setUpdateBy(operName);
                    advertisingActivity.setUpdateTime(new Date());
                    advertisingActivityMapper.updateAdvertisingActivityByOnlyCondition(advertisingActivity);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + advertisingActivity.getCommissioner()+"的广告活动"+advertisingActivity.getActivity()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + advertisingActivity.getCommissioner()+"的广告活动"+advertisingActivity.getActivity()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + advertisingActivity.getCommissioner()+"的广告活动"+advertisingActivity.getActivity()+ " 的数据导入失败：";
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

    @Override
    public AdvertisingActivity selectAdvertisingActivityByOnlyCondition(AdvertisingActivity advertisingActivity) {
        return advertisingActivityMapper.selectAdvertisingActivityByOnlyCondition(advertisingActivity);
    }
}
