package com.ruoyi.project.oms.orderReturnRepeat.service;

import java.util.List;
import com.ruoyi.project.oms.orderReturnRepeat.domain.OrderReturnRepeat;

/**
 * 退货重复数据Service接口
 * 
 * @author Beignet
 * @date 2021-02-03
 */
public interface IOrderReturnRepeatService 
{
    /**
     * 查询退货重复数据
     * 
     * @param id 退货重复数据ID
     * @return 退货重复数据
     */
    public OrderReturnRepeat selectOrderReturnRepeatById(Long id);

    /**
     * 查询退货重复数据列表
     * 
     * @param orderReturnRepeat 退货重复数据
     * @return 退货重复数据集合
     */
    public List<OrderReturnRepeat> selectOrderReturnRepeatList(OrderReturnRepeat orderReturnRepeat);

    /**
     * 新增退货重复数据
     * 
     * @param orderReturnRepeat 退货重复数据
     * @return 结果
     */
    public int insertOrderReturnRepeat(OrderReturnRepeat orderReturnRepeat);

    /**
     * 修改退货重复数据
     * 
     * @param orderReturnRepeat 退货重复数据
     * @return 结果
     */
    public int updateOrderReturnRepeat(OrderReturnRepeat orderReturnRepeat);

    /**
     * 批量删除退货重复数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderReturnRepeatByIds(String ids);

    /**
     * 删除退货重复数据信息
     * 
     * @param id 退货重复数据ID
     * @return 结果
     */
    public int deleteOrderReturnRepeatById(Long id);

    /**
     * 导入退货重复数据
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importOrderReturnRepeat(List<OrderReturnRepeat> orderReturnRepeatList, boolean isUpdateSupport);


}
