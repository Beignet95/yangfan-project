package com.ruoyi.project.oms.transactionRecord.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ruoyi.common.utils.Arith;
import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import com.ruoyi.project.compdata.productPrincipal.service.IProductPrincipalService;
import com.ruoyi.project.compdata.truckService.domain.TruckService;
import com.ruoyi.project.compdata.truckService.service.ITruckServiceService;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecordImpTempVo;
import com.ruoyi.project.oms.transactionRecord.vo.FinanceVo;
import com.ruoyi.project.oms.transactionRecord.vo.SkuFee;
import com.ruoyi.project.oms.transactionRecord.vo.SkuRefundServiceFee;
import com.ruoyi.project.oms.transactionRecord.vo.SkuRemovalFee;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;
import com.ruoyi.project.pms.productinfoReation.vo.PasinProductinfoRelationVo;
import com.ruoyi.project.pms.productinfoReation.vo.ProductinfoRelationVo;
import com.ruoyi.project.pms.productinfoReation.vo.StringBigDecimalMapVo;
import com.ruoyi.project.pms.skuCoupon.service.ISkuCouponService;
import com.ruoyi.project.sms.storageRecord.service.IStorageRecordService;
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

    @Autowired
    private ITruckServiceService truckServiceService;

    @Autowired
    private IStorageRecordService storageRecordService;

    @Autowired
    private ISkuCouponService skuCouponService;

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

        Map relationMap = getAndCheackProductionRelation(impTempVos);
        Map<String, MskuProductinfoRelationVo> mskuProductinfoRelationVoMap = (Map<String, MskuProductinfoRelationVo>) relationMap.get("mskuProductinfoRelationVoMap");
        Map<String, String> conponSkuMap = (Map<String, String>) relationMap.get("couponSkuMap");
        Map<String, String> pasinSkuMap = (Map<String, String>) relationMap.get("pasinSkuMap");

        successNum = impTempVos.size();

        for (TransactionRecordImpTempVo impTempVo : impTempVos)
        {

            TransactionRecord transactionRecord = new TransactionRecord();
            BeanUtils.copyProperties(impTempVo,transactionRecord);

            if(StringUtils.isNotEmpty(impTempVo.getSku())){
                MskuProductinfoRelationVo mprMap = mskuProductinfoRelationVoMap.get(impTempVo.getSku());
                transactionRecord.setStandardSku(mprMap.getSku());//标准sku
                transactionRecord.setSpu(mprMap.getType());//型号
                transactionRecord.setPrincipal(mprMap.getPrincipal());//负责人
                //transactionRecord.setChargType(mprMap.getChargeType());
            }

            String description = transactionRecord.getDescription();
            if(StringUtils.isNotEmpty(description)&&description.startsWith("Save")&&StringUtils.isEmpty(transactionRecord.getStandardSku())){
                String standardSku = conponSkuMap.get(description);
                //skuProductinfoRelationVoMap.get(standardSku);
                transactionRecord.setStandardSku(standardSku);
            }

            if(StringUtils.isNotEmpty(description)&&description.startsWith("Early")&&StringUtils.isEmpty(transactionRecord.getStandardSku())){
                String[] strs = description.split(" ");
                if(strs.length>0){
                    String pasin = strs[strs.length-1];
                    String standardSku = pasinSkuMap.get(pasin);
                    transactionRecord.setStandardSku(standardSku);
                }
            }

            String language = "";
            String timeFormatStr = null;
            try{
                if(site.endsWith("US")){
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
                String monthStr = DateUtils.parseDateToStr("yyyy-MM",time);
                history_operate_code = HISTORY_OPERARE_PREFIX+":"+site+":"+monthStr+spareField;
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

    private Map getAndCheackProductionRelation(List<TransactionRecordImpTempVo> impTempVos) {
        Map<String, MskuProductinfoRelationVo> mskuProductinfoRelationVoMap = productinfoRelationService.getMskuProductinfoRelationVoMap();
        Map<String, String> couponSkuMap = skuCouponService.getCouponSkuMap();
        Map<String, String> pasinSkuMap = productinfoRelationService.getPasinSkuMap();

        StringBuilder mskuProductinfoRelationWarnMsg = new StringBuilder();
        StringBuilder conponSkuWarnMsg = new StringBuilder();
        StringBuilder pasinSkuWarnMsg = new StringBuilder();
        for (TransactionRecordImpTempVo impTempVo : impTempVos) {
            String msku = impTempVo.getSku();
            String description = impTempVo.getDescription();
            if (StringUtils.isNotEmpty(msku)) {
                if (!mskuProductinfoRelationVoMap.containsKey(msku)) mskuProductinfoRelationWarnMsg.append("msku " + msku + "缺失产品信息关系！<br/>");
            }
            if (StringUtils.isEmpty(msku)) {
                if (description.startsWith("Early")) {
                    String[] strs = description.split(" ");
                    if (strs.length > 0) {
                        String pasin = strs[strs.length - 1];
                        if (!pasinSkuMap.containsKey(pasin)) conponSkuWarnMsg.append("捆绑ASIN：" + pasin + "缺少相关映射关系！<br/>");
                    }
                }
                if (description.startsWith("Save")) {
                    if (!couponSkuMap.containsKey(description))
                        conponSkuWarnMsg.append("Coupon Title：" + description + "缺少相关映射关系！<br/>");
                }
            }
        }
        StringBuilder warnMsg = mskuProductinfoRelationWarnMsg.append(conponSkuWarnMsg).append(pasinSkuWarnMsg);
        if (warnMsg.length()!=0) {
            throw new BusinessException(warnMsg.toString());
        } else {
            Map map = new HashMap();
            map.put("mskuProductinfoRelationVoMap",mskuProductinfoRelationVoMap);
            map.put("couponSkuMap",couponSkuMap);
            map.put("pasinSkuMap",pasinSkuMap);
            return map;
        }
    }

    @Override
    public List<FinanceVo> selectTransactionAnaly(TransactionRecord transactionRecord) {

        String preMonthStr = (String)transactionRecord.getParams().get("month");
        Date time = null;
        if(StringUtils.isNotEmpty(preMonthStr)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
            Calendar c = Calendar.getInstance();
            try {
                // 注意格式需要与上面一致，不然会出现异常
                time = format.parse(preMonthStr);
                c.set(Calendar.DAY_OF_MONTH, 0);
                Date endTime = c.getTime();//最后一天
                Map params = new HashMap();
                params.put("startTime",time);
                params.put("endTime",endTime);
                transactionRecord.setParams(params);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        StringBuilder warnMsg = new StringBuilder();
        //获取卡车服务记录map
        Map<String,BigDecimal> truckFeeMap =  this.getTruckServiceFeeMap(transactionRecord);

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
        //从卡车服务模块  获取卡车服务费
        Map<String,Long> tructServiceFeeMap = truckServiceService.getTructServiceFeeMap(null);

        //从仓储模块 获取每个标准SKU对应的总仓储费
        Map<String,BigDecimal> skuStorageFeeMap = storageRecordService.getSkuStorageFeeMapByMonth(time);
        //标准SKU对应退货移除费Map
        Map<String,BigDecimal> returnSkuRemovalFeeMap = this.getSkuRemovalFeeMap("Return");
        //标准SKU对应破损移除费Map
        Map<String,BigDecimal> disposalSkuRemovalFeeMap = this.getSkuRemovalFeeMap("Disposal");
        //总数量
        transactionRecord.setDescription(null);
        transactionRecord.setFulfilment(null);
        transactionRecord.setType("Order");
        TransactionRecord orderRecordGather = transactionRecordMapper.selectGatherRecord(transactionRecord);
        Long totalQuantity = 0l;
        BigDecimal totalSales = new BigDecimal(0);
        if(orderRecordGather!=null){
            totalQuantity = orderRecordGather.getQuantity();
            totalSales = orderRecordGather.getProductSales();
        }
        //TODO amazonRecordGather
        amazonRecordGatherMap.get("amazonRecordGatherMap");

        //早期 数据
        Map<String,BigDecimal> skuEarlyFeeMap = this.getSkuEarlyFeeMap(transactionRecord,warnMsg);

        //手续费数据
        Map<String,BigDecimal> skuCouponFeeMap = this.getSkuCouponFeeMap(transactionRecord);

        List<TransactionRecord> orderRecordAnalys = amazonRecordGatherMap.get("Order");
        if(orderRecordAnalys==null) return new ArrayList<>();

        //TODO 总店租(待确定）
        transactionRecord.setDescription("Subscription");
        transactionRecord.setType(null);
        transactionRecord.getParams().remove("descriptionCompareSign");
        TransactionRecord shopRentRecord = transactionRecordMapper.selectGatherRecord(transactionRecord);
        BigDecimal perSkuShopRent = (shopRentRecord!=null)?shopRentRecord.getOther():new BigDecimal(0);

        //其他服务费 Manual Processing Fee
        transactionRecord.setDescription("Manual Processing Fee");
        TransactionRecord otherTransactionGaterRecord = transactionRecordMapper.selectGatherRecord(transactionRecord);
        BigDecimal totalOtherServiceFee = (otherTransactionGaterRecord!=null)
                ? otherTransactionGaterRecord.getOtherTransactionFees():new BigDecimal(0);

        //TODO 平台调整费 FBA Inventory Reimbursement - General Adjustment
        transactionRecord.setDescription("FBA Inventory Reimbursement - General Adjustment");
        TransactionRecord platformAdjustmentRecord = transactionRecordMapper.selectGatherRecord(transactionRecord);
        BigDecimal platformAdjustmentFee = (platformAdjustmentRecord!=null)
                ? platformAdjustmentRecord.getOther():new BigDecimal(0);

        //平台退款服务费
        transactionRecord.setDescription(null);
        Map<String,BigDecimal> skuRefundServiceFeeMap = this.getSkuRefundServiceFeeMap(transactionRecord);

        //sku平台服务调整费
        transactionRecord.setType("Adjustment");
        transactionRecord.setDescription("FBA Inventory Reimbursement - General Adjustment");
        Map<String,BigDecimal> skuAdjustmentFeeMap = this.getSkuAdjustmentFeeMap(transactionRecord);

        //TODO 账号平台服务调整费
        transactionRecord.setDescription("");

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

            //仓储费（0.79%）
            //BigDecimal fbaInventoryAndInboundServicesFees = new BigDecimal(0);
            BigDecimal truckServiceFee = (truckFeeMap.get(standardSku)!=null)?truckFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setTruckServiceFee(truckServiceFee);
            BigDecimal storageFee = (skuStorageFeeMap.get(standardSku)!=null)?skuStorageFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setStorageFee(storageFee.multiply(new BigDecimal(-1)));//整数取负
            BigDecimal returnRemovalFee = (returnSkuRemovalFeeMap.get(standardSku)!=null)?returnSkuRemovalFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setReturnRemovalFee(returnRemovalFee);
            BigDecimal disposalRemovalFee = (disposalSkuRemovalFeeMap.get(standardSku)!=null)?disposalSkuRemovalFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setDisposalRemovalFee(disposalRemovalFee);
            BigDecimal fbaInventoryAndInboundServicesFees = truckServiceFee.add(storageFee).add(returnRemovalFee).add(disposalRemovalFee);
            financeVo.setFbaInventoryAndInboundServicesFees(fbaInventoryAndInboundServicesFees);

            //TODO 运输标签费

            //平台服务费 totalQuantity
            financeVo.setShopRent(perSkuShopRent);
            BigDecimal earlyFee = (skuEarlyFeeMap.get(standardSku)!=null)?skuEarlyFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setEarlyFee(earlyFee);
            financeVos.add(financeVo);
            BigDecimal otherServiceFee = orderRecord.getProductSales().divide(totalSales,4).multiply(totalOtherServiceFee);
            financeVo.setOtherServiceFee(otherServiceFee);
            BigDecimal refundServiceFee = (skuRefundServiceFeeMap.get(standardSku)!=null)?skuRefundServiceFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setRefundAdministrationFees(refundServiceFee);
            BigDecimal adjustments = (skuAdjustmentFeeMap.get(standardSku)!=null)?skuAdjustmentFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setAdjustments(adjustments);
            BigDecimal couponFee =  (skuCouponFeeMap.get(standardSku)!=null)?skuCouponFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setCouponFee(couponFee);
        }
        return financeVos;
    }

    private Map<String, BigDecimal> getSkuCouponFeeMap(TransactionRecord transactionRecord) {
        transactionRecord.setDescription("Save%");
        //transactionRecordMapper.selectSkuEarlyFeeGatherList 适用与获取手续费
        List<SkuFee> skuCouponFeeList = transactionRecordMapper.selectSkuEarlyFeeGatherList(transactionRecord);
        Map<String, BigDecimal> map = skuCouponFeeList.stream().collect(Collectors.toMap(SkuFee::getSku,SkuFee::getFee));
        return map;
    }

    private Map<String,BigDecimal> getSkuAdjustmentFeeMap(TransactionRecord transactionRecord) {
        List<SkuFee> skuAdjustmentFeeList = transactionRecordMapper.selectSkuAdjustmentFeeList(transactionRecord);
        Map<String,BigDecimal> map = skuAdjustmentFeeList.stream().collect(Collectors.toMap(SkuFee::getSku,SkuFee::getFee));
        return map;
    }

    private Map<String, BigDecimal> getSkuEarlyFeeMap(TransactionRecord transactionRecord,StringBuilder warnMsg) {
        transactionRecord.setDescription("Early%");
        transactionRecord.setFulfilment(null);
        transactionRecord.setType(null);
        Map params = transactionRecord.getParams();
        params.put("descriptionCompareSign","like");
        transactionRecord.setParams(params);
        List<SkuFee> earlyRecordList = transactionRecordMapper.selectSkuEarlyFeeGatherList(transactionRecord);
        Map<String, BigDecimal> map = earlyRecordList.stream().collect(Collectors.toMap(SkuFee::getSku,SkuFee::getFee));
        return map;
    }

    @Override
    public Map<String, BigDecimal> getSkuRemovalFeeMap(String removalType) {
        List<SkuRemovalFee> skuRemovalFeeList = transactionRecordMapper.selectSkuRemovalFeeList(removalType);
        Map<String,BigDecimal> skuRemovalFeeMap = skuRemovalFeeList.stream().collect(Collectors.toMap(SkuRemovalFee::getSku,SkuRemovalFee::getRemovalFee));
        return skuRemovalFeeMap;
    }

    @Override
    public Map<String, BigDecimal> getSkuRefundServiceFeeMap(TransactionRecord transactionRecord) {
        List<SkuRefundServiceFee> skuRefundServiceFeeList = transactionRecordMapper.selectSkuRefundServiceFeeList(transactionRecord);
        Map<String, BigDecimal> map = skuRefundServiceFeeList.stream().collect(Collectors.toMap(SkuRefundServiceFee::getSku,SkuRefundServiceFee::getRefundServiceFee));
        return map;
    }

    private Map<String,BigDecimal> getTruckServiceFeeMap(TransactionRecord transactionRecord) {
        transactionRecord.setDescription("FBA Amazon-Partnered Carrier Shipment Fee");
        List<TransactionRecord> truckFeeRecords = this.selectTransactionRecordList(transactionRecord);
        Map<String, MskuProductinfoRelationVo> mskuProductinfoRelationVoMap =  productinfoRelationService.getSkuMskuMap();
        Map<String,BigDecimal> truckFeeMap = new HashMap();
        for (TransactionRecord record:truckFeeRecords){
            //获取卡车上货物的数量
            TruckService tsParam = new TruckService();
            tsParam.setTruckRecordId(record.getId());
            List<TruckService> tsList = truckServiceService.selectTruckServiceList(tsParam);
            Long totalNum = 0l;
            for(TruckService ts:tsList){
                totalNum+=ts.getShipped();
            }
            for(TruckService ts:tsList){
                Double rate = Arith.div(ts.getShipped().doubleValue(),totalNum.doubleValue());
                BigDecimal tFee = new BigDecimal(Arith.mul(rate,record.getOther().doubleValue()));
                String standardSku = mskuProductinfoRelationVoMap.get(ts.getMsku()).getSku();
                truckFeeMap.put(standardSku,tFee);
            }
        }
        return truckFeeMap;
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
