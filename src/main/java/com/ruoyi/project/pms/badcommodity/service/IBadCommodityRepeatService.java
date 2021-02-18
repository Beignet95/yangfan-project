package com.ruoyi.project.pms.badcommodity.service;

import java.util.List;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodityRepeat;

/**
 * 不良记录重复数据Service接口
 * 
 * @author Beignet
 * @date 2021-02-18
 */
public interface IBadCommodityRepeatService 
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
     * 批量删除不良记录重复数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBadCommodityRepeatByIds(String ids);

    /**
     * 删除不良记录重复数据信息
     * 
     * @param id 不良记录重复数据ID
     * @return 结果
     */
    public int deleteBadCommodityRepeatById(Long id);

    /**
     * 导入不良记录重复数据
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importBadCommodityRepeat(List<BadCommodityRepeat> badCommodityRepeatList, boolean isUpdateSupport);


}
