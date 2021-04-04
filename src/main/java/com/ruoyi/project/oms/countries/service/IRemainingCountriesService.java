package com.ruoyi.project.oms.countries.service;

import java.util.List;
import com.ruoyi.project.oms.countries.domain.RemainingCountries;

/**
 * 其余四国补发Service接口
 * 
 * @author Beignet
 * @date 2021-04-01
 */
public interface IRemainingCountriesService 
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
     * 批量删除其余四国补发
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRemainingCountriesByIds(String ids);

    /**
     * 删除其余四国补发信息
     * 
     * @param id 其余四国补发ID
     * @return 结果
     */
    public int deleteRemainingCountriesById(Long id);

    /**
     * 导入其余四国补发
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importRemainingCountries(List<RemainingCountries> remainingCountriesList, boolean isUpdateSupport);


}
