package com.ruoyi.project.system.net.zwIpFiter.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.system.net.zwIpFiter.mapper.SysZwIpFilterMapper;
import com.ruoyi.project.system.net.zwIpFiter.domain.SysZwIpFilter;
import com.ruoyi.project.system.net.zwIpFiter.service.ISysZwIpFilterService;
import com.ruoyi.common.utils.text.Convert;

/**
 * ZwIpFilterService业务层处理
 * 
 * @author Beignet
 * @date 2021-01-30
 */
@Service
public class SysZwIpFilterServiceImpl implements ISysZwIpFilterService 
{
    private static final Logger log = LoggerFactory.getLogger(SysZwIpFilter.class);

    @Autowired
    private SysZwIpFilterMapper sysZwIpFilterMapper;

    /**
     * 查询ZwIpFilter
     * 
     * @param id ZwIpFilterID
     * @return ZwIpFilter
     */
    @Override
    public SysZwIpFilter selectSysZwIpFilterById(Long id)
    {
        return sysZwIpFilterMapper.selectSysZwIpFilterById(id);
    }

    /**
     * 查询ZwIpFilter列表
     * 
     * @param sysZwIpFilter ZwIpFilter
     * @return ZwIpFilter
     */
    @Override
    public List<SysZwIpFilter> selectSysZwIpFilterList(SysZwIpFilter sysZwIpFilter)
    {
        return sysZwIpFilterMapper.selectSysZwIpFilterList(sysZwIpFilter);
    }

    /**
     * 新增ZwIpFilter
     * 
     * @param sysZwIpFilter ZwIpFilter
     * @return 结果
     */
    @Override
    public int insertSysZwIpFilter(SysZwIpFilter sysZwIpFilter)
    {
        sysZwIpFilter.setCreateTime(DateUtils.getNowDate());
        return sysZwIpFilterMapper.insertSysZwIpFilter(sysZwIpFilter);
    }

    /**
     * 修改ZwIpFilter
     * 
     * @param sysZwIpFilter ZwIpFilter
     * @return 结果
     */
    @Override
    public int updateSysZwIpFilter(SysZwIpFilter sysZwIpFilter)
    {
        sysZwIpFilter.setUpdateTime(DateUtils.getNowDate());
        return sysZwIpFilterMapper.updateSysZwIpFilter(sysZwIpFilter);
    }

    /**
     * 删除ZwIpFilter对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysZwIpFilterByIds(String ids)
    {
        return sysZwIpFilterMapper.deleteSysZwIpFilterByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除ZwIpFilter信息
     * 
     * @param id ZwIpFilterID
     * @return 结果
     */
    @Override
    public int deleteSysZwIpFilterById(Long id)
    {
        return sysZwIpFilterMapper.deleteSysZwIpFilterById(id);
    }

    /**
     * 导入ZwIpFilter
     *
     * @param sysZwIpFilterList ZwIpFilterList数据
     * @return 导入结果
     */
    @Override
    public String importSysZwIpFilter(List<SysZwIpFilter> sysZwIpFilterList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(sysZwIpFilterList) || sysZwIpFilterList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (SysZwIpFilter sysZwIpFilter : sysZwIpFilterList)
        {
            try
            {
                // 验证数据是否已经
                SysZwIpFilter domain = sysZwIpFilterMapper.selectSysZwIpFilterByOnlyCondition(sysZwIpFilter);
                if (domain==null)
                {
                    sysZwIpFilter.setCreateBy(operName);
                    sysZwIpFilter.setCreateTime(new Date());
                    this.insertSysZwIpFilter(sysZwIpFilter);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ sysZwIpFilter.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    sysZwIpFilter.setUpdateBy(operName);
                    sysZwIpFilter.setUpdateTime(new Date());
                    sysZwIpFilterMapper.updateSysZwIpFilterByOnlyCondition(sysZwIpFilter);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + sysZwIpFilter.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + sysZwIpFilter.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + sysZwIpFilter.toString()+" 的数据导入失败：";
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
