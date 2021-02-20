package com.ruoyi.project.compdata.displayadvertising.service;

import java.util.List;
import com.ruoyi.project.compdata.displayadvertising.domain.DisplayAdvertising;

/**
 * Display广告数据源Service接口
 * 
 * @author Beignet
 * @date 2021-02-20
 */
public interface IDisplayAdvertisingService 
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
     * 批量删除Display广告数据源
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDisplayAdvertisingByIds(String ids);

    /**
     * 删除Display广告数据源信息
     * 
     * @param id Display广告数据源ID
     * @return 结果
     */
    public int deleteDisplayAdvertisingById(Long id);

    /**
     * 导入Display广告数据源
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importDisplayAdvertising(List<DisplayAdvertising> displayAdvertisingList, boolean isUpdateSupport);


}
