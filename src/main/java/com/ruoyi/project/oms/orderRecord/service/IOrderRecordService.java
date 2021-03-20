package com.ruoyi.project.oms.orderRecord.service;

import java.util.List;
import com.ruoyi.project.oms.orderRecord.domain.OrderRecord;

/**
 * 订单记录Service接口
 * 
 * @author Beignet
 * @date 2021-03-19
 */
public interface IOrderRecordService 
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
     * 批量删除订单记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderRecordByIds(String ids);

    /**
     * 删除订单记录信息
     * 
     * @param id 订单记录ID
     * @return 结果
     */
    public int deleteOrderRecordById(Long id);

    /**
     * 导入订单记录
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importOrderRecord(List<OrderRecord> orderRecordList, boolean isUpdateSupport);


}
