package com.ruoyi.project.oms.orderRefundRepeat.service;

import java.util.List;
import com.ruoyi.project.oms.orderRefundRepeat.domain.OrderRefundRepeat;

/**
 * 退款(重复数据）Service接口
 * 
 * @author Beignet
 * @date 2021-02-02
 */
public interface IOrderRefundRepeatService 
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
     * 批量删除退款(重复数据）
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderRefundRepeatByIds(String ids);

    /**
     * 删除退款(重复数据）信息
     * 
     * @param id 退款(重复数据）ID
     * @return 结果
     */
    public int deleteOrderRefundRepeatById(Long id);

    /**
     * 导入退款(重复数据）
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importOrderRefundRepeat(List<OrderRefundRepeat> orderRefundRepeatList, boolean isUpdateSupport);


}
