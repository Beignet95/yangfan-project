package com.ruoyi.project.compdata.stadvertising.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.Arith;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.math.MathUtil;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.compdata.advertisingactivity.domain.AdvertisingActivity;
import com.ruoyi.project.compdata.advertisingactivity.service.IAdvertisingActivityService;
import com.ruoyi.project.compdata.costprice.domain.SkuCostprice;
import com.ruoyi.project.compdata.costprice.service.ISkuCostpriceService;
import com.ruoyi.project.compdata.finance.domain.Finance;
import com.ruoyi.project.compdata.stadvertising.vo.StadvertisingAnalyVo;
import com.ruoyi.project.system.user.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.compdata.stadvertising.mapper.StadvertisingMapper;
import com.ruoyi.project.compdata.stadvertising.domain.Stadvertising;
import com.ruoyi.project.compdata.stadvertising.service.IStadvertisingService;
import com.ruoyi.common.utils.text.Convert;

/**
 * ST广告数据源Service业务层处理
 * 
 * @author Beignet
 * @date 2021-01-25
 */
@Service
public class StadvertisingServiceImpl implements IStadvertisingService
{
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private StadvertisingMapper stadvertisingMapper;

    @Autowired
    private IAdvertisingActivityService advertisingActivityService;

    @Autowired
    private ISkuCostpriceService skuCostpriceService;

    /**
     * 查询ST广告数据源
     * 
     * @param id ST广告数据源ID
     * @return ST广告数据源
     */
    @Override
    public Stadvertising selectStadvertisingById(Long id)
    {
        return stadvertisingMapper.selectStadvertisingById(id);
    }

    /**
     * 查询ST广告数据源列表
     * 
     * @param stadvertising ST广告数据源
     * @return ST广告数据源
     */
    @Override
    public List<Stadvertising> selectStadvertisingList(Stadvertising stadvertising)
    {
        List<Stadvertising> stadvertisingList = stadvertisingMapper.selectStadvertisingList(stadvertising);
        for(Stadvertising domain:stadvertisingList){
            domain.setCtr(MathUtil.float2PercentNum(domain.getCtr()));
            domain.setCvr(MathUtil.float2PercentNum(domain.getCvr()));
            domain.setAcos(MathUtil.float2PercentNum(domain.getAcos()));
        }
        return stadvertisingList;
    }

    /**
     * 新增ST广告数据源
     * 
     * @param stadvertising ST广告数据源
     * @return 结果
     */
    @Override
    public int insertStadvertising(Stadvertising stadvertising)
    {
        stadvertising.setCreateTime(DateUtils.getNowDate());
        return stadvertisingMapper.insertStadvertising(stadvertising);
    }

    /**
     * 修改ST广告数据源
     * 
     * @param stadvertising ST广告数据源
     * @return 结果
     */
    @Override
    public int updateStadvertising(Stadvertising stadvertising)
    {
        stadvertising.setUpdateTime(DateUtils.getNowDate());
        return stadvertisingMapper.updateStadvertising(stadvertising);
    }

    /**
     * 删除ST广告数据源对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteStadvertisingByIds(String ids)
    {
        return stadvertisingMapper.deleteStadvertisingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除ST广告数据源信息
     * 
     * @param id ST广告数据源ID
     * @return 结果
     */
    @Override
    public int deleteStadvertisingById(Long id)
    {
        return stadvertisingMapper.deleteStadvertisingById(id);
    }

