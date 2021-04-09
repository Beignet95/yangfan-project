package com.ruoyi.project.pms.skuCoupon.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.pms.skuCoupon.domain.SkuCoupon;

/**
 * sku与手续费关系Service接口
 * 
 * @author Beignet
 * @date 2021-03-10
 */
public interface ISkuCouponService 
{
    /**
     * 查询sku与手续费关系
     * 
     * @param id sku与手续费关系ID
     * @return sku与手续费关系
     */
    public SkuCoupon selectSkuCouponById(Long id);

    /**
     * 查询sku与手续费关系列表
     * 
     * @param skuCoupon sku与手续费关系
     * @return sku与手续费关系集合
     */
    public List<SkuCoupon> selectSkuCouponList(SkuCoupon skuCoupon);

    /**
     * 新增sku与手续费关系
     * 
     * @param skuCoupon sku与手续费关系
     * @return 结果
     */
    public int insertSkuCoupon(SkuCoupon skuCoupon);

    /**
     * 修改sku与手续费关系
     * 
     * @param skuCoupon sku与手续费关系
     * @return 结果
     */
    public int updateSkuCoupon(SkuCoupon skuCoupon);

    /**
     * 批量删除sku与手续费关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSkuCouponByIds(String ids);

    /**
     * 删除sku与手续费关系信息
     * 
     * @param id sku与手续费关系ID
     * @return 结果
     */
    public int deleteSkuCouponById(Long id);

    /**
     * 导入sku与手续费关系
     *
     * @param ids 需要导入的数据List
     * @return 结果
     */
    public String importSkuCoupon(List<SkuCoupon> skuCouponList, boolean isUpdateSupport);

    /**
     * 通过地区代码，获取到Coupu与sku的映射关系
     * @param areaCode
     * @return
     */
    Map<String, String> getCouponSkuMap();
}
