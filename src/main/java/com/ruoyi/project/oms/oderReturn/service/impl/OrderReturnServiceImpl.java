package com.ruoyi.project.oms.oderReturn.service.impl;

import java.util.*;

import com.ruoyi.project.oms.orderRefund.domain.OrderRefund;
import com.ruoyi.project.oms.orderRefundRepeat.domain.OrderRefundRepeat;
import com.ruoyi.project.oms.orderReturnRepeat.domain.OrderReturnRepeat;
import com.ruoyi.project.oms.orderReturnRepeat.mapper.OrderReturnRepeatMapper;
import com.ruoyi.project.oms.orderReturnRepeat.service.IOrderReturnRepeatService;
import com.ruoyi.project.system.dict.domain.DictData;
import com.ruoyi.project.system.dict.service.IDictDataService;
import com.ruoyi.project.system.dict.service.IDictTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.oms.oderReturn.mapper.OrderReturnMapper;
import com.ruoyi.project.oms.oderReturn.domain.OrderReturn;
import com.ruoyi.project.oms.oderReturn.service.IOrderReturnService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 退货Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-03
 */
@Service
public class OrderReturnServiceImpl implements IOrderReturnService 
{
    private static final Logger log = LoggerFactory.getLogger(OrderReturn.class);

    @Autowired
    private OrderReturnMapper orderReturnMapper;

    @Autowired
    private IOrderReturnRepeatService orderReturnRepeatService;

    @Autowired
    private IDictDataService dictDataService;

    @Autowired
    private IDictTypeService dictTypeService;

    /**
     * 查询退货
     * 
     * @param id 退货ID
     * @return 退货
     */
    @Override
    public OrderReturn selectOrderReturnById(Long id)
    {
        return orderReturnMapper.selectOrderReturnById(id);
    }

    /**
     * 查询退货列表
     * 
     * @param orderReturn 退货
     * @return 退货
     */
    @Override
    public List<OrderReturn> selectOrderReturnList(OrderReturn orderReturn)
    {
        return orderReturnMapper.selectOrderReturnList(orderReturn);
    }

    /**
     * 新增退货
     * 
     * @param orderReturn 退货
     * @return 结果
     */
    @Override
    public int insertOrderReturn(OrderReturn orderReturn)
    {
        orderReturn.setCreateTime(DateUtils.getNowDate());
        return orderReturnMapper.insertOrderReturn(orderReturn);
    }

    /**
     * 修改退货
     * 
     * @param orderReturn 退货
     * @return 结果
     */
    @Override
    public int updateOrderReturn(OrderReturn orderReturn)
    {
        orderReturn.setUpdateTime(DateUtils.getNowDate());
        return orderReturnMapper.updateOrderReturn(orderReturn);
    }

    /**
     * 删除退货对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderReturnByIds(String ids)
    {
        return orderReturnMapper.deleteOrderReturnByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除退货信息
     * 
     * @param id 退货ID
     * @return 结果
     */
    @Override
    public int deleteOrderReturnById(Long id)
    {
        return orderReturnMapper.deleteOrderReturnById(id);
    }

    /**
     * 导入退货
     *
     * @param orderReturnList 退货List数据
     * @return 导入结果
     */
    @Override
    public String importOrderReturn(List<OrderReturn> orderReturnList, boolean isUpdateSupport) {
        if (orderReturnList==null || orderReturnList.size() == 0)
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

        //从字典中获取翻译值List，并将List转为Map,方便获取
        List<DictData> returnReasonDictList = dictTypeService.selectDictDataByType("oms_return_reason");
        Map returnReasonDictMap = returnReasonDictList.stream().collect(Collectors.toMap(DictData::getDictLabel,
                DictData::getDictValue, (key1, key2) -> key2));

        //保存重复数据
        //保留size>1的值，就是保留具有重复数据的value
        Map<String, List<OrderReturn>> repeatMap = orderReturnList.stream().collect(Collectors.groupingBy(OrderReturn::getOrderId));
        repeatMap.entrySet().removeIf(m -> m.getValue().size()==1);
        String repeatId = UUID.randomUUID().toString().replaceAll("-","");
        if(repeatMap!=null&&repeatMap.size()>0)
            for(List<OrderReturn> list : repeatMap.values()){
                for (OrderReturn orderReturn:list){
                    OrderReturnRepeat orderReturnRepeat = new OrderReturnRepeat();
                    BeanUtils.copyProperties(orderReturn,orderReturnRepeat);
                    orderReturnRepeat.setRepeatId(repeatId);
                    //从字段中获取翻译值
                    orderReturnRepeat.setDetailedDispositionForcn((String)returnReasonDictMap.get(orderReturn.getDetailedDisposition()));
                    orderReturnRepeat.setReasonForcn((String)returnReasonDictMap.get(orderReturn.getReason()));
                    orderReturnRepeatService.insertOrderReturnRepeat(orderReturnRepeat);
                    repeatNum++;
                    repeatMsg.append("<br/>" + repeatNum + "、订单号为" + orderReturn.getOrderId()+" 的数据为此Excel的重复数据");
                }
            }


        for (OrderReturn orderReturn : orderReturnList)
        {
            try
            {
                // 验证数据是否已经
                OrderReturn domain = orderReturnMapper.selectOrderReturnByOnlyCondition(orderReturn);
                if(!repeatMap.containsKey(orderReturn.getOrderId())) {//不是重复数据，判断是插入还是更新
                    if (domain==null)
                    {
                        orderReturn.setDetailedDispositionForcn((String)returnReasonDictMap.get(orderReturn.getDetailedDisposition()));
                        orderReturn.setReasonForcn((String)returnReasonDictMap.get(orderReturn.getReason()));
                        orderReturn.setCreateBy(operName);
                        orderReturn.setCreateTime(new Date());
                        this.insertOrderReturn(orderReturn);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、订单号为"+ orderReturn.getOrderId()+" 的数据导入成功");
                    }
                    else if (isUpdateSupport)
                    {
                        orderReturn.setDetailedDispositionForcn((String)returnReasonDictMap.get(orderReturn.getDetailedDisposition()));
                        orderReturn.setReasonForcn((String)returnReasonDictMap.get(orderReturn.getReason()));
                        orderReturn.setUpdateBy(operName);
                        orderReturn.setUpdateTime(new Date());
                        orderReturnMapper.updateOrderReturnByOnlyCondition(orderReturn);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、订单号为"+ orderReturn.getOrderId()+" 的数据更新成功");
                    }
                    else
                    {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum  + "、订单号为"+ orderReturn.getOrderId()+" 的数据已存在");
                    }

                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + "、订单号为"+ orderReturn.getOrderId()+" 的数据导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        // /oms/orderReturnRepeat
        if(failureNum>0&&repeatNum>0){
            failureMsg.insert(0,"<button onclick=\"window.open('/oms/orderReturnRepeat?repeatId="+repeatId+"')\">查看重复数据</button><br/>共 "+repeatNum+" 条数据重复，共有 "+failureNum+" 条数据格式不正确。<br/>"+
                    "其中错误数据如下（Excel上的重复数据不列出）：");
            throw new BusinessException(failureMsg.toString());
        }
        else if(repeatNum>0){
            repeatMsg.insert(0,"<button onclick=\"window.open('/oms/orderReturnRepeat?repeatId="+repeatId+"')\">查看重复数据</button><br/>共 "+repeatNum+" 条数据重复,重复数据如下");
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
    public OrderReturn selectOrderReturnByOnlyCondition(OrderReturn orderReturn) {
        return orderReturnMapper.selectOrderReturnByOnlyCondition(orderReturn);
    }
}
