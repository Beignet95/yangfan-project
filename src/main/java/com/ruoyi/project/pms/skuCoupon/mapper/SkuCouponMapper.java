package com.ruoyi.project.pms.skuCoupon.mapper;

import java.util.List;
import com.ruoyi.project.pms.skuCoupon.domain.SkuCoupon;

/**
 * sku与手续费关系Mapper接口
 * 
 * @author Beignet
 * @date 2021-03-10
 */
public interface SkuCouponMapper 
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
     * 删除sku与手续费关系
     * 
     * @param id sku与手续费关系ID
     * @return 结果
     */
    public int deleteSkuCouponById(Long id);

    /**
     * 批量删除sku与手续费关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSkuCouponByIds(String[] ids);

    /**
     * 通过除ID外的唯一条件更新${subTable.functionName}信息
     *
     * @param SkuCoupon ${subTable.functionName}
     * @return 结果
     */
    public int updateSkuCouponByOnlyCondition(SkuCoupon skuCoupon);

    /**
     * 通过除ID外的唯一条件查询${subTable.functionName}信息
     *
     * @param SkuCoupon ${subTable.functionName}
     * @return ${subTable.functionName}对象
     */
    public SkuCoupon selectSkuCouponByOnlyCondition(SkuCoupon skuCoupon);
}
