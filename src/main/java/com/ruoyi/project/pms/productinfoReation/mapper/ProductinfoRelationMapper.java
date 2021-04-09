package com.ruoyi.project.pms.productinfoReation.mapper;

import java.util.List;

import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;
import com.ruoyi.project.pms.productinfoReation.vo.PasinProductinfoRelationVo;
import com.ruoyi.project.pms.productinfoReation.vo.ProductinfoRelationVo;
import com.ruoyi.project.pms.productinfoReation.vo.String2MapVo;

/**
 * 产品信息映射Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-05
 */
public interface ProductinfoRelationMapper 
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
     * 删除产品信息映射
     * 
     * @param id 产品信息映射ID
     * @return 结果
     */
    public int deleteProductinfoRelationById(Long id);

    /**
     * 批量删除产品信息映射
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteProductinfoRelationByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param ProductinfoRelation ${subTable.functionName}
     * @return 结果
     */
    public int updateProductinfoRelationByOnlyCondition(ProductinfoRelation productinfoRelation);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param ProductinfoRelation ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation selectProductinfoRelationByOnlyCondition(ProductinfoRelation productinfoRelation);

    /**
     * 获取标准SKU与MSKU关系
     * @return
     */
    public List<MskuProductinfoRelationVo> selectMskuProductinfoRelationVoList(String areaCode);

    /**
     * 获取标准sku与父asin的关系
     * @return
     */
    public List<String2MapVo> selectSkuPasinList();

    /**
     * 获取标准sku与产品关联信息（ProductRelationInfo）的关系
     * @return
     */
    List<String2MapVo> selectPasinSkuList();

    /**
     * 获取coupon与产品关联信息（ProductRelationInfo）的关系
     * @return
     */
    List<ProductinfoRelationVo> selectCouponProductinfoRelationVo();

    List<PasinProductinfoRelationVo> selectPasinProductinfoRelationVoList();
}
