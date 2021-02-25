package com.ruoyi.project.compdata.productPrincipal.mapper;

import java.util.List;
import com.ruoyi.project.compdata.productPrincipal.domain.ProductPrincipal;

/**
 * 产品负责人映射Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-23
 */
public interface ProductPrincipalMapper 
{
    /**
     * 查询产品负责人映射
     * 
     * @param id 产品负责人映射ID
     * @return 产品负责人映射
     */
    public ProductPrincipal selectProductPrincipalById(Long id);

    /**
     * 查询产品负责人映射列表
     * 
     * @param productPrincipal 产品负责人映射
     * @return 产品负责人映射集合
     */
    public List<ProductPrincipal> selectProductPrincipalList(ProductPrincipal productPrincipal);

    /**
     * 新增产品负责人映射
     * 
     * @param productPrincipal 产品负责人映射
     * @return 结果
     */
    public int insertProductPrincipal(ProductPrincipal productPrincipal);

    /**
     * 修改产品负责人映射
     * 
     * @param productPrincipal 产品负责人映射
     * @return 结果
     */
    public int updateProductPrincipal(ProductPrincipal productPrincipal);

    /**
     * 删除产品负责人映射
     * 
     * @param id 产品负责人映射ID
     * @return 结果
     */
    public int deleteProductPrincipalById(Long id);

    /**
     * 批量删除产品负责人映射
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProductPrincipalByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param ProductPrincipal ${subTable.functionName}
     * @return 结果
     */
    public int updateProductPrincipalByOnlyCondition(ProductPrincipal productPrincipal);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param ProductPrincipal ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public ProductPrincipal selectProductPrincipalByOnlyCondition(ProductPrincipal productPrincipal);
}
