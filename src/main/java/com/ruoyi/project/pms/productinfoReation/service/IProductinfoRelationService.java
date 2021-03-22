package com.ruoyi.project.pms.productinfoReation.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;
import com.ruoyi.project.pms.productinfoReation.vo.PasinProductinfoRelationVo;
import com.ruoyi.project.pms.productinfoReation.vo.ProductinfoRelationVo;

/**
 * 产品信息映射Service接口
 * 
 * @author Beignet
 * @date 2021-03-05
 */
public interface IProductinfoRelationService 
{
    /**
     * 查询产品信息映射
     * 
     * @param id 产品信息映射ID
     * @return 产品信息映射
     */
    public ProductinfoRelation selectProductinfoRelationById(Long id);

    /**
     * 查询产品信息映射列表
     * 
     * @param productinfoRelation 产品信息映射
     * @return 产品信息映射集合
     */
    public List<ProductinfoRelation> selectProductinfoRelationList(ProductinfoRelation productinfoRelation);

    /**
     * 新增产品信息映射
     * 
     * @param productinfoRelation 产品信息映射
     * @return 结果
     */
    public int insertProductinfoRelation(ProductinfoRelation productinfoRelation);

    /**
     * 修改产品信息映射
     * 
     * @param productinfoRelation 产品信息映射
     * @return 结果
     */
    public int updateProductinfoRelation(ProductinfoRelation productinfoRelation);

    /**
     * 批量删除产品信息映射
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProductinfoRelationByIds(String ids);

    /**
     * 删除产品信息映射信息
     * 
     * @param id 产品信息映射ID
     * @return 结果
     */
    public int deleteProductinfoRelationById(Long id);

    /**
     * 导入产品信息映射
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importProductinfoRelation(List<ProductinfoRelation> productinfoRelationList, boolean isUpdateSupport);

    public Map<String, MskuProductinfoRelationVo> getSkuMskuMap();

    /**
     * 获取标准SKU与父ASIN的关系Map
     * @return
     */
    public Map<String, String> getPasinSkuMap();

    public List<MskuProductinfoRelationVo> selectMskuProductinfoRelationVoList();

    /**
     * 获取父ASIN与商品的关系Map
     * @return
     */
    Map<String, PasinProductinfoRelationVo> getPasinProductinfoRelationVoMap();

    /**
     * 获取Coupon与商品的关系Map
     * @return
     */
    Map<String, ProductinfoRelationVo> getCouponProductinfoRelationVoMap();

    Map<String, MskuProductinfoRelationVo> getMskuProductinfoRelationVoMap();

    Map<String, MskuProductinfoRelationVo> getAsinProductinfoRelationVoMap();

    Map<String, ProductinfoRelation> getSkuProductinfoRelationVoMap();
}
