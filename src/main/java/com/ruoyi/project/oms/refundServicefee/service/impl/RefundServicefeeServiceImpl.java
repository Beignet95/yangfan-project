package com.ruoyi.project.oms.refundServicefee.service.impl;

import java.util.List;

import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.oms.refundServicefee.mapper.RefundServicefeeMapper;
import com.ruoyi.project.oms.refundServicefee.domain.RefundServicefee;
import com.ruoyi.project.oms.refundServicefee.service.IRefundServicefeeService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 平台退款服务费Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-10
 */
@Service
public class RefundServicefeeServiceImpl implements IRefundServicefeeService 
{
    private static final Logger log = LoggerFactory.getLogger(RefundServicefee.class);

    private final String HISTORY_OPERARE_PREFIX = "imp:RefundServicefee:r:";

    @Autowired
    private RefundServicefeeMapper refundServicefeeMapper;

    @Autowired
    private IHistoryOperateService historyOperateService;

    /**
     * 查询平台退款服务费
     * 
     * @param id 平台退款服务费ID
     * @return 平台退款服务费
     */
    @Override
    public RefundServicefee selectRefundServicefeeById(Long id)
    {
        return refundServicefeeMapper.selectRefundServicefeeById(id);
    }

    /**
     * 查询平台退款服务费列表
     * 
     * @param refundServicefee 平台退款服务费
     * @return 平台退款服务费
     */
    @Override
    public List<RefundServicefee> selectRefundServicefeeList(RefundServicefee refundServicefee)
    {
        return refundServicefeeMapper.selectRefundServicefeeList(refundServicefee);
    }

    /**
     * 新增平台退款服务费
     * 
     * @param refundServicefee 平台退款服务费
     * @return 结果
     */
    @Override
    public int insertRefundServicefee(RefundServicefee refundServicefee)
    {
        refundServicefee.setCreateTime(DateUtils.getNowDate());
        return refundServicefeeMapper.insertRefundServicefee(refundServicefee);
    }

    /**
     * 修改平台退款服务费
     * 
     * @param refundServicefee 平台退款服务费
     * @return 结果
     */
    @Override
    public int updateRefundServicefee(RefundServicefee refundServicefee)
    {
        refundServicefee.setUpdateTime(DateUtils.getNowDate());
        return refundServicefeeMapper.updateRefundServicefee(refundServicefee);
    }

    /**
     * 删除平台退款服务费对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRefundServicefeeByIds(String ids)
    {
        return refundServicefeeMapper.deleteRefundServicefeeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除平台退款服务费信息
     * 
     * @param id 平台退款服务费ID
     * @return 结果
     */
    @Override
    public int deleteRefundServicefeeById(Long id)
    {
        return refundServicefeeMapper.deleteRefundServicefeeById(id);
    }

    /**
     * 导入平台退款服务费
     *
     * @param refundServicefeeList 平台退款服务费List数据
     * @return 导入结果
     */
    @Override
    public String importRefundServicefee(List<RefundServicefee> refundServicefeeList, boolean isUpdateSupport,String account,Date month) {
        refundServicefeeList = refundServicefeeList.stream().filter(refundServicefee
                -> ("Refund Administration Fee".equals(refundServicefee.getPaymentDetail())
                ||"Refund commission".equals(refundServicefee.getPaymentDetail() )) ).collect(Collectors.toList());
        if (StringUtils.isNull(refundServicefeeList) || refundServicefeeList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        HistoryOperate ho = checkAndInterceptImp(account,month);//检查是否有做个导入操作

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (RefundServicefee refundServicefee : refundServicefeeList)
        {
            try
            {
                refundServicefee.setAccount(account);
                refundServicefee.setMonth(month);
                refundServicefee.setCreateBy(operName);
                refundServicefee.setCreateTime(new Date());
                this.insertRefundServicefee(refundServicefee);
                successNum++;
                successMsg.append("<br/>" + successNum + "、"+ refundServicefee.toString()+" 的数据导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + refundServicefee.toString()+" 的数据导入失败：";
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
            historyOperateService.insertHistoryOperate(ho);
        }
        return successMsg.toString();
    }

    private HistoryOperate checkAndInterceptImp(String account, Date month) {
        String monthStr = DateUtils.parseDateToStr("yyyy-MM",month);
        String history_operate_code = HISTORY_OPERARE_PREFIX+account+":"+ monthStr;
        HistoryOperate ho = new HistoryOperate();
        ho.setRepeatCode(history_operate_code);
        List<HistoryOperate> hoRes = historyOperateService.selectHistoryOperateList(ho);
        if(hoRes.size()>0) throw new BusinessException(monthStr+"月份，账号为"
                +account+"的退款服务费数据导入操作已被锁定！你可能已经导入过数据了！");
        return ho;
    }
}
