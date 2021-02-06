package com.ruoyi.project.oms.oderReturn.service;

import java.util.List;
import com.ruoyi.project.oms.oderReturn.domain.OrderReturn;

/**
 * 退货Service接口
 * 
 * @author Beignet
 * @date 2021-02-03
 */
public interface IOrderReturnService 
{
    /**
     * 查询退货
     * 
     * @param id 退货ID
     * @return 退货
     */
    public OrderReturn selectOrderReturnById(Long id);

    /**
     * 查询退货列表
     * 
     * @param orderReturn 退货
     * @return 退货集合
     */
    public List<OrderReturn> selectOrderReturnList(OrderReturn orderReturn);

    /**
     * 新增退货
     * 
     * @param orderReturn 退货
     * @return 结果
     */
    public int insertOrderReturn(OrderReturn orderReturn);

    /**
     * 修改退货
     * 
     * @param orderReturn 退货
     * @return 结果
     */
    public int updateOrderReturn(OrderReturn orderReturn);

    /**
     * 批量删除退货
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderReturnByIds(String ids);

    /**
     * 删除退货信息
     * 
     * @param id 退货ID
     * @return 结果
     */
    public int deleteOrderReturnById(Long id);

    /**
     * 导入退货
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importOrderReturn(List<OrderReturn> orderReturnList, boolean isUpdateSupport);


    OrderReturn selectOrderReturnByOrderId(String orderId);
}
