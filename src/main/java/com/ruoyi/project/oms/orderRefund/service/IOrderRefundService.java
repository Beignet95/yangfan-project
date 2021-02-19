package com.ruoyi.project.oms.orderRefund.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.oms.orderRefund.domain.OrderRefund;

/**
 * 退款Service接口
 * 
 * @author Beignet
 * @date 2021-02-02
 */
public interface IOrderRefundService
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
     * 批量删除退款
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderRefundByIds(String ids);

    /**
     * 删除退款信息
     * 
     * @param id 退款ID
     * @return 结果
     */
    public int deleteOrderRefundById(Long id);

    /**
     * 导入退款
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importOrderRefund(List<OrderRefund> OrderRefundList, boolean isUpdateSupport);


    OrderRefund selectOrderRefundByOnlyCondition(OrderRefund orderRefund);
}
