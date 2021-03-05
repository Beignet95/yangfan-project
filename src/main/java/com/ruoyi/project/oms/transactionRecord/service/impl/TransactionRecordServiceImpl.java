package com.ruoyi.project.oms.transactionRecord.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import com.ruoyi.project.compdata.productPrincipal.domain.ProductPrincipal;
import com.ruoyi.project.compdata.productPrincipal.service.IProductPrincipalService;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecordImpTempVo;
import com.ruoyi.project.oms.transactionRecord.vo.FinanceVo;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodity;
import com.ruoyi.project.pms.badcommodity.domain.BadCommodityRepeat;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;
import com.ruoyi.project.system.dict.domain.DictData;
import com.ruoyi.project.system.dict.service.IDictTypeService;
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
import com.ruoyi.project.oms.transactionRecord.mapper.TransactionRecordMapper;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecord;
import com.ruoyi.project.oms.transactionRecord.service.ITransactionRecordService;
import com.ruoyi.common.utils.text.Convert;
import org.springframework.transaction.annotation.Transactional;

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

    private final String HISTORY_OPERARE_PREFIX = "imp:t:r:";

    @Autowired
    private TransactionRecordMapper transactionRecordMapper;

    @Autowired
    private IHistoryOperateService historyOperateService;

    @Autowired
    private IProductPrincipalService productPrincipalService;

    @Autowired
    private IDictTypeService dictTypeService;

    @Autowired
    private IProductinfoRelationService productinfoRelationService;

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
     * @param impTempVos 交易数据List数据
     * @return 导入结果
     */
    @Override
    @Transactional
    public String importTransactionRecord(List<TransactionRecordImpTempVo> impTempVos, boolean isUpdateSupport, String account, String site,String spareField) {

        if (StringUtils.isNull(impTempVos) || impTempVos.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        String history_operate_code = "";
        HistoryOperate ho = new HistoryOperate();

        Map<String, MskuProductinfoRelationVo> mskuProductinfoRelationVoMap =  productinfoRelationService.getSkuMskuMap();

        successNum = impTempVos.size();

        for (TransactionRecordImpTempVo impTempVo : impTempVos)
        {

            TransactionRecord transactionRecord = new TransactionRecord();
            BeanUtils.copyProperties(impTempVo,transactionRecord);

            if(StringUtils.isNotEmpty(impTempVo.getSku())){
                if(mskuProductinfoRelationVoMap.get(impTempVo.getSku())==null){
                    throw new BusinessException("SKU与标准SKU的映射不全，请检查！缺失msku为："+impTempVo.getSku()+"的映射");
                }
                MskuProductinfoRelationVo mprMap = mskuProductinfoRelationVoMap.get(impTempVo.getSku());
                transactionRecord.setStandardSku(mprMap.getSku());//标准sku
                transactionRecord.setSpu(mprMap.getType());//型号
                transactionRecord.setPrincipal(mprMap.getPrincipal());//负责人
                //transactionRecord.setChargType(mprMap.getChargeType());
            }

            String language = "";
            String timeFormatStr = null;
            try{
                if("US".equals(site)){
                    language = "en";
                    timeFormatStr = "MMM d',' yyyy HH:mm:ss a 'PST'";
                    transactionRecord.setSpareField(spareField);
                }else{
                    language = site.split("-")[1];
                }
            }catch (Exception e){
                throw new BusinessException("站点格式异常！");
            }

            Locale locale = new Locale(language,"");
            Date time = DateUtils.parseUTCDate4CSV(impTempVo.getTime(), locale,timeFormatStr);//因为欧洲各国时间不一样，所以在此层处理
            transactionRecord.setTime(time);
            if(StringUtils.isEmpty(history_operate_code)){
                history_operate_code = HISTORY_OPERARE_PREFIX+":"+site+":"+time.getMonth()+spareField;
                ho.setRepeatCode(history_operate_code);
                List<HistoryOperate> hoRes = historyOperateService.selectHistoryOperateList(ho);
                if(hoRes.size()>0) throw new BusinessException(time.getMonth()+"月份，站点为"+site+"的数据导入操作已被锁定！你可能已经导入过数据了！");
            }

            //从字典中获取翻译值List，并将List转为Map,方便获取
            Map<String,String> transationTypeMap = dictTypeService.getDictDataAsMapByType("oms_transation_type_translate");
            try
            {
                transactionRecord.setAccount(account);
                transactionRecord.setSite(site);
                String type = transactionRecord.getType();
                if(StringUtils.isNotEmpty(type)){
                    String typeOfEn = transationTypeMap.get(type);
                    type=(StringUtils.isNotEmpty(typeOfEn))?typeOfEn:type;
                    transactionRecord.setType(type);
                }

                this.insertTransactionRecord(transactionRecord);
//                // 验证数据是否已经
//                TransactionRecord domain = transactionRecordMapper.selectTransactionRecordByOnlyCondition(transactionRecord);
//                String uniqueStr = transactionRecord.getTime()+"-"+transactionRecord.getOrderId()+"-"+transactionRecord.getSku();
//
//                transactionRecord.setAccount(account);
//                transactionRecord.setSite(site);
//
//                if (domain==null)
//                {
//                    transactionRecord.setCreateBy(operName);
//                    transactionRecord.setCreateTime(new Date());
//                    this.insertTransactionRecord(transactionRecord);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、"+ uniqueStr+" 的数据导入成功");
//                }
//                else if (isUpdateSupport)
//                {
//                    transactionRecord.setUpdateBy(operName);
//                    transactionRecord.setUpdateTime(new Date());
//                    transactionRecordMapper.updateTransactionRecordByOnlyCondition(transactionRecord);
//                    successNum++;
//                    successMsg.append("<br/>" + successNum + "、" + uniqueStr+" 的数据更新成功");
//                }
//                else
//                {
//                    failureNum++;
//                    failureMsg.append("<br/>" + failureNum + "、" + uniqueStr+" 的数据已存在");
//                }
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
            historyOperateService.insertHistoryOperate(ho);
        }
        return successMsg.toString();
    }

    @Override
    public List<FinanceVo> selectTransactionAnaly(TransactionRecord transactionRecord) {

        String preMonthStr = (String)transactionRecord.getParams().get("month");
        if(StringUtils.isNotEmpty(preMonthStr)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
            Calendar c = Calendar.getInstance();
            Date date = null;
            try {
                // 注意格式需要与上面一致，不然会出现异常
                date = format.parse(preMonthStr);
                c.set(Calendar.DAY_OF_MONTH, 0);
                Date endTime = c.getTime();//最后一天
                Map params = new HashMap();
                params.put("startTime",date);
                params.put("endTime",endTime);
                transactionRecord.setParams(params);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        transactionRecord.setFulfilment("Amazon");
        List<TransactionRecord> amazonRecordGather = transactionRecordMapper.selectTransactionAnaly(transactionRecord);
        transactionRecord.setFulfilment("Seller");
        List<TransactionRecord> sellerRecordGather = transactionRecordMapper.selectTransactionAnaly(transactionRecord);
        Map<String,List<TransactionRecord>> amazonRecordGatherMap = amazonRecordGather.stream().
                collect(Collectors.groupingBy(TransactionRecord::getType));
        Map<String,List<TransactionRecord>> sellerRecordGatherMap = sellerRecordGather.stream().
                collect(Collectors.groupingBy(TransactionRecord::getStandardSku));

        if(amazonRecordGatherMap.size()==0) return new ArrayList<>();

        List<TransactionRecord> RefundRecords = amazonRecordGatherMap.get("Refund");
        Map<String,TransactionRecord> refundRecordMap = new HashMap<>();
        if(RefundRecords!=null&&RefundRecords.size()>0){
            //refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku, Function.identity()));
            refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }

        //Seller订单
        List<TransactionRecord> sellerRecords = sellerRecordGatherMap.get("Order");
        Map<String,TransactionRecord> sellerRecordMap = new HashMap<>();
        if(sellerRecords!=null&&sellerRecords.size()>0){
            //refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku, Function.identity()));
            sellerRecordMap = sellerRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }
        //Seller退款
        List<TransactionRecord> sellerRefundRecords = sellerRecordGatherMap.get("Order");
        Map<String,TransactionRecord> sellerRefundRecordMap = new HashMap<>();
        if(sellerRefundRecords!=null&&sellerRefundRecords.size()>0){
            //refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku, Function.identity()));
            sellerRefundRecordMap = sellerRefundRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }

        //Adjustment
        List<TransactionRecord> adjustmentRecords = amazonRecordGatherMap.get("Adjustment");
        Map<String,TransactionRecord> adjustmentRecordMap = new HashMap<>();
        if(adjustmentRecords!=null&&adjustmentRecords.size()>0){
            adjustmentRecordMap = adjustmentRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }

        //Fee Adjustment  一般只有美国有这个数据
        List<TransactionRecord> feeAdjustmentRecords = amazonRecordGatherMap.get("Fee Adjustment");
        Map<String,TransactionRecord> feeAdjustmentRecordMap = new HashMap<>();
        if(feeAdjustmentRecords!=null&&feeAdjustmentRecords.size()>0){
            feeAdjustmentRecordMap = feeAdjustmentRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }

        List<TransactionRecord> orderRecordAnalys = amazonRecordGatherMap.get("Order");
        if(orderRecordAnalys==null) return new ArrayList<>();

        List<FinanceVo> financeVos = new ArrayList<>();
        for(TransactionRecord orderRecord:orderRecordAnalys){
            FinanceVo financeVo = new FinanceVo();
            String site = orderRecord.getSite();
            String standardSku = orderRecord.getStandardSku();
            financeVo.setAccount(orderRecord.getAccount());
            financeVo.setSite(orderRecord.getSite());
            financeVo.setQuantity(orderRecord.getQuantity());
            financeVo.setFbaProductSales(orderRecord.getProductSales());
            financeVo.setPrincipal(orderRecord.getPrincipal());
            financeVo.setSku(orderRecord.getStandardSku());
            financeVo.setType(orderRecord.getSpu());

            TransactionRecord sellerRecord = sellerRecordMap.get(site+standardSku);
            TransactionRecord sellerRefundRecord = sellerRefundRecordMap.get(site+standardSku);
            TransactionRecord feeAdjustmentRecord = feeAdjustmentRecordMap.get(site+standardSku);
            BigDecimal productSalesOfSeller = new BigDecimal(0);
            BigDecimal productSaleRefundsOfSeller = new BigDecimal(0);
            BigDecimal sellerFulfilledSellingFees = new BigDecimal(0);
            BigDecimal sellerFulfilledSellingFeeRefunds = new BigDecimal(0);
            BigDecimal fbaTransactionFeeRefunds = new BigDecimal(0);
            BigDecimal fbaTransactionFeeRefundsOfUs = new BigDecimal(0);
            if(sellerRecord!=null){
                productSalesOfSeller = sellerRecord.getProductSales();
                sellerFulfilledSellingFees = sellerRecord.getSellingFees();
            }
            if(sellerRefundRecord!=null){
                productSaleRefundsOfSeller = sellerRefundRecord.getProductSales();
                sellerFulfilledSellingFeeRefunds = sellerRefundRecord.getSellingFees();
            }
            if(feeAdjustmentRecord!=null){
                fbaTransactionFeeRefundsOfUs = feeAdjustmentRecord.getFbaFees();
            }

            //销售收入(含自配送）
            TransactionRecord refundRecord = refundRecordMap.get(orderRecord.getSite()+orderRecord.getStandardSku());
            if(refundRecord!=null) financeVo.setFbaProductSalesRefunds(refundRecord.getProductSales());
            BigDecimal salesRevenue = new BigDecimal(0);
            BigDecimal fbaProductSales = orderRecord.getProductSales();
            BigDecimal fbaProductSalesRefunds = (refundRecord!=null)?refundRecord.getProductSales():new BigDecimal(0);
            salesRevenue = fbaProductSales.add(fbaProductSalesRefunds).add(productSalesOfSeller).add(productSaleRefundsOfSeller);
            financeVo.setProductSalesOfSeller(productSalesOfSeller);
            financeVo.setProductSaleRefundsOfSeller(productSaleRefundsOfSeller);
            financeVo.setFbaProductSales(fbaProductSales);
            financeVo.setFbaProductSalesRefunds(fbaProductSalesRefunds);//TODO 重复
            financeVo.setSalesRevenue(salesRevenue);

            //赔偿金
            TransactionRecord adjustmentRecord = adjustmentRecordMap.get(orderRecord.getStandardSku());
            BigDecimal fbaInventoryCredit = new BigDecimal(0);
            if(adjustmentRecord!=null) fbaInventoryCredit = adjustmentRecord.getTotal();
            financeVo.setFbaInventoryCredit(fbaInventoryCredit);

            //尾程运费
            BigDecimal shippingCredits = orderRecord.getPostageCredits();
            financeVo.setShippingCredits(shippingCredits);
            BigDecimal shippingCreditRefunds = new BigDecimal(0);
            if(refundRecord!=null) shippingCreditRefunds = refundRecord.getPostageCredits();
            financeVo.setShippingCreditRefunds(shippingCreditRefunds);
            BigDecimal finalFreight = shippingCredits.add(shippingCreditRefunds);
            financeVo.setFinalFreight(finalFreight);

            //包装费
            BigDecimal giftWrapCredits = orderRecord.getGiftWrapCredits();
            BigDecimal giftWrapCreditRefunds = new BigDecimal(0);
            if(refundRecord!=null) giftWrapCreditRefunds = refundRecord.getGiftWrapCredits();
            BigDecimal packagingFee = giftWrapCredits.add(giftWrapCreditRefunds);
            financeVo.setGiftWrapCredits(giftWrapCredits);
            financeVo.setGiftWrapCreditRefunds(giftWrapCreditRefunds);
            financeVo.setPackagingFee(packagingFee);

            //促销费
            BigDecimal promotionalRebates = orderRecord.getPromotionalRebates();
            BigDecimal promotionalRebateRefunds = new BigDecimal(0);
            if(refundRecord!=null) promotionalRebateRefunds = refundRecord.getPromotionalRebates();
            BigDecimal promotionFee = promotionalRebates.add(promotionalRebateRefunds);
            financeVo.setPromotionalRebates(promotionalRebates);
            financeVo.setPromotionalRebateRefunds(promotionalRebateRefunds);
            financeVo.setPromotionFee(promotionFee);

            //销售佣金(含自配送）
            BigDecimal fbaSellingFees = orderRecord.getSellingFees();
            BigDecimal sellingFeeRefunds = new BigDecimal(0);
            if(refundRecord!=null) sellingFeeRefunds = refundRecord.getSellingFees();
            BigDecimal selfdeliveryCommission = fbaSellingFees.add(sellingFeeRefunds).add(sellerFulfilledSellingFees).add(sellerFulfilledSellingFeeRefunds);
            financeVo.setFbaSellingFees(fbaSellingFees);
            financeVo.setSellerFulfilledSellingFees(sellerFulfilledSellingFees);
            financeVo.setSellerFulfilledSellingFeeRefunds(sellerFulfilledSellingFeeRefunds);
            financeVo.setSellingFeeRefunds(sellingFeeRefunds);
            financeVo.setSelfdeliveryCommission(selfdeliveryCommission);

            //尾程运费退回（N6+T6+AG6,占销售比例14.5%）
            BigDecimal fbaTransactionFees = orderRecord.getFbaFees();
            if(refundRecord!=null) fbaTransactionFeeRefunds = refundRecord.getFbaFees().add(fbaTransactionFeeRefundsOfUs);//加上美国fbaTransactionFeeRefunds
            BigDecimal finalFreightReturn = fbaTransactionFees.add(fbaTransactionFeeRefunds);
            financeVo.setFbaTransactionFees(fbaTransactionFees);
            financeVo.setFbaTransactionFeeRefunds(fbaTransactionFeeRefunds);
            financeVo.setFinalFreightReturn(finalFreightReturn);

            //TODO 其他交易费 暂时不做计算
            BigDecimal othertransactionFees = new BigDecimal(0);
            BigDecimal otherTransactionFeeRefunds = new BigDecimal(0);
            BigDecimal otherTransactionFee = new BigDecimal(0);
            financeVo.setOthertransactionFees(othertransactionFees);
            financeVo.setOtherTransactionFeeRefunds(otherTransactionFeeRefunds);
            financeVo.setOtherTransactionFee(otherTransactionFee);



            financeVos.add(financeVo);
        }
        return financeVos;
    }

    private TransactionRecordImpTempVo mergeRepeatRecord(Map<String,List<TransactionRecordImpTempVo>> recordGroupsMap,String orderId){
        List<TransactionRecordImpTempVo> records = recordGroupsMap.get(orderId);
        TransactionRecordImpTempVo mergeRecord = new TransactionRecordImpTempVo();
        BeanUtils.copyProperties(records.get(0),mergeRecord);
        Long quantiy = 0L;
        BigDecimal productSales = new BigDecimal(0);
        BigDecimal productSalesTax = new BigDecimal(0);
        BigDecimal postageCredits = new BigDecimal(0);
        BigDecimal shippingCreditsTax = new BigDecimal(0);
        BigDecimal giftWrapCredits = new BigDecimal(0);
        BigDecimal giftwrapCreditsTax = new BigDecimal(0);
        BigDecimal promotionalRebates = new BigDecimal(0);
        BigDecimal promotionalRebatesTax = new BigDecimal(0);
        BigDecimal marketplaceWithheldTax = new BigDecimal(0);
        BigDecimal sellingFees = new BigDecimal(0);
        BigDecimal fbaFees = new BigDecimal(0);
        BigDecimal otherTransactionFees = new BigDecimal(0);
        BigDecimal other = new BigDecimal(0);
        BigDecimal total= new BigDecimal(0);
        for(TransactionRecordImpTempVo record : records){
            if(record.getQuantity()!=null) quantiy += record.getQuantity();
            if(record.getProductSales()!=null) productSales= productSales.add(record.getProductSales());
            if(record.getProductSalesTax()!=null) productSalesTax = productSalesTax.add(record.getProductSalesTax());
            if(record.getPostageCredits()!=null) postageCredits = postageCredits.add(record.getPostageCredits());
            if(record.getShippingCreditsTax()!=null) shippingCreditsTax = shippingCreditsTax.add(record.getShippingCreditsTax());
            if(record.getGiftWrapCredits()!=null) giftWrapCredits = giftWrapCredits.add(record.getGiftWrapCredits());
            if(record.getGiftwrapCreditsTax()!=null) giftwrapCreditsTax = giftwrapCreditsTax.add(record.getGiftwrapCreditsTax());
            if(record.getPromotionalRebates()!=null) promotionalRebates = promotionalRebates.add(record.getPromotionalRebates());
            if(record.getPromotionalRebatesTax()!=null) promotionalRebatesTax = promotionalRebatesTax.add(record.getPromotionalRebatesTax());
            if(record.getMarketplaceWithheldTax()!=null) marketplaceWithheldTax = marketplaceWithheldTax.add(record.getMarketplaceWithheldTax());
            if(record.getSellingFees()!=null) sellingFees = sellingFees.add(record.getSellingFees());
            if(record.getFbaFees()!=null) fbaFees = fbaFees.add(record.getFbaFees());
            if(record.getOtherTransactionFees()!=null) otherTransactionFees = otherTransactionFees.add(record.getOtherTransactionFees());
            if(record.getOther()!=null) other = other.add(record.getOther());
            if(record.getTotal()!=null) total = total.add(record.getTotal());
        }
        mergeRecord.setQuantity(quantiy);
        mergeRecord.setProductSales(productSales);
        mergeRecord.setProductSalesTax(productSalesTax);
        mergeRecord.setPostageCredits(postageCredits);
        mergeRecord.setShippingCreditsTax(shippingCreditsTax);
        mergeRecord.setGiftWrapCredits(giftWrapCredits);
        mergeRecord.setGiftwrapCreditsTax(giftwrapCreditsTax);
        mergeRecord.setPromotionalRebates(promotionalRebates);
        mergeRecord.setPromotionalRebatesTax(promotionalRebatesTax);
        mergeRecord.setMarketplaceWithheldTax(marketplaceWithheldTax);
        mergeRecord.setSellingFees(sellingFees);
        mergeRecord.setFbaFees(fbaFees);
        mergeRecord.setOtherTransactionFees(otherTransactionFees);
        mergeRecord.setOther(other);
        mergeRecord.setTotal(total);

        recordGroupsMap.remove(orderId);
        return mergeRecord;
    }
}
