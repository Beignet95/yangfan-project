package com.ruoyi.project.oms.refundServicefee.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.project.oms.refundServicefee.domain.RefundServicefee;

/**
 * 平台退款服务费Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-10
 */
public interface RefundServicefeeMapper 
{
    /**
     * 查询平台退款服务费
     * 
     * @param id 平台退款服务费ID
     * @return 平台退款服务费
     */
    public RefundServicefee selectRefundServicefeeById(Long id);

    /**
     * 查询平台退款服务费列表
     * 
     * @param refundServicefee 平台退款服务费
     * @return 平台退款服务费集合
     */
    public List<RefundServicefee> selectRefundServicefeeList(RefundServicefee refundServicefee);

    /**
     * 新增平台退款服务费
     * 
     * @param refundServicefee 平台退款服务费
     * @return 结果
     */
    public int insertRefundServicefee(RefundServicefee refundServicefee);

    /**
     * 修改平台退款服务费
     * 
     * @param refundServicefee 平台退款服务费
     * @return 结果
     */
    public int updateRefundServicefee(RefundServicefee refundServicefee);

    /**
     * 删除平台退款服务费
     * 
     * @param id 平台退款服务费ID
     * @return 结果
     */
    public int deleteRefundServicefeeById(Long id);

    /**
     * 批量删除平台退款服务费
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRefundServicefeeByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param RefundServicefee ${subTable.functionName}
     * @return 结果
     */
    public int updateRefundServicefeeByOnlyCondition(RefundServicefee refundServicefee);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param RefundServicefee ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public RefundServicefee selectRefundServicefeeByOnlyCondition(RefundServicefee refundServicefee);

    /**
     * 更具月份和账号删除记录
     * @param month
     * @param account
     * @return
     */
    int deleteRefundServicefeeByTypeAndAccount(Date month, String account);
}
