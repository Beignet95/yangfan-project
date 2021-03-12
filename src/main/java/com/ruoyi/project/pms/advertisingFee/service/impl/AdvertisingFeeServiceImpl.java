package com.ruoyi.project.pms.advertisingFee.service.impl;

import java.text.DateFormat;
import java.util.List;

import com.opencsv.CSVIterator;
import com.ruoyi.project.compdata.advertisingactivity.domain.AdvertisingActivity;
import com.ruoyi.project.compdata.advertisingactivity.service.IAdvertisingActivityService;
import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.pms.advertisingFee.mapper.AdvertisingFeeMapper;
import com.ruoyi.project.pms.advertisingFee.domain.AdvertisingFee;
import com.ruoyi.project.pms.advertisingFee.service.IAdvertisingFeeService;
import com.ruoyi.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;

/**
 * 广告费费用Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-12
 */
@Service
public class AdvertisingFeeServiceImpl implements IAdvertisingFeeService 
{
    private static final Logger log = LoggerFactory.getLogger(AdvertisingFee.class);

    private final String HISTORY_OPERARE_PREFIX = "imp:advertisingFee";

    @Autowired
    private IHistoryOperateService historyOperateService;

    @Autowired
    private AdvertisingFeeMapper advertisingFeeMapper;

    /**
     * 查询广告费费用
     * 
     * @param id 广告费费用ID
     * @return 广告费费用
     */
    @Override
    public AdvertisingFee selectAdvertisingFeeById(Long id)
    {
        return advertisingFeeMapper.selectAdvertisingFeeById(id);
    }

    /**
     * 查询广告费费用列表
     * 
     * @param advertisingFee 广告费费用
     * @return 广告费费用
     */
    @Override
    public List<AdvertisingFee> selectAdvertisingFeeList(AdvertisingFee advertisingFee)
    {
        return advertisingFeeMapper.selectAdvertisingFeeList(advertisingFee);
    }

    /**
     * 新增广告费费用
     * 
     * @param advertisingFee 广告费费用
     * @return 结果
     */
    @Override
    public int insertAdvertisingFee(AdvertisingFee advertisingFee)
    {
        advertisingFee.setCreateTime(DateUtils.getNowDate());
        return advertisingFeeMapper.insertAdvertisingFee(advertisingFee);
    }

    /**
     * 修改广告费费用
     * 
     * @param advertisingFee 广告费费用
     * @return 结果
     */
    @Override
    public int updateAdvertisingFee(AdvertisingFee advertisingFee)
    {
        advertisingFee.setUpdateTime(DateUtils.getNowDate());
        return advertisingFeeMapper.updateAdvertisingFee(advertisingFee);
    }

    /**
     * 删除广告费费用对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAdvertisingFeeByIds(String ids)
    {
        return advertisingFeeMapper.deleteAdvertisingFeeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除广告费费用信息
     * 
     * @param id 广告费费用ID
     * @return 结果
     */
    @Override
    public int deleteAdvertisingFeeById(Long id)
    {
        return advertisingFeeMapper.deleteAdvertisingFeeById(id);
    }

    /**
     * 导入广告费费用
     *
     * @param advertisingFeeList 广告费费用List数据
     * @return 导入结果
     */
    @Override
    @Transactional
    public String importAdvertisingFee(List<AdvertisingFee> advertisingFeeList, boolean isUpdateSupport,Date month,String site) {
        String monthStr = DateFormatUtils.format(month, "yyyy-MM");
        String hocode = HISTORY_OPERARE_PREFIX+":"+ site+":"+monthStr;
        if(historyOperateService.checkIsExitHistoryOperate(hocode)){
            throw new BusinessException(monthStr+"的广告数据已被锁定！你可能已经导入过数据");
        }
        if (StringUtils.isNull(advertisingFeeList) || advertisingFeeList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        checkAdvertisingActivity(advertisingFeeList);//校验广告活动数据是否完整，避免分析数据不准确

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (AdvertisingFee advertisingFee : advertisingFeeList)
        {
            try
            {
                // 验证数据是否已经
                advertisingFee.setMonth(month);//先设置好时间，再查询
                advertisingFee.setSite(site);
                if (StringUtils.isEmpty(advertisingFee.getCampaign())||advertisingFee.getCharge()==null) continue;

                    advertisingFee.setCreateBy(operName);
                    advertisingFee.setCreateTime(new Date());
                    this.insertAdvertisingFee(advertisingFee);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ advertisingFee.toString()+" 的数据导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + advertisingFee.toString()+" 的数据导入失败：";
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
            historyOperateService.insertHistoryOperate(new HistoryOperate(null,hocode));
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    @Autowired
    IAdvertisingActivityService advertisingActivityService;

    private void checkAdvertisingActivity(List<AdvertisingFee> advertisingFeeList) {
       List<AdvertisingActivity> activityList = advertisingActivityService.selectAdvertisingActivityList(null);
       Map<String,String> map = activityList.stream().collect(Collectors.toMap(AdvertisingActivity::getActivity,AdvertisingActivity::getSku));

       StringBuilder warnMsg = new StringBuilder();
       int index=1;
       for (AdvertisingFee advertisingFee:advertisingFeeList){
           String campaign = advertisingFee.getCampaign();
           if(StringUtils.isNotEmpty(campaign)&&!map.containsKey(advertisingFee.getCampaign())){
               warnMsg.append(index+", "+advertisingFee.getCampaign()+"  广告词缺少广告活动映射！<br/>");
           }
           index++;
       }
       if(warnMsg.length()>0) throw new BusinessException(warnMsg.toString());
    }
}
