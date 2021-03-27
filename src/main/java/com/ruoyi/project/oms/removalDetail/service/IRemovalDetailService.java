package com.ruoyi.project.oms.removalDetail.service;

import java.util.Date;
import java.util.List;
import com.ruoyi.project.oms.removalDetail.domain.RemovalDetail;

/**
 * 移除明细Service接口
 * 
 * @author Beignet
 * @date 2021-03-08
 */
public interface IRemovalDetailService 
{
    /**
     * 查询移除明细
     * 
     * @param id 移除明细ID
     * @return 移除明细
     */
    public RemovalDetail selectRemovalDetailById(Long id);

    /**
     * 查询移除明细列表
     * 
     * @param removalDetail 移除明细
     * @return 移除明细集合
     */
    public List<RemovalDetail> selectRemovalDetailList(RemovalDetail removalDetail);

    /**
     * 新增移除明细
     * 
     * @param removalDetail 移除明细
     * @return 结果
     */
    public int insertRemovalDetail(RemovalDetail removalDetail);

    /**
     * 修改移除明细
     * 
     * @param removalDetail 移除明细
     * @return 结果
     */
    public int updateRemovalDetail(RemovalDetail removalDetail);

    /**
     * 批量删除移除明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRemovalDetailByIds(String ids);

    /**
     * 删除移除明细信息
     * 
     * @param id 移除明细ID
     * @return 结果
     */
    public int deleteRemovalDetailById(Long id);

    /**
     * 导入移除明细
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importRemovalDetail(List<RemovalDetail> removalDetailList, boolean isUpdateSupport, String account, Date month);


    int unlockData(Date month, String account);
}
