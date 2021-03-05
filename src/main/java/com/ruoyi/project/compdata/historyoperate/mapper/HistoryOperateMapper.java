package com.ruoyi.project.compdata.historyoperate.mapper;

import java.util.List;
import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;

/**
 * 历史操作记录Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-26
 */
public interface HistoryOperateMapper 
{
    /**
     * 查询历史操作记录
     * 
     * @param id 历史操作记录ID
     * @return 历史操作记录
     */
    public HistoryOperate selectHistoryOperateById(Long id);

    /**
     * 查询历史操作记录列表
     * 
     * @param historyOperate 历史操作记录
     * @return 历史操作记录集合
     */
    public List<HistoryOperate> selectHistoryOperateList(HistoryOperate historyOperate);

    /**
     * 新增历史操作记录
     * 
     * @param historyOperate 历史操作记录
     * @return 结果
     */
    public int insertHistoryOperate(HistoryOperate historyOperate);

    /**
     * 修改历史操作记录
     * 
     * @param historyOperate 历史操作记录
     * @return 结果
     */
    public int updateHistoryOperate(HistoryOperate historyOperate);

    /**
     * 删除历史操作记录
     * 
     * @param id 历史操作记录ID
     * @return 结果
     */
    public int deleteHistoryOperateById(Long id);

    /**
     * 批量删除历史操作记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteHistoryOperateByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param HistoryOperate ${subTable.functionName}
     * @return 结果
     */
    public int updateHistoryOperateByOnlyCondition(HistoryOperate historyOperate);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param HistoryOperate ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public HistoryOperate selectHistoryOperateByOnlyCondition(HistoryOperate historyOperate);
}
