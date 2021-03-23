package com.ruoyi.project.pms.skuCoupon.service.impl;

import java.util.*;

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
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.ShiroUtils;
import com.ruoyi.project.pms.skuCoupon.mapper.SkuCouponMapper;
import com.ruoyi.project.pms.skuCoupon.domain.SkuCoupon;
import com.ruoyi.project.pms.skuCoupon.service.ISkuCouponService;
import com.ruoyi.common.utils.text.Convert;

/**
 * sku与手续费关系Service业务层处理
 * 
 * @author Beignet
 * @date 2021-03-10
 */
@Service
public class SkuCouponServiceImpl implements ISkuCouponService 
{
    private static final Logger log = LoggerFactory.getLogger(SkuCoupon.class);

    @Autowired
    private SkuCouponMapper skuCouponMapper;

    /**
     * 查询sku与手续费关系
     * 
     * @param id sku与手续费关系ID
     * @return sku与手续费关系
     */
    @Override
    public SkuCoupon selectSkuCouponById(Long id)
    {
        return skuCouponMapper.selectSkuCouponById(id);
    }

    /**
     * 查询sku与手续费关系列表
     * 
     * @param skuCoupon sku与手续费关系
     * @return sku与手续费关系
     */
    @Override
    public List<SkuCoupon> selectSkuCouponList(SkuCoupon skuCoupon)
    {
        return skuCouponMapper.selectSkuCouponList(skuCoupon);
    }

    /**
     * 新增sku与手续费关系
     * 
     * @param skuCoupon sku与手续费关系
     * @return 结果
     */
    @Override
    public int insertSkuCoupon(SkuCoupon skuCoupon)
    {
        ProductinfoRelation pr = new ProductinfoRelation(null,null,null,null,skuCoupon.getSku());
        if(!productinfoRelationService.checkProductinfoRelation(pr))
            throw new BusinessException("产品信息中没有标准SKU为 "+skuCoupon.getSku()+" 的数据！请完善产品信息！");
        skuCoupon.setCreateTime(DateUtils.getNowDate());
        return skuCouponMapper.insertSkuCoupon(skuCoupon);
    }

    /**
     * 修改sku与手续费关系
     * 
     * @param skuCoupon sku与手续费关系
     * @return 结果
     */
    @Override
    public int updateSkuCoupon(SkuCoupon skuCoupon)
    {
        skuCoupon.setUpdateTime(DateUtils.getNowDate());
        return skuCouponMapper.updateSkuCoupon(skuCoupon);
    }

    /**
     * 删除sku与手续费关系对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSkuCouponByIds(String ids)
    {
        return skuCouponMapper.deleteSkuCouponByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除sku与手续费关系信息
     * 
     * @param id sku与手续费关系ID
     * @return 结果
     */
    @Override
    public int deleteSkuCouponById(Long id)
    {
        return skuCouponMapper.deleteSkuCouponById(id);
    }

    /**
     * 导入sku与手续费关系
     *
     * @param skuCouponList sku与手续费关系List数据
     * @return 导入结果
     */
    @Override
    public String importSkuCoupon(List<SkuCoupon> skuCouponList, boolean isUpdateSupport) {
        if (StringUtils.isNull(skuCouponList) || skuCouponList.size() == 0)
        {
            throw new BusinessException("导入数据不能为空！");
        }

        checkProductinfoRelation(skuCouponList);

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String operName = ShiroUtils.getLoginName();
        for (SkuCoupon skuCoupon : skuCouponList)
        {
            try
            {
                if(StringUtils.isEmpty(skuCoupon.getCouponTitle())&&StringUtils.isEmpty(skuCoupon.getSku()))
                    continue;
                // 验证数据是否已经
                SkuCoupon domain = skuCouponMapper.selectSkuCouponByOnlyCondition(skuCoupon);
                if (domain==null)
                {
                    skuCoupon.setCreateBy(operName);
                    skuCoupon.setCreateTime(new Date());
                    this.insertSkuCoupon(skuCoupon);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、"+ skuCoupon.toString()+" 的数据导入成功");
                }
                else if (isUpdateSupport)
                {
                    skuCoupon.setUpdateBy(operName);
                    skuCoupon.setUpdateTime(new Date());
                    skuCouponMapper.updateSkuCouponByOnlyCondition(skuCoupon);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、" + skuCoupon.toString()+" 的数据更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、" + skuCoupon.toString()+" 的数据已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、" + skuCoupon.toString()+" 的数据导入失败：";
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


    private void checkProductinfoRelation(List<SkuCoupon> skuCouponList) {
        List<ProductinfoRelation> prList = productinfoRelationService.selectProductinfoRelationList(null);
        Map<String, ProductinfoRelation> skuPrMap = prList.stream()
                .collect(Collectors.toMap(ProductinfoRelation::getSku, Function.identity(), (key1, key2) -> key2));
        Set<String> skuCot = new LinkedHashSet();
        for(SkuCoupon skuCoupon:skuCouponList){
            String sku = skuCoupon.getSku();
            if(StringUtils.isNotEmpty(sku)&&!skuPrMap.containsKey(sku)) skuCot.add(sku);
        }
        if(skuCot.size()>0){
            StringBuilder warnMsg = new StringBuilder();
            warnMsg.append("以下SKU缺少产品信息关系！");
            for(String sku:skuCot){
                warnMsg.append("<br/>"+sku);
            }
            throw new BusinessException(warnMsg.toString());
        }
    }

    @Override
    public Map<String, String> getCouponSkuMap() {
        List<SkuCoupon> skuCouponList = skuCouponMapper.selectSkuCouponList(null);
        Map<String,String> map = skuCouponList.stream().collect(Collectors.toMap(SkuCoupon::getCouponTitle,SkuCoupon::getSku));
        return map;
    }
}
