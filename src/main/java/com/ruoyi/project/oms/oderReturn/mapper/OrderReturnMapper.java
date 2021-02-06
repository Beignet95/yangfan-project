package com.ruoyi.project.oms.oderReturn.mapper;

import java.util.List;
import com.ruoyi.project.oms.oderReturn.domain.OrderReturn;

/**
 * 退货Mapper接口
 * 
 * @author Beignet
 * @date 2021-02-03
 */
public interface OrderReturnMapper 
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
     * 删除退货
     * 
     * @param id 退货ID
     * @return 结果
     */
    public int deleteOrderReturnById(Long id);

    /**
     * 批量删除退货
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOrderReturnByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param OrderReturn ${subTable.functionName}
     * @return 结果
     */
    public int updateOrderReturnByOnlyCondition(OrderReturn orderReturn);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param OrderReturn ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public OrderReturn selectOrderReturnByOnlyCondition(OrderReturn orderReturn);
}
