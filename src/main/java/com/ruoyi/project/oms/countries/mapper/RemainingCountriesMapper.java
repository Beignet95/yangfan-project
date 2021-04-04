package com.ruoyi.project.oms.countries.mapper;

import java.util.List;
import com.ruoyi.project.oms.countries.domain.RemainingCountries;

/**
 * 其余四国补发Mapper接口
 * 
 * @author Beignet
 * @date 2021-04-01
 */
public interface RemainingCountriesMapper 
{
    /**
     * 查询其余四国补发
     * 
     * @param id 其余四国补发ID
     * @return 其余四国补发
     */
    public RemainingCountries selectRemainingCountriesById(Long id);

    /**
     * 查询其余四国补发列表
     * 
     * @param remainingCountries 其余四国补发
     * @return 其余四国补发集合
     */
    public List<RemainingCountries> selectRemainingCountriesList(RemainingCountries remainingCountries);

    /**
     * 新增其余四国补发
     * 
     * @param remainingCountries 其余四国补发
     * @return 结果
     */
    public int insertRemainingCountries(RemainingCountries remainingCountries);

    /**
     * 修改其余四国补发
     * 
     * @param remainingCountries 其余四国补发
     * @return 结果
     */
    public int updateRemainingCountries(RemainingCountries remainingCountries);

    /**
     * 删除其余四国补发
     * 
     * @param id 其余四国补发ID
     * @return 结果
     */
    public int deleteRemainingCountriesById(Long id);

    /**
     * 批量删除其余四国补发
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRemainingCountriesByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param RemainingCountries ${subTable.functionName}
     * @return 结果
     */
    public int updateRemainingCountriesByOnlyCondition(RemainingCountries remainingCountries);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param RemainingCountries ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public RemainingCountries selectRemainingCountriesByOnlyCondition(RemainingCountries remainingCountries);
}
