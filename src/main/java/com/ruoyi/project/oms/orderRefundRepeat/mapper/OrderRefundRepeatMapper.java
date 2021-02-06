package com.ruoyi.project.oms.orderRefundRepeat.mapper;

import java.util.List;
import com.ruoyi.project.oms.orderRefundRepeat.domain.OrderRefundRepeat;

/**
 * 退款(重复数据）Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-02
 */
public interface OrderRefundRepeatMapper 
{
    /**
     * 查询退款(重复数据）
     * 
     * @param id 退款(重复数据）ID
     * @return 退款(重复数据）
     */
    public OrderRefundRepeat selectOrderRefundRepeatById(Long id);

    /**
     * 查询退款(重复数据）列表
     * 
     * @param orderRefundRepeat 退款(重复数据）
     * @return 退款(重复数据）集合
     */
    public List<OrderRefundRepeat> selectOrderRefundRepeatList(OrderRefundRepeat orderRefundRepeat);

    /**
     * 新增退款(重复数据）
     * 
     * @param orderRefundRepeat 退款(重复数据）
     * @return 结果
     */
    public int insertOrderRefundRepeat(OrderRefundRepeat orderRefundRepeat);

    /**
     * 修改退款(重复数据）
     * 
     * @param orderRefundRepeat 退款(重复数据）
     * @return 结果
     */
    public int updateOrderRefundRepeat(OrderRefundRepeat orderRefundRepeat);

    /**
     * 删除退款(重复数据）
     * 
     * @param id 退款(重复数据）ID
     * @return 结果
     */
    public int deleteOrderRefundRepeatById(Long id);

    /**
     * 批量删除退款(重复数据）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderRefundRepeatByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param OrderRefundRepeat ${subTable.functionName}
     * @return 结果
     */
    public int updateOrderRefundRepeatByOnlyCondition(OrderRefundRepeat orderRefundRepeat);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param OrderRefundRepeat ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public OrderRefundRepeat selectOrderRefundRepeatByOnlyCondition(OrderRefundRepeat orderRefundRepeat);
}
