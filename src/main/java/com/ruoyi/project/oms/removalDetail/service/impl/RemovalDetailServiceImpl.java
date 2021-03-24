package com.ruoyi.project.oms.removalDetail.service.impl;

import java.util.*;

import com.ruoyi.project.pms.asinMsku.domain.AsinMsku;
import com.ruoyi.project.pms.asinMsku.service.IAsinMskuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.oms.removalDetail.mapper.RemovalDetailMapper;
import com.ruoyi.project.oms.removalDetail.domain.RemovalDetail;
import com.ruoyi.project.oms.removalDetail.service.IRemovalDetailService;
import com.ruoyi.common.utils.text.Convert;

import javax.swing.*;

/**
 * 移除明细Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-08
 */
@Service
public class RemovalDetailServiceImpl implements IRemovalDetailService 
{
    private static final Logger log = LoggerFactory.getLogger(RemovalDetail.class);

    @Autowired
    private RemovalDetailMapper removalDetailMapper;

    /**
     * 查询移除明细
     * 
     * @param id 移除明细ID
     * @return 移除明细
     */
    @Override
    public RemovalDetail selectRemovalDetailById(Long id)
    {
        return removalDetailMapper.selectRemovalDetailById(id);
    }

    /**
     * 查询移除明细列表
     * 
     * @param removalDetail 移除明细
     * @return 移除明细
     */
    @Override
    public List<RemovalDetail> selectRemovalDetailList(RemovalDetail removalDetail)
    {
        return removalDetailMapper.selectRemovalDetailList(removalDetail);
    }

    /**
     * 新增移除明细
     * 
     * @param removalDetail 移除明细
     * @return 结果
     */
    @Override
    public int insertRemovalDetail(RemovalDetail removalDetail)
    {
        removalDetail.setCreateTime(DateUtils.getNowDate());
        return removalDetailMapper.insertRemovalDetail(removalDetail);
    }

    /**
     * 修改移除明细
     * 
     * @param removalDetail 移除明细
     * @return 结果
     */
    @Override
    public int updateRemovalDetail(RemovalDetail removalDetail)
    {
        removalDetail.setUpdateTime(DateUtils.getNowDate());
        return removalDetailMapper.updateRemovalDetail(removalDetail);
    }

    /**
     * 删除移除明细对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRemovalDetailByIds(String ids)
    {
        return removalDetailMapper.deleteRemovalDetailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除移除明细信息
     * 
     * @param id 移除明细ID
     * @return 结果
     */
    @Override
    public int deleteRemovalDetailById(Long id)
    {
        return removalDetailMapper.deleteRemovalDetailById(id);
    }

    /**
     * 导入移除明细
     *
     * @param removalDetailList 移除明细List数据
     * @return 导入结果
     */
    @Override
    public String importRemovalDetail(List<RemovalDetail> removalDetailList, boolean isUpdateSupport) {
        if (StringUtils.isNull(removalDetailList) || removalDetailList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        checkMskuAsin(removalDetailList);

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (RemovalDetail removalDetail : removalDetailList)
        {
            try
            {
                if(removalDetail.getRemovalFee()==null) continue;//移除费为空时，则不作操作
                // 验证数据是否已经
                RemovalDetail domain = removalDetailMapper.selectRemovalDetailByOnlyCondition(removalDetail);
                if (domain==null)
                {
                    removalDetail.setCreateBy(operName);
                    removalDetail.setCreateTime(new Date());
                    this.insertRemovalDetail(removalDetail);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ removalDetail.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    removalDetail.setUpdateBy(operName);
                    removalDetail.setUpdateTime(new Date());
                    removalDetailMapper.updateRemovalDetailByOnlyCondition(removalDetail);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + removalDetail.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + removalDetail.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + removalDetail.toString()+" 的数据导入失败：";
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
    IAsinMskuService asinMskuService;
    private void checkMskuAsin(List<RemovalDetail> removalDetailList) {
        if(removalDetailList.get(0)==null) throw new BusinessException("报表格式异常！请下载模板进行对照！");
        List<AsinMsku> asinMskuList = asinMskuService.selectAsinMskuList(null);
        Map<String,String> mskuAsinMap =  asinMskuList.stream().collect(Collectors.toMap(AsinMsku::getMsku,AsinMsku::getAsin));
        Set<String> cot = new LinkedHashSet();
        for(RemovalDetail removalDetail : removalDetailList){
            String msku = removalDetail.getSku();
            if(StringUtils.isNotEmpty(msku)&&!mskuAsinMap.containsKey(msku)) cot.add(msku);
        }
        if(cot.size()>0){
            StringBuilder warnMsg = new StringBuilder();
            warnMsg.append("以下MSKU缺少与ASIN的关系！");
            for(String msku:cot){
            warnMsg.append("<br/>"+msku);
        }
        throw new BusinessException(warnMsg.toString());
        }
    }
}
