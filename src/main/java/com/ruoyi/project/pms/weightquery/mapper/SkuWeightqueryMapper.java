package com.ruoyi.project.pms.weightquery.mapper;

import com.ruoyi.project.pms.weightquery.domain.SkuWeightquery;

import java.util.List;


/**
 * 补发登记SKUMapper接口
 * 
 * @author Beignet
 * @date 2021-04-01
 */
public interface SkuWeightqueryMapper 
{
    /**
     * 查询补发登记SKU
     * 
     * @param id 补发登记SKUID
     * @return 补发登记SKU
     */
    public SkuWeightquery selectSkuWeightqueryById(Long id);

    /**
     * 查询补发登记SKU列表
     * 
     * @param skuWeightquery 补发登记SKU
     * @return 补发登记SKU集合
     */
    public List<SkuWeightquery> selectSkuWeightqueryList(SkuWeightquery skuWeightquery);

    /**
     * 新增补发登记SKU
     * 
     * @param skuWeightquery 补发登记SKU
     * @return 结果
     */
    public int insertSkuWeightquery(SkuWeightquery skuWeightquery);

    /**
     * 修改补发登记SKU
     * 
     * @param skuWeightquery 补发登记SKU
     * @return 结果
     */
    public int updateSkuWeightquery(SkuWeightquery skuWeightquery);

    /**
     * 删除补发登记SKU
     * 
     * @param id 补发登记SKUID
     * @return 结果
     */
    public int deleteSkuWeightqueryById(Long id);

    /**
     * 批量删除补发登记SKU
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSkuWeightqueryByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param SkuWeightquery ${subTable.functionName}
     * @return 结果
     */
    public int updateSkuWeightqueryByOnlyCondition(SkuWeightquery skuWeightquery);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param SkuWeightquery ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public SkuWeightquery selectSkuWeightqueryByOnlyCondition(SkuWeightquery skuWeightquery);
}
