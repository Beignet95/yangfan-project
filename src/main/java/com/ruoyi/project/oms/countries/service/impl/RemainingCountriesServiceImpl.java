package com.ruoyi.project.oms.countries.service.impl;

import java.util.List;
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
import com.ruoyi.project.oms.countries.mapper.RemainingCountriesMapper;
import com.ruoyi.project.oms.countries.domain.RemainingCountries;
import com.ruoyi.project.oms.countries.service.IRemainingCountriesService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 其余四国补发Service业务层处理
 * 
 * @author Beignet
 * @date 2021-04-01
 */
@Service
public class RemainingCountriesServiceImpl implements IRemainingCountriesService 
{
    private static final Logger log = LoggerFactory.getLogger(RemainingCountries.class);

    @Autowired
    private RemainingCountriesMapper remainingCountriesMapper;

    /**
     * 查询其余四国补发
     * 
     * @param id 其余四国补发ID
     * @return 其余四国补发
     */
    @Override
    public RemainingCountries selectRemainingCountriesById(Long id)
    {
        return remainingCountriesMapper.selectRemainingCountriesById(id);
    }

    /**
     * 查询其余四国补发列表
     * 
     * @param remainingCountries 其余四国补发
     * @return 其余四国补发
     */
    @Override
    public List<RemainingCountries> selectRemainingCountriesList(RemainingCountries remainingCountries)
    {
        return remainingCountriesMapper.selectRemainingCountriesList(remainingCountries);
    }

    /**
     * 新增其余四国补发
     * 
     * @param remainingCountries 其余四国补发
     * @return 结果
     */
    @Override
    public int insertRemainingCountries(RemainingCountries remainingCountries)
    {
        remainingCountries.setCreateTime(DateUtils.getNowDate());
        return remainingCountriesMapper.insertRemainingCountries(remainingCountries);
    }

    /**
     * 修改其余四国补发
     * 
     * @param remainingCountries 其余四国补发
     * @return 结果
     */
    @Override
    public int updateRemainingCountries(RemainingCountries remainingCountries)
    {
        remainingCountries.setUpdateTime(DateUtils.getNowDate());
        return remainingCountriesMapper.updateRemainingCountries(remainingCountries);
    }

    /**
     * 删除其余四国补发对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRemainingCountriesByIds(String ids)
    {
        return remainingCountriesMapper.deleteRemainingCountriesByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除其余四国补发信息
     * 
     * @param id 其余四国补发ID
     * @return 结果
     */
    @Override
    public int deleteRemainingCountriesById(Long id)
    {
        return remainingCountriesMapper.deleteRemainingCountriesById(id);
    }

    /**
     * 导入其余四国补发
     *
     * @param remainingCountriesList 其余四国补发List数据
     * @return 导入结果
     */
    @Override
    public String importRemainingCountries(List<RemainingCountries> remainingCountriesList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(remainingCountriesList) || remainingCountriesList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (RemainingCountries remainingCountries : remainingCountriesList)
        {
            try {
                // 验证数据是否已经
                RemainingCountries domain = remainingCountriesMapper.selectRemainingCountriesByOnlyCondition(remainingCountries);
                if (StringUtils.isNull(remainingCountries.getId()) && StringUtils.isEmpty(remainingCountries.getWarehouseSku())) {
                    break;
                } else {
                    if (domain == null) {
                        remainingCountries.setSite("其余四国补发");
                        remainingCountries.setCreateBy(operName);
                        remainingCountries.setCreateTime(new Date());
                        this.insertRemainingCountries(remainingCountries);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、" + remainingCountries.toString() + " 的数据导入成功");
                    } else if (isUpdateSupport) {
                        remainingCountries.setUpdateBy(operName);
                        remainingCountries.setUpdateTime(new Date());
                        remainingCountriesMapper.updateRemainingCountriesByOnlyCondition(remainingCountries);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、" + remainingCountries.toString() + " 的数据更新成功");
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、" + remainingCountries.toString() + " 的数据已存在");
                    }
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + remainingCountries.toString()+" 的数据导入失败：";
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
