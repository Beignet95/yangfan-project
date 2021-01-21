package com.ruoyi.project.compdata.advertising.service.impl;

import com.ruoyi.common.exception.BusinessException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.compdata.advertising.domain.Advertising;
import com.ruoyi.project.compdata.advertising.mapper.AdvertisingMapper;
import com.ruoyi.project.compdata.advertising.service.IAdvertisingService;
import com.ruoyi.project.compdata.advertising.vo.AdvertisingAnalyParamVo;
import com.ruoyi.project.compdata.advertising.vo.AdvertisingAnalySearchVo;
import com.ruoyi.project.compdata.advertising.vo.AdvertisingAnalyVo;
import com.ruoyi.project.compdata.advertising.vo.AdvertisingEchartsVo;
import com.ruoyi.project.system.config.service.IConfigService;
import com.ruoyi.project.system.user.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author Beignet
 * @date 2021-01-08
 */
@Service
public class AdvertisingServiceImpl implements IAdvertisingService
{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private AdvertisingMapper advertisingMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public Advertising selectAdvertisingById(Long id)
    {
        return advertisingMapper.selectAdvertisingById(id);
    }

    @Autowired
    private IConfigService configService;

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param advertising 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<Advertising> selectAdvertisingList(Advertising advertising)
    {
        return advertisingMapper.selectAdvertisingList(advertising);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param advertising 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertAdvertising(Advertising advertising)
    {
        return advertisingMapper.insertAdvertising(advertising);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param advertising 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateAdvertising(Advertising advertising)
    {
        return advertisingMapper.updateAdvertising(advertising);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteAdvertisingByIds(String ids)
    {
        return advertisingMapper.deleteAdvertisingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteAdvertisingById(Long id)
    {
        return advertisingMapper.deleteAdvertisingById(id);
    }

//    @Override
//    public String importData4AdVo(List<Advertising> list, Boolean isUpdateSupport) {
//        List<Advertising> adList = list.stream().map(vo->{
//            Advertising advertising = new Advertising();
//            BeanUtils.copyProperties(vo,advertising);
//            advertising.setMonth(vo.getMonth());
//            advertising.setCtr(percentText2Float(vo.getCtrStr()));
//            advertising.setCvr(percentText2Float(vo.getCvrStr()));
//            advertising.setAcos(percentText2Float(vo.getAcosStr()));
//            advertising.setAdvertisingOrderPercentage(percentText2Float(vo.getAdvertisingOrderPercentageStr()));
//            advertising.setAcoas(percentText2Float(vo.getAcoasStr()));
//            advertising.setRefundRate(percentText2Float(vo.getRefundRateStr()));
//            return advertising;
//        }).collect(Collectors.toList());
//        return importData4Advertising(adList,isUpdateSupport);
//    }


    /**
     * 百分比字符串（如99%）转为Float型数据
     * @param percentText
     * @return
     */
    public static Float percentText2Float(String percentText){
        if (percentText==null||percentText.length()<2){ percentText="0%";}
        String numStr = percentText.replaceAll("%","");
        return Float.parseFloat(numStr);
    }
    /**
     * Float型数据转为百分比字符串（如99%）
     * @param floatNum
     * @return
     */
    public String Float2PercentText(Float floatNum){
        if (floatNum==null) floatNum=0f;
        Float centerNum = floatNum*100;
        return centerNum.toString()+"%";
    }


    @Override
    public List<AdvertisingAnalyVo> selectAdExposureClickVo(AdvertisingAnalyParamVo advertisingAnalyParamVo) {
        return advertisingMapper.selectAdvertisingAnaly(advertisingAnalyParamVo);
    }

    @Override
    public AdvertisingEchartsVo selectAdvertisingEchartsVo(AdvertisingAnalyParamVo advertisingAnalyParamVo) {
        List<AdvertisingAnalyVo> adExposureClickVoList = selectAdExposureClickVo(advertisingAnalyParamVo);
        AdvertisingEchartsVo advertisingEchartsVo =  new AdvertisingEchartsVo();

        List<String> months = adExposureClickVoList.stream().map(vo->{
            return vo.getMonth();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMonths(months.toArray(new String[months.size()]));

        List<Long> exposures = adExposureClickVoList.stream().map(vo->{
            return vo.getTotalExposure();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setTotalExposures(exposures.toArray(new Long[exposures.size()]));

        List<Long> clicks = adExposureClickVoList.stream().map(vo->{
            return vo.getTotalClick();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setTotalClicks(clicks.toArray(new Long[clicks.size()]));

        List<Float> methCtrs = adExposureClickVoList.stream().map(vo->{
            if(vo.getMethCtr()==null) return 0f;
            return vo.getMethCtr()*100;
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMethCtrs(methCtrs.toArray(new Float[methCtrs.size()]));

        List<Integer> totalAdvertisingOrders = adExposureClickVoList.stream().map(vo->{
            return vo.getTotalAdvertisingOrder();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setTotalAdvertisingOrders(totalAdvertisingOrders.toArray(new Integer[totalAdvertisingOrders.size()]));

        List<Float> methCvrs = adExposureClickVoList.stream().map(vo->{
            if(vo.getMethCvr()==null) return 0f;
            return vo.getMethCvr()*100;
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMethCvrs(methCvrs.toArray(new Float[methCvrs.size()]));

        List<Integer> maxSessionses = adExposureClickVoList.stream().map(vo->{
            return vo.getMaxSessions();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMaxSessionses(maxSessionses.toArray(new Integer[maxSessionses.size()]));

        List<Float> maxCvrs = adExposureClickVoList.stream().map(vo->{
            if(vo.getMaxCvr()==null) return 0f;
            return vo.getMaxCvr()*100;
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMaxCvrs(maxCvrs.toArray(new Float[maxCvrs.size()]));

        List<Integer> totalAdvertisingSpends = adExposureClickVoList.stream().map(vo->{
            return vo.getTotalAdvertisingSpend();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setTotalAdvertisingSpends(totalAdvertisingSpends.toArray(new Integer[totalAdvertisingSpends.size()]));

        List<BigDecimal> totalSaleses = adExposureClickVoList.stream().map(vo->{
            return vo.getTotalSales();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setTotalSaleses(totalSaleses.toArray(new BigDecimal[totalSaleses.size()]));

        List<BigDecimal> totalAdvertisingSaleses = adExposureClickVoList.stream().map(vo->{
            return vo.getTotalAdvertisingSales();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setTotalAdvertisingSaleses(totalAdvertisingSaleses.toArray(new BigDecimal[totalAdvertisingSaleses.size()]));

        List<Float> methAcoses = adExposureClickVoList.stream().map(vo->{
            if(vo.getMethAcos()==null) return 0f;
            return vo.getMethAcos()*100;
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMethAcoses(methAcoses.toArray(new Float[methAcoses.size()]));

        List<Float> methCpcs = adExposureClickVoList.stream().map(vo->{
            if(vo.getMethCpc()==null) return 0f;
            return vo.getMethCpc();
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMethCpcs(methCpcs.toArray(new Float[methCpcs.size()]));

        List<Float> methAcoases = adExposureClickVoList.stream().map(vo->{
            if(vo.getMethAcoas()==null) return 0f;
            return vo.getMethAcoas()*100;
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMethAcoases(methAcoases.toArray(new Float[methAcoases.size()]));

        List<Float> methRefundRates = adExposureClickVoList.stream().map(vo->{
            if(vo.getMethRefundRate()==null) return 0f;
            return vo.getMethRefundRate()*100;
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMethRefundRates(methRefundRates.toArray(new Float[methRefundRates.size()]));

        List<Float> methAdvertisingOrderPercentages = adExposureClickVoList.stream().map(vo->{
            if(vo.getMethAdvertisingOrderPercentage()==null) return 0f;
            return vo.getMethAdvertisingOrderPercentage()*100;
        }).collect(Collectors.toList());
        advertisingEchartsVo.setMethAdvertisingOrderPercentages(methAdvertisingOrderPercentages.toArray(new Float[methAdvertisingOrderPercentages.size()]));

        return advertisingEchartsVo;
    }

    @Override
    public void updateAdvertisingByOnlyCondition(Advertising vo) {
        advertisingMapper.updateAdvertisingByOnlyCondition(vo);
    }

    @Override
    public AdvertisingAnalySearchVo selectAdvertisingAnalySearchVo(AdvertisingAnalyParamVo advertisingAnalyParamVo) {
        List<String> types = advertisingMapper.selectDistinctColumn("type",advertisingAnalyParamVo);
        List<String> storeCodes = advertisingMapper.selectDistinctColumn("store_code",advertisingAnalyParamVo);
        List<String> asins = advertisingMapper.selectDistinctColumn("asin",advertisingAnalyParamVo);
        List<String> skus = advertisingMapper.selectDistinctColumn("sku",advertisingAnalyParamVo);
        AdvertisingAnalySearchVo advertisingAnalySearchVo = new AdvertisingAnalySearchVo();
        advertisingAnalySearchVo.setTypes(types);
        advertisingAnalySearchVo.setStoreCodes(storeCodes);
        advertisingAnalySearchVo.setAsins(asins);
        advertisingAnalySearchVo.setSkus(skus);
        advertisingAnalySearchVo.setSelectType(advertisingAnalyParamVo.getType());
        advertisingAnalySearchVo.setSelectAsin(advertisingAnalyParamVo.getAsin());
        advertisingAnalySearchVo.setSelectStoreCode(advertisingAnalyParamVo.getStoreCode());
        advertisingAnalySearchVo.setSelectSku(advertisingAnalyParamVo.getSku());
        advertisingAnalySearchVo.setChangeSelect(advertisingAnalyParamVo.getChangeSelect());
        return advertisingAnalySearchVo;
    }

    @Override
    public String importData(List<Advertising> list, Boolean isUpdateSupport) {
        if (StringUtils.isNull(list) || list.size() == 0)
        {
            throw new BusinessException("导入广告数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        Date operTime = new Date();
        for (Advertising advertising : list)
        {
            try
            {
                // 验证是否存在这个广告数据
                Advertising advertisingList = advertisingMapper.selectAdvertisingByOnlyCondition(advertising.getStoreCode(),advertising.getAsin(),advertising.getMonth());
                if (advertisingList==null&&StringUtils.isNotEmpty(advertising.getMonth()))
                {
                    advertising.setCreateBy(operName);
                    advertising.setCreateTime(operTime);
                    this.insertAdvertising(advertising);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + advertising.getStoreCode()+"商店"+advertising.getMonth()+"月的"+advertising.getSku() + " sku广告导入成功");
                }
                else if (isUpdateSupport)
                {
                    advertising.setUpdateBy(operName);
                    advertising.setUpdateTime(operTime);
                    this.updateAdvertisingByOnlyCondition(advertising);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + advertising.getStoreCode()+"商店"+advertising.getMonth()+"月的"+advertising.getSku() + " sku广告更新成功");
                }
                else
                {
                    if (StringUtils.isEmpty(advertising.getMonth())) continue;
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + advertising.getStoreCode()+"商店"+advertising.getMonth()+"月的"+advertising.getSku() + " sku广告已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + advertising.getStoreCode()+"商店"+advertising.getMonth()+"月的"+advertising.getSku() + " sku广告导入失败：";
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
