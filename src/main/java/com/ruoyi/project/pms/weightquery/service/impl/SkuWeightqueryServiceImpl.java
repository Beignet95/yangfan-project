package com.ruoyi.project.pms.weightquery.service.impl;

import java.util.List;

import com.ruoyi.project.pms.weightquery.domain.SkuWeightquery;
import com.ruoyi.project.pms.weightquery.mapper.SkuWeightqueryMapper;
import com.ruoyi.project.pms.weightquery.service.ISkuWeightqueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;

import com.ruoyi.common.utils.text.Convert;

/**
 * 补发登记SKUService业务层处理
 * 
 * @author Beignet
 * @date 2021-04-01
 */
@Service
public class SkuWeightqueryServiceImpl implements ISkuWeightqueryService
{
    private static final Logger log = LoggerFactory.getLogger(SkuWeightquery.class);

    @Autowired
    private SkuWeightqueryMapper skuWeightqueryMapper;

    /**
     * 查询补发登记SKU
     * 
     * @param id 补发登记SKUID
     * @return 补发登记SKU
     */
    @Override
    public SkuWeightquery selectSkuWeightqueryById(Long id)
    {
        return skuWeightqueryMapper.selectSkuWeightqueryById(id);
    }

    /**
     * 查询补发登记SKU列表
     * 
     * @param skuWeightquery 补发登记SKU
     * @return 补发登记SKU
     */
    @Override
    public List<SkuWeightquery> selectSkuWeightqueryList(SkuWeightquery skuWeightquery)
    {
        return skuWeightqueryMapper.selectSkuWeightqueryList(skuWeightquery);
    }

    /**
     * 新增补发登记SKU
     * 
     * @param skuWeightquery 补发登记SKU
     * @return 结果
     */
    @Override
    public int insertSkuWeightquery(SkuWeightquery skuWeightquery)
    {
        return skuWeightqueryMapper.insertSkuWeightquery(skuWeightquery);
    }

    /**
     * 修改补发登记SKU
     * 
     * @param skuWeightquery 补发登记SKU
     * @return 结果
     */
    @Override
    public int updateSkuWeightquery(SkuWeightquery skuWeightquery)
    {
        return skuWeightqueryMapper.updateSkuWeightquery(skuWeightquery);
    }

    /**
     * 删除补发登记SKU对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSkuWeightqueryByIds(String ids)
    {
        return skuWeightqueryMapper.deleteSkuWeightqueryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除补发登记SKU信息
     * 
     * @param id 补发登记SKUID
     * @return 结果
     */
    @Override
    public int deleteSkuWeightqueryById(Long id)
    {
        return skuWeightqueryMapper.deleteSkuWeightqueryById(id);
    }

    /**
     * 导入补发登记SKU
     *
     * @param skuWeightqueryList 补发登记SKUList数据
     * @return 导入结果
     */
    @Override
    public String importSkuWeightquery(List<SkuWeightquery> skuWeightqueryList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(skuWeightqueryList) || skuWeightqueryList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (SkuWeightquery skuWeightquery : skuWeightqueryList)
        {
            try
            {
                // 验证数据是否已经
                SkuWeightquery domain = skuWeightqueryMapper.selectSkuWeightqueryByOnlyCondition(skuWeightquery);
                if (domain==null)
                {
                    skuWeightquery.setCreateBy(operName);
                    skuWeightquery.setCreateTime(new Date());
                    this.insertSkuWeightquery(skuWeightquery);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ skuWeightquery.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    skuWeightquery.setUpdateBy(operName);
                    skuWeightquery.setUpdateTime(new Date());
                    skuWeightqueryMapper.updateSkuWeightqueryByOnlyCondition(skuWeightquery);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + skuWeightquery.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + skuWeightquery.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + skuWeightquery.toString()+" 的数据导入失败：";
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
}
