package com.ruoyi.project.compdata.displayadvertising.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.utils.Arith;
import com.ruoyi.common.utils.math.MathUtil;
import com.ruoyi.project.compdata.advertisingactivity.domain.AdvertisingActivity;
import com.ruoyi.project.compdata.advertisingactivity.service.IAdvertisingActivityService;
import com.ruoyi.project.compdata.costprice.domain.SkuCostprice;
import com.ruoyi.project.compdata.costprice.service.ISkuCostpriceService;
import com.ruoyi.project.compdata.videoadvertising.domain.VideoAdvertising;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.compdata.displayadvertising.mapper.DisplayAdvertisingMapper;
import com.ruoyi.project.compdata.displayadvertising.domain.DisplayAdvertising;
import com.ruoyi.project.compdata.displayadvertising.service.IDisplayAdvertisingService;
import com.ruoyi.common.utils.text.Convert;

/**
 * Display广告数据源Service业务层处理
 * 
 * @author Beignet
 * @date 2021-02-20
 */
@Service
public class DisplayAdvertisingServiceImpl implements IDisplayAdvertisingService 
{
    private static final Logger log = LoggerFactory.getLogger(DisplayAdvertising.class);

    @Autowired
    private DisplayAdvertisingMapper displayAdvertisingMapper;

    @Autowired
    private ISkuCostpriceService skuCostpriceService;

    @Autowired
    private IAdvertisingActivityService advertisingActivityService;

    /**
     * 查询Display广告数据源
     * 
     * @param id Display广告数据源ID
     * @return Display广告数据源
     */
    @Override
    public DisplayAdvertising selectDisplayAdvertisingById(Long id)
    {
        return displayAdvertisingMapper.selectDisplayAdvertisingById(id);
    }

    /**
     * 查询Display广告数据源列表
     * 
     * @param displayAdvertising Display广告数据源
     * @return Display广告数据源
     */
    @Override
    public List<DisplayAdvertising> selectDisplayAdvertisingList(DisplayAdvertising displayAdvertising)
    {
        List<DisplayAdvertising> displayAdvertisingList = displayAdvertisingMapper.selectDisplayAdvertisingList(displayAdvertising);
        for(DisplayAdvertising domain:displayAdvertisingList){
            domain.setCtr(MathUtil.float2PercentNum(domain.getCtr()));
            domain.setCvr(MathUtil.float2PercentNum(domain.getCvr()));
            domain.setAcos(MathUtil.float2PercentNum(domain.getAcos()));
        }
        return displayAdvertisingList;
    }

    /**
     * 新增Display广告数据源
     * 
     * @param displayAdvertising Display广告数据源
     * @return 结果
     */
    @Override
    public int insertDisplayAdvertising(DisplayAdvertising displayAdvertising)
    {
        displayAdvertising.setCreateTime(DateUtils.getNowDate());
        return displayAdvertisingMapper.insertDisplayAdvertising(displayAdvertising);
    }

    /**
     * 修改Display广告数据源
     * 
     * @param displayAdvertising Display广告数据源
     * @return 结果
     */
    @Override
    public int updateDisplayAdvertising(DisplayAdvertising displayAdvertising)
    {
        displayAdvertising.setUpdateTime(DateUtils.getNowDate());
        return displayAdvertisingMapper.updateDisplayAdvertising(displayAdvertising);
    }

    /**
     * 删除Display广告数据源对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDisplayAdvertisingByIds(String ids)
    {
        return displayAdvertisingMapper.deleteDisplayAdvertisingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除Display广告数据源信息
     * 
     * @param id Display广告数据源ID
     * @return 结果
     */
    @Override
    public int deleteDisplayAdvertisingById(Long id)
    {
        return displayAdvertisingMapper.deleteDisplayAdvertisingById(id);
    }

