package com.ruoyi.project.oms.orderRefund.mapper;

import java.util.List;
import com.ruoyi.project.oms.orderRefund.domain.OrderRefund;

/**
 * 退款Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-02
 */
public interface OrderRefundMapper
{
    /**
     * 查询退款
     * 
     * @param id 退款ID
     * @return 退款
     */
    public OrderRefund selectOrderRefundById(Long id);

    /**
     * 查询退款列表
     * 
     * @param OrderRefund 退款
     * @return 退款集合
     */
    public List<OrderRefund> selectOrderRefundList(OrderRefund OrderRefund);

    /**
     * 新增退款
     * 
     * @param OrderRefund 退款
     * @return 结果
     */
    public int insertOrderRefund(OrderRefund OrderRefund);

    /**
     * 修改退款
     * 
     * @param OrderRefund 退款
     * @return 结果
     */
    public int updateOrderRefund(OrderRefund OrderRefund);

    /**
     * 删除退款
     * 
     * @param id 退款ID
     * @return 结果
     */
    public int deleteOrderRefundById(Long id);

    /**
     * 批量删除退款
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderRefundByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param OrderRefund ${subTable.functionName}
     * @return 结果
     */
    public int updateOrderRefundByOnlyCondition(OrderRefund OrderRefund);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param OrderRefund ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public OrderRefund selectOrderRefundByOnlyCondition(OrderRefund OrderRefund);
}
