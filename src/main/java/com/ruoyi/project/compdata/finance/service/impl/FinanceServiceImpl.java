package com.ruoyi.project.compdata.finance.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.Arith;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.common.utils.math.MathUtil;
import com.ruoyi.framework.aspectj.lang.annotation.DataScope;
import com.ruoyi.project.compdata.finance.vo.*;
import com.ruoyi.project.system.config.service.IConfigService;
import com.ruoyi.project.system.user.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.compdata.finance.mapper.FinanceMapper;
import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.finance.service.IFinanceService;
import com.ruoyi.common.utils.text.Convert;

/**
 * 财务数据Service业务层处理
 * 
 * @author Beignet
 * @date 2021-01-13
 */
@Service
public class FinanceServiceImpl implements IFinanceService
{

    private static final Logger log = LoggerFactory.getLogger(FinanceServiceImpl.class);

    @Autowired
    private IConfigService configService;

    @Autowired
    private FinanceMapper financeMapper;


    /**
     * 查询财务数据
     * 
     * @param id 财务数据ID
     * @return 财务数据
     */
    @Override
    public Finance selectFinanceById(Long id)
    {
        return financeMapper.selectFinanceById(id);
    }

    /**
     * 查询财务数据列表
     * 
     * @param finance 财务数据
     * @return 财务数据
     */
    @Override
    public List<Finance> selectFinanceList(Finance finance)
    {
        return financeMapper.selectFinanceList(finance);
    }

    /**
     * 新增财务数据
     * 
     * @param finance 财务数据
     * @return 结果
     */
    @Override
    public int insertFinance(Finance finance)
    {
        return financeMapper.insertFinance(finance);
    }

    /**
     * 修改财务数据
     * 
     * @param finance 财务数据
     * @return 结果
     */
    @Override
    public int updateFinance(Finance finance)
    {
        return financeMapper.updateFinance(finance);
    }

    /**
     * 删除财务数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFinanceByIds(String ids)
    {
        return financeMapper.deleteFinanceByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除财务数据信息
     * 
     * @param id 财务数据ID
     * @return 结果
     */
    @Override
    public int deleteFinanceById(Long id)
    {
        return financeMapper.deleteFinanceById(id);
    }

