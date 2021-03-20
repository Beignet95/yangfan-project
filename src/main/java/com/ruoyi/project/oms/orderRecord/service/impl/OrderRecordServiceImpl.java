package com.ruoyi.project.oms.orderRecord.service.impl;

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
import com.ruoyi.project.oms.orderRecord.mapper.OrderRecordMapper;
import com.ruoyi.project.oms.orderRecord.domain.OrderRecord;
import com.ruoyi.project.oms.orderRecord.service.IOrderRecordService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 订单记录Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-19
 */
@Service
public class OrderRecordServiceImpl implements IOrderRecordService 
{
    private static final Logger log = LoggerFactory.getLogger(OrderRecord.class);

    @Autowired
    private OrderRecordMapper orderRecordMapper;

    /**
     * 查询订单记录
     * 
     * @param id 订单记录ID
     * @return 订单记录
     */
    @Override
    public OrderRecord selectOrderRecordById(Long id)
    {
        return orderRecordMapper.selectOrderRecordById(id);
    }

    /**
     * 查询订单记录列表
     * 
     * @param orderRecord 订单记录
     * @return 订单记录
     */
    @Override
    public List<OrderRecord> selectOrderRecordList(OrderRecord orderRecord)
    {
        return orderRecordMapper.selectOrderRecordList(orderRecord);
    }

    /**
     * 新增订单记录
     * 
     * @param orderRecord 订单记录
     * @return 结果
     */
    @Override
    public int insertOrderRecord(OrderRecord orderRecord)
    {
        orderRecord.setCreateTime(DateUtils.getNowDate());
        return orderRecordMapper.insertOrderRecord(orderRecord);
    }

    /**
     * 修改订单记录
     * 
     * @param orderRecord 订单记录
     * @return 结果
     */
    @Override
    public int updateOrderRecord(OrderRecord orderRecord)
    {
        orderRecord.setUpdateTime(DateUtils.getNowDate());
        return orderRecordMapper.updateOrderRecord(orderRecord);
    }

    /**
     * 删除订单记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteOrderRecordByIds(String ids)
    {
        return orderRecordMapper.deleteOrderRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除订单记录信息
     * 
     * @param id 订单记录ID
     * @return 结果
     */
    @Override
    public int deleteOrderRecordById(Long id)
    {
        return orderRecordMapper.deleteOrderRecordById(id);
    }

    /**
     * 导入订单记录
     *
     * @param orderRecordList 订单记录List数据
     * @return 导入结果
     */
    @Override
    public String importOrderRecord(List<OrderRecord> orderRecordList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(orderRecordList) || orderRecordList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (OrderRecord orderRecord : orderRecordList)
        {
            try
            {
                // 验证数据是否已经
                OrderRecord domain = orderRecordMapper.selectOrderRecordByOnlyCondition(orderRecord);
                if (domain==null)
                {
                    orderRecord.setCreateBy(operName);
                    orderRecord.setCreateTime(new Date());
                    this.insertOrderRecord(orderRecord);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ orderRecord.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    orderRecord.setUpdateBy(operName);
                    orderRecord.setUpdateTime(new Date());
                    orderRecordMapper.updateOrderRecordByOnlyCondition(orderRecord);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + orderRecord.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + orderRecord.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + orderRecord.toString()+" 的数据导入失败：";
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
