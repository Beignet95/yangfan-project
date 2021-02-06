package com.ruoyi.project.oms.orderRefundRepeat.service.impl;

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
import com.ruoyi.project.oms.orderRefundRepeat.mapper.OrderRefundRepeatMapper;
import com.ruoyi.project.oms.orderRefundRepeat.domain.OrderRefundRepeat;
import com.ruoyi.project.oms.orderRefundRepeat.service.IOrderRefundRepeatService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 退款(重复数据）Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-02
 */
@Service
public class OrderRefundRepeatServiceImpl implements IOrderRefundRepeatService 
{
    private static final Logger log = LoggerFactory.getLogger(OrderRefundRepeat.class);

    @Autowired
    private OrderRefundRepeatMapper orderRefundRepeatMapper;

    /**
     * 查询退款(重复数据）
     * 
     * @param id 退款(重复数据）ID
     * @return 退款(重复数据）
     */
    @Override
    public OrderRefundRepeat selectOrderRefundRepeatById(Long id)
    {
        return orderRefundRepeatMapper.selectOrderRefundRepeatById(id);
    }

    /**
     * 查询退款(重复数据）列表
     * 
     * @param orderRefundRepeat 退款(重复数据）
     * @return 退款(重复数据）
     */
    @Override
    public List<OrderRefundRepeat> selectOrderRefundRepeatList(OrderRefundRepeat orderRefundRepeat)
    {
        return orderRefundRepeatMapper.selectOrderRefundRepeatList(orderRefundRepeat);
    }

    /**
     * 新增退款(重复数据）
     * 
     * @param orderRefundRepeat 退款(重复数据）
     * @return 结果
     */
    @Override
    public int insertOrderRefundRepeat(OrderRefundRepeat orderRefundRepeat)
    {
        orderRefundRepeat.setCreateTime(DateUtils.getNowDate());
        return orderRefundRepeatMapper.insertOrderRefundRepeat(orderRefundRepeat);
    }

    /**
     * 修改退款(重复数据）
     * 
     * @param orderRefundRepeat 退款(重复数据）
     * @return 结果
     */
    @Override
    public int updateOrderRefundRepeat(OrderRefundRepeat orderRefundRepeat)
    {
        orderRefundRepeat.setUpdateTime(DateUtils.getNowDate());
        return orderRefundRepeatMapper.updateOrderRefundRepeat(orderRefundRepeat);
    }

    /**
     * 删除退款(重复数据）对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderRefundRepeatByIds(String ids)
    {
        return orderRefundRepeatMapper.deleteOrderRefundRepeatByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除退款(重复数据）信息
     * 
     * @param id 退款(重复数据）ID
     * @return 结果
     */
    @Override
    public int deleteOrderRefundRepeatById(Long id)
    {
        return orderRefundRepeatMapper.deleteOrderRefundRepeatById(id);
    }

    /**
     * 导入退款(重复数据）
     *
     * @param orderRefundRepeatList 退款(重复数据）List数据
     * @return 导入结果
     */
    @Override
    public String importOrderRefundRepeat(List<OrderRefundRepeat> orderRefundRepeatList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(orderRefundRepeatList) || orderRefundRepeatList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (OrderRefundRepeat orderRefundRepeat : orderRefundRepeatList)
        {
            try
            {
                // 验证数据是否已经
                OrderRefundRepeat domain = orderRefundRepeatMapper.selectOrderRefundRepeatByOnlyCondition(orderRefundRepeat);
                if (domain==null)
                {
                    orderRefundRepeat.setCreateBy(operName);
                    orderRefundRepeat.setCreateTime(new Date());
                    this.insertOrderRefundRepeat(orderRefundRepeat);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ orderRefundRepeat.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    orderRefundRepeat.setUpdateBy(operName);
                    orderRefundRepeat.setUpdateTime(new Date());
                    orderRefundRepeatMapper.updateOrderRefundRepeatByOnlyCondition(orderRefundRepeat);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + orderRefundRepeat.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + orderRefundRepeat.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + orderRefundRepeat.toString()+" 的数据导入失败：";
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
