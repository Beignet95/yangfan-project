package com.ruoyi.project.oms.refundServicefee.service;

import java.util.Date;
import java.util.List;
import com.ruoyi.project.oms.refundServicefee.domain.RefundServicefee;

/**
 * 平台退款服务费Service接口
 * 
 * @author Beignet
 * @date 2021-03-10
 */
public interface IRefundServicefeeService 
{
    /**
     * 查询平台退款服务费
     * 
     * @param id 平台退款服务费ID
     * @return 平台退款服务费
     */
    public RefundServicefee selectRefundServicefeeById(Long id);

    /**
     * 查询平台退款服务费列表
     * 
     * @param refundServicefee 平台退款服务费
     * @return 平台退款服务费集合
     */
    public List<RefundServicefee> selectRefundServicefeeList(RefundServicefee refundServicefee);

    /**
     * 新增平台退款服务费
     * 
     * @param refundServicefee 平台退款服务费
     * @return 结果
     */
    public int insertRefundServicefee(RefundServicefee refundServicefee);

    /**
     * 修改平台退款服务费
     * 
     * @param refundServicefee 平台退款服务费
     * @return 结果
     */
    public int updateRefundServicefee(RefundServicefee refundServicefee);

    /**
     * 批量删除平台退款服务费
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRefundServicefeeByIds(String ids);

    /**
     * 删除平台退款服务费信息
     * 
     * @param id 平台退款服务费ID
     * @return 结果
     */
    public int deleteRefundServicefeeById(Long id);

    /**
     * 导入平台退款服务费
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importRefundServicefee(List<RefundServicefee> refundServicefeeList, boolean isUpdateSupport, String account, Date month);


}
