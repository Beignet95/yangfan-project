package com.ruoyi.project.pms.badcommodity.mapper;

import java.util.List;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodity;

/**
 * 不良记录Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-01
 */
public interface BadCommodityMapper 
{
    /**
     * 查询不良记录
     * 
     * @param id 不良记录ID
     * @return 不良记录
     */
    public BadCommodity selectBadCommodityById(Long id);

    /**
     * 查询不良记录列表
     * 
     * @param badCommodity 不良记录
     * @return 不良记录集合
     */
    public List<BadCommodity> selectBadCommodityList(BadCommodity badCommodity);

    /**
     * 新增不良记录
     * 
     * @param badCommodity 不良记录
     * @return 结果
     */
    public int insertBadCommodity(BadCommodity badCommodity);

    /**
     * 修改不良记录
     * 
     * @param badCommodity 不良记录
     * @return 结果
     */
    public int updateBadCommodity(BadCommodity badCommodity);

    /**
     * 删除不良记录
     * 
     * @param id 不良记录ID
     * @return 结果
     */
    public int deleteBadCommodityById(Long id);

    /**
     * 批量删除不良记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBadCommodityByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param BadCommodity ${subTable.functionName}
     * @return 结果
     */
    public int updateBadCommodityByOnlyCondition(BadCommodity badCommodity);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param BadCommodity ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public BadCommodity selectBadCommodityByOnlyCondition(BadCommodity badCommodity);
}
