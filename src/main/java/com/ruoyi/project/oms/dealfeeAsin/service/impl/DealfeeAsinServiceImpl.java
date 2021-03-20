package com.ruoyi.project.oms.dealfeeAsin.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.oms.dealfeeAsin.mapper.DealfeeAsinMapper;
import com.ruoyi.project.oms.dealfeeAsin.domain.DealfeeAsin;
import com.ruoyi.project.oms.dealfeeAsin.service.IDealfeeAsinService;
import com.ruoyi.common.utils.text.Convert;

/**
 * Deal Fee与ASIN映射Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-16
 */
@Service
public class DealfeeAsinServiceImpl implements IDealfeeAsinService 
{
    private static final Logger log = LoggerFactory.getLogger(DealfeeAsin.class);

    @Autowired
    private DealfeeAsinMapper dealfeeAsinMapper;

    /**
     * 查询Deal Fee与ASIN映射
     * 
     * @param id Deal Fee与ASIN映射ID
     * @return Deal Fee与ASIN映射
     */
    @Override
    public DealfeeAsin selectDealfeeAsinById(Long id)
    {
        return dealfeeAsinMapper.selectDealfeeAsinById(id);
    }

    /**
     * 查询Deal Fee与ASIN映射列表
     * 
     * @param dealfeeAsin Deal Fee与ASIN映射
     * @return Deal Fee与ASIN映射
     */
    @Override
    public List<DealfeeAsin> selectDealfeeAsinList(DealfeeAsin dealfeeAsin)
    {
        return dealfeeAsinMapper.selectDealfeeAsinList(dealfeeAsin);
    }

    /**
     * 新增Deal Fee与ASIN映射
     * 
     * @param dealfeeAsin Deal Fee与ASIN映射
     * @return 结果
     */
    @Override
    public int insertDealfeeAsin(DealfeeAsin dealfeeAsin)
    {
        dealfeeAsin.setCreateTime(DateUtils.getNowDate());
        return dealfeeAsinMapper.insertDealfeeAsin(dealfeeAsin);
    }

    /**
     * 修改Deal Fee与ASIN映射
     * 
     * @param dealfeeAsin Deal Fee与ASIN映射
     * @return 结果
     */
    @Override
    public int updateDealfeeAsin(DealfeeAsin dealfeeAsin)
    {
        dealfeeAsin.setUpdateTime(DateUtils.getNowDate());
        return dealfeeAsinMapper.updateDealfeeAsin(dealfeeAsin);
    }

    /**
     * 删除Deal Fee与ASIN映射对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDealfeeAsinByIds(String ids)
    {
        return dealfeeAsinMapper.deleteDealfeeAsinByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Deal Fee与ASIN映射信息
     * 
     * @param id Deal Fee与ASIN映射ID
     * @return 结果
     */
    @Override
    public int deleteDealfeeAsinById(Long id)
    {
        return dealfeeAsinMapper.deleteDealfeeAsinById(id);
    }

    /**
     * 导入Deal Fee与ASIN映射
     *
     * @param dealfeeAsinList Deal Fee与ASIN映射List数据
     * @return 导入结果
     */
    @Override
    public String importDealfeeAsin(List<DealfeeAsin> dealfeeAsinList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(dealfeeAsinList) || dealfeeAsinList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (DealfeeAsin dealfeeAsin : dealfeeAsinList)
        {
            try
            {
                // 验证数据是否已经
                DealfeeAsin domain = dealfeeAsinMapper.selectDealfeeAsinByOnlyCondition(dealfeeAsin);
                if (domain==null)
                {
                    dealfeeAsin.setCreateBy(operName);
                    dealfeeAsin.setCreateTime(new Date());
                    this.insertDealfeeAsin(dealfeeAsin);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ dealfeeAsin.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    dealfeeAsin.setUpdateBy(operName);
                    dealfeeAsin.setUpdateTime(new Date());
                    dealfeeAsinMapper.updateDealfeeAsinByOnlyCondition(dealfeeAsin);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + dealfeeAsin.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + dealfeeAsin.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + dealfeeAsin.toString()+" 的数据导入失败：";
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
    public DealfeeAsin selectDealfeeAsinByRecordId(Long recordId) {
        return dealfeeAsinMapper.selectDealfeeAsinByOnlyCondition(new DealfeeAsin(null,recordId,null,null));
    }
}
