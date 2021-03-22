package com.ruoyi.project.pms.asinMsku.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.pms.asinMsku.mapper.AsinMskuMapper;
import com.ruoyi.project.pms.asinMsku.domain.AsinMsku;
import com.ruoyi.project.pms.asinMsku.service.IAsinMskuService;
import com.ruoyi.common.utils.text.Convert;

/**
 * ASIN与亚马逊产品SKU关系Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-05
 */
@Service
public class AsinMskuServiceImpl implements IAsinMskuService 
{
    private static final Logger log = LoggerFactory.getLogger(AsinMsku.class);

    @Autowired
    private AsinMskuMapper asinMskuMapper;

    /**
     * 查询ASIN与亚马逊产品SKU关系
     * 
     * @param id ASIN与亚马逊产品SKU关系ID
     * @return ASIN与亚马逊产品SKU关系
     */
    @Override
    public AsinMsku selectAsinMskuById(Long id)
    {
        return asinMskuMapper.selectAsinMskuById(id);
    }

    /**
     * 查询ASIN与亚马逊产品SKU关系列表
     * 
     * @param asinMsku ASIN与亚马逊产品SKU关系
     * @return ASIN与亚马逊产品SKU关系
     */
    @Override
    public List<AsinMsku> selectAsinMskuList(AsinMsku asinMsku)
    {
        return asinMskuMapper.selectAsinMskuList(asinMsku);
    }

    /**
     * 新增ASIN与亚马逊产品SKU关系
     * 
     * @param asinMsku ASIN与亚马逊产品SKU关系
     * @return 结果
     */
    @Override
    public int insertAsinMsku(AsinMsku asinMsku)
    {
        return asinMskuMapper.insertAsinMsku(asinMsku);
    }

    /**
     * 修改ASIN与亚马逊产品SKU关系
     * 
     * @param asinMsku ASIN与亚马逊产品SKU关系
     * @return 结果
     */
    @Override
    public int updateAsinMsku(AsinMsku asinMsku)
    {
        return asinMskuMapper.updateAsinMsku(asinMsku);
    }

    /**
     * 删除ASIN与亚马逊产品SKU关系对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAsinMskuByIds(String ids)
    {
        return asinMskuMapper.deleteAsinMskuByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除ASIN与亚马逊产品SKU关系信息
     * 
     * @param id ASIN与亚马逊产品SKU关系ID
     * @return 结果
     */
    @Override
    public int deleteAsinMskuById(Long id)
    {
        return asinMskuMapper.deleteAsinMskuById(id);
    }

    /**
     * 导入ASIN与亚马逊产品SKU关系
     *
     * @param asinMskuList ASIN与亚马逊产品SKU关系List数据
     * @return 导入结果
     */
    @Override
    public String importAsinMsku(List<AsinMsku> asinMskuList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(asinMskuList) || asinMskuList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (AsinMsku asinMsku : asinMskuList)
        {
            try
            {
                //为空不作操作
                if(StringUtils.isEmpty(asinMsku.getAsin())&&StringUtils.isEmpty(asinMsku.getMsku())) continue;
                // 验证数据是否已经
                AsinMsku domain = asinMskuMapper.selectAsinMskuByOnlyCondition(asinMsku);
                if (domain==null)
                {
                    asinMsku.setCreateBy(operName);
                    asinMsku.setCreateTime(new Date());
                    this.insertAsinMsku(asinMsku);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ asinMsku.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    asinMsku.setUpdateBy(operName);
                    asinMsku.setUpdateTime(new Date());
                    asinMskuMapper.updateAsinMskuByOnlyCondition(asinMsku);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + asinMsku.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + asinMsku.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + asinMsku.toString()+" 的数据导入失败：";
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