    /**
     * 导入Display广告数据源
     *
     * @param displayAdvertisingList Display广告数据源List数据
     * @return 导入结果
     */
    @Override
    public String importDisplayAdvertising(List<DisplayAdvertising> displayAdvertisingList, boolean isUpdateSupport) {
        if (StringUtils.isNull(displayAdvertisingList) || displayAdvertisingList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (DisplayAdvertising displayAdvertising : displayAdvertisingList)
        {
            try
            {
                // 验证数据是否已经
                DisplayAdvertising domain = displayAdvertisingMapper.selectDisplayAdvertisingByOnlyCondition(displayAdvertising);

                if(domain==null||isUpdateSupport){//需要保存或更新数据时才填充其他字段
                    /** 从广告活动模块获取基础数据填充 **/
                    AdvertisingActivity advertisingActivityParam = new AdvertisingActivity();
                    advertisingActivityParam.setActivity(displayAdvertising.getCampaignName());
                    AdvertisingActivity AdvertisingActivityRes = advertisingActivityService.selectAdvertisingActivityByOnlyCondition(advertisingActivityParam);
                    if(AdvertisingActivityRes!=null){
                        AdvertisingActivityRes.setStoreCode(displayAdvertising.getStoreCode());
                        BeanUtils.copyProperties(AdvertisingActivityRes,displayAdvertising);
                    }
                    /** 从sku单价模块获取数据填充 **/
                    SkuCostprice skuCostpriceParam = new SkuCostprice();
                    String sku = displayAdvertising.getSku();
                    if(StringUtils.isNotEmpty(sku)){
                        skuCostpriceParam.setSku(displayAdvertising.getSku());
                        SkuCostprice skuCostprice = skuCostpriceService.selectSkuCostpriceByOnlyCondition(skuCostpriceParam);
                        displayAdvertising.setCostprice(skuCostprice==null?null:skuCostprice.getCostprice());
                        /**设置产品类型**/
                        char indexStr = sku.charAt(2);
                        displayAdvertising.setType(indexStr=='T'?"硒鼓":"墨盒");
                    }
                    /**设置link**/
                    displayAdvertising.setLink("http://www.amazon.com/dp/"+displayAdvertising.getAsin());
                    /**计算设置利润（不计算广告点数，只计算平台佣金，税费，不良率）
                     * 利润=(14天总销售额/14天总订单数)*(1-15%-2%)-成本**/
                    Long totalOrders = displayAdvertising.getTotalOrders();
                    BigDecimal costPrice = displayAdvertising.getCostprice();
                    try{
                        double persale = Arith.div(displayAdvertising.getTotalSales().doubleValue(),totalOrders,2);
                        persale =  persale*0.83;//-stadvertising.getCostprice().doubleValue();
                        BigDecimal profit = new BigDecimal(persale-displayAdvertising.getCostprice().doubleValue());
                        displayAdvertising.setProfit(profit);
                    }catch (Exception e){
                        displayAdvertising.setProfit(null);
                    }
                    /**CPA（广告费用/订单总数）
                     * CPA=花费/14天总销售量**/
                    Long totalUnit = displayAdvertising.getTotalUnits();//此处可用双等于（==）
                    if(totalUnit==null||totalUnit==0) displayAdvertising.setCpa(null);
                    else displayAdvertising.setCpa(new BigDecimal(Arith.div(displayAdvertising.getSpend().doubleValue(),totalUnit)).setScale(5,2));
                    /**CPA盈亏(利润值-CPA)
                     * CPA盈亏=利润-CPA
                     */
                    try{
                        displayAdvertising.setCpaProfit(displayAdvertising.getProfit().subtract(displayAdvertising.getCpa()));
                    }catch (Exception e){
                        displayAdvertising.setCpaProfit(null);
                    }

                    /**设置CVR等级
                     * 0%≤CVR<20%：差=4  20%≤CVR<30%：中=3    30%≤CVR<40%：良=2   CVR≥40%：优=1   **/
                    Float cvr = displayAdvertising.getCvr();
                    if(cvr==null);
                    else if(cvr<0.2) displayAdvertising.setCvrLevel(4);
                    else if(cvr>=0.2&&cvr<0.3) displayAdvertising.setCvrLevel(3);
                    else if(cvr>=0.3&&cvr<0.4) displayAdvertising.setCvrLevel(2);
                    else displayAdvertising.setCvrLevel(1);
                    /**设置CTR等级
                     * 0≤CTR<0.2%：差=4  0.2%≤CTR<0.5%：中=3    0.5%≤CTR<1%：良=2   CTR≥1%：优=1   **/
                    Float ctr = displayAdvertising.getCtr();
                    if(ctr==null) ;
                    else if(ctr<0.02) displayAdvertising.setCtrLevel(4);
                    else if(ctr>=0.02&&ctr<0.05) displayAdvertising.setCtrLevel(3);
                    else if(ctr>=0.05&&ctr<0.1) displayAdvertising.setCtrLevel(2);
                    else displayAdvertising.setCtrLevel(1);
                    /**设置Acos等级
                     * Acos>50%：差=4  30<Acos≤50%：中=3    20<Acos≤30%：良=2   0<Acos≤20%：优=1   **/
                    Float acos = displayAdvertising.getAcos();
                    if(acos==null) ;
                    else if(acos>0.5) displayAdvertising.setAcosLevel(4);
                    else if(acos>0.3&&acos<=0.5) displayAdvertising.setAcosLevel(3);
                    else if(acos>=0.2&&acos<0.3) displayAdvertising.setAcosLevel(2);
                    else displayAdvertising.setAcosLevel(1);
                    /**设置CVR等级
                     * CPA<-2：差=4  '-2<CPA<0：中=3    'CPA=0：良=2   CPA>0：优=1   **/
                    Float cpa = (displayAdvertising.getCpaProfit()==null)? null: displayAdvertising.getCpaProfit().floatValue();
                    if(cpa==null) ;
                    else if(cpa<-2) displayAdvertising.setCpaProfitLevel(4);
                    else if(cpa>-2&&cpa<=0) displayAdvertising.setCpaProfitLevel(3);
                    else if(cpa==0) displayAdvertising.setCpaProfitLevel(2);
                    else displayAdvertising.setCpaProfitLevel(1);

                    /**设置识别码**/
                    displayAdvertising.setIdentificationCode(displayAdvertising.getCampaignName()+"-"+displayAdvertising.getTargeting());
                }

                String identyStr = displayAdvertising.getStoreCode()+"-"+displayAdvertising.getStartDate()+"-"+displayAdvertising.getEndDate()+"-"
                        +displayAdvertising.getCampaignName() +"-"+displayAdvertising.getAdGroupName()+"-"+displayAdvertising.getTargeting();

                if (domain==null)
                {
                    displayAdvertising.setCreateBy(operName);
                    displayAdvertising.setCreateTime(new Date());
                    this.insertDisplayAdvertising(displayAdvertising);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ identyStr+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    displayAdvertising.setUpdateBy(operName);
                    displayAdvertising.setUpdateTime(new Date());
                    displayAdvertisingMapper.updateDisplayAdvertisingByOnlyCondition(displayAdvertising);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + identyStr+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + identyStr+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                String identyStr = displayAdvertising.getStoreCode()+"-"+displayAdvertising.getStartDate()+"-"+displayAdvertising.getEndDate()+"-"
                        +displayAdvertising.getCampaignName() +"-"+displayAdvertising.getAdGroupName()+"-"+displayAdvertising.getTargeting();
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + identyStr+" 的数据导入失败：";
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
