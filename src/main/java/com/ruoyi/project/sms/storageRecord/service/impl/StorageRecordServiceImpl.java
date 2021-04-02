package com.ruoyi.project.sms.storageRecord.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import com.ruoyi.project.sms.storageRecord.vo.SkuStorageFee;
import com.ruoyi.project.sms.storageRecord.vo.StorageRecordImpTempVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.BeanUtils;
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
     * @param impTempVos 仓储记录List数据
     * @return 导入结果
     */
    @Override
    @Transactional
    public String importStorageRecord(List<StorageRecordImpTempVo> impTempVos, boolean isUpdateSupport, String account,String site) {
        if (StringUtils.isNull(impTempVos) || impTempVos.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        HistoryOperate ho = new HistoryOperate();
        if(StringUtils.isNotEmpty(site)&&site.contains("-")){
            String[] siteStrs = site.split("-");
            String countryCode = siteStrs[1];
            impTempVos = impTempVos.stream().filter(s->countryCode.equals(s.getCountryCode())).collect(Collectors.toList());
        }else throw new BusinessException("站点格式异常");
        Map<String,ProductinfoRelation> relationMap = getAndCheackProductionRelation(impTempVos);

        String timeFormatStr = "MMM-yy";
        //Locale locale = new Locale(language,"");
        Locale locale = new Locale("en","");

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();

        for (StorageRecordImpTempVo impTempVo : impTempVos)
        {
            try
            {
                StorageRecord storageRecord = new StorageRecord();
                BeanUtils.copyProperties(impTempVo,storageRecord);

                String monthStr = impTempVo.getMonth();
                Date time = DateUtils.parseUTCDate4CSV(monthStr, locale,timeFormatStr);//因为欧洲各国时间不一样，所以在此层处理
                if(time==null) time = DateUtils.parseDate(monthStr);
                if(time==null) {//适配仓储费yy-MM的格式
                    String[] monstrs = monthStr.split("-");
                    monthStr = monstrs[1]+"-"+monstrs[0];
                     time = DateUtils.parseUTCDate4CSV(monthStr, locale,timeFormatStr);
                }
                if(time==null) throw new BusinessException("仓储费月份的时间格式异常！");
                storageRecord.setMonth(time);

                ho = checkAndInterceptImp(storageRecord,site);

                ProductinfoRelation relation = relationMap.get(storageRecord.getAsin());
                storageRecord.setSpu(relation.getType());
                storageRecord.setStandardSku(relation.getSku());
                storageRecord.setPrincipal(relation.getPrincipal());

                storageRecord.setAccount(account);
                storageRecord.setCreateBy(operName);
                storageRecord.setCreateTime(new Date());
                this.insertStorageRecord(storageRecord);
                successNum++;
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + impTempVo.toString()+" 的数据导入失败：";
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
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条!");
            historyOperateService.insertHistoryOperate(ho);
        }
        return successMsg.toString();
    }

    @Autowired
    private IProductinfoRelationService productinfoRelationService;
    private Map<String,ProductinfoRelation> getAndCheackProductionRelation(List<StorageRecordImpTempVo> storageRecordList) {
        StringBuilder warnMsg = new StringBuilder();
        List<ProductinfoRelation> relationList = productinfoRelationService.selectProductinfoRelationList(null);
        Map<String,ProductinfoRelation> asinRelationMap = relationList.stream().collect(Collectors
                .toMap(ProductinfoRelation::getAsin, Function.identity(),(entity1, entity2) -> {
                    String sku1 = entity1.getSku();
                    String sku2 = entity2.getSku();
                    if(sku1.length()>sku2.length()) return entity2;
                    else if(sku1.length()<sku2.length()) return entity1;
                    else if(sku1.compareTo(sku2)>0) return entity2;
                    return entity1;
                }));
        int index = 1;
        for(StorageRecordImpTempVo record:storageRecordList){
            if(!asinRelationMap.containsKey(record.getAsin())) warnMsg.append(index+",ASIN:"+record.getAsin()+" 缺少相关的产品信息关系！<br/>");
            index++;
        }

        if(warnMsg.length()>0) throw new BusinessException(warnMsg.toString());
        else return asinRelationMap;
    }

    @Override
    public Map<String, BigDecimal> getSkuStorageFeeMapByMonth(Date month) {
        List<SkuStorageFee> skuStorageFeeList = storageRecordMapper.selectSkuStorageFeeByMonth(month);
        Map<String, BigDecimal>  skuStorageMap = skuStorageFeeList.stream()
                .collect(Collectors.toMap(SkuStorageFee::getSku,SkuStorageFee::getStorageFee));
        return skuStorageMap;
    }

    @Override
    public List<StorageRecord> getStorageRecordGather(StorageRecord storageRecord) {
        return storageRecordMapper.selectStorageRecordGather(storageRecord);
    }

    @Override
    @Transactional
    public int unlockData(Date month, String account,String site) {
        String monstr = DateUtils.parseDateToStr("yyyy-MM",month);
        if(historyOperateService.deleteHistoryOperateByOperateCode(getHistoryCode(site,monstr))>0){
            return storageRecordMapper.deleteStorageRecordLockUnit(month,account,site.split("-")[1]);
        }else return -1;
    }

    @Override
    public List<StorageRecord> selectAsinVolumnList(StorageRecord storageRecord) {
        return storageRecordMapper.selectAsinVolumnList(storageRecord);
    }

    private String getHistoryCode(String account, String monthStr) {
        return  HISTORY_OPERARE_PREFIX+account+":"+monthStr;
    }

    /**
     *有存在过导入记录，则抛出异常
     * 否则返回一个HistoryOperate对象
     */
    private HistoryOperate checkAndInterceptImp(StorageRecordImpTempVo storageRecord, String site) {
            //String timeStr = DateUtils.parseDateToStr("yyyy-MM",storageRecord.getMonth());
            String history_operate_code = getHistoryCode(site,storageRecord.getMonth());
            HistoryOperate ho = new HistoryOperate();
            ho.setRepeatCode(history_operate_code);
            List<HistoryOperate> hoRes = historyOperateService.selectHistoryOperateList(ho);
            if(hoRes.size()>0) throw new BusinessException(storageRecord.getMonth()+"月份，站点为"
                    +site+"的仓储数据导入操作已被锁定！你可能已经导入过数据了！");
            return ho;
    }

    private HistoryOperate checkAndInterceptImp(StorageRecord storageRecord, String site) {
        //String timeStr = DateUtils.parseDateToStr("yyyy-MM",storageRecord.getMonth());
        String monthStr = DateUtils.parseDateToStr("yyyy-MM",storageRecord.getMonth());
        String history_operate_code = getHistoryCode(site,monthStr);
        HistoryOperate ho = new HistoryOperate();
        ho.setRepeatCode(history_operate_code);
        List<HistoryOperate> hoRes = historyOperateService.selectHistoryOperateList(ho);
        if(hoRes.size()>0) throw new BusinessException(storageRecord.getMonth()+"月份，站点为"
                +site+"的仓储数据导入操作已被锁定！你可能已经导入过数据了！");
        return ho;
    }
}
