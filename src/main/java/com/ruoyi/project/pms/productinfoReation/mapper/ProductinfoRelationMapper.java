package com.ruoyi.project.pms.productinfoReation.mapper;

import java.util.List;

import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;

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
    public com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation selectProductinfoRelationById(Long id);

    /**
     * 查询产品信息映射列表
     * 
     * @param productinfoRelation 产品信息映射
     * @return 产品信息映射集合
     */
    public List<com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation> selectProductinfoRelationList(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation);

    /**
     * 新增产品信息映射
     * 
     * @param productinfoRelation 产品信息映射
     * @return 结果
     */
    public int insertProductinfoRelation(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation);

    /**
     * 修改产品信息映射
     * 
     * @param productinfoRelation 产品信息映射
     * @return 结果
     */
    public int updateProductinfoRelation(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation);

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
    public int updateProductinfoRelationByOnlyCondition(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param ProductinfoRelation ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation selectProductinfoRelationByOnlyCondition(com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation productinfoRelation);

    /**
     * 获取标准SKU与MSKU关系
     * @return
     */
    public List<MskuProductinfoRelationVo> selectMskuProductinfoRelationVoList();
}
