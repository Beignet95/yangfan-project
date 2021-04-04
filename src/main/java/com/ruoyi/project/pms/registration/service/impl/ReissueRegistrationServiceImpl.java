package com.ruoyi.project.pms.registration.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.project.oms.countries.domain.RemainingCountries;
import com.ruoyi.project.oms.countries.mapper.RemainingCountriesMapper;
import com.ruoyi.project.pms.registration.domain.ReissueRegistration;
import com.ruoyi.project.pms.registration.mapper.ReissueRegistrationMapper;
import com.ruoyi.project.pms.registration.service.IReissueRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.common.utils.text.Convert;

/**
 * 补发登记Service业务层处理
 * 
 * @author Kwl
 * @date 2021-04-01
 */
@Service
public class ReissueRegistrationServiceImpl implements IReissueRegistrationService
{
    private static final Logger log = LoggerFactory.getLogger(ReissueRegistration.class);

    @Autowired
    private ReissueRegistrationMapper reissueRegistrationMapper;

    /**
     * 查询补发登记
     *
     * @param id 补发登记ID
     * @return 补发登记
     */
    @Override
    public ReissueRegistration selectReissueRegistrationById(Long id)
    {
        return reissueRegistrationMapper.selectReissueRegistrationById(id);
    }

    /**
     * 查询补发登记列表
     *
     * @param reissueRegistration 补发登记
     * @return 补发登记
     */
    @Override
    public List<ReissueRegistration> selectReissueRegistrationList(ReissueRegistration reissueRegistration)
    {
            return reissueRegistrationMapper.selectReissueRegistrationList(reissueRegistration);
    }

    /**
     * 新增补发登记
     *
     * @param reissueRegistration 补发登记
     * @return 结果
     */
    @Override
    public int insertReissueRegistration(ReissueRegistration reissueRegistration)
    {
        reissueRegistration.setCreateTime(DateUtils.getNowDate());
        return reissueRegistrationMapper.insertReissueRegistration(reissueRegistration);
    }

    /**
     * 修改补发登记
     *
     * @param reissueRegistration 补发登记
     * @return 结果
     */
    @Override
    public int updateReissueRegistration(ReissueRegistration reissueRegistration)
    {
        reissueRegistration.setUpdateTime(DateUtils.getNowDate());
        return reissueRegistrationMapper.updateReissueRegistration(reissueRegistration);
    }

    /**
     * 删除补发登记对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteReissueRegistrationByIds(String ids)
    {
        return reissueRegistrationMapper.deleteReissueRegistrationByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除补发登记信息
     *
     * @param id 补发登记ID
     * @return 结果
     */
    @Override
    public int deleteReissueRegistrationById(Long id)
    {
        return reissueRegistrationMapper.deleteReissueRegistrationById(id);
    }

    /**
     * 导入补发登记
     *
     * @param reissueRegistrationList 补发登记List数据
     * @return 导入结果
     */
    @Override
    public String importReissueRegistration(List<ReissueRegistration> reissueRegistrationList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(reissueRegistrationList) || reissueRegistrationList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (ReissueRegistration reissueRegistration : reissueRegistrationList)
        {
            try
            {
                // 验证数据是否已经
                ReissueRegistration domain = reissueRegistrationMapper.selectReissueRegistrationByOnlyCondition(reissueRegistration);
                if (StringUtils.isNull(reissueRegistration.getId()) && StringUtils.isEmpty(reissueRegistration.getOrderNumber())){
                    break;
                }else {
                    if (domain==null)
                    {
                        reissueRegistration.setCreateBy(operName);
                        reissueRegistration.setCreateTime(new Date());
                        this.insertReissueRegistration(reissueRegistration);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、"+ reissueRegistration.toString()+" 的数据导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        reissueRegistration.setUpdateBy(operName);
                        reissueRegistration.setUpdateTime(new Date());
                        reissueRegistrationMapper.updateReissueRegistrationByOnlyCondition(reissueRegistration);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、" + reissueRegistration.toString()+" 的数据更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、" + reissueRegistration.toString()+" 的数据已存在");
                    }
                }

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + reissueRegistration.toString()+" 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