    @Override
    public String importFinance(List<Finance> financeList, boolean isUpdateSupport) {
        if (StringUtils.isNull(financeList) || financeList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (Finance finance : financeList)
        {
            try
            {
                // 验证是否存在这个用户
                Finance f = financeMapper.selectUserByOnlyCondition(finance);
                if (f==null&&StringUtils.isNotEmpty(finance.getSite()))
                {
                    finance.setCreateBy(operName);
                    finance.setCreateTime(new Date());
                    this.insertFinance(finance);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ finance.getPrincipal() +finance.getMonth()+finance.getSite()+finance.getType()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    finance.setUpdateBy(operName);
                    finance.setUpdateTime(new Date());
                    financeMapper.updateFinanceByOnlyCondition(finance);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + finance.getPrincipal() +finance.getMonth()+finance.getSite()+finance.getType()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + finance.getPrincipal() +finance.getMonth()+finance.getSite()+finance.getType()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                if(StringUtils.isEmpty(finance.getPrincipal())&&StringUtils.isEmpty(finance.getMonth())&&
                        StringUtils.isEmpty(finance.getType())&&StringUtils.isEmpty(finance.getSite())) continue;
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + finance.getPrincipal() +finance.getMonth()+finance.getType() + " 的数据导入失败：";
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

    @Override
    /**
     * 获取财务echarts分析数据
     */
    public FinanceEchartsVo selectFinanceEchartsVo(Finance finance) throws IllegalAccessException {
        List<FinanceAnalyVo> analyVoList = financeMapper.selectFinanceAgg(finance);
        FinanceEchartsVo echartsVo = new FinanceEchartsVo();


        List<String> months = analyVoList.stream().map(domain->{
            return domain.getMonth();
        }).collect(Collectors.toList());
        echartsVo.setMonths(months.toArray(new String[months.size()]));

        List<BigDecimal> grossProfits = analyVoList.stream().map(domain->{
            return domain.getTotalGrossProfit();
        }).collect(Collectors.toList());
        echartsVo.setTotalGrossProfits(grossProfits.toArray(new BigDecimal[grossProfits.size()]));

        /**计算财务毛利环比,广告费环比 start**/
        Float[] grossProfitGRs = new Float[analyVoList.size()];
        Float[] advertisingFeeRateGRs = new Float[analyVoList.size()];
        for(int i=0;i<analyVoList.size();i++){
            if(i==0){
                grossProfitGRs[i]=null;
                advertisingFeeRateGRs[i]=null;
            }else{
                //计算财务毛利环比
                BigDecimal range =  analyVoList.get(i).getTotalGrossProfit().subtract(analyVoList.get(i-1).getTotalGrossProfit());
                grossProfitGRs[i] =  MathUtil.float2PercentNum(Arith.div(range,analyVoList.get(i-1).getTotalGrossProfit(),0),0);
                //计算广告费环比
                advertisingFeeRateGRs[i] =  MathUtil.float2PercentNum(analyVoList.get(i).getAdvertisingFeeRate()-analyVoList.get(i-1).getAdvertisingFeeRate(),2);
            }
        }
        echartsVo.setGrossProfitGRs(grossProfitGRs);
        echartsVo.setAdvertisingFeeRateGRs(advertisingFeeRateGRs);
        /**计算财务毛利环比,广告费环比 end**/

        List<BigDecimal> totalAdvertisingFees = analyVoList.stream().map(domain->{
            return domain.getTotalAdvertisingFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalAdvertisingFees(totalAdvertisingFees.toArray(new BigDecimal[totalAdvertisingFees.size()]));

        List<Float> advertisingRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getAdvertisingFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setAdvertisingFeeRates(advertisingRates.toArray(new Float[advertisingRates.size()]));
        //totalSalesRevenues totalCompensations compensationRates totalFinalFreights finalFreightRates totalPackagingFees packagingRages
        List<BigDecimal> totalSalesRevenues = analyVoList.stream().map(domain->{
            return domain.getTotalSalesRevenue();
        }).collect(Collectors.toList());
        echartsVo.setTotalSalesRevenues(totalSalesRevenues.toArray(new BigDecimal[totalSalesRevenues.size()]));

        List<BigDecimal> totalCompensations = analyVoList.stream().map(domain->{
            return domain.getTotalCompensation();
        }).collect(Collectors.toList());
        echartsVo.setTotalCompensations(totalSalesRevenues.toArray(new BigDecimal[totalSalesRevenues.size()]));

        List<Float> compensationRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getCompensationRate());
        }).collect(Collectors.toList());
        echartsVo.setCompensationRates(compensationRates.toArray(new Float[compensationRates.size()]));

        List<BigDecimal> totalFinalFreights = analyVoList.stream().map(domain->{
            return domain.getTotalFinalFreight();
        }).collect(Collectors.toList());
        echartsVo.setTotalFinalFreights(totalFinalFreights.toArray(new BigDecimal[totalFinalFreights.size()]));

        List<Float> finalFreightRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getFinalFreightRate());
        }).collect(Collectors.toList());
        echartsVo.setFinalFreightRates(finalFreightRates.toArray(new Float[finalFreightRates.size()]));

        List<BigDecimal> totalPackagingFees = analyVoList.stream().map(domain->{
            return domain.getTotalPackagingFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalPackagingFees(totalPackagingFees.toArray(new BigDecimal[totalPackagingFees.size()]));

        List<Float> packagingRages = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getPackagingRage(),3);
        }).collect(Collectors.toList());
        echartsVo.setPackagingRages(packagingRages.toArray(new Float[packagingRages.size()]));
        //totalPromotionFees promotionFeeRates totalPlatformRefunds
        List<BigDecimal> totalPromotionFees = analyVoList.stream().map(domain->{
            return domain.getTotalPromotionFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalPromotionFees(totalPromotionFees.toArray(new BigDecimal[totalPromotionFees.size()]));

        List<Float> promotionFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getPromotionFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setPromotionFeeRates(promotionFeeRates.toArray(new Float[promotionFeeRates.size()]));

        List<BigDecimal> totalPlatformRefunds = analyVoList.stream().map(domain->{
            return domain.getTotalPlatformRefund();
        }).collect(Collectors.toList());
        echartsVo.setTotalPlatformRefunds(totalPlatformRefunds.toArray(new BigDecimal[totalPlatformRefunds.size()]));
        //platformRefundRates totalFinalFreightReturns finalFreightReturns totalOtherTransactionFees otherTransactionFeeRates
        List<Float> platformRefundRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getPlatformRefundRate());
        }).collect(Collectors.toList());
        echartsVo.setPlatformRefundRates(platformRefundRates.toArray(new Float[platformRefundRates.size()]));

