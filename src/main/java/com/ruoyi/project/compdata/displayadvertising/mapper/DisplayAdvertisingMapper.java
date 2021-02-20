package com.ruoyi.project.compdata.displayadvertising.mapper;

import java.util.List;
import com.ruoyi.project.compdata.displayadvertising.domain.DisplayAdvertising;

/**
 * Display广告数据源Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-20
 */
public interface DisplayAdvertisingMapper 
{
    /**
     * 查询Display广告数据源
     * 
     * @param id Display广告数据源ID
     * @return Display广告数据源
     */
    public DisplayAdvertising selectDisplayAdvertisingById(Long id);

    /**
     * 查询Display广告数据源列表
     * 
     * @param displayAdvertising Display广告数据源
     * @return Display广告数据源集合
     */
    public List<DisplayAdvertising> selectDisplayAdvertisingList(DisplayAdvertising displayAdvertising);

    /**
     * 新增Display广告数据源
     * 
     * @param displayAdvertising Display广告数据源
     * @return 结果
     */
    public int insertDisplayAdvertising(DisplayAdvertising displayAdvertising);

    /**
     * 修改Display广告数据源
     * 
     * @param displayAdvertising Display广告数据源
     * @return 结果
     */
    public int updateDisplayAdvertising(DisplayAdvertising displayAdvertising);

    /**
     * 删除Display广告数据源
     * 
     * @param id Display广告数据源ID
     * @return 结果
     */
    public int deleteDisplayAdvertisingById(Long id);

    /**
     * 批量删除Display广告数据源
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDisplayAdvertisingByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param DisplayAdvertising ${subTable.functionName}
     * @return 结果
     */
    public int updateDisplayAdvertisingByOnlyCondition(DisplayAdvertising displayAdvertising);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param DisplayAdvertising ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public DisplayAdvertising selectDisplayAdvertisingByOnlyCondition(DisplayAdvertising displayAdvertising);
}
