package com.ruoyi.project.compdata.productPrincipal.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.compdata.productPrincipal.domain.ProductPrincipal;

/**
 * 产品负责人映射Service接口
 * 
 * @author Beignet
 * @date 2021-02-23
 */
public interface IProductPrincipalService 
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
     * 批量删除产品负责人映射
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProductPrincipalByIds(String ids);

    /**
     * 删除产品负责人映射信息
     * 
     * @param id 产品负责人映射ID
     * @return 结果
     */
    public int deleteProductPrincipalById(Long id);

    /**
     * 导入产品负责人映射
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importProductPrincipal(List<ProductPrincipal> productPrincipalList, boolean isUpdateSupport);


    /**
     * Map<String, List<ProductPrincipal>>
     * String key platformSku
     * @return
     */
    Map<String, ProductPrincipal> getProductPrincipalMap();
}
