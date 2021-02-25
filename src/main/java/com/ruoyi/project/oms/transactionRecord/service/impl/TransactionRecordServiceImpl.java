package com.ruoyi.project.oms.transactionRecord.service.impl;

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
import com.ruoyi.project.oms.transactionRecord.mapper.TransactionRecordMapper;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;
import com.ruoyi.project.oms.transactionRecord.service.ITransactionRecordService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 交易数据Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-23
 */
@Service
public class TransactionRecordServiceImpl implements ITransactionRecordService 
{
    private static final Logger log = LoggerFactory.getLogger(TransactionRecord.class);

    @Autowired
    private TransactionRecordMapper transactionRecordMapper;

    /**
     * 查询交易数据
     * 
     * @param id 交易数据ID
     * @return 交易数据
     */
    @Override
    public TransactionRecord selectTransactionRecordById(Long id)
    {
        return transactionRecordMapper.selectTransactionRecordById(id);
    }

    /**
     * 查询交易数据列表
     * 
     * @param transactionRecord 交易数据
     * @return 交易数据
     */
    @Override
    public List<TransactionRecord> selectTransactionRecordList(TransactionRecord transactionRecord)
    {
        return transactionRecordMapper.selectTransactionRecordList(transactionRecord);
    }

    /**
     * 新增交易数据
     * 
     * @param transactionRecord 交易数据
     * @return 结果
     */
    @Override
    public int insertTransactionRecord(TransactionRecord transactionRecord)
    {
        transactionRecord.setCreateTime(DateUtils.getNowDate());
        return transactionRecordMapper.insertTransactionRecord(transactionRecord);
    }

    /**
     * 修改交易数据
     * 
     * @param transactionRecord 交易数据
     * @return 结果
     */
    @Override
    public int updateTransactionRecord(TransactionRecord transactionRecord)
    {
        transactionRecord.setUpdateTime(DateUtils.getNowDate());
        return transactionRecordMapper.updateTransactionRecord(transactionRecord);
    }

    /**
     * 删除交易数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTransactionRecordByIds(String ids)
    {
        return transactionRecordMapper.deleteTransactionRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除交易数据信息
     * 
     * @param id 交易数据ID
     * @return 结果
     */
    @Override
    public int deleteTransactionRecordById(Long id)
    {
        return transactionRecordMapper.deleteTransactionRecordById(id);
    }

    /**
     * 导入交易数据
     *
     * @param transactionRecordList 交易数据List数据
     * @return 导入结果
     */
    @Override
    public String importTransactionRecord(List<TransactionRecord> transactionRecordList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(transactionRecordList) || transactionRecordList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (TransactionRecord transactionRecord : transactionRecordList)
        {
            try
            {
                // 验证数据是否已经
                TransactionRecord domain = transactionRecordMapper.selectTransactionRecordByOnlyCondition(transactionRecord);
                String uniqueStr = transactionRecord.getTime()+"-"+transactionRecord.getOrderId()+"-"+transactionRecord.getSku();
                if (domain==null)
                {
                    transactionRecord.setCreateBy(operName);
                    transactionRecord.setCreateTime(new Date());
                    this.insertTransactionRecord(transactionRecord);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ uniqueStr+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    transactionRecord.setUpdateBy(operName);
                    transactionRecord.setUpdateTime(new Date());
                    transactionRecordMapper.updateTransactionRecordByOnlyCondition(transactionRecord);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + uniqueStr+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + uniqueStr+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + transactionRecord.getTime()+"-"+transactionRecord.getOrderId()+"-"
                        +transactionRecord.getSku()+" 的数据导入失败：";
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
