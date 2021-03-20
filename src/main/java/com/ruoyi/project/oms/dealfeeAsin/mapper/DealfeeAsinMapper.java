package com.ruoyi.project.oms.dealfeeAsin.mapper;

import java.util.List;
import com.ruoyi.project.oms.dealfeeAsin.domain.DealfeeAsin;

/**
 * Deal Fee与ASIN映射Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-16
 */
public interface DealfeeAsinMapper 
{
    /**
     * 查询Deal Fee与ASIN映射
     * 
     * @param id Deal Fee与ASIN映射ID
     * @return Deal Fee与ASIN映射
     */
    public DealfeeAsin selectDealfeeAsinById(Long id);

    /**
     * 查询Deal Fee与ASIN映射列表
     * 
     * @param dealfeeAsin Deal Fee与ASIN映射
     * @return Deal Fee与ASIN映射集合
     */
    public List<DealfeeAsin> selectDealfeeAsinList(DealfeeAsin dealfeeAsin);

    /**
     * 新增Deal Fee与ASIN映射
     * 
     * @param dealfeeAsin Deal Fee与ASIN映射
     * @return 结果
     */
    public int insertDealfeeAsin(DealfeeAsin dealfeeAsin);

    /**
     * 修改Deal Fee与ASIN映射
     * 
     * @param dealfeeAsin Deal Fee与ASIN映射
     * @return 结果
     */
    public int updateDealfeeAsin(DealfeeAsin dealfeeAsin);

    /**
     * 删除Deal Fee与ASIN映射
     * 
     * @param id Deal Fee与ASIN映射ID
     * @return 结果
     */
    public int deleteDealfeeAsinById(Long id);

    /**
     * 批量删除Deal Fee与ASIN映射
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDealfeeAsinByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param DealfeeAsin ${subTable.functionName}
     * @return 结果
     */
    public int updateDealfeeAsinByOnlyCondition(DealfeeAsin dealfeeAsin);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param DealfeeAsin ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public DealfeeAsin selectDealfeeAsinByOnlyCondition(DealfeeAsin dealfeeAsin);
}
