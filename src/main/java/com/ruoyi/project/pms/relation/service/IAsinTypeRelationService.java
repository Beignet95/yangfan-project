package com.ruoyi.project.pms.relation.service;

import java.util.List;
import com.ruoyi.project.pms.relation.domain.AsinTypeRelation;

/**
 * ASIN与型号关联Service接口
 * 
 * @author Beignet
 * @date 2021-01-22
 */
public interface IAsinTypeRelationService 
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
     * 批量删除ASIN与型号关联
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAsinTypeRelationByIds(String ids);

    /**
     * 删除ASIN与型号关联信息
     * 
     * @param id ASIN与型号关联ID
     * @return 结果
     */
    public int deleteAsinTypeRelationById(Long id);

    String importAsinTypeRelation(List<AsinTypeRelation> relationList, boolean updateSupport);

    String syncAsinTypeRalation2AdvertisingData();

    AsinTypeRelation selectAsinTypeRelationByAsin(String asin);
}
