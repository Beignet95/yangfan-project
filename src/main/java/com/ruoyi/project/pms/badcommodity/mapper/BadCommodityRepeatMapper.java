package com.ruoyi.project.pms.badcommodity.mapper;

import java.util.List;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodityRepeat;

/**
 * 不良记录重复数据Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-18
 */
public interface BadCommodityRepeatMapper 
{
    /**
     * 查询不良记录重复数据
     * 
     * @param id 不良记录重复数据ID
     * @return 不良记录重复数据
     */
    public BadCommodityRepeat selectBadCommodityRepeatById(Long id);

    /**
     * 查询不良记录重复数据列表
     * 
     * @param badCommodityRepeat 不良记录重复数据
     * @return 不良记录重复数据集合
     */
    public List<BadCommodityRepeat> selectBadCommodityRepeatList(BadCommodityRepeat badCommodityRepeat);

    /**
     * 新增不良记录重复数据
     * 
     * @param badCommodityRepeat 不良记录重复数据
     * @return 结果
     */
    public int insertBadCommodityRepeat(BadCommodityRepeat badCommodityRepeat);

    /**
     * 修改不良记录重复数据
     * 
     * @param badCommodityRepeat 不良记录重复数据
     * @return 结果
     */
    public int updateBadCommodityRepeat(BadCommodityRepeat badCommodityRepeat);

    /**
     * 删除不良记录重复数据
     * 
     * @param id 不良记录重复数据ID
     * @return 结果
     */
    public int deleteBadCommodityRepeatById(Long id);

    /**
     * 批量删除不良记录重复数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBadCommodityRepeatByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param BadCommodityRepeat ${subTable.functionName}
     * @return 结果
     */
    public int updateBadCommodityRepeatByOnlyCondition(BadCommodityRepeat badCommodityRepeat);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param BadCommodityRepeat ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public BadCommodityRepeat selectBadCommodityRepeatByOnlyCondition(BadCommodityRepeat badCommodityRepeat);
}
