package com.ruoyi.project.pms.asinMsku.service;

import java.util.List;
import com.ruoyi.project.pms.asinMsku.domain.AsinMsku;

/**
 * ASIN与亚马逊产品SKU关系Service接口
 * 
 * @author Beignet
 * @date 2021-03-05
 */
public interface IAsinMskuService 
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
     * 批量删除ASIN与亚马逊产品SKU关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAsinMskuByIds(String ids);

    /**
     * 删除ASIN与亚马逊产品SKU关系信息
     * 
     * @param id ASIN与亚马逊产品SKU关系ID
     * @return 结果
     */
    public int deleteAsinMskuById(Long id);

    /**
     * 导入ASIN与亚马逊产品SKU关系
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importAsinMsku(List<AsinMsku> asinMskuList, boolean isUpdateSupport);


}