    @Override
    public String impStadvertising(List<Stadvertising> stadvertisingList, boolean updateSupport) {
        if (StringUtils.isNull(stadvertisingList) || stadvertisingList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (Stadvertising stadvertising : stadvertisingList)
        {
            try
            {
                // 验证数据是否已经存在
                if(StringUtils.isEmpty(stadvertising.getStoreCode())) continue;
                Stadvertising s = stadvertisingMapper.selectStadvertisingMapperByOnlyCondition(stadvertising);

                if(s==null||updateSupport){//需要保存或更新数据时才填充其他字段
                    /** 从广告活动模块获取基础数据填充 **/
                    AdvertisingActivity advertisingActivityParam = new AdvertisingActivity();
                    advertisingActivityParam.setActivity(stadvertising.getCampaignName());
                    AdvertisingActivity AdvertisingActivityRes = advertisingActivityService.selectAdvertisingActivityByOnlyCondition(advertisingActivityParam);
                    if(AdvertisingActivityRes!=null){
                        AdvertisingActivityRes.setStoreCode(stadvertising.getStoreCode());
                        BeanUtils.copyProperties(AdvertisingActivityRes,stadvertising);
                    }
                    /** 从sku单价模块获取数据填充 **/
                    SkuCostprice skuCostpriceParam = new SkuCostprice();
                    String sku = stadvertising.getSku();
                    if(StringUtils.isNotEmpty(sku)){
                        skuCostpriceParam.setSku(stadvertising.getSku());
                        SkuCostprice skuCostprice = skuCostpriceService.selectSkuCostpriceByOnlyCondition(skuCostpriceParam);
                        stadvertising.setCostprice(skuCostprice==null?null:skuCostprice.getCostprice());
                        /**设置产品类型**/
                        char indexStr = sku.charAt(2);
                        stadvertising.setType(indexStr=='T'?"硒鼓":"墨盒");
                    }
                    /**设置link**/
                    stadvertising.setLink("http://www.amazon.com/dp/"+stadvertising.getAsin());
                    /**计算设置利润（不计算广告点数，只计算平台佣金，税费，不良率）
                     * 利润=(7天内广告SKU销售额/7天内广告SKU销售量)*(1-15%-2%)-成本**/
                    Integer advertisingUnits = stadvertising.getAdvertisedSkuUnits();
                    BigDecimal costPrice = stadvertising.getCostprice();
                    if(advertisingUnits==null||advertisingUnits==0||costPrice==null) stadvertising.setProfit(null);
                    else {
                        double persale = Arith.div(stadvertising.getAdvertisedSkuSales().doubleValue(),advertisingUnits);
                        persale =  persale*0.83;//-stadvertising.getCostprice().doubleValue();
                        BigDecimal profit = new BigDecimal(persale-stadvertising.getCostprice().doubleValue());
                        stadvertising.setProfit(profit);
                    }
                    /**CPA（广告费用/订单总数）
                     * CPA=花费/7天总销售量**/
                    Integer totalUnit = stadvertising.getTotalUnits();//此处可用双等于（==）
                    if(totalUnit==null||totalUnit==0) stadvertising.setCpa(null);
                    else stadvertising.setCpa(new BigDecimal(Arith.div(stadvertising.getSpend().doubleValue(),totalUnit)).setScale(5,2));
                    /**CPA盈亏(利润值-CPA)
                     * CPA盈亏=利润-CPA
                     */
                    try{
                        stadvertising.setCpaProfit(stadvertising.getProfit().subtract(stadvertising.getCpa()));
                    }catch (Exception e){
                        stadvertising.setCpaProfit(null);
                    }

                    /**设置CVR等级
                     * 0%≤CVR<20%：差=4  20%≤CVR<30%：中=3    30%≤CVR<40%：良=2   CVR≥40%：优=1   **/
                    Float cvr = stadvertising.getCvr();
                    if(cvr==null);
                    else if(cvr<0.2) stadvertising.setCvrLevel(4);
                    else if(cvr>=0.2&&cvr<0.3) stadvertising.setCvrLevel(3);
                    else if(cvr>=0.3&&cvr<0.4) stadvertising.setCvrLevel(2);
                    else stadvertising.setCvrLevel(1);
                    /**设置CTR等级
                     * 0≤CTR<0.2%：差=4  0.2%≤CTR<0.5%：中=3    0.5%≤CTR<1%：良=2   CTR≥1%：优=1   **/
                    Float ctr = stadvertising.getCtr();
                    if(ctr==null) ;
                    else if(ctr<0.02) stadvertising.setCtrLevel(4);
                    else if(ctr>=0.02&&ctr<0.05) stadvertising.setCtrLevel(3);
                    else if(ctr>=0.05&&ctr<0.1) stadvertising.setCtrLevel(2);
                    else stadvertising.setCtrLevel(1);
                    /**设置Acos等级
                     * Acos>50%：差=4  30<Acos≤50%：中=3    20<Acos≤30%：良=2   0<Acos≤20%：优=1   **/
                    Float acos = stadvertising.getAcos();
                    if(acos==null) ;
                    else if(acos>0.5) stadvertising.setAcosLevel(4);
                    else if(acos>0.3&&acos<=0.5) stadvertising.setAcosLevel(3);
                    else if(acos>=0.2&&acos<0.3) stadvertising.setAcosLevel(2);
                    else stadvertising.setAcosLevel(1);
                    /**设置CVR等级
                     * CPA<-2：差=4  '-2<CPA<0：中=3    'CPA=0：良=2   CPA>0：优=1   **/
                    Float cpa = (stadvertising.getCpaProfit()==null)? null: stadvertising.getCpaProfit().floatValue();
                    if(cpa==null) ;
                    else if(cpa<-2) stadvertising.setCpaProfitLevel(4);
                    else if(cpa>-2&&cpa<=0) stadvertising.setCpaProfitLevel(3);
                    else if(cpa==0) stadvertising.setCpaProfitLevel(2);
                    else stadvertising.setCpaProfitLevel(1);

                    /**设置识别码**/
                    stadvertising.setIdentificationCode(stadvertising.getTargeting()+"-"+stadvertising.getCustomerSearchTerm());
                }


                if (s==null&&StringUtils.isNotEmpty(stadvertising.getStoreCode()))
                {
                    stadvertising.setCreateBy(operName);
                    stadvertising.setCreateTime(new Date());
                    this.insertStadvertising(stadvertising);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ stadvertising.getStoreCode()+" 的数据导入成功");
                }
                else if (updateSupport)
                {
                    stadvertising.setUpdateBy(operName);
                    stadvertising.setUpdateTime(new Date());
                    stadvertisingMapper.updateFinanceByOnlyCondition(stadvertising);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ stadvertising.getStoreCode()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、"+ stadvertising.getStoreCode()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、"+ stadvertising.getStoreCode()+" 的数据导入失败：";
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
    public List<StadvertisingAnalyVo> selectStadvertisingAnalyVoList(Stadvertising stadvertising){
        List<Stadvertising> stadvertisingList =  this.selectStadvertisingList(stadvertising);
        List<StadvertisingAnalyVo> stadvertisingAnalyVoList = stadvertisingList.stream().map(sta->{
            StadvertisingAnalyVo vo = new StadvertisingAnalyVo();
            BeanUtils.copyProperties(sta,vo);
            String sku = sta.getSku();
            //判断设置产品类型
            if(StringUtils.isEmpty(sku)){
                vo.setType(null);
            }else{
                char indexStr = sku.charAt(2);
                vo.setType(indexStr=='T'?"硒鼓":"墨盒");
            }
            //凭借link
            vo.setLink("https://www.amazon.com/dp/"+sku);
            if(sta.getCostprice()==null) {
                vo.setProfit(null);
            }else if(sta.getTotalOrders()==null) {
                vo.setProfit(new BigDecimal(0));
            }else {
                //利润=(7天内广告SKU销售额/7天内广告SKU销售量)*(1-15%-2%)-成本
                try {
                   if(sta.getTotalUnits()>0){
                       Float profit =  Arith.div(vo.getAdvertisedSkuSales(),new BigDecimal(vo.getAdvertisedSkuUnits()),2);
                       float cp = Float.valueOf(vo.getCostprice().toString());
                       profit = profit*0.83f-cp;
                       vo.setProfit(new BigDecimal(profit).setScale(2, BigDecimal.ROUND_HALF_UP));
                   }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //CPA=花费/7天总销售量
                try {
                    if(sta.getTotalOrders()>0){
                        Float cpaF = Arith.div(vo.getSpend(),new BigDecimal(vo.getTotalOrders()),2);
                        vo.setCpa(new BigDecimal(cpaF));
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //CPA盈亏=利润-CPA
                if (vo.getProfit()!=null)
                vo.setCpaProfit(vo.getProfit().subtract(vo.getCpa()));
            }

            return vo;
        }).collect(Collectors.toList());
        return stadvertisingAnalyVoList;
    }

    @Override
    public List<StadvertisingAnalyVo> selectStadvertisingAnalyVoList(StadvertisingAnalyVo stadvertisingAnalyVo) {
        Stadvertising stadvertising = new Stadvertising();
        BeanUtils.copyProperties(stadvertisingAnalyVo,stadvertising);
        List<StadvertisingAnalyVo> stadvertisingList = this.selectStadvertisingAnalyVoList(stadvertising);
        return stadvertisingList;
    }

    @Override
    public int selectCount(Stadvertising stadvertising) {
        return stadvertisingMapper.selectCount(stadvertising);
    }
}
