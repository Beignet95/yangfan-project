package com.ruoyi.project.compdata.productPrincipal.service.impl;

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
import com.ruoyi.project.compdata.productPrincipal.mapper.ProductPrincipalMapper;
import com.ruoyi.project.compdata.productPrincipal.domain.ProductPrincipal;
import com.ruoyi.project.compdata.productPrincipal.service.IProductPrincipalService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 产品负责人映射Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-23
 */
@Service
public class ProductPrincipalServiceImpl implements IProductPrincipalService 
{
    private static final Logger log = LoggerFactory.getLogger(ProductPrincipal.class);

    @Autowired
    private ProductPrincipalMapper productPrincipalMapper;

    /**
     * 查询产品负责人映射
     * 
     * @param id 产品负责人映射ID
     * @return 产品负责人映射
     */
    @Override
    public ProductPrincipal selectProductPrincipalById(Long id)
    {
        return productPrincipalMapper.selectProductPrincipalById(id);
    }

    /**
     * 查询产品负责人映射列表
     * 
     * @param productPrincipal 产品负责人映射
     * @return 产品负责人映射
     */
    @Override
    public List<ProductPrincipal> selectProductPrincipalList(ProductPrincipal productPrincipal)
    {
        return productPrincipalMapper.selectProductPrincipalList(productPrincipal);
    }

    /**
     * 新增产品负责人映射
     * 
     * @param productPrincipal 产品负责人映射
     * @return 结果
     */
    @Override
    public int insertProductPrincipal(ProductPrincipal productPrincipal)
    {
        return productPrincipalMapper.insertProductPrincipal(productPrincipal);
    }

    /**
     * 修改产品负责人映射
     * 
     * @param productPrincipal 产品负责人映射
     * @return 结果
     */
    @Override
    public int updateProductPrincipal(ProductPrincipal productPrincipal)
    {
        return productPrincipalMapper.updateProductPrincipal(productPrincipal);
    }

    /**
     * 删除产品负责人映射对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteProductPrincipalByIds(String ids)
    {
        return productPrincipalMapper.deleteProductPrincipalByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除产品负责人映射信息
     * 
     * @param id 产品负责人映射ID
     * @return 结果
     */
    @Override
    public int deleteProductPrincipalById(Long id)
    {
        return productPrincipalMapper.deleteProductPrincipalById(id);
    }

    /**
     * 导入产品负责人映射
     *
     * @param productPrincipalList 产品负责人映射List数据
     * @return 导入结果
     */
    @Override
    public String importProductPrincipal(List<ProductPrincipal> productPrincipalList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(productPrincipalList) || productPrincipalList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (ProductPrincipal productPrincipal : productPrincipalList)
        {
            try
            {
                // 验证数据是否已经
                ProductPrincipal domain = productPrincipalMapper.selectProductPrincipalByOnlyCondition(productPrincipal);
                if (domain==null)
                {
                    productPrincipal.setCreateBy(operName);
                    productPrincipal.setCreateTime(new Date());
                    this.insertProductPrincipal(productPrincipal);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ productPrincipal.getSku()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    productPrincipal.setUpdateBy(operName);
                    productPrincipal.setUpdateTime(new Date());
                    productPrincipalMapper.updateProductPrincipalByOnlyCondition(productPrincipal);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + productPrincipal.getSku()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + productPrincipal.getSku()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + productPrincipal.getSku()+" 的数据导入失败：";
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
