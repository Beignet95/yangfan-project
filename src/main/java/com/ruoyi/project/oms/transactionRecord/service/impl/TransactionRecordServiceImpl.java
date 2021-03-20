package com.ruoyi.project.oms.transactionRecord.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.ruoyi.common.utils.Arith;
import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.historyoperate.domain.HistoryOperate;
import com.ruoyi.project.compdata.historyoperate.service.IHistoryOperateService;
import com.ruoyi.project.compdata.productPrincipal.service.IProductPrincipalService;
import com.ruoyi.project.compdata.truckService.domain.TruckService;
import com.ruoyi.project.compdata.truckService.service.ITruckServiceService;
import com.ruoyi.project.oms.dealfeeAsin.domain.DealfeeAsin;
import com.ruoyi.project.oms.dealfeeAsin.service.IDealfeeAsinService;
import com.ruoyi.project.oms.transactionRecord.constant.TransactionType;
import com.ruoyi.project.oms.transactionRecord.domain.TransactionRecordImpTempVo;
import com.ruoyi.project.oms.transactionRecord.vo.*;
import com.ruoyi.project.pms.advertisingFee.domain.AdvertisingFee;
import com.ruoyi.project.pms.advertisingFee.service.IAdvertisingFeeService;
import com.ruoyi.project.pms.productinfoReation.domain.ProductinfoRelation;
import com.ruoyi.project.pms.productinfoReation.service.IProductinfoRelationService;
import com.ruoyi.project.pms.productinfoReation.vo.MskuProductinfoRelationVo;
import com.ruoyi.project.pms.skuCoupon.service.ISkuCouponService;
import com.ruoyi.project.sms.storageRecord.domain.StorageRecord;
import com.ruoyi.project.sms.storageRecord.service.IStorageRecordService;
import com.ruoyi.project.sms.storageRecord.service.impl.StorageRecordServiceImpl;
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

    @Autowired
    private IAdvertisingFeeService advertisingFeeService;

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

            //设置标准SKU 型号 负责人
            if(StringUtils.isNotEmpty(impTempVo.getSku())){
                MskuProductinfoRelationVo mprMap = mskuProductinfoRelationVoMap.get(impTempVo.getSku());
                transactionRecord.setStandardSku(mprMap.getSku());//标准sku
                transactionRecord.setSpu(mprMap.getType());//型号
                transactionRecord.setPrincipal(mprMap.getPrincipal());//负责人
                //transactionRecord.setChargType(mprMap.getChargeType());
            }

            //如果是手续费 设置标准SKU
            String description = transactionRecord.getDescription();
            if(StringUtils.isNotEmpty(description)&&description.startsWith("Save")&&StringUtils.isEmpty(transactionRecord.getStandardSku())){
                String standardSku = conponSkuMap.get(description);
                //skuProductinfoRelationVoMap.get(standardSku);
                transactionRecord.setStandardSku(standardSku);
            }

            //如果是早期费用 设置标准SKU
            if(StringUtils.isNotEmpty(description)&&description.startsWith("Early")&&StringUtils.isEmpty(transactionRecord.getStandardSku())){
                String[] strs = description.split(" ");
                if(strs.length>0){
                    String pasin = strs[strs.length-1];
                    String standardSku = pasinSkuMap.get(pasin);
                    transactionRecord.setStandardSku(standardSku);
                }
            }

            //将交易记录的type映射为统一的标记
            //从字典中获取翻译值List，并将List转为Map,方便获取
            Map<String,String> transationTypeMap = dictTypeService.getDictDataAsMapByType("oms_transation_type_translate");
            String type = impTempVo.getType();
            if(StringUtils.isNotEmpty(type)){
                String typeOfEn = transationTypeMap.get(type);
                type=(StringUtils.isNotEmpty(typeOfEn))?typeOfEn:type;
                transactionRecord.setType(Integer.parseInt(type));
            }else{
                transactionRecord.setType(0);
            }

            //如果是BD或LD记录，将对应的SKU（本为空）设置为BD或LD
            if(transactionRecord.getType()==TransactionType.DEAL_FEE){
                if(transactionRecord.getDescription()!=null&&transactionRecord.getDescription().contains("Lightning Deal")){
                    transactionRecord.setSku("LD");
                }else transactionRecord.setSku("BD");
            }

            //设置好账号和站点
            transactionRecord.setAccount(account);
            transactionRecord.setSite(site);

            //处理每个国家的时间
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

            try
            {
                this.insertTransactionRecord(transactionRecord);
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
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条!");
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
    public Map<String,Object> selectTransactionAnaly(TransactionRecord transactionRecord) {

        String preMonthStr = (String)transactionRecord.getParams().get("month");
        Date startTime = null;
        if(StringUtils.isNotEmpty(preMonthStr)){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月");
            Calendar c = Calendar.getInstance();
            try {
                // 注意格式需要与上面一致，不然会出现异常
                startTime = format.parse(preMonthStr);
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH),
                        0, 0, 0);
                Date endTime = c.getTime();//最后一天
                Map params = new HashMap();
                params.put("beginTime",startTime);
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
        Map<Integer,List<TransactionRecord>> amazonRecordGatherMap = amazonRecordGather.stream().
                collect(Collectors.groupingBy(TransactionRecord::getType));
        Map<Integer,List<TransactionRecord>> sellerRecordGatherMap = sellerRecordGather.stream().
                collect(Collectors.groupingBy(TransactionRecord::getType));

        if(amazonRecordGatherMap.size()==0) return new HashMap<>();

        //Amazon订单和退单
        List<TransactionRecord> orderRecords = amazonRecordGatherMap.get(TransactionType.ORDER);
        List<TransactionRecord> RefundRecords = amazonRecordGatherMap.get(TransactionType.REFUND);
        Map<String,TransactionRecord> refundRecordMap = new HashMap<>();
        if(RefundRecords!=null&&RefundRecords.size()>0){
            //refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku, Function.identity()));
            refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }
        Map<String,TransactionRecord> orderRecordMap = new HashMap<>();
        if(orderRecords!=null&&orderRecords.size()>0){
            //refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku, Function.identity()));
            orderRecordMap = orderRecords.stream().collect(Collectors.toMap(k->k.getStandardSku(), Function.identity()));
        }
        //TODO 数量
        List<TransactionRecord> analyRecordList = transactionRecordMapper.selectQuantityAnaly(transactionRecord);
        Map<String,TransactionRecord> analyRecordMap=
                analyRecordList.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku,Function.identity()));

        //Seller订单
        List<TransactionRecord> sellerRecords = sellerRecordGatherMap.get(TransactionType.ORDER);
        Map<String,TransactionRecord> sellerRecordMap = new HashMap<>();
        if(sellerRecords!=null&&sellerRecords.size()>0){
            //refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku, Function.identity()));
            sellerRecordMap = sellerRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }
        //Seller退款
        List<TransactionRecord> sellerRefundRecords = sellerRecordGatherMap.get(TransactionType.REFUND);
        Map<String,TransactionRecord> sellerRefundRecordMap = new HashMap<>();
        if(sellerRefundRecords!=null&&sellerRefundRecords.size()>0){
            //refundRecordMap = RefundRecords.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku, Function.identity()));
            sellerRefundRecordMap = sellerRefundRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }

        //Adjustment
        List<TransactionRecord> adjustmentRecords = amazonRecordGatherMap.get(TransactionType.ADJUSTMENT);
        Map<String,TransactionRecord> adjustmentRecordMap = new HashMap<>();
        if(adjustmentRecords!=null&&adjustmentRecords.size()>0){
            adjustmentRecordMap = adjustmentRecords.stream().collect(Collectors.toMap(k->k.getSite()+k.getStandardSku(), Function.identity()));
        }

        //Fee Adjustment
        Map<String,TransactionRecord> skuFeeAdjustmentMap = this.getSkuFeeAdjustmentMap(transactionRecord);
        //从卡车服务模块  获取卡车服务费
        Map<String,Long> tructServiceFeeMap = truckServiceService.getTructServiceFeeMap(null);

        //从仓储模块 获取每个标准SKU对应的总仓储费
        Map<String, StorageRecord> skuStorageFeeMap = this.getSkuStorageFeeMapByMonth(transactionRecord);
        //标准SKU对应退货移除费Map
        Map<String,BigDecimal> returnSkuRemovalFeeMap = this.getSkuRemovalFeeMap("Return");
        //标准SKU对应破损移除费Map
        Map<String,BigDecimal> disposalSkuRemovalFeeMap = this.getSkuRemovalFeeMap("Disposal");
        //总数量
        transactionRecord.setDescription(null);
        transactionRecord.setFulfilment(null);
        transactionRecord.setType(TransactionType.ORDER);
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

        //订单数据
        List<TransactionRecord> orderRecordAnalys = amazonRecordGatherMap.get(TransactionType.ORDER);
        if(orderRecordAnalys==null) return new HashMap<>();

        //TODO 总店租(待确定）
        transactionRecord.setDescription("Subscription");
        transactionRecord.setType(null);
        //transactionRecord.getParams().remove("descriptionCompareSign");
        TransactionRecord shopRentRecord = transactionRecordMapper.selectGatherRecord(transactionRecord);
        BigDecimal perSkuShopRent = (shopRentRecord!=null)?shopRentRecord.getOther():new BigDecimal(0);

        //其他服务费 Manual Processing Fee
        transactionRecord.setDescription("Manual Processing Fee%");
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
        transactionRecord.setType(TransactionType.ADJUSTMENT);
        transactionRecord.setDescription("FBA Inventory Reimbursement - General Adjustment");
        Map<String,BigDecimal> skuAdjustmentFeeMap = this.getSkuAdjustmentFeeMap(transactionRecord);

        //TODO 账号平台服务调整费
        transactionRecord.setDescription("");

        //广告费 costOfAdvertising
        Map<String,AdvertisingFee> skuAdvertisingFeeMap = this.getSkuAdvertisingFeeMap(transactionRecord);

        //BD和LD费用
        Map<String,BigDecimal> skuBDFeeMap = this.getSkuBDFeeMap(transactionRecord);
        Map<String,BigDecimal> skuLDFeeMap = this.getSkuLDFeeMap(transactionRecord);


        List<FinanceVo> financeVos = new ArrayList<>();
        for(TransactionRecord orderRecord:analyRecordList){
            String standardSku = orderRecord.getStandardSku();
            TransactionRecord amazonRecord = orderRecordMap.get(standardSku);
            if (amazonRecord!=null) {
                amazonRecord.setQuantity(orderRecord.getQuantity());
                BeanUtils.copyProperties(amazonRecord,orderRecord);
            }else {
                TransactionRecord tr = new TransactionRecord();
                tr.initNumber();
                tr.setAccount(orderRecord.getAccount());
                tr.setSite(orderRecord.getSite());
                tr.setPrincipal(orderRecord.getPrincipal());
                tr.setSpu(orderRecord.getSpu());
                tr.setQuantity(orderRecord.getQuantity());
                tr.setStandardSku(orderRecord.getStandardSku());
                BeanUtils.copyProperties(tr,orderRecord);
            }

            FinanceVo financeVo = new FinanceVo();
            String site = orderRecord.getSite();

            financeVo.setAccount(orderRecord.getAccount());
            financeVo.setSite(orderRecord.getSite());
            financeVo.setQuantity(orderRecord.getQuantity());
            financeVo.setFbaProductSales(orderRecord.getProductSales());//FBA退款需要与other汇总
            financeVo.setPrincipal(orderRecord.getPrincipal());
            financeVo.setSku(orderRecord.getStandardSku());
            financeVo.setType(orderRecord.getSpu());

            TransactionRecord sellerRecord = sellerRecordMap.get(site+standardSku);
            TransactionRecord sellerRefundRecord = sellerRefundRecordMap.get(site+standardSku);
            TransactionRecord refundRecord = refundRecordMap.get(orderRecord.getSite()+orderRecord.getStandardSku());
            BigDecimal productSalesOfSeller = new BigDecimal(0);//自配送销售
            BigDecimal productSaleRefundsOfSeller = new BigDecimal(0);//自配送销售退款
            BigDecimal fbaProductSales = orderRecord.getProductSales();//FBA销售
            BigDecimal fbaProductSalesRefunds = new BigDecimal(0);//FBA销售退款
            BigDecimal shippingCredits = orderRecord.getPostageCredits();//尾程运费信用
            BigDecimal shippingCreditRefunds = new BigDecimal(0);//尾程运费信用退款
            BigDecimal giftWrapCredits = orderRecord.getGiftWrapCredits();//包装费
            BigDecimal giftWrapCreditRefunds = new BigDecimal(0);//包装费退款
            BigDecimal promotionalRebates = new BigDecimal(0);//促销费
            BigDecimal promotionalRebateRefunds = new BigDecimal(0);//促销费退款
            BigDecimal sellerFulfilledSellingFees = new BigDecimal(0);//自配送销售佣金
            BigDecimal sellerFulfilledSellingFeeRefunds = new BigDecimal(0);//自配送销售退款佣金
            BigDecimal fbaSellingFees = orderRecord.getSellingFees();//FBA销售佣金
            BigDecimal sellingFeeRefunds = new BigDecimal(0);//FBA销售退款佣金
            BigDecimal fbaTransactionFees = orderRecord.getFbaFees();//尾程运费
            BigDecimal fbaTransactionFeeRefunds = new BigDecimal(0);//尾程运费退回
            //卡车服务费
            //仓储费
            //长期仓储费
            //退货移除费
            //破损库存销毁手续费
            if(sellerRecord!=null){
                productSalesOfSeller = sellerRecord.getProductSales();
                promotionalRebates = sellerRecord.getPromotionalRebates();
                sellerFulfilledSellingFees = sellerRecord.getSellingFees();
                fbaTransactionFees = fbaTransactionFees.add(sellerRecord.getFbaFees());
            }
            if(sellerRefundRecord!=null){
                productSaleRefundsOfSeller = sellerRefundRecord.getProductSales();
                sellerFulfilledSellingFeeRefunds = sellerRefundRecord.getSellingFees();
                promotionalRebateRefunds = sellerRefundRecord.getPromotionalRebates();
                shippingCreditRefunds = sellerRefundRecord.getPostageCredits();
                giftWrapCreditRefunds = sellerRefundRecord.getGiftWrapCredits();
                sellerFulfilledSellingFeeRefunds = sellerRefundRecord.getSellingFees();
                fbaTransactionFeeRefunds = sellerRefundRecord.getFbaFees();
            }
            if(refundRecord!=null){
                fbaProductSalesRefunds = refundRecord.getProductSales().add(refundRecord.getOther());//FBA销售退款 需与other相加（指引没有说明）
                shippingCreditRefunds = shippingCreditRefunds.add(refundRecord.getPostageCredits());
                giftWrapCreditRefunds = giftWrapCreditRefunds.add(refundRecord.getGiftWrapCredits());
                promotionalRebateRefunds = promotionalRebateRefunds.add(refundRecord.getPromotionalRebates());
                sellingFeeRefunds = refundRecord.getSellingFees();
                fbaTransactionFeeRefunds = fbaTransactionFeeRefunds.add(refundRecord.getFbaFees());
            }
            if(skuFeeAdjustmentMap.get(standardSku)!=null){
                fbaTransactionFeeRefunds = fbaTransactionFeeRefunds.add(skuFeeAdjustmentMap.get(standardSku).getFbaFees());
                skuFeeAdjustmentMap.remove(standardSku);
            }

            //销售收入(含自配送）
            BigDecimal salesRevenue = fbaProductSales.add(fbaProductSalesRefunds).add(productSalesOfSeller).add(productSaleRefundsOfSeller);
            financeVo.setProductSalesOfSeller(productSalesOfSeller);
            financeVo.setProductSaleRefundsOfSeller(productSaleRefundsOfSeller);
            financeVo.setFbaProductSales(fbaProductSales);
            financeVo.setFbaProductSalesRefunds(fbaProductSalesRefunds);
            financeVo.setSalesRevenue(salesRevenue);

            //赔偿金
            TransactionRecord adjustmentRecord = adjustmentRecordMap.get(orderRecord.getStandardSku());
            BigDecimal fbaInventoryCredit = new BigDecimal(0);
            if(adjustmentRecord!=null) fbaInventoryCredit = adjustmentRecord.getTotal();
            financeVo.setFbaInventoryCredit(fbaInventoryCredit);

            //尾程运费
            financeVo.setShippingCredits(shippingCredits);
            financeVo.setShippingCreditRefunds(shippingCreditRefunds);
            BigDecimal finalFreight = shippingCredits.add(shippingCreditRefunds);
            financeVo.setFinalFreight(finalFreight);

            //包装费

            financeVo.setGiftWrapCredits(giftWrapCredits);
            financeVo.setGiftWrapCreditRefunds(giftWrapCreditRefunds);
            BigDecimal packagingFee = giftWrapCredits.add(giftWrapCreditRefunds);
            financeVo.setPackagingFee(packagingFee);

            //促销费
            promotionalRebates = promotionalRebates.add(orderRecord.getPromotionalRebates());
            if(refundRecord!=null) promotionalRebateRefunds = refundRecord.getPromotionalRebates();
            BigDecimal promotionFee = promotionalRebates.add(promotionalRebateRefunds);
            financeVo.setPromotionalRebates(promotionalRebates);
            financeVo.setPromotionalRebateRefunds(promotionalRebateRefunds);
            financeVo.setPromotionFee(promotionFee);

            //尾程运费退回（N6+T6+AG6,占销售比例14.5%）
            BigDecimal finalFreightReturn = fbaTransactionFees.add(fbaTransactionFeeRefunds);
            financeVo.setFbaTransactionFees(fbaTransactionFees);
            financeVo.setFbaTransactionFeeRefunds(fbaTransactionFeeRefunds);
            financeVo.setFinalFreightReturn(finalFreightReturn);

            //TODO 其他交易费 暂时不做计算
//            BigDecimal othertransactionFees = new BigDecimal(0);
//            BigDecimal otherTransactionFeeRefunds = new BigDecimal(0);
//            BigDecimal otherTransactionFee = new BigDecimal(0);
//            financeVo.setOthertransactionFees(othertransactionFees);
//            financeVo.setOtherTransactionFeeRefunds(otherTransactionFeeRefunds);
//            financeVo.setOtherTransactionFee(otherTransactionFee);

            //仓储费（0.79%）
            //BigDecimal fbaInventoryAndInboundServicesFees = new BigDecimal(0);
            BigDecimal truckServiceFee = (truckFeeMap.get(standardSku)!=null)?truckFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setTruckServiceFee(truckServiceFee);
            BigDecimal storageFee = (skuStorageFeeMap.get(standardSku)!=null&&skuStorageFeeMap.get(standardSku).getStorageFee()!=null)
                    ?skuStorageFeeMap.get(standardSku).getStorageFee().multiply(new BigDecimal(-1)):new BigDecimal(0);//整数取负
            financeVo.setStorageFee(storageFee);
            BigDecimal returnRemovalFee = (returnSkuRemovalFeeMap.get(standardSku)!=null)?returnSkuRemovalFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setReturnRemovalFee(returnRemovalFee);
            BigDecimal disposalRemovalFee = (disposalSkuRemovalFeeMap.get(standardSku)!=null)?disposalSkuRemovalFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setDisposalRemovalFee(disposalRemovalFee);
            BigDecimal fbaInventoryAndInboundServicesFees = new BigDecimal(0);
            fbaInventoryAndInboundServicesFees = fbaInventoryAndInboundServicesFees.add(truckServiceFee).add(storageFee).add(returnRemovalFee).add(disposalRemovalFee);
            financeVo.setFbaInventoryAndInboundServicesFees(fbaInventoryAndInboundServicesFees);
            financeVo.setFbaInventoryAndInboundServicesFees(fbaInventoryAndInboundServicesFees);

            //TODO 运输标签费

            //平台服务费 totalQuantity
            financeVo.setShopRent(perSkuShopRent);
            BigDecimal bdDealFee = (skuBDFeeMap.get(standardSku)!=null)?skuBDFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setBdDealFee(bdDealFee);
            BigDecimal ldDealFee = (skuLDFeeMap.get(standardSku)!=null)?skuLDFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setLdDealFee(ldDealFee);
            BigDecimal earlyFee = new BigDecimal(0);
            if(skuEarlyFeeMap.get(standardSku)!=null){
                earlyFee =  skuEarlyFeeMap.get(standardSku);
                skuEarlyFeeMap.remove(standardSku);//获取后移除，最后还为空则将数据返回到页面
            }
            financeVo.setEarlyFee(earlyFee);
            financeVos.add(financeVo);
            BigDecimal otherServiceFee = orderRecord.getProductSales().divide(totalSales,4).multiply(totalOtherServiceFee);
            financeVo.setOtherServiceFee(otherServiceFee);
            BigDecimal refundServiceFee = (skuRefundServiceFeeMap.get(standardSku)!=null)?skuRefundServiceFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setRefundAdministrationFees(refundServiceFee);
            BigDecimal adjustments = (skuAdjustmentFeeMap.get(standardSku)!=null)?skuAdjustmentFeeMap.get(standardSku):new BigDecimal(0);
            financeVo.setAdjustments(adjustments);
            BigDecimal couponFee = new BigDecimal(0);
            if(skuCouponFeeMap.get(standardSku)!=null){
                couponFee =  skuCouponFeeMap.get(standardSku);
                skuCouponFeeMap.remove(standardSku);//获取后移除，最后还为空则将数据返回到页面
            }
            financeVo.setCouponFee(couponFee);

            //TODO sellingFeeRefunds = sellingFeeRefunds.subtract(refundAdministrationFees);//20项 = 20项 减去 第30项
            sellingFeeRefunds = sellingFeeRefunds.subtract(refundServiceFee);//20项 = 20项 减去 第30项
            //销售佣金(含自配送）
            financeVo.setSellerFulfilledSellingFees(sellerFulfilledSellingFees);
            financeVo.setSellerFulfilledSellingFeeRefunds(sellerFulfilledSellingFeeRefunds);
            financeVo.setFbaSellingFees(fbaSellingFees);
            financeVo.setSellingFeeRefunds(sellingFeeRefunds);
            BigDecimal selfdeliveryCommission = sellerFulfilledSellingFees.add(sellerFulfilledSellingFeeRefunds)
                    .add(fbaSellingFees).add(sellingFeeRefunds);
            financeVo.setSelfdeliveryCommission(selfdeliveryCommission);

            //销售佣金(含自配送）
//            BigDecimal fbaSellingFees = orderRecord.getSellingFees();
//            BigDecimal sellingFeeRefunds = new BigDecimal(0);
//            if(refundRecord!=null) sellingFeeRefunds = refundRecord.getSellingFees();
//            //FBA销售退款佣金 FBA订单的refund中的selling fee,多余部分减去第30项
//            sellingFeeRefunds = sellingFeeRefunds.subtract(refundServiceFee);
//            BigDecimal selfdeliveryCommission = fbaSellingFees.add(sellingFeeRefunds).add(sellerFulfilledSellingFees).add(sellerFulfilledSellingFeeRefunds);
//            financeVo.setFbaSellingFees(fbaSellingFees);
//            financeVo.setSellerFulfilledSellingFees(sellerFulfilledSellingFees);
//            financeVo.setSellerFulfilledSellingFeeRefunds(sellerFulfilledSellingFeeRefunds);
//            financeVo.setSellingFeeRefunds(sellingFeeRefunds);
//            financeVo.setSelfdeliveryCommission(selfdeliveryCommission);

            //广告费（3.32%）
            BigDecimal costOfAdvertising = (skuAdvertisingFeeMap.get(standardSku)!=null&&skuAdvertisingFeeMap.get(standardSku).getCharge()!=null)
                    ?skuAdvertisingFeeMap.get(standardSku).getCharge():new BigDecimal(0);
            financeVo.setCostOfAdvertising(costOfAdvertising);
            BigDecimal advertisingFee = costOfAdvertising;
            financeVo.setAdvertisingFee(advertisingFee);

            //去除map中的值 剩下的需要再作汇总
            skuStorageFeeMap.remove(standardSku);
            skuAdvertisingFeeMap.remove(standardSku);
        }

        //未匹配的记录也许放置到list中
        Map<String,FinanceVo> noSalesRecordGather = new HashMap<>();
        for(Map.Entry<String, StorageRecord> entry : skuStorageFeeMap.entrySet()){
            StorageRecord storageRecord = (StorageRecord)entry.getValue();
            FinanceVo financeVo = new FinanceVo();
            financeVo.setAccount(storageRecord.getAccount());
            financeVo.setSite(storageRecord.getAccount()+"-"+storageRecord.getCountryCode());
            financeVo.setPrincipal(storageRecord.getPrincipal());
            financeVo.setSku(storageRecord.getStandardSku());
            financeVo.setType(storageRecord.getSpu());
            financeVo.setStorageFee(storageRecord.getStorageFee());
            financeVo.setFbaInventoryAndInboundServicesFees(storageRecord.getStorageFee());
            noSalesRecordGather.put(entry.getKey(),financeVo);

        }
        for(Map.Entry<String, AdvertisingFee> entry : skuAdvertisingFeeMap.entrySet()){
            AdvertisingFee advertisingFee = (AdvertisingFee)entry.getValue();
            FinanceVo financeVo = new FinanceVo();

            String standartSku = advertisingFee.getStandardSku();
            if(noSalesRecordGather.containsKey(standartSku)) {
                financeVo = noSalesRecordGather.get(standartSku);
                financeVo.setAdvertisingFee(advertisingFee.getCharge());
            }else {
                financeVo.setAccount(advertisingFee.getSite());
                financeVo.setSite(advertisingFee.getSite());
                financeVo.setPrincipal(advertisingFee.getPrincipal());
                financeVo.setSku(advertisingFee.getStandardSku());
                financeVo.setType(advertisingFee.getSpu());
                financeVo.setAdvertisingFee(advertisingFee.getCharge());
                financeVo.setCostOfAdvertising(advertisingFee.getCharge());
            }
            noSalesRecordGather.put(entry.getKey(),financeVo);
        }

        for(Map.Entry<String, TransactionRecord> entry : skuFeeAdjustmentMap.entrySet()){
            TransactionRecord record = (TransactionRecord)entry.getValue();
            FinanceVo financeVo = new FinanceVo();

            String standartSku = record.getStandardSku();
            if(noSalesRecordGather.containsKey(standartSku)) {
                financeVo = noSalesRecordGather.get(standartSku);
                financeVo.setFbaTransactionFeeRefunds(record.getFbaFees());
                financeVo.setFinalFreightReturn(record.getFbaFees());
            }else {
                financeVo.setAccount(record.getAccount());
                financeVo.setSite(record.getSite());
                financeVo.setPrincipal(record.getPrincipal());
                financeVo.setSku(record.getStandardSku());
                financeVo.setType(record.getSpu());
                financeVo.setFbaTransactionFeeRefunds(record.getFbaFees());
                financeVo.setFinalFreightReturn(record.getFbaFees());

            }
            noSalesRecordGather.put(entry.getKey(),financeVo);
        }

        List<FinanceVo> list = new ArrayList<FinanceVo>(noSalesRecordGather.values());
        financeVos.addAll(list);

        FinanceVo totalGather = getTotalGather(financeVos);
        financeVos.add(totalGather);

        if(skuCouponFeeMap.size()>0){
            warnMsg.append("<br/>以下手续费记录找不到匹配,请确认映射关系是否有误！");
            for (String key : skuCouponFeeMap.keySet()) {
                BigDecimal couponFee = skuCouponFeeMap.get(key);
                warnMsg.append("<br/>标准sku："+key+"，手续费："+couponFee);
            }
        }
        if(skuEarlyFeeMap.size()>0){
            warnMsg.append("<br/>以下早期费用记录找不到匹配,请确认映射关系是否有误！");
            for (String key : skuEarlyFeeMap.keySet()) {
                BigDecimal earlyFee = skuEarlyFeeMap.get(key);
                warnMsg.append("<br/>标准sku："+key+"，手续费："+earlyFee);
            }
        }
        if(skuFeeAdjustmentMap.size()>0){
            warnMsg.append("<br/>以下尾程运费退回-Fee Adjustment+refund fba fee费用记录找不到匹配,请确认映射关系是否有误！");
            for (String key : skuFeeAdjustmentMap.keySet()) {
                BigDecimal fbaTransactionFeeRefunds = skuFeeAdjustmentMap.get(key).getFbaFees();
                warnMsg.append("<br/>标准sku："+key+"，尾程运费退回："+fbaTransactionFeeRefunds);
            }
        }
        Map resultMap = new HashMap();
        resultMap.put("data",financeVos);
        resultMap.put("msg",warnMsg.toString());

        return resultMap;
    }

    private Map<String, TransactionRecord> getSkuFeeAdjustmentMap(TransactionRecord transactionRecord) {
        transactionRecord.setFulfilment(null);
        transactionRecord.setType(TransactionType.FEE_ADJUSTMENT);
        List<TransactionRecord> feeAdjustmentRecordList = transactionRecordMapper.selectTransactionAnaly(transactionRecord);
        Map<String, TransactionRecord> map = feeAdjustmentRecordList.stream().collect(Collectors.toMap(TransactionRecord::getStandardSku,Function.identity()));
        return map;
    }

    private FinanceVo getTotalGather(List<FinanceVo> financeVos) {
        Long quantity = 0l;
        FinanceVo totalGather = new FinanceVo(null,null,"合计",null,null,null,null,0l
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0), new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0)
                ,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0));
        for(FinanceVo finance:financeVos){
            totalGather.setQuantity(totalGather.getQuantity()+((finance.getQuantity()!=null)?finance.getQuantity():0l));
            totalGather.setSalesRevenue(totalGather.getSalesRevenue().add((finance.getSalesRevenue()!=null)
                    ?finance.getSalesRevenue():new BigDecimal(0)));
            totalGather.setProductSalesOfSeller(totalGather.getProductSalesOfSeller().add((finance.getProductSalesOfSeller()!=null)
                    ?finance.getProductSalesOfSeller():new BigDecimal(0)));
            totalGather.setFbaProductSales(totalGather.getFbaProductSales().add((finance.getFbaProductSales()!=null)
                    ?finance.getFbaProductSales():new BigDecimal(0)));
            totalGather.setFbaProductSalesRefunds(totalGather.getFbaProductSalesRefunds().add((finance.getFbaProductSalesRefunds()!=null)
                    ?finance.getFbaProductSalesRefunds():new BigDecimal(0)));
            //赔偿金
            totalGather.setCompensation(totalGather.getCompensation().add((finance.getCompensation()!=null)
                    ?finance.getCompensation():new BigDecimal(0)));

            //尾程运费信用 finalFreight
            totalGather.setFinalFreight(totalGather.getFinalFreight().add((finance.getFinalFreight()!=null)
                    ?finance.getFinalFreight():new BigDecimal(0)));
            //尾程运费信用 shippingCredits
            totalGather.setShippingCredits(totalGather.getShippingCredits().add((finance.getShippingCredits()!=null)
                    ?finance.getShippingCredits():new BigDecimal(0)));
            //尾程运费信用退款 shippingCreditRefunds
            totalGather.setShippingCreditRefunds(totalGather.getShippingCreditRefunds().add((finance.getShippingCreditRefunds()!=null)
                    ?finance.getShippingCreditRefunds():new BigDecimal(0)));
            //包装费 packagingFee
            totalGather.setPackagingFee(totalGather.getPackagingFee().add((finance.getPackagingFee()!=null)
                    ?finance.getPackagingFee():new BigDecimal(0)));
            //包装费 giftWrapCredits
            totalGather.setGiftWrapCredits(totalGather.getGiftWrapCredits().add((finance.getGiftWrapCredits()!=null)
                    ?finance.getGiftWrapCredits():new BigDecimal(0)));
            //包装费退款 giftWrapCreditRefunds
            totalGather.setGiftWrapCreditRefunds(totalGather.getGiftWrapCreditRefunds().add((finance.getGiftWrapCreditRefunds()!=null)
                    ?finance.getGiftWrapCreditRefunds():new BigDecimal(0)));
            //促销费 promotionFee
            totalGather.setPromotionFee(totalGather.getPromotionFee().add((finance.getPromotionFee()!=null)
                    ?finance.getPromotionFee():new BigDecimal(0)));
            //促销费 promotionalRebates
            totalGather.setPromotionalRebates(totalGather.getPromotionalRebates().add((finance.getPromotionalRebates()!=null)
                    ?finance.getPromotionalRebates():new BigDecimal(0)));
            //促销费 promotionalRebateRefunds
            totalGather.setPromotionalRebateRefunds(totalGather.getPromotionalRebateRefunds().add((finance.getPromotionalRebateRefunds()!=null)
                    ?finance.getPromotionalRebateRefunds():new BigDecimal(0)));
            //销售佣金(含自配送） selfdeliveryCommission
            totalGather.setSelfdeliveryCommission(totalGather.getSelfdeliveryCommission().add((finance.getSelfdeliveryCommission()!=null)
                    ?finance.getSelfdeliveryCommission():new BigDecimal(0)));
            //自配送销售佣金 sellerFulfilledSellingFees
            totalGather.setSellerFulfilledSellingFees(totalGather.getSellerFulfilledSellingFees().add((finance.getSellerFulfilledSellingFees()!=null)
                    ?finance.getSellerFulfilledSellingFees():new BigDecimal(0)));
            //自配送销售退款佣金 sellerFulfilledSellingFeeRefunds
            totalGather.setSellerFulfilledSellingFeeRefunds(totalGather.getSellerFulfilledSellingFeeRefunds().add((finance.getSellerFulfilledSellingFeeRefunds()!=null)
                    ?finance.getSellerFulfilledSellingFeeRefunds():new BigDecimal(0)));
            //FBA销售佣金 fbaSellingFees
            totalGather.setFbaSellingFees(totalGather.getFbaSellingFees().add((finance.getFbaSellingFees()!=null)
                    ?finance.getFbaSellingFees():new BigDecimal(0)));
            //FBA销售退款佣金 sellingFeeRefunds
            totalGather.setSellingFeeRefunds(totalGather.getSellingFeeRefunds().add((finance.getSellingFeeRefunds()!=null)
                    ?finance.getSellingFeeRefunds():new BigDecimal(0)));
            //尾程运费退回（N6+T6+AG6,占销售比例14.5%） finalFreightReturn
            totalGather.setFinalFreightReturn(totalGather.getFinalFreightReturn().add((finance.getFinalFreightReturn()!=null)
                    ?finance.getFinalFreightReturn():new BigDecimal(0)));
            //尾程运费 fbaTransactionFees
            totalGather.setFbaTransactionFees(totalGather.getFbaTransactionFees().add((finance.getFbaTransactionFees()!=null)
                    ?finance.getFbaTransactionFees():new BigDecimal(0)));
            //尾程运费退回 fbaTransactionFeeRefunds
            totalGather.setFbaTransactionFeeRefunds(totalGather.getFbaTransactionFeeRefunds().add((finance.getFbaTransactionFeeRefunds()!=null)
                    ?finance.getFbaTransactionFeeRefunds():new BigDecimal(0)));
            //仓储费（0.79%）fbaInventoryAndInboundServicesFees
            totalGather.setFbaInventoryAndInboundServicesFees(totalGather.getFbaInventoryAndInboundServicesFees()
                    .add((finance.getFbaInventoryAndInboundServicesFees()!=null) ?finance.getFbaInventoryAndInboundServicesFees():new BigDecimal(0)));
            //卡车服务费 truckServiceFee
            totalGather.setTruckServiceFee(totalGather.getTruckServiceFee()
                    .add((finance.getTruckServiceFee()!=null) ?finance.getTruckServiceFee():new BigDecimal(0)));
            //仓储费 storageFee
            totalGather.setStorageFee(totalGather.getStorageFee()
                    .add((finance.getStorageFee()!=null) ?finance.getStorageFee():new BigDecimal(0)));
            //长期仓储费 longtimeStorageFee
            totalGather.setLongtimeStorageFee(totalGather.getLongtimeStorageFee()
                    .add((finance.getLongtimeStorageFee()!=null) ?finance.getLongtimeStorageFee():new BigDecimal(0)));
            //退货移除费 returnRemovalFee
            totalGather.setReturnRemovalFee(totalGather.getReturnRemovalFee()
                    .add((finance.getReturnRemovalFee()!=null) ?finance.getReturnRemovalFee():new BigDecimal(0)));
            //破损库存销毁手续费 disposalRemovalFee
            totalGather.setDisposalRemovalFee(totalGather.getDisposalRemovalFee()
                    .add((finance.getDisposalRemovalFee()!=null) ?finance.getDisposalRemovalFee():new BigDecimal(0)));
            //平台服务费 serviceFees
            //店租 shopRent
            //BD bdDealFee
            totalGather.setBdDealFee(totalGather.getBdDealFee()
                    .add((finance.getBdDealFee()!=null) ?finance.getBdDealFee():new BigDecimal(0)));
            //LD ldDealFee
            totalGather.setLdDealFee(totalGather.getLdDealFee()
                    .add((finance.getLdDealFee()!=null) ?finance.getLdDealFee():new BigDecimal(0)));
            //早期 earlyFee
            totalGather.setEarlyFee(totalGather.getEarlyFee()
                    .add((finance.getEarlyFee()!=null) ?finance.getEarlyFee():new BigDecimal(0)));
            //手续费 couponFee
            totalGather.setCouponFee(totalGather.getCouponFee()
                    .add((finance.getCouponFee()!=null) ?finance.getCouponFee():new BigDecimal(0)));
            //其他服务费 otherServiceFee
            totalGather.setOtherServiceFee(totalGather.getOtherServiceFee()
                    .add((finance.getOtherServiceFee()!=null) ?finance.getOtherServiceFee():new BigDecimal(0)));
            //平台退款服务费 refundAdministrationFees
            totalGather.setRefundAdministrationFees(totalGather.getRefundAdministrationFees()
                    .add((finance.getRefundAdministrationFees()!=null) ?finance.getRefundAdministrationFees():new BigDecimal(0)));
            //平台服务调整费 Adjustments
            totalGather.setAdjustments(totalGather.getAdjustments()
                    .add((finance.getAdjustments()!=null) ?finance.getAdjustments():new BigDecimal(0)));

            //广告费（3.32%） advertisingFee
            totalGather.setAdvertisingFee(totalGather.getAdvertisingFee()
                    .add((finance.getAdvertisingFee()!=null) ?finance.getAdvertisingFee():new BigDecimal(0)));
            //广告费 costOfAdvertising
            totalGather.setCostOfAdvertising(totalGather.getCostOfAdvertising()
                    .add((finance.getCostOfAdvertising()!=null) ?finance.getCostOfAdvertising():new BigDecimal(0)));
            //广告费退款 refundForAdvertiser
            totalGather.setRefundForAdvertiser(totalGather.getRefundForAdvertiser()
                    .add((finance.getRefundForAdvertiser()!=null) ?finance.getRefundForAdvertiser():new BigDecimal(0)));

        }
        return totalGather;
    }

    private Map<String, AdvertisingFee> getSkuAdvertisingFeeMap(TransactionRecord transactionRecord) {
        AdvertisingFee af = new AdvertisingFee();
        af.setMonth((Date)transactionRecord.getParams().get("startTime"));
        af.setSite(transactionRecord.getSite());
        List<AdvertisingFee> advertisingFeeList = advertisingFeeService.selectSkuAdvertisingFeeList(af);
        Map<String, AdvertisingFee> map = advertisingFeeList.stream().collect(Collectors.toMap(AdvertisingFee::getStandardSku,Function.identity()));
        return map;
    }

    private Map<String, StorageRecord> getSkuStorageFeeMapByMonth(TransactionRecord transactionRecord) {
       Date time = (Date) transactionRecord.getParams().get("beginTime");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time); // 设置时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 再设置为上一个月
        time = calendar.getTime();

        StorageRecord storageRecord = new StorageRecord();
       storageRecord.setMonth(time);
       storageRecord.setAccount(transactionRecord.getAccount());
       String site = transactionRecord.getSite();
       if(StringUtils.isNotEmpty(site)&&site.contains("-")){
           String countryCode = site.split("-")[1];
           storageRecord.setCountryCode(countryCode);
       }else{
           storageRecord.setCountryCode(site);
       }
       List<StorageRecord> storageRecordGatherList = storageRecordService.getStorageRecordGather(storageRecord);
       Map<String,StorageRecord> map =  storageRecordGatherList.stream().collect(Collectors.toMap(StorageRecord::getStandardSku,Function.identity()));
       return map;
    }

    private Map<String, BigDecimal> getSkuLDFeeMap(TransactionRecord transactionRecord) {
        transactionRecord.setSku("LD");
        List<SkuFee> skuLDFeeList = transactionRecordMapper.selectSkuBDOrLdFeeList(transactionRecord);
        if(skuLDFeeList.size()>0){
            Map<String, BigDecimal> map = skuLDFeeList.stream().collect(Collectors.toMap(SkuFee::getSku,SkuFee::getFee));
            return map;
        }else {
            return new HashMap<>();
        }
    }

    private Map<String, BigDecimal> getSkuBDFeeMap(TransactionRecord transactionRecord) {
        transactionRecord.setSku("BD");
        transactionRecord.setType(TransactionType.DEAL_FEE);
        List<SkuFee> skuBDFeeList = transactionRecordMapper.selectSkuBDOrLdFeeList(transactionRecord);
        if(skuBDFeeList.size()>0){
            Map<String, BigDecimal> map = skuBDFeeList.stream().collect(Collectors.toMap(SkuFee::getSku,SkuFee::getFee));
            return map;
        }else {
            return new HashMap<>();
        }
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

    @Override
    public List<BLDTransactionRecordVo> selectBLDTransactionReocordVoList(BLDTransactionRecordVo vo) {
        return transactionRecordMapper.selectBLDTransactionReocordVoList(vo);
    }

    @Autowired
    IDealfeeAsinService dealfeeAsinService;
    @Override
    public int BLDRecordrelateASIN(Long recordId, String asin) {
        TransactionRecord record = this.selectTransactionRecordById(recordId);
        if(record==null) throw new BusinessException("你所操作的记录已被删除！无法关联ASIN！");
        DealfeeAsin dealfeeAsin = dealfeeAsinService.selectDealfeeAsinByRecordId(recordId);
        dealfeeAsin.setDescription(record.getDescription());
        dealfeeAsin.setAsin(asin);
        if(dealfeeAsin!=null){
            return dealfeeAsinService.updateDealfeeAsin(dealfeeAsin);
        }else {
            return dealfeeAsinService.insertDealfeeAsin(dealfeeAsin);
        }
    }

    @Override
    public String exportOrderIdStrs(TransactionRecord transactionRecord) {
        List<TransactionRecord> list = this.selectTransactionRecordList(transactionRecord);
        StringBuilder orderIdStrs = new StringBuilder();
        for(TransactionRecord record: list){
            orderIdStrs.append(record.getOrderId()+" ");
        }
        return orderIdStrs.toString();
    }

    @Override
    @Transactional
    public String importOrderSku4FeeAdjustmentOrder(List<OrderIdSku> orderIdSkuList, boolean updateSupport) {
        if (StringUtils.isNull(orderIdSkuList) || orderIdSkuList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }else {
            orderIdSkuList = orderIdSkuList.stream().filter(
                    s->s!=null&&StringUtils.isNotEmpty(s.getSku())&&StringUtils.isNotEmpty(s.getOrderId())).collect(Collectors.toList());
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();

        Map<String,ProductinfoRelation> skuProductinfoRelationMap = this.checkAndGetProductinfoRelationMap(orderIdSkuList);

        for (OrderIdSku orderIdSku : orderIdSkuList)
        {
            try
            {
                // 验证数据是否已经
                TransactionRecord record = new TransactionRecord();
                String sku =  orderIdSku.getSku();
                ProductinfoRelation proinfo = skuProductinfoRelationMap.get(sku);
                record.setOrderId(orderIdSku.getOrderId());
                record.setStandardSku(sku);
                record.setPrincipal(proinfo.getPrincipal());
                record.setSpu(proinfo.getType());
                this.updateAdjustmentRecordByOrderId(record);
                successNum++;
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + orderIdSku.getOrderId()+" 的数据导入失败：";
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

    private int updateAdjustmentRecordByOrderId(TransactionRecord record) {
        return transactionRecordMapper.updateAdjustmentRecordByOrderId(record);
    }

    private Map<String,ProductinfoRelation> checkAndGetProductinfoRelationMap(List<OrderIdSku> orderIdSkuList) {
        List<ProductinfoRelation> relationList = productinfoRelationService.selectProductinfoRelationList(null);
        Map<String,ProductinfoRelation> skuProductinfoRelationMap =
                relationList.stream().collect(Collectors.toMap(ProductinfoRelation::getSku,Function.identity(),(e1,e2)->e1));
        StringBuilder warnMsg = new StringBuilder();
        for(OrderIdSku oIdSku:orderIdSkuList){
            String sku = oIdSku.getSku();
            if(!skuProductinfoRelationMap.containsKey(sku)) warnMsg.append("<br/>"+sku);
        }
        if(warnMsg.length()>0){
            warnMsg.insert(0,"以下标准SKU缺少产品信息关系：");
            throw new BusinessException(warnMsg.toString());
        }
        return skuProductinfoRelationMap;
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
