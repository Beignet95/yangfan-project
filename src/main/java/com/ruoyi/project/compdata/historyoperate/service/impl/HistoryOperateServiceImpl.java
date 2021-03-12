package com.ruoyi.project.compdata.historyoperate.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.compdata.historyoperate.mapper.HistoryOperateMapper;
import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 历史操作记录Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-26
 */
@Service
public class HistoryOperateServiceImpl implements IHistoryOperateService 
{
    private static final Logger log = LoggerFactory.getLogger(HistoryOperate.class);

    @Autowired
    private HistoryOperateMapper historyOperateMapper;

    /**
     * 查询历史操作记录
     * 
     * @param id 历史操作记录ID
     * @return 历史操作记录
     */
    @Override
    public HistoryOperate selectHistoryOperateById(Long id)
    {
        return historyOperateMapper.selectHistoryOperateById(id);
    }

    /**
     * 查询历史操作记录列表
     * 
     * @param historyOperate 历史操作记录
     * @return 历史操作记录
     */
    @Override
    public List<HistoryOperate> selectHistoryOperateList(HistoryOperate historyOperate)
    {
        return historyOperateMapper.selectHistoryOperateList(historyOperate);
    }

    /**
     * 新增历史操作记录
     * 
     * @param historyOperate 历史操作记录
     * @return 结果
     */
    @Override
    public int insertHistoryOperate(HistoryOperate historyOperate)
    {
        return historyOperateMapper.insertHistoryOperate(historyOperate);
    }

    /**
     * 修改历史操作记录
     * 
     * @param historyOperate 历史操作记录
     * @return 结果
     */
    @Override
    public int updateHistoryOperate(HistoryOperate historyOperate)
    {
        return historyOperateMapper.updateHistoryOperate(historyOperate);
    }

    /**
     * 删除历史操作记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteHistoryOperateByIds(String ids)
    {
        return historyOperateMapper.deleteHistoryOperateByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除历史操作记录信息
     * 
     * @param id 历史操作记录ID
     * @return 结果
     */
    @Override
    public int deleteHistoryOperateById(Long id)
    {
        return historyOperateMapper.deleteHistoryOperateById(id);
    }

    /**
     * 导入历史操作记录
     *
     * @param historyOperateList 历史操作记录List数据
     * @return 导入结果
     */
    @Override
    public String importHistoryOperate(List<HistoryOperate> historyOperateList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(historyOperateList) || historyOperateList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (HistoryOperate historyOperate : historyOperateList)
        {
            try
            {
                // 验证数据是否已经
                HistoryOperate domain = historyOperateMapper.selectHistoryOperateByOnlyCondition(historyOperate);
                if (domain==null)
                {
                    historyOperate.setCreateBy(operName);
                    historyOperate.setCreateTime(new Date());
                    this.insertHistoryOperate(historyOperate);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ historyOperate.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    historyOperate.setUpdateBy(operName);
                    historyOperate.setUpdateTime(new Date());
                    historyOperateMapper.updateHistoryOperateByOnlyCondition(historyOperate);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + historyOperate.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + historyOperate.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + historyOperate.toString()+" 的数据导入失败：";
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

    @Override
    public boolean checkIsExitHistoryOperate(String hocode) {
        HistoryOperate ho = new HistoryOperate();
        ho.setRepeatCode(hocode);
        List<HistoryOperate> hoList = historyOperateMapper.selectHistoryOperateList(ho);
        return hoList.size()>0;
    }
}
