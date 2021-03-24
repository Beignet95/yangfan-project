package com.ruoyi.project.compdata.truckService.service.impl;

import java.util.*;

import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;
import com.ruoyi.project.oms.transactionRecord.service.ITransactionRecordService;
import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.compdata.truckService.mapper.TruckServiceMapper;
import com.ruoyi.project.compdata.truckService.domain.TruckService;
import com.ruoyi.project.compdata.truckService.service.ITruckServiceService;
import com.ruoyi.common.utils.text.Convert;
import sun.security.pkcs11.wrapper.Functions;

/**
 * 卡车费用Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-06
 */
@Service
public class TruckServiceServiceImpl implements ITruckServiceService

{
    private static final Logger log = LoggerFactory.getLogger(TruckService.class);

    @Autowired
    private TruckServiceMapper truckServiceMapper;

    @Autowired
    private ITransactionRecordService transactionRecordService;

    /**
     * 查询卡车费用
     * 
     * @param id 卡车费用ID
     * @return 卡车费用
     */
    @Override
    public TruckService selectTruckServiceById(Long id)
    {
        return truckServiceMapper.selectTruckServiceById(id);
    }

    /**
     * 查询卡车费用列表
     * 
     * @param truckService 卡车费用
     * @return 卡车费用
     */
    @Override
    public List<TruckService> selectTruckServiceList(TruckService truckService)
    {
        return truckServiceMapper.selectTruckServiceList(truckService);
    }

    /**
     * 新增卡车费用
     * 
     * @param truckService 卡车费用
     * @return 结果
     */
    @Override
    public int insertTruckService(TruckService truckService)
    {
        truckService.setCreateTime(DateUtils.getNowDate());
        return truckServiceMapper.insertTruckService(truckService);
    }

    /**
     * 修改卡车费用
     * 
     * @param truckService 卡车费用
     * @return 结果
     */
    @Override
    public int updateTruckService(TruckService truckService)
    {
        truckService.setUpdateTime(DateUtils.getNowDate());
        return truckServiceMapper.updateTruckService(truckService);
    }

    /**
     * 删除卡车费用对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteTruckServiceByIds(String ids)
    {
        return truckServiceMapper.deleteTruckServiceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除卡车费用信息
     * 
     * @param id 卡车费用ID
     * @return 结果
     */
    @Override
    public int deleteTruckServiceById(Long id)
    {
        return truckServiceMapper.deleteTruckServiceById(id);
    }

    /**
     * 导入卡车费用
     *
     * @param truckServiceList 卡车费用List数据
     * @return 导入结果
     */
    public String importTruckService(List<TruckService> truckServiceList, boolean updateSupport,Long truckRecordId) {
        if (StringUtils.isNull(truckServiceList) || truckServiceList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        checkProductinfoRelation(truckServiceList);

        TransactionRecord truckFeeRecord = transactionRecordService.selectTransactionRecordById(truckRecordId);
        if(truckFeeRecord==null) throw new BusinessException("卡车服务收费记录不存在！可能已经被删除！");

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (TruckService truckService : truckServiceList)
        {
            try
            {
                // 验证数据是否已经
                truckService.setTruckRecordId(truckRecordId);
                truckService.setMonth(truckFeeRecord.getTime());
                truckService.setAccount(truckFeeRecord.getAccount());
                truckService.setSite(truckFeeRecord.getSite());
                TruckService domain = truckServiceMapper.selectTruckServiceByOnlyCondition(truckService);
                if (domain==null)
                {
                    truckService.setCreateBy(operName);
                    truckService.setCreateTime(new Date());
                    this.insertTruckService(truckService);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ truckService.getAsin()+" 的数据导入成功");
                }
                else if (updateSupport)
                {
                    truckService.setUpdateBy(operName);
                    truckService.setUpdateTime(new Date());
                    truckServiceMapper.updateTruckServiceByOnlyCondition(truckService);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + truckService.getAsin()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + truckService.getAsin()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + truckService.getAsin()+" 的数据导入失败：";
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

    @Autowired
    IProductinfoRelationService productinfoRelationService;
    private void checkProductinfoRelation(List<TruckService> truckServiceList) {
        List<ProductinfoRelation> prList = productinfoRelationService.selectProductinfoRelationList(null);
        Map asinPrMap = prList.stream().collect(Collectors.toMap(ProductinfoRelation::getAsin, Function.identity(),(k1,k2)->k1));
        Set<String> cot = new LinkedHashSet();
        for(TruckService truckService:truckServiceList){
            String asin = truckService.getAsin();
            if(!asinPrMap.containsKey(asin)) cot.add(asin);
        }
        if(cot.size()>0) {
            StringBuilder warnMsg = new StringBuilder();
            warnMsg.append("以下ASIN缺少产品信息关系！");
            for (String asin : cot) {
                warnMsg.append("<br/>" + asin);
            }
        }
    }

    @Override
    public Map<String, Long> getTructServiceFeeMap(TruckService truckService) {
        try {
            List<TruckService> truckServices = this.selectTruckServiceList(truckService);
            Map<String, Long> map = truckServices.stream().collect(Collectors.toMap(
                    TruckService::getMsku, TruckService::getShipped));
            return map;
        }catch (Exception e){
            throw new BusinessException("卡车数据异常或同一标准sku的产品在两辆卡车上均有记录！");
        }
    }
}
