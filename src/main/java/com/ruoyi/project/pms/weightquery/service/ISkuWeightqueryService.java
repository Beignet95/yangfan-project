package com.ruoyi.project.pms.weightquery.service;

import com.ruoyi.project.pms.weightquery.domain.SkuWeightquery;

import java.util.List;


/**
 * 补发登记SKUService接口
 * 
 * @author Beignet
 * @date 2021-04-01
 */
public interface ISkuWeightqueryService 
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
     * 批量删除补发登记SKU
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSkuWeightqueryByIds(String ids);

    /**
     * 删除补发登记SKU信息
     * 
     * @param id 补发登记SKUID
     * @return 结果
     */
    public int deleteSkuWeightqueryById(Long id);

    /**
     * 导入补发登记SKU
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importSkuWeightquery(List<SkuWeightquery> skuWeightqueryList, boolean isUpdateSupport);


}
