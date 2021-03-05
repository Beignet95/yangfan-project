package com.ruoyi.project.pms.productinfoReation.service.impl;

import java.util.List;

import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.pms.productinfoReation.mapper.ProductinfoRelationMapper;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 产品信息映射Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-05
 */
@Service
public class ProductinfoRelationServiceImpl implements IProductinfoRelationService 
{
    private static final Logger log = LoggerFactory.getLogger(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation.class);

    @Autowired
    private ProductinfoRelationMapper productinfoRelationMapper;

    /**
     * 查询产品信息映射
     * 
     * @param id 产品信息映射ID
     * @return 产品信息映射
     */
    @Override
    public com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation selectProductinfoRelationById(Long id)
    {
        return productinfoRelationMapper.selectProductinfoRelationById(id);
    }

    /**
     * 查询产品信息映射列表
     * 
     * @param productinfoRelation 产品信息映射
     * @return 产品信息映射
     */
    @Override
    public List<com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation> selectProductinfoRelationList(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation)
    {
        return productinfoRelationMapper.selectProductinfoRelationList(productinfoRelation);
    }

    /**
     * 新增产品信息映射
     * 
     * @param productinfoRelation 产品信息映射
     * @return 结果
     */
    @Override
    public int insertProductinfoRelation(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation)
    {
        return productinfoRelationMapper.insertProductinfoRelation(productinfoRelation);
    }

    /**
     * 修改产品信息映射
     * 
     * @param productinfoRelation 产品信息映射
     * @return 结果
     */
    @Override
    public int updateProductinfoRelation(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation)
    {
        return productinfoRelationMapper.updateProductinfoRelation(productinfoRelation);
    }

    /**
     * 删除产品信息映射对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductinfoRelationByIds(String ids)
    {
        return productinfoRelationMapper.deleteProductinfoRelationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品信息映射信息
     * 
     * @param id 产品信息映射ID
     * @return 结果
     */
    @Override
    public int deleteProductinfoRelationById(Long id)
    {
        return productinfoRelationMapper.deleteProductinfoRelationById(id);
    }

    /**
     * 导入产品信息映射
     *
     * @param productinfoRelationList 产品信息映射List数据
     * @return 导入结果
     */
    @Override
    public String importProductinfoRelation(List<com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation> productinfoRelationList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(productinfoRelationList) || productinfoRelationList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation : productinfoRelationList)
        {
            try
            {
                // 验证数据是否已经
                com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation domain = productinfoRelationMapper.selectProductinfoRelationByOnlyCondition(productinfoRelation);
                if (domain==null)
                {
                    productinfoRelation.setCreateBy(operName);
                    productinfoRelation.setCreateTime(new Date());
                    this.insertProductinfoRelation(productinfoRelation);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ productinfoRelation.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    productinfoRelation.setUpdateBy(operName);
                    productinfoRelation.setUpdateTime(new Date());
                    productinfoRelationMapper.updateProductinfoRelationByOnlyCondition(productinfoRelation);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + productinfoRelation.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + productinfoRelation.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + productinfoRelation.toString()+" 的数据导入失败：";
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
    public Map<String, MskuProductinfoRelationVo> getSkuMskuMap() {
        List<MskuProductinfoRelationVo> mskuProductinfoRelationVos = mskuProductinfoRelationVos = productinfoRelationMapper.selectMskuProductinfoRelationVoList();
        Map<String,MskuProductinfoRelationVo> map = mskuProductinfoRelationVos.stream().collect(Collectors.toMap(
                MskuProductinfoRelationVo::getMsku, Function.identity(),(entity1, entity2) -> entity1));
        return map;
    }
}