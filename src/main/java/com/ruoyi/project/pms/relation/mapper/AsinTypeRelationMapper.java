package com.ruoyi.project.pms.relation.mapper;

import java.util.List;

import com.ruoyi.project.pms.relation.domain.AsinTypeRelation;

/**
 * ASIN与型号关联Mapper接口
 * 
 * @author Beignet
 * @date 2021-01-22
 */
public interface AsinTypeRelationMapper 
{

    /**
     * 查询ASIN与型号关联
     * 
     * @param id ASIN与型号关联ID
     * @return ASIN与型号关联
     */
    public AsinTypeRelation selectAsinTypeRelationById(Long id);

    /**
     * 查询ASIN与型号关联列表
     * 
     * @param asinTypeRelation ASIN与型号关联
     * @return ASIN与型号关联集合
     */
    public List<AsinTypeRelation> selectAsinTypeRelationList(AsinTypeRelation asinTypeRelation);

    /**
     * 新增ASIN与型号关联
     * 
     * @param asinTypeRelation ASIN与型号关联
     * @return 结果
     */
    public int insertAsinTypeRelation(AsinTypeRelation asinTypeRelation);

    /**
     * 修改ASIN与型号关联
     * 
     * @param asinTypeRelation ASIN与型号关联
     * @return 结果
     */
    public int updateAsinTypeRelation(AsinTypeRelation asinTypeRelation);

    /**
     * 删除ASIN与型号关联
     * 
     * @param id ASIN与型号关联ID
     * @return 结果
     */
    public int deleteAsinTypeRelationById(Long id);

    /**
     * 批量删除ASIN与型号关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAsinTypeRelationByIds(String[] ids);

    AsinTypeRelation selectAsinTypeRelationByAsin(String asin);

    int updateAsinTypeRelationByAsin(AsinTypeRelation asin);

    int syncAsinTypeRalation2AdvertisingData();
}
