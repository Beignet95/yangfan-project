package com.ruoyi.project.oms.orderRecord.mapper;

import java.util.List;
import com.ruoyi.project.oms.orderRecord.domain.OrderRecord;

/**
 * 订单记录Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-19
 */
public interface OrderRecordMapper 
{
    /**
     * 查询订单记录
     * 
     * @param id 订单记录ID
     * @return 订单记录
     */
    public OrderRecord selectOrderRecordById(Long id);

    /**
     * 查询订单记录列表
     * 
     * @param orderRecord 订单记录
     * @return 订单记录集合
     */
    public List<OrderRecord> selectOrderRecordList(OrderRecord orderRecord);

    /**
     * 新增订单记录
     * 
     * @param orderRecord 订单记录
     * @return 结果
     */
    public int insertOrderRecord(OrderRecord orderRecord);

    /**
     * 修改订单记录
     * 
     * @param orderRecord 订单记录
     * @return 结果
     */
    public int updateOrderRecord(OrderRecord orderRecord);

    /**
     * 删除订单记录
     * 
     * @param id 订单记录ID
     * @return 结果
     */
    public int deleteOrderRecordById(Long id);

    /**
     * 批量删除订单记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderRecordByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param OrderRecord ${subTable.functionName}
     * @return 结果
     */
    public int updateOrderRecordByOnlyCondition(OrderRecord orderRecord);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param OrderRecord ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public OrderRecord selectOrderRecordByOnlyCondition(OrderRecord orderRecord);
}
