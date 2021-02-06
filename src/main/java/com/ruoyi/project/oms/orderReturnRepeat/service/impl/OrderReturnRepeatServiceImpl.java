package com.ruoyi.project.oms.orderReturnRepeat.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.oms.orderReturnRepeat.mapper.OrderReturnRepeatMapper;
import com.ruoyi.project.oms.orderReturnRepeat.domain.OrderReturnRepeat;
import com.ruoyi.project.oms.orderReturnRepeat.service.IOrderReturnRepeatService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 退货重复数据Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-03
 */
@Service
public class OrderReturnRepeatServiceImpl implements IOrderReturnRepeatService 
{
    private static final Logger log = LoggerFactory.getLogger(OrderReturnRepeat.class);

    @Autowired
    private OrderReturnRepeatMapper orderReturnRepeatMapper;

    /**
     * 查询退货重复数据
     * 
     * @param id 退货重复数据ID
     * @return 退货重复数据
     */
    @Override
    public OrderReturnRepeat selectOrderReturnRepeatById(Long id)
    {
        return orderReturnRepeatMapper.selectOrderReturnRepeatById(id);
    }

    /**
     * 查询退货重复数据列表
     * 
     * @param orderReturnRepeat 退货重复数据
     * @return 退货重复数据
     */
    @Override
    public List<OrderReturnRepeat> selectOrderReturnRepeatList(OrderReturnRepeat orderReturnRepeat)
    {
        return orderReturnRepeatMapper.selectOrderReturnRepeatList(orderReturnRepeat);
    }

    /**
     * 新增退货重复数据
     * 
     * @param orderReturnRepeat 退货重复数据
     * @return 结果
     */
    @Override
    public int insertOrderReturnRepeat(OrderReturnRepeat orderReturnRepeat)
    {
        orderReturnRepeat.setCreateTime(DateUtils.getNowDate());
        return orderReturnRepeatMapper.insertOrderReturnRepeat(orderReturnRepeat);
    }

    /**
     * 修改退货重复数据
     * 
     * @param orderReturnRepeat 退货重复数据
     * @return 结果
     */
    @Override
    public int updateOrderReturnRepeat(OrderReturnRepeat orderReturnRepeat)
    {
        orderReturnRepeat.setUpdateTime(DateUtils.getNowDate());
        return orderReturnRepeatMapper.updateOrderReturnRepeat(orderReturnRepeat);
    }

    /**
     * 删除退货重复数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderReturnRepeatByIds(String ids)
    {
        return orderReturnRepeatMapper.deleteOrderReturnRepeatByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除退货重复数据信息
     * 
     * @param id 退货重复数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderReturnRepeatById(Long id)
    {
        return orderReturnRepeatMapper.deleteOrderReturnRepeatById(id);
    }

    /**
     * 导入退货重复数据
     *
     * @param orderReturnRepeatList 退货重复数据List数据
     * @return 导入结果
     */
    @Override
    public String importOrderReturnRepeat(List<OrderReturnRepeat> orderReturnRepeatList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(orderReturnRepeatList) || orderReturnRepeatList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (OrderReturnRepeat orderReturnRepeat : orderReturnRepeatList)
        {
            try
            {
                // 验证数据是否已经
                OrderReturnRepeat domain = orderReturnRepeatMapper.selectOrderReturnRepeatByOnlyCondition(orderReturnRepeat);
                if (domain==null)
                {
                    orderReturnRepeat.setCreateBy(operName);
                    orderReturnRepeat.setCreateTime(new Date());
                    this.insertOrderReturnRepeat(orderReturnRepeat);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ orderReturnRepeat.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    orderReturnRepeat.setUpdateBy(operName);
                    orderReturnRepeat.setUpdateTime(new Date());
                    orderReturnRepeatMapper.updateOrderReturnRepeatByOnlyCondition(orderReturnRepeat);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + orderReturnRepeat.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + orderReturnRepeat.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + orderReturnRepeat.toString()+" 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
