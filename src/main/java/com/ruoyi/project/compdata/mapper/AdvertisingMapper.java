package com.ruoyi.project.compdata.mapper;

import com.ruoyi.project.compdata.domain.Advertising;
import com.ruoyi.project.compdata.vo.AdVo;
import com.ruoyi.project.compdata.vo.AdvertisingAnalyParamVo;
import com.ruoyi.project.compdata.vo.AdvertisingAnalyVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author Beignet
 * @date 2021-01-08
 */
public interface AdvertisingMapper 
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
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteAdvertisingById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteAdvertisingByIds(String[] ids);

    Advertising selectAdvertisingByOnlyCondition(@Param("storeCode")String storeCode,@Param("asin")String asin,@Param("month") Date month);

    List<AdvertisingAnalyVo> selectAdvertisingAnaly(AdvertisingAnalyParamVo advertisingAnalyParamVo);

    public int  updateAdvertisingByOnlyCondition(Advertising vo);

    List<String> selectDistinctColumn(@Param("columnName") String columnName, AdvertisingAnalyParamVo advertisingAnalyParamVo);
}
