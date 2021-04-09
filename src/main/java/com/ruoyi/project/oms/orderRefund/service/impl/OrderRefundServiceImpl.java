package com.ruoyi.project.oms.orderRefund.service.impl;

import java.util.*;

import com.ruoyi.project.oms.orderRefund.service.IOrderRefundService;
import com.ruoyi.project.oms.orderRefundRepeat.domain.OrderRefundRepeat;
import com.ruoyi.project.oms.orderRefundRepeat.service.IOrderRefundRepeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.oms.orderRefund.mapper.OrderRefundMapper;
import com.ruoyi.project.oms.orderRefund.domain.OrderRefund;
import com.ruoyi.common.utils.text.Convert;

/**
 * 退款Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-02
 */
@Service
public class OrderRefundServiceImpl implements IOrderRefundService
{
    private static final Logger log = LoggerFactory.getLogger(OrderRefund.class);

    @Autowired
    private OrderRefundMapper orderRefundMapper;

    @Autowired
    private IOrderRefundRepeatService orderRefundRepeatService;

    /**
     * 查询退款
     * 
     * @param id 退款ID
     * @return 退款
     */
    @Override
    public OrderRefund selectOrderRefundById(Long id)
    {
        return orderRefundMapper.selectOrderRefundById(id);
    }

    /**
     * 查询退款列表
     * 
     * @param OrderRefund 退款
     * @return 退款
     */
    @Override
    public List<OrderRefund> selectOrderRefundList(OrderRefund OrderRefund)
    {
        return orderRefundMapper.selectOrderRefundList(OrderRefund);
    }

    /**
     * 新增退款
     * 
     * @param OrderRefund 退款
     * @return 结果
     */
    @Override
    public int insertOrderRefund(OrderRefund OrderRefund)
    {
        OrderRefund.setCreateTime(DateUtils.getNowDate());
        return orderRefundMapper.insertOrderRefund(OrderRefund);
    }

    /**
     * 修改退款
     * 
     * @param OrderRefund 退款
     * @return 结果
     */
    @Override
    public int updateOrderRefund(OrderRefund OrderRefund)
    {
        OrderRefund.setUpdateTime(DateUtils.getNowDate());
        return orderRefundMapper.updateOrderRefund(OrderRefund);
    }

    /**
     * 删除退款对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderRefundByIds(String ids)
    {
        return orderRefundMapper.deleteOrderRefundByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除退款信息
     * 
     * @param id 退款ID
     * @return 结果
     */
    @Override
    public int deleteOrderRefundById(Long id)
    {
        return orderRefundMapper.deleteOrderRefundById(id);
    }

    /**
     * 导入退款
     *
     * @param orderRefundList 退款List数据
     * @return 导入结果
     */
    @Override
    public String importOrderRefund(List<OrderRefund> orderRefundList, boolean isUpdateSupport) {
        if (StringUtils.isNull(orderRefundList) || orderRefundList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        int repeatNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        StringBuilder repeatMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();

        //保存重复数据
        //保留size>1的值，就是保留具有重复数据的value
        orderRefundList =  orderRefundList.stream().filter(k->StringUtils.isNotEmpty(k.getOrderId())).collect(Collectors.toList());
        Map<String, List<OrderRefund>> repeatMap = orderRefundList.stream().collect(Collectors.groupingBy(OrderRefund::getOrderId));
        repeatMap.entrySet().removeIf(m -> m.getValue().size()==1);
        String repeatId = UUID.randomUUID().toString().replaceAll("-","");
        if(repeatMap!=null&&repeatMap.size()>0)
        for(List<OrderRefund> list : repeatMap.values()){
            for (OrderRefund OrderRefund:list){
                OrderRefundRepeat OrderRefundRepeat = new OrderRefundRepeat();
                BeanUtils.copyProperties(OrderRefund,OrderRefundRepeat);
                OrderRefundRepeat.setRepeatId(repeatId);
                orderRefundRepeatService.insertOrderRefundRepeat(OrderRefundRepeat);
                repeatNum++;
                repeatMsg.append("<br/>" + repeatNum + "、订单号为" + OrderRefund.getOrderId()+" 的数据为此Excel的重复数据");
            }
        }

        for (OrderRefund OrderRefund : orderRefundList)
        {
            try
            {
                // 验证数据是否已经
                OrderRefund domain = orderRefundMapper.selectOrderRefundByOnlyCondition(OrderRefund);
                if(!repeatMap.containsKey(OrderRefund.getOrderId())){//不是重复数据，判断是插入还是更新
                    if (domain==null)
                    {
                        OrderRefund.setCreateBy(operName);
                        OrderRefund.setCreateTime(new Date());
                        this.insertOrderRefund(OrderRefund);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、订单号为"+ OrderRefund.getOrderId()+" 的数据导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        OrderRefund.setUpdateBy(operName);
                        OrderRefund.setUpdateTime(new Date());
                        orderRefundMapper.updateOrderRefundByOnlyCondition(OrderRefund);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、订单号为" + OrderRefund.getOrderId()+" 的数据更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、订单号为" + OrderRefund.getOrderId()+" 的数据已存在");
                    }
                }else {//是重复数据，不做操作

                }

            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、订单号为" + OrderRefund.getOrderId()+" 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        // /oms/orderRefundRepeat/export
        if(failureNum>0&&repeatNum>0){
            failureMsg.insert(0,"<button onclick=\"window.open('/oms/orderRefundRepeat?repeatId=\"+repeatId+\"')\">查看重复数据</button><br/>共 "+repeatNum+" 条数据重复，共有 "+failureNum+" 条数据格式不正确。<br/>"+
                    "其中错误数据如下（Excel上的重复数据不列出）：");
            throw new BusinessException(failureMsg.toString());
        }
        else if(repeatNum>0){
            repeatMsg.insert(0,"<button onclick=\"window.open('/oms/orderRefundRepeat?repeatId="+repeatId+"')\">查看重复数据</button><br/>共 "+repeatNum+" 条数据重复,重复数据如下");
            throw new BusinessException(repeatMsg.toString());
        }
        else if (failureNum > 0)
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

    @Override
    public OrderRefund selectOrderRefundByOnlyCondition(OrderRefund orderRefund) {
        return orderRefundMapper.selectOrderRefundByOnlyCondition(orderRefund);
    }
}
