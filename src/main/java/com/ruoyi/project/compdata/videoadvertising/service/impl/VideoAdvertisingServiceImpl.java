package com.ruoyi.project.compdata.videoadvertising.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import com.ruoyi.project.compdata.stadvertising.domain.Stadvertising;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.compdata.videoadvertising.mapper.VideoAdvertisingMapper;
import com.ruoyi.project.compdata.videoadvertising.domain.VideoAdvertising;
import com.ruoyi.project.compdata.videoadvertising.service.IVideoAdvertisingService;
import com.ruoyi.common.utils.text.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 视频广告数据源Service业务层处理
 * 
 * @author Beignet
 * @date 2021-01-29
 */
@Service
public class VideoAdvertisingServiceImpl implements IVideoAdvertisingService 
{
    private static final Logger log = LoggerFactory.getLogger(VideoAdvertising.class);

    @Autowired
    private VideoAdvertisingMapper videoAdvertisingMapper;

    @Autowired
    private IAdvertisingActivityService advertisingActivityService;

    @Autowired
    private ISkuCostpriceService skuCostpriceService;

    /**
     * 查询视频广告数据源
     * 
     * @param id 视频广告数据源ID
     * @return 视频广告数据源
     */
    @Override
    public VideoAdvertising selectVideoAdvertisingById(Long id)
    {
        return videoAdvertisingMapper.selectVideoAdvertisingById(id);
    }

    /**
     * 查询视频广告数据源列表
     * 
     * @param videoAdvertising 视频广告数据源
     * @return 视频广告数据源
     */
    @Override
    public List<VideoAdvertising> selectVideoAdvertisingList(VideoAdvertising videoAdvertising)
    {
        List<VideoAdvertising> videoAdvertisingList = videoAdvertisingMapper.selectVideoAdvertisingList(videoAdvertising);
        for(VideoAdvertising domain:videoAdvertisingList){
            domain.setCtr(MathUtil.float2PercentNum(domain.getCtr()));
            domain.setCvr(MathUtil.float2PercentNum(domain.getCvr()));
            domain.setAcos(MathUtil.float2PercentNum(domain.getAcos()));
        }
        return videoAdvertisingList;
    }

    /**
     * 新增视频广告数据源
     * 
     * @param videoAdvertising 视频广告数据源
     * @return 结果
     */
    @Override
    public int insertVideoAdvertising(VideoAdvertising videoAdvertising)
    {
        videoAdvertising.setCreateTime(DateUtils.getNowDate());
        return videoAdvertisingMapper.insertVideoAdvertising(videoAdvertising);
    }

    /**
     * 修改视频广告数据源
     * 
     * @param videoAdvertising 视频广告数据源
     * @return 结果
     */
    @Override
    public int updateVideoAdvertising(VideoAdvertising videoAdvertising)
    {
        videoAdvertising.setUpdateTime(DateUtils.getNowDate());
        return videoAdvertisingMapper.updateVideoAdvertising(videoAdvertising);
    }

    /**
     * 删除视频广告数据源对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVideoAdvertisingByIds(String ids)
    {
        return videoAdvertisingMapper.deleteVideoAdvertisingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除视频广告数据源信息
     * 
     * @param id 视频广告数据源ID
     * @return 结果
     */
    @Override
    public int deleteVideoAdvertisingById(Long id)
    {
        return videoAdvertisingMapper.deleteVideoAdvertisingById(id);
    }

