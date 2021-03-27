package com.ruoyi.project.oms.removalDetail.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.project.oms.removalDetail.domain.RemovalDetail;

/**
 * 移除明细Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-08
 */
public interface RemovalDetailMapper 
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
     * 删除移除明细
     * 
     * @param id 移除明细ID
     * @return 结果
     */
    public int deleteRemovalDetailById(Long id);

    /**
     * 批量删除移除明细
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRemovalDetailByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param RemovalDetail ${subTable.functionName}
     * @return 结果
     */
    public int updateRemovalDetailByOnlyCondition(RemovalDetail removalDetail);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param RemovalDetail ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public RemovalDetail selectRemovalDetailByOnlyCondition(RemovalDetail removalDetail);

    /**
     * 更具月份和账号删除移除明细
     * @param month
     * @param account
     * @return
     */
    int deleteRemovalDetailByTypeAndAccount(Date month, String account);
}
