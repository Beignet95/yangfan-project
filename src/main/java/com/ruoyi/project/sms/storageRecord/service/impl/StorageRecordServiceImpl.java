package com.ruoyi.project.sms.storageRecord.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import com.ruoyi.project.sms.storageRecord.vo.SkuStorageFee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.sms.storageRecord.mapper.StorageRecordMapper;
import com.ruoyi.project.sms.storageRecord.domain.StorageRecord;
import com.ruoyi.project.sms.storageRecord.service.IStorageRecordService;
import com.ruoyi.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仓储记录Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-06
 */
@Service
public class StorageRecordServiceImpl implements IStorageRecordService 
{
    private static final Logger log = LoggerFactory.getLogger(StorageRecord.class);

    private final String HISTORY_OPERARE_PREFIX = "imp:storage:r:";

    private final String CODE_SP = "imp:storage:r:";

    @Autowired
    private IHistoryOperateService historyOperateService;

    @Autowired
    private StorageRecordMapper storageRecordMapper;

    /**
     * 查询仓储记录
     * 
     * @param id 仓储记录ID
     * @return 仓储记录
     */
    @Override
    public StorageRecord selectStorageRecordById(Long id)
    {
        return storageRecordMapper.selectStorageRecordById(id);
    }

    /**
     * 查询仓储记录列表
     * 
     * @param storageRecord 仓储记录
     * @return 仓储记录
     */
    @Override
    public List<StorageRecord> selectStorageRecordList(StorageRecord storageRecord)
    {
        return storageRecordMapper.selectStorageRecordList(storageRecord);
    }

    /**
     * 新增仓储记录
     * 
     * @param storageRecord 仓储记录
     * @return 结果
     */
    @Override
    public int insertStorageRecord(StorageRecord storageRecord)
    {
        storageRecord.setCreateTime(DateUtils.getNowDate());
        return storageRecordMapper.insertStorageRecord(storageRecord);
    }

    /**
     * 修改仓储记录
     * 
     * @param storageRecord 仓储记录
     * @return 结果
     */
    @Override
    public int updateStorageRecord(StorageRecord storageRecord)
    {
        storageRecord.setUpdateTime(DateUtils.getNowDate());
        return storageRecordMapper.updateStorageRecord(storageRecord);
    }

    /**
     * 删除仓储记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStorageRecordByIds(String ids)
    {
        return storageRecordMapper.deleteStorageRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除仓储记录信息
     * 
     * @param id 仓储记录ID
     * @return 结果
     */
    @Override
    public int deleteStorageRecordById(Long id)
    {
        return storageRecordMapper.deleteStorageRecordById(id);
    }

    /**
     * 导入仓储记录
     *
     * @param storageRecordList 仓储记录List数据
     * @return 导入结果
     */
    @Override
    @Transactional
    public String importStorageRecord(List<StorageRecord> storageRecordList, boolean isUpdateSupport,String account) {
        if (StringUtils.isNull(storageRecordList) || storageRecordList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        HistoryOperate ho = checkAndInterceptImp(storageRecordList.get(0),account);

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();

        for (StorageRecord storageRecord : storageRecordList)
        {
            try
            {
                storageRecord.setAccount(account);
                storageRecord.setCreateBy(operName);
                storageRecord.setCreateTime(new Date());
                this.insertStorageRecord(storageRecord);
                successNum++;
//                StorageRecord domain = storageRecordMapper.selectStorageRecordByOnlyCondition(storageRecord);
//                // 验证数据是否已经
//                storageRecord.setAccount(account);
//                StorageRecord domain = storageRecordMapper.selectStorageRecordByOnlyCondition(storageRecord);
//                if (domain==null)
//                {
//                    storageRecord.setCreateBy(operName);
//                    storageRecord.setCreateTime(new Date());
//                    this.insertStorageRecord(storageRecord);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、"+ storageRecord.toString()+" 的数据导入成功");
//                }
//                else if (isUpdateSupport)
//                {
//                    storageRecord.setUpdateBy(operName);
//                    storageRecord.setUpdateTime(new Date());
//                    storageRecordMapper.updateStorageRecordByOnlyCondition(storageRecord);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、" + storageRecord.toString()+" 的数据更新成功");
//                }
//                else
//                {
//                    failureNum++;
//                    failureMsg.append("<br/>" + failureNum + "、" + storageRecord.toString()+" 的数据已存在");
//                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + storageRecord.toString()+" 的数据导入失败：";
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

    @Override
    public Map<String, BigDecimal> getSkuStorageFeeMapByMonth(Date month) {
        List<SkuStorageFee> skuStorageFeeList = storageRecordMapper.selectSkuStorageFeeByMonth(month);
        Map<String, BigDecimal>  skuStorageMap = skuStorageFeeList.stream()
                .collect(Collectors.toMap(SkuStorageFee::getSku,SkuStorageFee::getStorageFee));
        return skuStorageMap;
    }

    /**
     *有存在过导入记录，则抛出异常
     * 否则返回一个HistoryOperate对象
     */
    private HistoryOperate checkAndInterceptImp(StorageRecord storageRecord, String account) {
            String timeStr = DateUtils.parseDateToStr("yyyy-MM",storageRecord.getMonth());
            String history_operate_code = HISTORY_OPERARE_PREFIX+account+":"+timeStr;
            HistoryOperate ho = new HistoryOperate();
            ho.setRepeatCode(history_operate_code);
            List<HistoryOperate> hoRes = historyOperateService.selectHistoryOperateList(ho);
            if(hoRes.size()>0) throw new BusinessException(storageRecord.getMonth()+"月份，账号为"
                    +account+"的仓储数据导入操作已被锁定！你可能已经导入过数据了！");
            return ho;
    }
}
