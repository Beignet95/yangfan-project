package com.ruoyi.project.pms.registration.service;


import com.ruoyi.project.pms.registration.domain.ReissueRegistration;

import java.util.List;

/**
 * 补发登记Service接口
 * 
 * @author Kwl
 * @date 2021-04-01
 */
public interface IReissueRegistrationService 
{
    /**
     * 查询补发登记
     * 
     * @param id 补发登记ID
     * @return 补发登记
     */
    public ReissueRegistration selectReissueRegistrationById(Long id);

    /**
     * 查询补发登记列表
     * 
     * @param reissueRegistration 补发登记
     * @return 补发登记集合
     */
    public List<ReissueRegistration> selectReissueRegistrationList(ReissueRegistration reissueRegistration);

    /**
     * 新增补发登记
     * 
     * @param reissueRegistration 补发登记
     * @return 结果
     */
    public int insertReissueRegistration(ReissueRegistration reissueRegistration);

    /**
     * 修改补发登记
     * 
     * @param reissueRegistration 补发登记
     * @return 结果
     */
    public int updateReissueRegistration(ReissueRegistration reissueRegistration);

    /**
     * 批量删除补发登记
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteReissueRegistrationByIds(String ids);

    /**
     * 删除补发登记信息
     * 
     * @param id 补发登记ID
     * @return 结果
     */
    public int deleteReissueRegistrationById(Long id);

    /**
     * 导入补发登记
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importReissueRegistration(List<ReissueRegistration> reissueRegistrationList, boolean isUpdateSupport);


}
