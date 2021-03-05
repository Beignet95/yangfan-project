package com.ruoyi.project.pms.asinMsku.mapper;

import java.util.List;
import com.ruoyi.project.pms.asinMsku.domain.AsinMsku;

/**
 * ASIN与亚马逊产品SKU关系Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-05
 */
public interface AsinMskuMapper 
{
    /**
     * 查询ASIN与亚马逊产品SKU关系
     * 
     * @param id ASIN与亚马逊产品SKU关系ID
     * @return ASIN与亚马逊产品SKU关系
     */
    public AsinMsku selectAsinMskuById(Long id);

    /**
     * 查询ASIN与亚马逊产品SKU关系列表
     * 
     * @param asinMsku ASIN与亚马逊产品SKU关系
     * @return ASIN与亚马逊产品SKU关系集合
     */
    public List<AsinMsku> selectAsinMskuList(AsinMsku asinMsku);

    /**
     * 新增ASIN与亚马逊产品SKU关系
     * 
     * @param asinMsku ASIN与亚马逊产品SKU关系
     * @return 结果
     */
    public int insertAsinMsku(AsinMsku asinMsku);

    /**
     * 修改ASIN与亚马逊产品SKU关系
     * 
     * @param asinMsku ASIN与亚马逊产品SKU关系
     * @return 结果
     */
    public int updateAsinMsku(AsinMsku asinMsku);

    /**
     * 删除ASIN与亚马逊产品SKU关系
     * 
     * @param id ASIN与亚马逊产品SKU关系ID
     * @return 结果
     */
    public int deleteAsinMskuById(Long id);

    /**
     * 批量删除ASIN与亚马逊产品SKU关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAsinMskuByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param AsinMsku ${subTable.functionName}
     * @return 结果
     */
    public int updateAsinMskuByOnlyCondition(AsinMsku asinMsku);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param AsinMsku ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public AsinMsku selectAsinMskuByOnlyCondition(AsinMsku asinMsku);
}
