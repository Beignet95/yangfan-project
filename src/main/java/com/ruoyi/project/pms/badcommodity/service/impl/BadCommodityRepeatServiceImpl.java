package com.ruoyi.project.pms.badcommodity.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.pms.badcommodity.mapper.BadCommodityRepeatMapper;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodityRepeat;
import com.ruoyi.project.pms.badcommodity.service.IBadCommodityRepeatService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 不良记录重复数据Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-18
 */
@Service
public class BadCommodityRepeatServiceImpl implements IBadCommodityRepeatService 
{
    private static final Logger log = LoggerFactory.getLogger(BadCommodityRepeat.class);

    @Autowired
    private BadCommodityRepeatMapper badCommodityRepeatMapper;

    /**
     * 查询不良记录重复数据
     * 
     * @param id 不良记录重复数据ID
     * @return 不良记录重复数据
     */
    @Override
    public BadCommodityRepeat selectBadCommodityRepeatById(Long id)
    {
        return badCommodityRepeatMapper.selectBadCommodityRepeatById(id);
    }

    /**
     * 查询不良记录重复数据列表
     * 
     * @param badCommodityRepeat 不良记录重复数据
     * @return 不良记录重复数据
     */
    @Override
    public List<BadCommodityRepeat> selectBadCommodityRepeatList(BadCommodityRepeat badCommodityRepeat)
    {
        return badCommodityRepeatMapper.selectBadCommodityRepeatList(badCommodityRepeat);
    }

    /**
     * 新增不良记录重复数据
     * 
     * @param badCommodityRepeat 不良记录重复数据
     * @return 结果
     */
    @Override
    public int insertBadCommodityRepeat(BadCommodityRepeat badCommodityRepeat)
    {
        badCommodityRepeat.setCreateTime(DateUtils.getNowDate());
        return badCommodityRepeatMapper.insertBadCommodityRepeat(badCommodityRepeat);
    }

    /**
     * 修改不良记录重复数据
     * 
     * @param badCommodityRepeat 不良记录重复数据
     * @return 结果
     */
    @Override
    public int updateBadCommodityRepeat(BadCommodityRepeat badCommodityRepeat)
    {
        badCommodityRepeat.setUpdateTime(DateUtils.getNowDate());
        return badCommodityRepeatMapper.updateBadCommodityRepeat(badCommodityRepeat);
    }

    /**
     * 删除不良记录重复数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBadCommodityRepeatByIds(String ids)
    {
        return badCommodityRepeatMapper.deleteBadCommodityRepeatByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除不良记录重复数据信息
     * 
     * @param id 不良记录重复数据ID
     * @return 结果
     */
    @Override
    public int deleteBadCommodityRepeatById(Long id)
    {
        return badCommodityRepeatMapper.deleteBadCommodityRepeatById(id);
    }

    /**
     * 导入不良记录重复数据
     *
     * @param badCommodityRepeatList 不良记录重复数据List数据
     * @return 导入结果
     */
    @Override
    public String importBadCommodityRepeat(List<BadCommodityRepeat> badCommodityRepeatList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(badCommodityRepeatList) || badCommodityRepeatList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (BadCommodityRepeat badCommodityRepeat : badCommodityRepeatList)
        {
            try
            {
                // 验证数据是否已经
                BadCommodityRepeat domain = badCommodityRepeatMapper.selectBadCommodityRepeatByOnlyCondition(badCommodityRepeat);
                if (domain==null)
                {
                    badCommodityRepeat.setCreateBy(operName);
                    badCommodityRepeat.setCreateTime(new Date());
                    this.insertBadCommodityRepeat(badCommodityRepeat);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ badCommodityRepeat.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    badCommodityRepeat.setUpdateBy(operName);
                    badCommodityRepeat.setUpdateTime(new Date());
                    badCommodityRepeatMapper.updateBadCommodityRepeatByOnlyCondition(badCommodityRepeat);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + badCommodityRepeat.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + badCommodityRepeat.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + badCommodityRepeat.toString()+" 的数据导入失败：";
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
