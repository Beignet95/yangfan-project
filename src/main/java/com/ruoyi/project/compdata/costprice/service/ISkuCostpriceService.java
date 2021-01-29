package com.ruoyi.project.compdata.costprice.service;

import java.util.List;
import com.ruoyi.project.compdata.costprice.domain.SkuCostprice;

/**
 * 成本Service接口
 * 
 * @author Beignet
 * @date 2021-01-26
 */
public interface ISkuCostpriceService 
{
    /**
     * 查询成本
     * 
     * @param id 成本ID
     * @return 成本
     */
    public SkuCostprice selectSkuCostpriceById(Long id);

    /**
     * 查询成本列表
     * 
     * @param skuCostprice 成本
     * @return 成本集合
     */
    public List<SkuCostprice> selectSkuCostpriceList(SkuCostprice skuCostprice);

    /**
     * 新增成本
     * 
     * @param skuCostprice 成本
     * @return 结果
     */
    public int insertSkuCostprice(SkuCostprice skuCostprice);

    /**
     * 修改成本
     * 
     * @param skuCostprice 成本
     * @return 结果
     */
    public int updateSkuCostprice(SkuCostprice skuCostprice);

    /**
     * 批量删除成本
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSkuCostpriceByIds(String ids);

    /**
     * 删除成本信息
     * 
     * @param id 成本ID
     * @return 结果
     */
    public int deleteSkuCostpriceById(Long id);

    String importSkuCostpriceService(List<SkuCostprice> skuCostpriceList, boolean updateSupport);

    SkuCostprice selectSkuCostpriceByOnlyCondition(SkuCostprice skuCostprice);
}