    /**
     * 导入视频广告数据源
     *
     * @param videoAdvertisingList 视频广告数据源List数据
     * @return 导入结果
     */
    @Override
    public String importVideoAdvertising(List<VideoAdvertising> videoAdvertisingList, boolean isUpdateSupport) {
        //TODO 此方法为模板生成，需要完善，完善后请将此注释删除或修改
        if (StringUtils.isNull(videoAdvertisingList) || videoAdvertisingList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (VideoAdvertising videoAdvertising : videoAdvertisingList)
        {
            try
            {
                // 验证数据是否已经
                VideoAdvertising domain = videoAdvertisingMapper.selectVideoAdvertisingByOnlyCondition(videoAdvertising);

                if(domain==null||isUpdateSupport){//需要保存或更新数据时才填充其他字段
                    /** 从广告活动模块获取基础数据填充 **/
                    AdvertisingActivity advertisingActivityParam = new AdvertisingActivity();
                    advertisingActivityParam.setActivity(videoAdvertising.getCampaignName());
                    AdvertisingActivity AdvertisingActivityRes = advertisingActivityService.selectAdvertisingActivityByOnlyCondition(advertisingActivityParam);
                    if(AdvertisingActivityRes!=null){
                        AdvertisingActivityRes.setStoreCode(videoAdvertising.getStoreCode());
                        BeanUtils.copyProperties(AdvertisingActivityRes,videoAdvertising);
                    }
                    /** 从sku单价模块获取数据填充 **/
                    SkuCostprice skuCostpriceParam = new SkuCostprice();
                    String sku = videoAdvertising.getSku();
                    if(StringUtils.isNotEmpty(sku)){
                        skuCostpriceParam.setSku(videoAdvertising.getSku());
                        SkuCostprice skuCostprice = skuCostpriceService.selectSkuCostpriceByOnlyCondition(skuCostpriceParam);
                        videoAdvertising.setCostprice(skuCostprice==null?null:skuCostprice.getCostprice());
                        /**设置产品类型**/
                        char indexStr = sku.charAt(2);
                        videoAdvertising.setType(indexStr=='T'?"硒鼓":"墨盒");
                    }
                    /**设置link**/
                    videoAdvertising.setLink("http://www.amazon.com/dp/"+videoAdvertising.getAsin());
                    /**计算设置利润（不计算广告点数，只计算平台佣金，税费，不良率）
                     * 利润=(14天总销售额/14天总订单数)*(1-15%-2%)-成本**/
                    Long totalOrders = videoAdvertising.getTotalOrders();
                    BigDecimal costPrice = videoAdvertising.getCostprice();
                    if(totalOrders==null||totalOrders==0||costPrice==null) videoAdvertising.setProfit(null);
                    else {
                        double persale = Arith.div(videoAdvertising.getTotalSales().doubleValue(),totalOrders,2);
                        persale =  persale*0.83;//-stadvertising.getCostprice().doubleValue();
                        BigDecimal profit = new BigDecimal(persale-videoAdvertising.getCostprice().doubleValue());
                        videoAdvertising.setProfit(profit);
                    }
                    /**CPA（广告费用/订单总数）
                     * CPA=花费/14天总销售量**/
                    Long totalUnit = videoAdvertising.getTotalUnits();//此处可用双等于（==）
                    if(totalUnit==null||totalUnit==0) videoAdvertising.setCpa(null);
                    else videoAdvertising.setCpa(new BigDecimal(Arith.div(videoAdvertising.getSpend().doubleValue(),totalUnit)).setScale(5,2));
                    /**CPA盈亏(利润值-CPA)
                     * CPA盈亏=利润-CPA
                     */
                    try{
                        videoAdvertising.setCpaProfit(videoAdvertising.getProfit().subtract(videoAdvertising.getCpa()));
                    }catch (Exception e){
                        videoAdvertising.setCpaProfit(null);
                    }

                    /**设置CVR等级
                     * 0%≤CVR<20%：差=4  20%≤CVR<30%：中=3    30%≤CVR<40%：良=2   CVR≥40%：优=1   **/
                    Float cvr = videoAdvertising.getCvr();
                    if(cvr==null);
                    else if(cvr<0.2) videoAdvertising.setCvrLevel(4);
                    else if(cvr>=0.2&&cvr<0.3) videoAdvertising.setCvrLevel(3);
                    else if(cvr>=0.3&&cvr<0.4) videoAdvertising.setCvrLevel(2);
                    else videoAdvertising.setCvrLevel(1);
                    /**设置CTR等级
                     * 0≤CTR<0.2%：差=4  0.2%≤CTR<0.5%：中=3    0.5%≤CTR<1%：良=2   CTR≥1%：优=1   **/
                    Float ctr = videoAdvertising.getCtr();
                    if(ctr==null) ;
                    else if(ctr<0.02) videoAdvertising.setCtrLevel(4);
                    else if(ctr>=0.02&&ctr<0.05) videoAdvertising.setCtrLevel(3);
                    else if(ctr>=0.05&&ctr<0.1) videoAdvertising.setCtrLevel(2);
                    else videoAdvertising.setCtrLevel(1);
                    /**设置Acos等级
                     * Acos>50%：差=4  30<Acos≤50%：中=3    20<Acos≤30%：良=2   0<Acos≤20%：优=1   **/
                    Float acos = videoAdvertising.getAcos();
                    if(acos==null) ;
                    else if(acos>0.5) videoAdvertising.setAcosLevel(4);
                    else if(acos>0.3&&acos<=0.5) videoAdvertising.setAcosLevel(3);
                    else if(acos>=0.2&&acos<0.3) videoAdvertising.setAcosLevel(2);
                    else videoAdvertising.setAcosLevel(1);
                    /**设置CVR等级
                     * CPA<-2：差=4  '-2<CPA<0：中=3    'CPA=0：良=2   CPA>0：优=1   **/
                    Float cpa = (videoAdvertising.getCpaProfit()==null)? null: videoAdvertising.getCpaProfit().floatValue();
                    if(cpa==null) ;
                    else if(cpa<-2) videoAdvertising.setCpaProfitLevel(4);
                    else if(cpa>-2&&cpa<=0) videoAdvertising.setCpaProfitLevel(3);
                    else if(cpa==0) videoAdvertising.setCpaProfitLevel(2);
                    else videoAdvertising.setCpaProfitLevel(1);

                    /**设置识别码**/
                    videoAdvertising.setIdentificationCode(videoAdvertising.getTargeting()+"-"+videoAdvertising.getCustomerSearchTerm());
                }


                if (domain==null&&StringUtils.isNotEmpty(videoAdvertising.getStoreCode()))
                {
                    videoAdvertising.setCreateBy(operName);
                    videoAdvertising.setCreateTime(new Date());
                    this.insertVideoAdvertising(videoAdvertising);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ videoAdvertising.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    videoAdvertising.setUpdateBy(operName);
                    videoAdvertising.setUpdateTime(new Date());
                    videoAdvertisingMapper.updateVideoAdvertisingByOnlyCondition(videoAdvertising);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + videoAdvertising.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + videoAdvertising.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + videoAdvertising.toString()+" 的数据导入失败：";
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
