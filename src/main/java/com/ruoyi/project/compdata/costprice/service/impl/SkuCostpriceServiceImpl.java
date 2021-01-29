package com.ruoyi.project.compdata.costprice.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.finance.service.impl.FinanceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.compdata.costprice.mapper.SkuCostpriceMapper;
import com.ruoyi.project.compdata.costprice.domain.SkuCostprice;
import com.ruoyi.project.compdata.costprice.service.ISkuCostpriceService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 成本Service业务层处理
 * 
 * @author Beignet
 * @date 2021-01-26
 */
@Service
public class SkuCostpriceServiceImpl implements ISkuCostpriceService
{

    private static final Logger log = LoggerFactory.getLogger(SkuCostpriceServiceImpl.class);
    @Autowired
    private SkuCostpriceMapper skuCostpriceMapper;

    /**
     * 查询成本
     * 
     * @param id 成本ID
     * @return 成本
     */
    @Override
    public SkuCostprice selectSkuCostpriceById(Long id)
    {
        return skuCostpriceMapper.selectSkuCostpriceById(id);
    }

    /**
     * 查询成本列表
     * 
     * @param skuCostprice 成本
     * @return 成本
     */
    @Override
    public List<SkuCostprice> selectSkuCostpriceList(SkuCostprice skuCostprice)
    {
        return skuCostpriceMapper.selectSkuCostpriceList(skuCostprice);
    }

    /**
     * 新增成本
     * 
     * @param skuCostprice 成本
     * @return 结果
     */
    @Override
    public int insertSkuCostprice(SkuCostprice skuCostprice)
    {
        return skuCostpriceMapper.insertSkuCostprice(skuCostprice);
    }

    /**
     * 修改成本
     * 
     * @param skuCostprice 成本
     * @return 结果
     */
    @Override
    public int updateSkuCostprice(SkuCostprice skuCostprice)
    {
        return skuCostpriceMapper.updateSkuCostprice(skuCostprice);
    }

    /**
     * 删除成本对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSkuCostpriceByIds(String ids)
    {
        return skuCostpriceMapper.deleteSkuCostpriceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除成本信息
     * 
     * @param id 成本ID
     * @return 结果
     */
    @Override
    public int deleteSkuCostpriceById(Long id)
    {
        return skuCostpriceMapper.deleteSkuCostpriceById(id);
    }

    @Override
    public String importSkuCostpriceService(List<SkuCostprice> skuCostpriceList, boolean updateSupport) {
        if (StringUtils.isNull(skuCostpriceList) || skuCostpriceList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (SkuCostprice costprice : skuCostpriceList)
        {
            try
            {
                if(StringUtils.isEmpty(costprice.getSku())) continue;
                // 验证是否存在这个用户
                SkuCostprice p = skuCostpriceMapper.selectSkuCostpriceByOnlyCondition(costprice);
                if (p==null)
                {
                    costprice.setCreateBy(operName);
                    costprice.setCreateTime(new Date());
                    this.insertSkuCostprice(costprice);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ costprice.getSku() +" 的数据导入成功");
                }
                else if (updateSupport)
                {
                    costprice.setUpdateBy(operName);
                    costprice.setUpdateTime(new Date());
                    skuCostpriceMapper.updateSkuCostpriceByOnlyCondition(costprice);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"  + costprice.getSku() +" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + costprice.getSku() +" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + costprice.getSku() + " 的数据导入失败：";
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
    public SkuCostprice selectSkuCostpriceByOnlyCondition(SkuCostprice skuCostprice) {
        return skuCostpriceMapper.selectSkuCostpriceByOnlyCondition(skuCostprice);
    }
}