        List<BigDecimal> totalFinalFreightReturns = analyVoList.stream().map(domain->{
            return domain.getTotalFinalFreightReturn();
        }).collect(Collectors.toList());
        echartsVo.setTotalFinalFreightReturns(totalFinalFreightReturns.toArray(new BigDecimal[totalFinalFreightReturns.size()]));

        List<Float> finalFreightReturnRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getFinalFreightReturnRate());
        }).collect(Collectors.toList());
        echartsVo.setFinalFreightReturnRates(finalFreightReturnRates.toArray(new Float[finalFreightReturnRates.size()]));

        List<BigDecimal> totalFinalFreightReturn2s = analyVoList.stream().map(domain->{
            return domain.getTotalFinalFreightReturn2();
        }).collect(Collectors.toList());
        echartsVo.setTotalFinalFreightReturn2s(totalFinalFreightReturn2s.toArray(new BigDecimal[totalFinalFreightReturn2s.size()]));

        List<Float> finalFreightReturn2Rates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getFinalFreightReturn2Rate());
        }).collect(Collectors.toList());
        echartsVo.setFinalFreightReturn2Rates(finalFreightReturn2Rates.toArray(new Float[finalFreightReturn2Rates.size()]));

        List<BigDecimal> totalOtherTransactionFees = analyVoList.stream().map(domain->{
            return domain.getTotalOtherTransactionFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalOtherTransactionFees(totalOtherTransactionFees.toArray(new BigDecimal[totalOtherTransactionFees.size()]));

        List<Float> otherTransactionFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getOtherTransactionFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setOtherTransactionFeeRates(otherTransactionFeeRates.toArray(new Float[otherTransactionFeeRates.size()]));

        // totalSelfdeliveryCommissions selfdeliveryCommissionRates
        List<BigDecimal> totalSelfdeliveryCommissions = analyVoList.stream().map(domain->{
            return domain.getTotalSelfdeliveryCommission();
        }).collect(Collectors.toList());
        echartsVo.setTotalSelfdeliveryCommissions(totalSelfdeliveryCommissions.toArray(new BigDecimal[totalSelfdeliveryCommissions.size()]));

        List<Float> selfdeliveryCommissionRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getSelfdeliveryCommissionRate());
        }).collect(Collectors.toList());
        echartsVo.setSelfdeliveryCommissionRates(selfdeliveryCommissionRates.toArray(new Float[selfdeliveryCommissionRates.size()]));
        //totalFbaSalesCommissions fbaSalesCommissionRates finalFreightReturnRates totalStorageFees
        List<BigDecimal> totalFbaSalesCommissions = analyVoList.stream().map(domain->{
            return domain.getTotalFbaSalesCommission();
        }).collect(Collectors.toList());
        echartsVo.setTotalFbaSalesCommissions(totalFbaSalesCommissions.toArray(new BigDecimal[totalFbaSalesCommissions.size()]));

        List<Float> fbaSalesCommissionRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getFbaSalesCommissionRate());
        }).collect(Collectors.toList());
        echartsVo.setFbaSalesCommissionRates(fbaSalesCommissionRates.toArray(new Float[fbaSalesCommissionRates.size()]));


        List<BigDecimal> totalStorageFees = analyVoList.stream().map(domain->{
            return domain.getTotalStorageFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalStorageFees(totalStorageFees.toArray(new BigDecimal[totalStorageFees.size()]));
        //storageFeeRates totalShippingLabelFees
        List<Float> storageFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getStorageFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setStorageFeeRates(storageFeeRates.toArray(new Float[storageFeeRates.size()]));

        List<BigDecimal> totalShippingLabelFees = analyVoList.stream().map(domain->{
            return domain.getTotalShippingLabelFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalShippingLabelFees(totalShippingLabelFees.toArray(new BigDecimal[totalShippingLabelFees.size()]));
        //shippingLabelFeeRates totalPlaformServiceFees plaformServiceFeeRates totalPlatformRefundServiceFees
        List<Float> shippingLabelFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getShippingLabelFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setShippingLabelFeeRates(shippingLabelFeeRates.toArray(new Float[shippingLabelFeeRates.size()]));

        List<BigDecimal> totalPlaformServiceFees = analyVoList.stream().map(domain->{
            return domain.getTotalPlaformServiceFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalPlaformServiceFees(totalPlaformServiceFees.toArray(new BigDecimal[totalPlaformServiceFees.size()]));

        List<Float> plaformServiceFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getPlaformServiceFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setPlaformServiceFeeRates(plaformServiceFeeRates.toArray(new Float[plaformServiceFeeRates.size()]));

        List<BigDecimal> totalPlatformRefundServiceFees = analyVoList.stream().map(domain->{
            return domain.getTotalPlatformRefundServiceFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalPlatformRefundServiceFees(totalPlatformRefundServiceFees.toArray(new BigDecimal[totalPlatformRefundServiceFees.size()]));
        //platformRefundServiceFeeRates totalPlatformServiceAdjustmentFees platformServiceAdjustmentFeeRates
        List<Float> platformRefundServiceFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getPlatformRefundServiceFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setPlatformRefundServiceFeeRates(platformRefundServiceFeeRates.toArray(new Float[platformRefundServiceFeeRates.size()]));

        List<BigDecimal> totalPlatformServiceAdjustmentFees = analyVoList.stream().map(domain->{
            return domain.getTotalPlatformServiceAdjustmentFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalPlatformServiceAdjustmentFees(totalPlatformServiceAdjustmentFees.toArray(new BigDecimal[totalPlatformServiceAdjustmentFees.size()]));

        List<Float> platformServiceAdjustmentFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getPlatformServiceAdjustmentFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setPlatformServiceAdjustmentFeeRates(platformServiceAdjustmentFeeRates.toArray(new Float[platformServiceAdjustmentFeeRates.size()]));
        //totalServiceProviderFees serviceProviderFeeRates totalOtherFees otherFeeRates totalAsinkingNums tatalYcDeliveryNums prices
        List<BigDecimal> totalServiceProviderFees = analyVoList.stream().map(domain->{
            return domain.getTotalServiceProviderFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalServiceProviderFees(totalServiceProviderFees.toArray(new BigDecimal[totalServiceProviderFees.size()]));

        List<Float> serviceProviderFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getServiceProviderFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setServiceProviderFeeRates(serviceProviderFeeRates.toArray(new Float[serviceProviderFeeRates.size()]));

        List<BigDecimal> totalOtherFees = analyVoList.stream().map(domain->{
            return domain.getTotalOtherFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalOtherFees(totalOtherFees.toArray(new BigDecimal[totalOtherFees.size()]));

        List<Float> otherFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getOtherFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setOtherFeeRates(otherFeeRates.toArray(new Float[otherFeeRates.size()]));

        List<Integer> totalAsinkingNums = analyVoList.stream().map(domain->{
            return domain.getTotalAsinkingNum();
        }).collect(Collectors.toList());
        echartsVo.setTotalAsinkingNums(totalAsinkingNums.toArray(new Integer[totalAsinkingNums.size()]));

        List<Integer> tatalYcDeliveryNums = analyVoList.stream().map(domain->{
            return domain.getTatalYcDeliveryNum();
        }).collect(Collectors.toList());
        echartsVo.setTatalYcDeliveryNums(tatalYcDeliveryNums.toArray(new Integer[tatalYcDeliveryNums.size()]));

        List<BigDecimal> prices = analyVoList.stream().map(domain->{
            return domain.getPrice();
        }).collect(Collectors.toList());
        echartsVo.setPrices(prices.toArray(new BigDecimal[prices.size()]));

        //  tatalMixedVat mixedVatRate tatalMixedVat2 mixedVat2Rate tatalClearBrokerFee  clearBrokerFeeRate
        List<BigDecimal> tatalMixedVat = analyVoList.stream().map(domain->{
            return domain.getTotalMixedVat();
        }).collect(Collectors.toList());
        echartsVo.setTotalMixedVats(tatalMixedVat.toArray(new BigDecimal[tatalMixedVat.size()]));

        List<Float> mixedVatRate = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getMixedVatRate());
        }).collect(Collectors.toList());
        echartsVo.setMixedVatRates(mixedVatRate.toArray(new Float[mixedVatRate.size()]));

        List<BigDecimal> tatalMixedVat2 = analyVoList.stream().map(domain->{
            return domain.getTotalMixedVat2();
        }).collect(Collectors.toList());
        echartsVo.setTotalMixedVat2s(tatalMixedVat2.toArray(new BigDecimal[tatalMixedVat2.size()]));

        List<Float> mixedVat2Rate = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getMixedVat2Rate());
        }).collect(Collectors.toList());
        echartsVo.setMixedVat2Rates(mixedVat2Rate.toArray(new Float[mixedVat2Rate.size()]));

        List<BigDecimal> clearBrokerFeeRate = analyVoList.stream().map(domain->{
            return domain.getTotalClearBrokerFee();
        }).collect(Collectors.toList());
        echartsVo.setTotalClearBrokerFees(clearBrokerFeeRate.toArray(new BigDecimal[clearBrokerFeeRate.size()]));

        List<Float> clearBrokerFeeRates = analyVoList.stream().map(domain->{
            return MathUtil.float2PercentNum(domain.getClearBrokerFeeRate());
        }).collect(Collectors.toList());
        echartsVo.setClearBrokerFeeRates(clearBrokerFeeRates.toArray(new Float[clearBrokerFeeRates.size()]));


        return echartsVo;
    }

    @Override
    public Map selectAnalySearch(FinanceAnalyParamVo paramVo) {
        Map paramMap = new HashMap();
        paramMap.put("columnName","type");
        List<String> types =  financeMapper.selectDistinctColumn("type",paramVo);
        paramMap.put("types",types);
        paramMap.put("columnName","site");
        List<String> sites =  financeMapper.selectDistinctColumn("site",paramVo);
        paramMap.put("sites",sites);
        paramMap.put("columnName","principal");
        List<String> principals =  financeMapper.selectDistinctColumn("principal",paramVo);
        paramMap.put("principals",principals);

        return paramMap;
    }

    @Override
    public List<TypeProfitAnalyVo> selectTypeProfitAnalyVoList(String  curMonthStr) {
        /**参数设置 start**/
        if(StringUtils.isEmpty(curMonthStr)){
            curMonthStr = DateUtils.getCurrentMonthStr("yyyy年MM月");
        }
        String preMonthStr = DateUtils.getPreviousMonthStr(curMonthStr,"yyyy年MM月");
        Map params = new HashMap();
        params.put("curMonth",curMonthStr);
        params.put("preMonth",preMonthStr);
        /**参数设置 end**/
        List<TypeProfitAnalyVo> typeProfitAnalyVoList =
                financeMapper.selectTypeProfitAnalyVoList(params);
        BigDecimal totalProfit = financeMapper.selectTotalProfitByMonth(curMonthStr);
        /**下滑利润占比 的计算与设置 **/
        String finalCurMonthStr = curMonthStr;//我也不清楚为什么必须要这样赋值
        typeProfitAnalyVoList = typeProfitAnalyVoList.stream().map(vo->{
            TypeProfitAnalyVo typeProfitAnaly = new TypeProfitAnalyVo();
            BeanUtils.copyProperties(vo,typeProfitAnaly);
            Float decliningProfitpercentage = null;
            try {
                decliningProfitpercentage = Arith.div(vo.getProfitGross(),totalProfit,4);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            typeProfitAnaly.setDecliningProfitpercentage(decliningProfitpercentage);
            typeProfitAnaly.setCurMonth(finalCurMonthStr);
            typeProfitAnaly.setPreMonth(preMonthStr);
            return typeProfitAnaly;
        }).collect(Collectors.toList());
        return  typeProfitAnalyVoList;
    }

    @Override
    public TypeProfitEchartsVo selectTypeProfitEchartsVo(String curMonthStr) {
        List<TypeProfitAnalyVo> typeProfitAnalyVoList = this.selectTypeProfitAnalyVoList(curMonthStr);
        TypeProfitEchartsVo echartsVo = new TypeProfitEchartsVo();

        List<String> types = typeProfitAnalyVoList.stream().map(vo->{
            return vo.getType();
        }).collect(Collectors.toList());
        echartsVo.setTypes(types.toArray(new String[types.size()]));

        List<BigDecimal> curMonthProfits = typeProfitAnalyVoList.stream().map(vo->{
            return vo.getCurMonthProfit();
        }).collect(Collectors.toList());
        echartsVo.setCurMonthProfits(curMonthProfits.toArray(new BigDecimal[curMonthProfits.size()]));

        List<BigDecimal> preMonthProfits = typeProfitAnalyVoList.stream().map(vo->{
            return vo.getPreMonthProfit();
        }).collect(Collectors.toList());
        echartsVo.setPreMonthProfits(preMonthProfits.toArray(new BigDecimal[preMonthProfits.size()]));

        List<BigDecimal> profitGrosses = typeProfitAnalyVoList.stream().map(vo->{
            return vo.getProfitGross();
        }).collect(Collectors.toList());
        echartsVo.setProfitGrosses(profitGrosses.toArray(new BigDecimal[profitGrosses.size()]));

        List<Float> decliningProfitpercentages = typeProfitAnalyVoList.stream().map(vo->{
            return vo.getDecliningProfitpercentage();
        }).collect(Collectors.toList());
        echartsVo.setDecliningProfitpercentages(decliningProfitpercentages.toArray(new Float[decliningProfitpercentages.size()]));

        List<Float> profitGrowthRatios = typeProfitAnalyVoList.stream().map(vo->{
            return vo.getProfitGrowthRatio();
        }).collect(Collectors.toList());
        echartsVo.setProfitGrowthRatios(profitGrowthRatios.toArray(new Float[profitGrowthRatios.size()]));

        /** 有数据的时，获取第一列的月份信息 **/
        if(typeProfitAnalyVoList.size()>0){
            echartsVo.setCurMonth(typeProfitAnalyVoList.get(0).getCurMonth());
            echartsVo.setPreMonth(typeProfitAnalyVoList.get(0).getPreMonth());
        }

        return echartsVo;
    }

}
