package com.ruoyi.project.compdata.service;

import java.util.List;
import com.ruoyi.project.compdata.domain.Advertising;
import com.ruoyi.project.compdata.vo.*;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author Beignet
 * @date 2021-01-08
 */
public interface IAdvertisingService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public Advertising selectAdvertisingById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param advertising 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<Advertising> selectAdvertisingList(Advertising advertising);

    /**
     * 新增【请填写功能名称】
     * 
     * @param advertising 【请填写功能名称】
     * @return 结果
     */
    public int insertAdvertising(Advertising advertising);

    /**
     * 修改【请填写功能名称】
     * 
     * @param advertising 【请填写功能名称】
     * @return 结果
     */
    public int updateAdvertising(Advertising advertising);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAdvertisingByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteAdvertisingById(Long id);

    String importData4Advertising(List<Advertising> list, Boolean isUpdateSupport);

    String importData4AdVo(List<AdVo> list,Boolean isUpdateSupport);

    List<AdvertisingAnalyVo> selectAdExposureClickVo(AdvertisingAnalyParamVo advertisingAnalyParamVo);

    AdvertisingEchartsVo selectAdvertisingEchartsVo(AdvertisingAnalyParamVo advertisingAnalyParamVo);

    void updateAdvertisingByOnlyCondition(Advertising vo);

    AdvertisingAnalySearchVo selectAdvertisingAnalySearchVo(AdvertisingAnalyParamVo advertisingAnalyParamVo);
}
